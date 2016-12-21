package com.nzwlkj.fiveakitchen.welcome;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by WuWenGuang on 2016/8/18.
 */
public class ViewPagerAdapter extends PagerAdapter {

    private List<View> views;//承接所有的view
    private Context context;

    public ViewPagerAdapter(List<View> views, Context context) {
        this.views = views;
        this.context = context;
    }

    //销毁不需要的View
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    //加载View的方法
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return (view == object);
    }
}
