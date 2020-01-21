package com.example.mp3player;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mp3player.MyService.MyBinder ;

import java.io.File;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {

     MyService musicService ;
     ProgressBar pg ;

     private static  final String TAG = "MyService" ;

     public Handler mhandler = new Handler() ;

     public static int Duration  = 1;
     public static int Progress = 0 ;
     public static int progress_status = 1 ;

     public Thread thread ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showtracks();
        startsvc();

        pg = (ProgressBar)findViewById(R.id.progressBar) ;
        pg.setMax(100);

         //showProgress();

    }



    public void showProgress(){

         thread =  new Thread(new Runnable() {
            @Override

            public void run() {

                while (progress_status <=100){

                    android.os.SystemClock.sleep(3);

                    mhandler.post(new Runnable() {
                        @Override
                        public void run() {

                            try{

                                if( musicService.player.getFilePath() != null) {
                                    Duration = musicService.player.getDuration();
                                    Progress = musicService.player.getProgress();

                                    if(Duration==0)
                                        Duration =1 ;

                                String str = "Progress = "+ Integer.toString(Progress) + " Duration =  "+ Integer.toString(Duration) ;
                                    Log.i(TAG ,  str) ;

                                    progress_status =  (int) ( (float)Progress/(float)Duration * 100 ) ;
                                    pg.setProgress(progress_status);

                                }

                            }catch (Exception e){
                                e.printStackTrace();
                            }



                            Log.i(TAG , Integer.toString(progress_status) ) ;

                        }
                    });


                }


            }
        });

         thread.start() ;

    }



    public void showtracks(){

         File musicDir = new File(
                Environment.getExternalStorageDirectory().getPath() + "/Music/");

        File [] files = musicDir.listFiles() ;

        ListView listView = (ListView)findViewById(R.id.listview) ;

        ListAdapter adapter =
                new CustomAdapter(this , files) ;
          // new ArrayAdapter<File>(this , android.R.layout.simple_list_item_1 , files);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                File selectedFile = (File)( parent.getItemAtPosition(position));
                String filepath   = selectedFile.getAbsolutePath()  ;

                if( musicService.player.getFilePath() != null){
                    musicService.player.stop();
                }


                musicService.player.load(filepath);
                showProgress();


            }
        });



    }




    public void startsvc(){

        Intent serviceIntent = new Intent(this ,  MyService.class) ;
        ContextCompat.startForegroundService(this , serviceIntent);
        //startService(serviceIntent) ;
        bindService(serviceIntent , mServiceConnection , Context.BIND_AUTO_CREATE);

    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBinder myBinder = (MyBinder)service ;
            musicService = myBinder.getBoundService() ;

            //Toast.makeText(MainActivity.this, "Service Bound", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection) ;
        Intent intent = new Intent(this , MyService.class) ;
        stopService(intent) ;


    }



    public void playtrack(View view){
        musicService.player.play();
    }

    public void pausetrack(View view){
        musicService.player.pause();
    }




}
