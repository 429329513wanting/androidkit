package com.example.uplibrary.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.blankj.utilcode.util.LogUtils;
import com.example.uplibrary.R;
import com.example.uplibrary.VideoActivity;

public class MyService extends Service {

    MyBinder myBinder = new MyBinder();
    long time = 0;
    private String flag = "";
    //倒计时
    Handler handler = new Handler(){

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1){

                time++;
                LogUtils.d("Service"+time+"");
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext());
                builder.setContentTitle("测试");
                builder.setContentText(time+"");
                builder.setWhen(System.currentTimeMillis());
                builder.setSmallIcon(R.mipmap.ic_launcher);
                builder.setProgress(100,(int) time,true);

                Intent intent = new Intent(getApplicationContext(), VideoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplication(),0,intent,PendingIntent.FLAG_CANCEL_CURRENT);
                builder.setContentIntent(pendingIntent);

                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(1,builder.build());

                LogUtils.d("MyFlag==="+flag);


            }

            if (time>=1000000){

                handler.removeMessages(1);
                stopSelf();

            }else {


                Message msgg = handler.obtainMessage(1);
                handler.sendMessageDelayed(msgg,1000);
            }
        }
    };

    public MyService() {

    }

    public class MyBinder extends Binder{


        public MyService getService(){

            return MyService.this;
        }

    }



    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        new Thread(new Runnable() {
            @Override
            public void run() {

                handler.sendEmptyMessage(1);

            }
        }).start();

        return super.onStartCommand(intent, flags, startId);

    }




    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        LogUtils.d(MyService.class.getSimpleName()+"onBind");

        return myBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {

        LogUtils.d(MyService.class.getSimpleName()+"onUnbind");

        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtils.d(MyService.class.getSimpleName()+"onDestroy");
    }

    public void setFlag(String msg){

        this.flag = msg;
    }

}
