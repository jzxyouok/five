package com.nzwlkj.fiveakitchen.welcome;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.nzwlkj.fiveakitchen.R;
import com.nzwlkj.fiveakitchen.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends Activity implements ViewPager.OnPageChangeListener{

    private ViewPager vp;
    private ViewPagerAdapter vpAdapter;
    private List<View> views;
    private ImageView[] dots;
    private int[] ids = {R.id.iv1,R.id.iv2,R.id.iv3};
    private Button start_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initViews();
        initDots();
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(this);
        views = new ArrayList<View>();
        views.add(inflater.inflate(R.layout.guide_one, null));
        views.add(inflater.inflate(R.layout.guide_two, null));
        views.add(inflater.inflate(R.layout.guide_three, null));
        vpAdapter = new ViewPagerAdapter(views, this);
        vp = (ViewPager) findViewById(R.id.viewpager);
        vp.setAdapter(vpAdapter);
        start_btn = (Button) views.get(2).findViewById(R.id.start_btn);
        start_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GuideActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        vp.setOnPageChangeListener(this);//回调方法

    }

    private void initDots() {
        dots = new ImageView[views.size()];
        for (int i=0; i<views.size(); i++) {
            dots[i] = (ImageView) findViewById(ids[i]);
        }
    }

    //滑动状态改变的时候调用
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


    }

    //当页面被滑动时调用
    @Override
    public void onPageSelected(int position) {

    }

    //当前新的页面被选中的时候调用
    @Override
    public void onPageScrollStateChanged(int state) {
        for ( int i=0; i<ids.length; i++){
            if(state == i){
                dots[i].setImageResource(R.mipmap.login_point_selected);
            } else {
                dots[i].setImageResource(R.mipmap.login_point);
            }
        }
    }
}
