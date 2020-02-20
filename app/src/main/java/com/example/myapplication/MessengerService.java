package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.Random;

public class MessengerService extends Service {

    private final String TAG = this.getClass().getSimpleName();

    @SuppressLint("HandlerLeak")
    Messenger mMessenger = new Messenger(new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            if (msg != null && msg.arg1 == ConfigHelper.MSG_ID_CLIENT) {
                if (msg.getData() == null) {
                    return;
                }
                String content = (String) msg.getData().get(ConfigHelper.MSG_CONTENT);  //接收客户端的消息
                Log.d(TAG, "Message from client: " + content);

                //回复消息给客户端
                Message replyMsg = Message.obtain();
                replyMsg.arg1 = ConfigHelper.MSG_ID_SERVER;
                Bundle bundle = new Bundle();
                bundle.putString(ConfigHelper.MSG_CONTENT, "听到你的消息了，请说点正经的" + new Random().nextInt(10));
                replyMsg.setData(bundle);

                try {
                    msg.replyTo.send(replyMsg);     //回信
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    });

    @Nullable
    @Override
    public IBinder onBind(final Intent intent) {
        Log.d(TAG, getCurProcessName(this));
        return mMessenger.getBinder();
    }

    String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

}
