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
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class VegPostActivity extends Activity implements View.OnClickListener{

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    //private static final String Url = "http://169.254.4.219/FiveA/vegorder/addapp.do";
    private static final String Url = "http://119.29.253.40/FiveA/vegorder/addapp.do";
    final private int DATE_PICKER_ID = 1;
    final private int TIME_PICKER_ID = 2;
    final private int ORDER_RESULT_SUCCESS = 0;
    final private int ORDER_RESULT_FAIL = 1;

    private Bundle bundle;
    private String blVegName;
    private String blVegInfo;
    private String blVegPrice;
    private String blVegImgURL;

    private ImageView vegImg;
    private TextView vegName;
    private TextView vegInfo;
    private TextView vegPrice;
    private TextView tvDate;
    private TextView tvTime;
    private EditText etPeopleName;
    private EditText etPeoplePhone;
    private EditText etVegNumber;
    private Button btnReset;
    private Button btnSubmit;

    private int showYear;
    private int showMonth;
    private int showDay;
    private int showHour;
    private int showMinute;
    private Calendar c;
    private int orderYear;
    private int orderMonth;
    private int orderDay;

    private String submitPeopleName;
    private String submitPeoplePhone;
    private int submitVegNumber;
    private String submitDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_veg_post);

        initData();
        initView();
    }

    private void initData() {

        bundle = getIntent().getExtras();
        blVegName = bundle.getString("vegName");
        blVegInfo = bundle.getString("vegInfo");
        blVegPrice = bundle.getString("vegPrice");
        blVegImgURL = bundle.getString("vegImgs");

    }

    private void initView() {

        vegImg = (ImageView) findViewById(R.id.iv_veg_post_img);
        vegName = (TextView) findViewById(R.id.tv_veg_post_name);
        vegInfo = (TextView) findViewById(R.id.tv_veg_post_info);
        vegPrice = (TextView) findViewById(R.id.tv_veg_post_price);
        etVegNumber = (EditText) findViewById(R.id.et_veg_people_number);
        etPeopleName = (EditText) findViewById(R.id.et_veg_people_name);
        etPeoplePhone = (EditText) findViewById(R.id.et_veg_people_phone);
        btnSubmit = (Button) findViewById(R.id.btn_subimt_order);
        btnReset = (Button) findViewById(R.id.btn_reset_order);
        tvDate = (TextView) findViewById(R.id.tv_date);
        tvTime = (TextView) findViewById(R.id.tv_time);
        tvDate.setOnClickListener(this);
        tvTime.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        btnReset.setOnClickListener(this);
        setVegMessage();
    }

    private void setVegMessage() {

        vegName.setText(blVegName);
        vegInfo.setText(blVegInfo);
        vegPrice.setText(blVegPrice);
        Picasso.with(this).load(blVegImgURL).into(vegImg);
        setDateTime();
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

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_date:
                showDialog(DATE_PICKER_ID);
                break;
            case R.id.tv_time:
                showDialog(TIME_PICKER_ID);
                break;
            case R.id.btn_subimt_order:
                submitOrder(handler);
                break;
            case R.id.btn_reset_order:
                resetOrder();
                break;
        }
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

    private void resetOrder() {

        etPeopleName.setText("");
        etPeoplePhone.setText("");
        etVegNumber.setText("");
        setDateTime();

    }

    public void submitOrder(final Handler handler) {

        submitPeopleName = etPeopleName.getText().toString();
        submitPeoplePhone = etPeoplePhone.getText().toString();
        submitVegNumber = Integer.parseInt(etVegNumber.getText().toString());
        submitDateTime = tvDate.getText().toString() + " " + tvTime.getText().toString();

        if (isEmpty(submitPeopleName) || isEmpty(submitPeoplePhone) || isEmpty(submitDateTime)) {
            Toast.makeText(VegPostActivity.this, R.string.post_toast, Toast.LENGTH_LONG).show();
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

    Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            int numTest = msg.what;
            if (numTest == ORDER_RESULT_SUCCESS) {
                Toast.makeText(VegPostActivity.this, R.string.order_success, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(VegPostActivity.this,OrderSuccessActivity.class);
                VegPostActivity.this.finish();
                startActivity(intent);
            } else if (numTest == ORDER_RESULT_FAIL) {
                Toast.makeText(VegPostActivity.this, R.string.order_fail, Toast.LENGTH_LONG).show();
            }
        }
    };

    private boolean isEmpty(String s) {
        return "".equals(s.trim()) || null == s;
    }

    private String toJson() {

        JSONObject root = new JSONObject();
        root.put("VegOrderNum", c.getTimeInMillis() + "");//订单号
        root.put("VegOrderTime", orderTime());//下单时间
        root.put("VegName", blVegName);//所选蔬菜
        root.put("VegPrice",blVegPrice);//蔬菜单价
        root.put("VegNumber",submitVegNumber);//蔬菜数量
        root.put("VegOrderName", submitPeopleName);//客户姓名
        root.put("VegOrderPhone", submitPeoplePhone);//客户电话
        root.put("VegOrderAddress", null);//收货地址
        String jsonString = root.toJSONString();
        return jsonString;

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

    private String post(String url, String json) throws IOException {

        OkHttpClient client = new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder().url(url).post(body).build();
        Response response = client.newCall(request).execute();
        if (response.isSuccessful()) {
            //String result = ;
            return response.body().string();
        } else {
            throw new IOException("Unexpected code " + response);
        }
    }

}
