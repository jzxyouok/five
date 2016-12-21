package com.nzwlkj.fiveakitchen.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.nzwlkj.fiveakitchen.R;
import com.nzwlkj.fiveakitchen.application.CustomApplication;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CookPostActivity extends Activity implements View.OnClickListener{

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //private static final String Url = "http://169.254.4.219/FiveA/findcook/addapp.do";
    private static final String Url = "http://119.29.253.40/FiveA/findcook/addapp.do";
    final private int DATE_PICKER_ID = 1;
    final private int TIME_PICKER_ID = 2;
    final private int ORDER_RESULT_SUCCESS = 0;
    final private int ORDER_RESULT_FAIL = 1;

    private CustomApplication app;

    private Bundle bundle;
    private ImageView cookImg;
    private TextView cookName;
    private TextView cookInfo;
    private TextView cookFrom;
    private TextView cookPrice;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvCity;
    private EditText etPeopleName;
    private EditText etPeoplePhone;
    private EditText etPeopleBeizhu;
    private EditText etPeopleStreet;
    private Button btnReset;
    private Button btnSubmit;

    private String blCookName;
    private String blCookInfo;
    private String blCookFrom;
    private String blCookPrice;
    private String blCookImgURL;
    private String blAddress;

    private int showYear;
    private int showMonth;
    private int showDay;
    private int showHour;
    private int showMinute;

    private String submitPeopleName;
    private String submitPeoplePhone;
    private String submitPeopleBeizhu;
    private String submitPeopleCity;
    private String submitPeopleStreet;
    private String submitDateTime;
    private String submitPeopleAddress;

    private Calendar c;
    private int orderYear;
    private int orderMonth;
    private int orderDay;

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int numTest = msg.what;
            if (numTest == ORDER_RESULT_SUCCESS) {
                Toast.makeText(CookPostActivity.this, R.string.order_success, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CookPostActivity.this,OrderSuccessActivity.class);
                CookPostActivity.this.finish();
                startActivity(intent);
            } else if (numTest == ORDER_RESULT_FAIL) {
                Toast.makeText(CookPostActivity.this, R.string.order_fail, Toast.LENGTH_LONG).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cook_post);
        initData();
        initView();

    }

    private void initView() {

        cookImg = (ImageView) findViewById(R.id.iv_cook_post_img);
        cookName = (TextView) findViewById(R.id.tv_cook_post_name);
        cookInfo = (TextView) findViewById(R.id.tv_cook_post_info);
        cookFrom = (TextView) findViewById(R.id.tv_cook_post_from);
        cookPrice = (TextView) findViewById(R.id.tv_cook_post_price);
        tvCity = (TextView)findViewById(R.id.tv_chose_city);
        etPeopleStreet = (EditText) findViewById(R.id.et_cook_people_street);
        etPeopleName = (EditText) findViewById(R.id.et_cook_people_name);
        etPeoplePhone = (EditText) findViewById(R.id.et_cook_people_phone);
        etPeopleBeizhu = (EditText) findViewById(R.id.et_cook_people_beizhu);

        btnSubmit = (Button) findViewById(R.id.btn_subimt_order);
        btnReset = (Button) findViewById(R.id.btn_reset_order);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvDate.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        tvCity.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        setCookMessage();

    }

    private void initData() {

        bundle = getIntent().getExtras();
        blCookName = bundle.getString("cookName");
        blCookInfo = bundle.getString("cookInfo");
        blCookFrom = bundle.getString("cookFrom_gs");
        blCookPrice = bundle.getString("cookPrice");
        blCookImgURL = bundle.getString("cookImgs");
        //由城市选择器提交的bundle
        blAddress = bundle.getString("city");
        setApplicationData();

    }

    private void setApplicationData() {

        app = (CustomApplication) getApplication();
        app.setApplicationCookName(blCookName);
        app.setApplicationCookInfo(blCookInfo);
        app.setApplicationCookFrom(blCookFrom);
        app.setApplicationCookPrice(blCookPrice);
        app.setApplicationCookImgURL(blCookImgURL);

    }

    private void setCookMessage() {

        int dd =  blCookName.length();

        if (blCookName != null) {
            cookName.setText(blCookName);
            cookInfo.setText(blCookInfo);
            cookFrom.setText(blCookFrom);
            cookPrice.setText(blCookPrice);
            Picasso.with(this).load(blCookImgURL).into(cookImg);

        } else {

            app = (CustomApplication) getApplication();
            String appCookName = app.getApplicationCookName();
            String appCookInfo = app.getApplicationCookInfo();
            String appCookFrom = app.getApplicationCookFrom();
            String appCookPrice = app.getApplicationCookPrice();
            String appCookImg = app.getApplicationCookImgURL();

            cookName.setText(appCookName);
            cookInfo.setText(appCookInfo);
            cookFrom.setText(appCookFrom);
            cookPrice.setText(appCookPrice);
            Picasso.with(this).load(appCookImg).into(cookImg);

        }

        setDateTime();
        tvCity.setText(blAddress);
    }

    private void setDateTime() {

        c = Calendar.getInstance();
        showYear = c.get(Calendar.YEAR);
        showMonth = c.get(Calendar.MONTH) + 1;
        showDay = c.get(Calendar.DAY_OF_MONTH);
        showHour = c.get(Calendar.HOUR_OF_DAY);
        showMinute = c.get(Calendar.MINUTE);
        String date = showYear + "/" + (showMonth < 10 ? "0" + showMonth : showMonth) + "/" +
                (showDay < 10 ? "0" + showDay : showDay);
        String time = (showHour < 10 ? "0" + showHour : showHour) + ":" + (showMinute < 10 ? "0" +
                showMinute : showMinute);
        tvDate.setText(date);
        tvTime.setText(time);

    }

    private String orderTime() {

        c = Calendar.getInstance();
        orderYear = c.get(Calendar.YEAR);
        orderMonth = c.get(Calendar.MONTH) + 1;
        orderDay = c.get(Calendar.DAY_OF_MONTH);
        String orderTime = orderYear + "-" + (orderMonth < 10 ? "0" + orderMonth : orderMonth) +
                "-" + (orderDay < 10 ? "0" + orderDay : orderDay);
        return orderTime;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_date:
                showDialog(DATE_PICKER_ID);
                break;
            case R.id.tv_time:
                showDialog(TIME_PICKER_ID);
                break;
            case R.id.tv_chose_city:
                toCityChsoe();
                break;
            case R.id.btn_subimt_order:
                submitOrder(handler);
                break;
            case R.id.btn_reset_order:
                resetOrder();
                break;
        }
    }

    private void toCityChsoe () {
        Intent intent = new Intent(CookPostActivity.this,CityChoseActivity.class);
        startActivity(intent);
    }

    public void submitOrder(final Handler handler) {

        submitPeopleName = etPeopleName.getText().toString();
        submitPeoplePhone = etPeoplePhone.getText().toString();
        submitPeopleBeizhu = etPeopleBeizhu.getText().toString();
        submitPeopleStreet = etPeopleStreet.getText().toString();
        submitPeopleCity = tvCity.getText().toString();
        submitDateTime = tvDate.getText().toString() + " " + tvTime.getText().toString();
        submitPeopleAddress = submitPeopleCity+"/"+submitPeopleStreet;
        if (isEmpty(submitPeopleName) || isEmpty(submitPeoplePhone) || isEmpty(submitDateTime) || isEmpty(submitPeopleStreet)) {
            Toast.makeText(CookPostActivity.this, R.string.post_toast, Toast.LENGTH_LONG).show();
            return;
        }

        new Thread() {

            String json = toJson();
            String postTest = null;


            @Override
            public void run() {
                try {
                    postTest = post(Url, json);
                    if (postTest.equals("success")) {
                        handler.sendEmptyMessage(ORDER_RESULT_SUCCESS);
                    } else {
                        handler.sendEmptyMessage(ORDER_RESULT_FAIL);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();

    }

    private String toJson() {

        JSONObject root = new JSONObject();
        root.put("CookOrderNum", c.getTimeInMillis() + "");//订单号
        root.put("CookOrderTime", orderTime());//下单时间
        root.put("CooKTaoCan", blCookName);//套餐类型
        root.put("CookOrderName", submitPeopleName);//客户姓名
        root.put("CookOrderPhone", submitPeoplePhone);//客户电话
        root.put("CookOrderBeizhu", submitPeopleBeizhu);//客户备注
        root.put("CookEatTime", submitDateTime);//用餐时间
        root.put("CookEatAddress", submitPeopleAddress);//用餐地址
        String jsonString = root.toJSONString();
        return jsonString;

    }


    private String post(String url, String json) throws IOException {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

    private void resetOrder() {

        etPeopleName.setText("");
        etPeoplePhone.setText("");
        etPeopleBeizhu.setText("");
        setDateTime();

    }

    DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            int month = monthOfYear + 1;
            String date = year + "/" + (month < 10 ? "0" + month : month) + "/" + (dayOfMonth < 10 ?
                    "0" + dayOfMonth : dayOfMonth);
            tvDate.setText(date);
        }
    };

    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker timePicker, int hour, int mintue) {
            String time = (hour < 10 ? "0" + hour : hour) + ":" + (mintue < 10 ? "0" + mintue : mintue);
            tvTime.setText(time);
        }
    };

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_PICKER_ID:
                return new DatePickerDialog(this, onDateSetListener, showYear, showMonth - 1, showDay);
            case TIME_PICKER_ID:
                return new TimePickerDialog(this, onTimeSetListener, showHour, showMinute, true);
        }
        return null;
    }

    private boolean isEmpty(String s) {
        return "".equals(s.trim()) || null == s;
    }

}
