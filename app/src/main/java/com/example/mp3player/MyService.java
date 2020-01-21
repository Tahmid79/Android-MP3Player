package com.example.mp3player;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

public class MyService extends Service {

    public static final String CHANNEL_ID = "channel_id";

    public IBinder binder =  new MyBinder() ;
    public MP3Player player = new MP3Player() ;

    @Override
    public int onStartCommand( Intent intent, int flags, int startId ) {

        //createNotificationChannel();
        //buildNotification();

       // notification_handle();
        //ntf();

        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();




        //notification_handle();
        //ntf();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.

        ntf();

        return binder ;
    }

    ////////////////////////////////////////////////////////////////////////////////

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            NotificationChannel serviceChannel =
                    new NotificationChannel(CHANNEL_ID, "MP3PlayerChannel", NotificationManager.IMPORTANCE_HIGH);

            NotificationManager manager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            manager.createNotificationChannel(serviceChannel);

        }
    }


    public void buildNotification(){

        Intent notificationIntent = new Intent(this , MainActivity.class) ;

        PendingIntent pendingIntent
                = PendingIntent.getActivity(this , 0, notificationIntent , 0);

        Notification notification =  new NotificationCompat.Builder(this, CHANNEL_ID)

                .setContentTitle("MP3 Player")
                .setContentText("Sample Text")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon( BitmapFactory.decodeResource(getResources() ,  R.mipmap.ic_launcher_round ))
                .setContentIntent(pendingIntent)
                .build()  ;

        startForeground(1 , notification);


    }

    public void notification_handle( ){


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //startForegroundService(it) ;

            NotificationChannel serviceChannel =
                    new NotificationChannel(CHANNEL_ID, "MP3PlayerChannel", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager =
                    (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            manager.createNotificationChannel(serviceChannel);

        }

        /////////////////////////////////////////////////////////////


        Intent notificationIntent = new Intent(this , MainActivity.class) ;

        PendingIntent pendingIntent
                = PendingIntent.getActivity(this , 0, notificationIntent , 0);

        Notification notification =
         new NotificationCompat.Builder(this, CHANNEL_ID)

                .setContentTitle("MP3 Player")
                .setContentText("Sample Text")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setLargeIcon( BitmapFactory.decodeResource( getResources() ,  R.mipmap.ic_launcher_round ))
                .setContentIntent(pendingIntent)
                .build()  ;

        startForeground(1 , notification ) ;


    }

    public void ntf(){
        NotificationManager manager ;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {


            NotificationChannel serviceChannel =
                    new NotificationChannel(CHANNEL_ID, "MP3PlayerChannel", NotificationManager.IMPORTANCE_DEFAULT);

           manager =(NotificationManager) getSystemService(NOTIFICATION_SERVICE);


            manager.createNotificationChannel(serviceChannel);

        }

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this, CHANNEL_ID) ;


        builder.setContentTitle("MP3 Player") ;
        builder.setContentText("Music") ;
        builder.setSmallIcon(R.drawable.ic_launcher_foreground) ;
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources() , R.drawable.msc));

        Intent intent = new Intent(this , MainActivity.class) ;
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 1001,intent,0) ;
        builder.setContentIntent(pendingIntent) ;

        Notification notification = builder.build() ;

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this) ;
        notificationManagerCompat.notify( 101 , notification);



    }



    public class MyBinder extends Binder{

        MyService getBoundService(){
            return MyService.this ;
        }

    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        stopForeground(false);
        mNotificationManager.cancelAll();
        player.stop();
        stopSelf();

    }

}


