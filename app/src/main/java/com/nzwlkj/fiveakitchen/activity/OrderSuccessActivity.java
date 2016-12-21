package com.nzwlkj.fiveakitchen.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.nzwlkj.fiveakitchen.R;

import java.util.Timer;
import java.util.TimerTask;

public class OrderSuccessActivity extends Activity {

    private static final int TIME = 3000;
    private TextView tvSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_success);
        initView();
        initToMain();
    }

    private void initView() {
        tvSecond = (TextView) findViewById(R.id.tv_jump_home);
    }

    private void initToMain() {

        final Intent intent = new Intent(this, MainActivity.class);
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };
        timer.schedule(task, TIME);

    }
}
