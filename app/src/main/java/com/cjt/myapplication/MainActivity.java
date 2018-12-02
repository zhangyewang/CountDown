package com.cjt.myapplication;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView mTimeTv ;
    private Button mStartCountBtn ;
    private int timeSeconds ;
    private boolean flag = false ;
    Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTimeTv = (TextView) findViewById(R.id.tvTime);
        mStartCountBtn = (Button) findViewById(R.id.startCountBtn);
        timeSeconds = formatTime2Second(0 , 3 ,10) ;
        mTimeTv.setText(formatTime(timeSeconds));
        mStartCountBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.startCountBtn:
            flag = !flag ;
            if(flag) {
                mHandler.postDelayed(timeRun,1000);
                mStartCountBtn.setText("Stop");
            } else{
                mHandler.removeCallbacks(timeRun); // 停止倒计时
                mStartCountBtn.setText("Start");
            }
            break;
        }
    }

    Runnable timeRun = new Runnable() {
        @Override
        public void run() {
            Log.e("CJT","timeRun---run()");
            int tempSec = timeSeconds-- ;  // 全局的秒数减一
            mHandler.postDelayed(this,1000);
            mTimeTv.setText(formatTime(tempSec));

            if(tempSec ==0 ){
                Toast.makeText(MainActivity.this, "时间到！", Toast.LENGTH_SHORT).show();
                mHandler.removeCallbacks(timeRun); // 停止倒计时
                timeSeconds = formatTime2Second(0 ,0 ,10) ;
            }
        }
    };

    private String formatTime(int seconds){
        StringBuffer sb = new StringBuffer();
        sb.append((seconds/60)/60).append(':').append((seconds/60)%60).append(':').append(seconds%60);
        return sb.toString() ;
    }

    private int formatTime2Second(int hour , int minute , int seconds){
        return hour*60*60 + minute*60 + seconds ;
    }
}
