package com.nzwlkj.fiveakitchen.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;

import com.nzwlkj.fiveakitchen.R;
import com.nzwlkj.fiveakitchen.fragment.BasicDialogFragment;
import com.nzwlkj.fiveakitchen.fragment.CookFragment;
import com.nzwlkj.fiveakitchen.fragment.HomeFragment;
import com.nzwlkj.fiveakitchen.fragment.VegFragment;

public class MainActivity extends FragmentActivity implements BasicDialogFragment.CallBackValue {

    //Java代码
    private ImageView iv_xiala;
    private Fragment home;
    private Fragment cook;
    private Fragment veg;

    int[] imagesId = new int[]{
            R.mipmap.xiala, R.mipmap.xialanull
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        setSlect(0);
    }

    private void initView() {

        iv_xiala = (ImageView) findViewById(R.id.iv_xiala);

    }

    private void initEvent() {

        iv_xiala.setOnClickListener(listener);

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.iv_xiala:
                    iv_xiala.setImageResource(imagesId[1]);
                    showDialogFragment();
                    break;

            }
        }
    };

    private void showDialogFragment() {
        BasicDialogFragment dialog = new BasicDialogFragment();
        dialog.show(getFragmentManager(), "BasicDialog");
    }

    private void setSlect(int i) {

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        hideFragment(transaction);
        switch (i) {
            case 0:
                iv_xiala.setImageResource(imagesId[0]);
                if (home == null) {
                    home = new HomeFragment();
                    transaction.add(R.id.show_fragment, home);
                } else {
                    transaction.show(home);
                }
                break;
            case 1:
                iv_xiala.setImageResource(imagesId[0]);
                if (cook == null) {
                    cook = new CookFragment();
                    transaction.add(R.id.show_fragment, cook);
                } else {
                    transaction.show(cook);
                }
                break;
            case 2:
                iv_xiala.setImageResource(imagesId[0]);
                if (veg == null) {
                    veg = new VegFragment();
                    transaction.add(R.id.show_fragment, veg);
                } else {
                    transaction.show(veg);
                }
                break;
            case 3:
                iv_xiala.setImageResource(imagesId[0]);
                if (home == null) {
                    home = new HomeFragment();
                    transaction.add(R.id.show_fragment, home);
                } else {
                    transaction.show(home);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {

        if (home != null) {
            transaction.hide(home);
        }
        if (cook != null) {
            transaction.hide(cook);
        }
        if (veg != null) {
            transaction.hide(veg);
        }

    }


    @Override
    public void SendMessageValue(int strValue) {
        setSlect(strValue);
    }
}
