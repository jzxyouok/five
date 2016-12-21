package com.nzwlkj.fiveakitchen.welcome;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.nzwlkj.fiveakitchen.R;
import com.nzwlkj.fiveakitchen.activity.MainActivity;

public class WelcomeActivity extends Activity {

    private boolean isFristIn = false;

    private static final int TIME1 = 3000;
    private static final int TIME2 = 0;
    private static final int GO_HOME = 1000;
    private static final int GO_GUIDE = 1001;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case GO_HOME:
                        goHome();
                    break;
                case GO_GUIDE:
                        goGuide();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init();

    }

    private void init(){
        SharedPreferences preferences = getSharedPreferences("nzwlkj",MODE_WORLD_WRITEABLE);
        isFristIn = preferences.getBoolean("isFristIn",true);
        if (!isFristIn) {
            mHandler.sendEmptyMessageDelayed(GO_HOME,TIME1);
        } else {
            mHandler.sendEmptyMessageDelayed(GO_GUIDE,TIME2);
            SharedPreferences.Editor editor  = preferences.edit();
            editor.putBoolean("isFristIn", false);
            editor.commit();
        }
    }

    private void goHome(){
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void goGuide(){
        Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
        startActivity(intent);
        finish();
    }
}
