package com.example.myapplication;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends IntentService {
    private static final String TAG = MainActivity.class.getSimpleName();

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public MyService(String name) {
        super(name);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }

    /**
     * 可被绑定者调用的服务里的方法
     */
    private void MyServiceMethod(String str) {
        Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
    }

    private class MyBinder extends Binder implements IBindMethod{
        @Override
        public void doSomeThing(String from) {
            MyServiceMethod(from);
        }
    }
}
