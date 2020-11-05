package com.example.memo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Timer;
import  android.content.Intent;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView text;
    private int second = 5;
    private Handler handler;
    private Runnable runnable;

    Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int flag= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setFlags(flag, flag);
        setContentView(R.layout.activity_welcome);
        initView();

        timer.schedule(task, 1000, 1000);

        //不点击跳过
        handler = new Handler();
        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }

    private void initView() {
        text = findViewById(R.id.text);
        text.setOnClickListener(this);
    }

    TimerTask task = new TimerTask() {

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    second--;
                    text.setText("欢迎回来，点击跳过 " + second);
                    if (second < 0) {
                        timer.cancel();
                        text.setVisibility(View.GONE);
                    }
                }
            });
        }
    };

    //点击跳过
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text:
                Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
                if (runnable != null) {
                    handler.removeCallbacks(runnable);
                }
                break;
            default:
                break;
        }
    }
}