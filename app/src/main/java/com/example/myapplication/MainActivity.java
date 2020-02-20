package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.severdemo.ISchool;

public class MainActivity extends AppCompatActivity {
    private IBindMethod iBindMethod;
    private ISchool iSchool;

    private Intent intent;

    private TextView schoolText;

    private boolean isBind = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        schoolText = findViewById(R.id.tv_school);
        findViewById(R.id.tv_hello).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, IPCTestActivity.class);
                startActivity(intent);
            }
        });
        schoolText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent();
                intent.setAction("com.example.severdemo.serverService.school");
                intent.setPackage("com.example.severdemo");
                getSchool(schoolText);
            }
        });

//        Intent intent = new Intent(MainActivity.this, ServerService.class);
//        bindService(intent, connection, BIND_AUTO_CREATE);
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            iBindMethod = (IBindMethod) iBinder;
            iBindMethod.doSomeThing("start");
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {

        }
    };


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iSchool = ISchool.Stub.asInterface(service);
            try {
                Log.d("schoolText","schoolName: ==="+iSchool.getSchoolName());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            try {
                schoolText.setText("schoolName:" + iSchool.getSchoolName() + "schoolNum:" + iSchool.getStudentNum() + "  student:" + iSchool.getStudent());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            isBind = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBind = false;
        }
    };

    private void bind() {
        bindService(intent, serviceConnection, Service.BIND_AUTO_CREATE);
    }

    public void getSchool(View view) {
        if (!isBind) {
            bind();
        }
    }
}
