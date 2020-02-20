package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class IPCTestActivity extends Activity {
    private final String TAG = this.getClass().getSimpleName();

    TextView mTvResult;
    Button mBtnAddPerson;
    EditText mEtMsgContent;
    Button mBtnSendMsg;

    /**
     * 客户端的 Messenger
     */
    @SuppressLint("HandlerLeak")
    Messenger mClientMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            if (msg != null && msg.arg1 == ConfigHelper.MSG_ID_SERVER) {
                if (msg.getData() == null) {
                    return;
                }

                String content = (String) msg.getData().get(ConfigHelper.MSG_CONTENT);
                mTvResult.setText(content);
                Log.d(TAG, "Message from server: " + content);
            }
        }
    });

    //服务端的 Messenger
    private Messenger mServerMessenger;

    private ServiceConnection mMessengerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(final ComponentName name, final IBinder service) {
            mServerMessenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(final ComponentName name) {
            mServerMessenger = null;
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aidl);
        initView();
        bindMessengerService();
    }

    private void initView() {
        mTvResult = findViewById(R.id.tv_result);
        mBtnAddPerson = findViewById(R.id.btn_add_person);
        mEtMsgContent = findViewById(R.id.et_msg_content);
        mBtnSendMsg = findViewById(R.id.btn_send_msg);
        mBtnSendMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMsg();
            }
        });
    }

    private void bindMessengerService() {
        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent, mMessengerConnection, BIND_AUTO_CREATE);
    }


    public void sendMsg() {
        String msgContent = mEtMsgContent.getText().toString();
        msgContent = TextUtils.isEmpty(msgContent) ? "默认消息" : msgContent;

        Message message = Message.obtain();
        message.arg1 = ConfigHelper.MSG_ID_CLIENT;
        Bundle bundle = new Bundle();
        bundle.putString(ConfigHelper.MSG_CONTENT, msgContent);
        message.setData(bundle);
        message.replyTo = mClientMessenger;     //指定回信人是客户端定义的

        try {
            mServerMessenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mMessengerConnection);
    }

}
