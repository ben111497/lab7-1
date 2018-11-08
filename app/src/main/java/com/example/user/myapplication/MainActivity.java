package com.example.user.myapplication;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity
implements OnClickListener{
    protected Button start;
    protected SeekBar rab2,tur2;
    int rab_count=0,tur_count=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start=(Button)findViewById(R.id.start);
        rab2=(SeekBar)findViewById(R.id.rab2);
        tur2=(SeekBar)findViewById(R.id.tur2);
        start.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(MainActivity.this, "開始", Toast.LENGTH_SHORT).show();
        runThread();
        runAsyncTask();
    }
    private void runThread(){
        rab_count=0;
        new Thread(){
            public void run(){
                do{
                    try{
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    rab_count+=(int)(Math.random()*3);
                    Message msg=new Message();
                    msg.what=1;
                    mHandler.sendMessage(msg);
                }while(rab_count<=100);
            }
        }.start();
    }
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case 1:
                    rab2.setProgress(rab_count);
                    break;
            }
            if(rab_count>=100&&tur_count<100)
                Toast.makeText(MainActivity.this, "兔子完成", Toast.LENGTH_SHORT).show();
        }
    };
    private void runAsyncTask(){
        new AsyncTask<Void,Integer,Boolean>(){
            @Override
            protected  void onPreExecute(){
                super.onPreExecute();
                tur_count=0;
            }
            @Override
            protected Boolean doInBackground(Void... voids){
                do{
                    try{
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    tur_count+=(int)(Math.random()*3);
                    publishProgress(tur_count);
                }while(tur_count<=100);
                return true;
            }
            @Override
            protected  void onProgressUpdate(Integer... values){
                super.onProgressUpdate(values);
                tur2.setProgress(values[0]);
            }
            protected void onPostExecute(Boolean status){
                if(rab_count<100)Toast.makeText(MainActivity.this,"烏龜獲勝",Toast.LENGTH_LONG).show();
            }
        }.execute();
    }
}
