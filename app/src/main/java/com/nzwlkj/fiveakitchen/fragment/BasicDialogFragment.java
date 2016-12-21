package com.nzwlkj.fiveakitchen.fragment;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

import com.nzwlkj.fiveakitchen.R;

/**
 * A simple {@link Fragment} subclass.
 * 菜单类
 */
@SuppressLint("NewApi")
public class BasicDialogFragment extends DialogFragment {

    //Java代码
    private View rootView;
    private ImageButton imgBut_Shangla;
    private Button btn_home;
    private Button btn_cook;
    private Button btn_veg;
    CallBackValue callBackValue;

    //设置Dialog的大小
    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(70,1280);
        //getDialog().getWindow().setLayout(1080, 950);
    }

    //显示位置，在最顶上
    @Override
    public void onStart() {
        super.onStart();
        final DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        final WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.width = dm.widthPixels;
        layoutParams.gravity = Gravity.TOP;
        getDialog().getWindow().setAttributes(layoutParams);
    }


    /**
     * fragment与activity产生关联是  回调这个方法
     */
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //当前fragment从activity重写了回调接口  得到接口的实例化对象
        callBackValue =(CallBackValue) getActivity();
    }

    //设置Dialog的显示方式
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setStyle(DialogFragment.STYLE_NO_TITLE,10);
        setStyle(DialogFragment.STYLE_NO_FRAME, 0);//无边框
    }

    public BasicDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_basic_dialog, container, false);
        initView();
        initEvent();
        return rootView;
    }

    private void initView() {
        imgBut_Shangla = (ImageButton) rootView.findViewById(R.id.imgButShangla);
        btn_home = (Button) rootView.findViewById(R.id.btn_home);
        btn_cook = (Button) rootView.findViewById(R.id.btn_cook);
        btn_veg = (Button) rootView.findViewById(R.id.btn_veg);
    }

    private void initEvent() {

        imgBut_Shangla.setOnClickListener(listener);
        btn_home.setOnClickListener(listener);
        btn_cook.setOnClickListener(listener);
        btn_veg.setOnClickListener(listener);

    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            switch (view.getId()) {
                case R.id.imgButShangla:
                    showHomeAcivity();
                    break;
                case R.id.btn_home:
                    showHome();
                    break;
                case R.id.btn_cook:
                    showCook();
                    break;
                case R.id.btn_veg:
                    showVeg();
                    break;
                default:
                    break;
            }
        }

    };

    private void closeDialog() {
        getActivity().getFragmentManager().beginTransaction().remove(this).commit();

    }

    private void showHomeAcivity() {
        callBackValue.SendMessageValue(3);
        closeDialog();
    }

    private void showHome() {
        callBackValue.SendMessageValue(0);
        closeDialog();
    }

    private void showCook() {
        callBackValue.SendMessageValue(1);
        closeDialog();
    }

    private void showVeg() {
        callBackValue.SendMessageValue(2);
        closeDialog();
    }

    //定义一个回调接口
    public interface CallBackValue{
        public void SendMessageValue(int strValue);
    }

}
