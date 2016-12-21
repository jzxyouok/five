package com.nzwlkj.fiveakitchen.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.nzwlkj.fiveakitchen.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * A simple {@link Fragment} subclass.
 * App首页
 */
public class HomeFragment extends Fragment {

    private View view;
    private ImageView show;

    int[] imagesIds = new int[]{
            R.mipmap.home_img_one, R.mipmap.home_img_two, R.mipmap.home_img_three
    };
    int currentImageId = 0;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);

        show = (ImageView) view.findViewById(R.id.home_img);

        final Handler myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // 如果该消息是本程序所发送的
                if (msg.what == 0x1233) {
                    // 动态地修改所显示的图片
                    int num = currentImageId++ % imagesIds.length;
                    showImg(num);//通过showImg，可以给每张图片设置不同的动画效果
                    //show.setImageResource(imagesIds[num]);//直接设置图片
                }
            }
        };
        // 定义一个计时器，让该计时器周期性地执行指定任务
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                // 发送空消息
                myHandler.sendEmptyMessage(0x1233);
            }
        }, 0, 5000);

        return view;

    }

    private void showImg(int num) {
        switch (num) {
            case 0:
                //移动
                Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.translate);
                animation1.setFillAfter(false);//应用动画结束后的状态
                //show.setImageResource(imagesIds[num]);//设置图片
                show.setBackgroundResource(imagesIds[num]);
                show.startAnimation(animation1);//设置动画
                break;
            case 1:
                //变成透明的
                Animation animation2 = AnimationUtils.loadAnimation(getActivity(),R.anim.alpha);
                animation2.setFillAfter(false);
                //show.setImageResource(imagesIds[num]);
                show.setBackgroundResource(imagesIds[num]);
                show.startAnimation(animation2);
                break;
            case 2:
                //放大缩小的
                Animation animation3 = AnimationUtils.loadAnimation(getActivity(),R.anim.scale);
                animation3.setFillAfter(false);
                //show.setImageResource(imagesIds[num]);
                show.setBackgroundResource(imagesIds[num]);
                show.startAnimation(animation3);
                break;
            default:
                break;
        }
    }

}
