package com.nzwlkj.fiveakitchen.activity;

/**
 * 三级联动城市选择器
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nzwlkj.fiveakitchen.R;
import com.nzwlkj.fiveakitchen.adapter.CityAdapter;
import com.nzwlkj.fiveakitchen.adapter.CountyAdapter;
import com.nzwlkj.fiveakitchen.adapter.ProvinceAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CityChoseActivity extends Activity implements View.OnClickListener {

    private RecyclerView lvProvince;
    private RecyclerView lvCity;
    private RecyclerView lvCounty;

    private static final int PROVINCE_MESSAGE = 1001;
    private static final int CITY_MESSAGE = 1002;
    private static final int COUNTY_MESSAGE = 1003;

    private TextView tvProvince;
    private TextView tvCity;
    private TextView tvCounty;
    private Button btnBack;
    private Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_chose);
        initView();
        readCityJSON();
        initEvent();
    }

    private void initView() {

        btnBack = (Button) findViewById(R.id.btn_back);
        btnFinish = (Button) findViewById(R.id.btn_finish);
        lvProvince = (RecyclerView) findViewById(R.id.lv_province);
        lvCity = (RecyclerView) findViewById(R.id.lv_city);
        lvCounty = (RecyclerView) findViewById(R.id.lv_county);
        tvProvince = (TextView) findViewById(R.id.tv_city_provice);
        tvCity = (TextView) findViewById(R.id.tv_city_city);
        tvCounty = (TextView) findViewById(R.id.tv_city_county);

    }

    private void initEvent() {

        btnBack.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
    }

    /**
     * 三级联动城市选择器---选择省份
     */
    private void readCityJSON() {

        List<String> listProvince = new ArrayList<>();

        try {
            InputStreamReader isr = new InputStreamReader(getAssets().open("address.json"), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }

            JSONArray array = new JSONArray(builder.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject lan = array.getJSONObject(i);
                String m = lan.getString("name");
                listProvince.add(m);
            }

            ProvinceAdapter provinceAdapter = new ProvinceAdapter(CityChoseActivity.this, listProvince, provinceHandler);
            lvProvince.setLayoutManager(new LinearLayoutManager(this));
            lvProvince.setAdapter(provinceAdapter);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 三级联动城市选择器---选择市区
     *
     * @param string 作用：传入省份，去选择识别该省对应的市区
     */

    private void readCityJSON(String string) {
        List<String> listCity = new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(getAssets().open("address.json"), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }

            JSONArray array = new JSONArray(builder.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject lan = array.getJSONObject(i);
                String nameProvince = lan.getString("name");
                if (nameProvince.equals(string)) {
                    JSONArray jsonCity = lan.getJSONArray("city");
                    for (int j = 0; j < jsonCity.length(); j++) {
                        JSONObject cit = jsonCity.getJSONObject(j);
                        String nameCity = cit.getString("name");
                        listCity.add(nameCity);
                    }
                }
            }

            CityAdapter cityAdapter = new CityAdapter(CityChoseActivity.this, listCity, provinceHandler);
            lvCity.setLayoutManager(new LinearLayoutManager(this));
            lvCity.setAdapter(cityAdapter);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 三级联动城市选择器---选择市区
     *
     * @param province 作用：传入省份，去选择识别该省对应的市区
     * @param city     作用：传入市区，去选择识别该省对应的县
     */
    private void readCityJSON(String province, String city) {
        List<String> listCounty = new ArrayList<>();
        try {
            InputStreamReader isr = new InputStreamReader(getAssets().open("address.json"), "UTF-8");
            BufferedReader br = new BufferedReader(isr);
            String line;
            StringBuilder builder = new StringBuilder();
            while ((line = br.readLine()) != null) {
                builder.append(line);
            }

            JSONArray array = new JSONArray(builder.toString());
            for (int i = 0; i < array.length(); i++) {
                JSONObject lan = array.getJSONObject(i);
                String nameProvince = lan.getString("name");
                if (nameProvince.equals(province)) {
                    JSONArray jsonCity = lan.getJSONArray("city");
                    for (int j = 0; j < jsonCity.length(); j++) {
                        JSONObject cit = jsonCity.getJSONObject(j);
                        String nameCity = cit.getString("name");
                        if (nameCity.equals(city)) {
                            JSONArray jsonCounty = cit.getJSONArray("area");
                            for (int k = 0; k < jsonCounty.length(); k++) {
                                Object object = jsonCounty.get(k);
                                String nameCounty = String.valueOf(object);
                                listCounty.add(nameCounty);
                            }
                        }
                    }
                }
            }

            CountyAdapter countyAdapter = new CountyAdapter(CityChoseActivity.this, listCounty, provinceHandler);
            lvCounty.setLayoutManager(new LinearLayoutManager(this));
            lvCounty.setAdapter(countyAdapter);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    Handler provinceHandler = new Handler() {

        private String snapProvince = null;

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case PROVINCE_MESSAGE:
                    HashMap map = (HashMap) msg.obj;
                    String cityProvince = (String) map.get("name");
                    tvProvince.setText(cityProvince);
                    snapProvince = cityProvince;
                    readCityJSON(cityProvince);
                    break;
                case CITY_MESSAGE:
                    HashMap map1 = (HashMap) msg.obj;
                    String cityName = (String) map1.get("name");
                    tvCity.setText(cityName);
                    readCityJSON(snapProvince, cityName);
                    break;
                case COUNTY_MESSAGE:
                    HashMap map2 = (HashMap) msg.obj;
                    String countyName = (String) map2.get("name");
                    tvCounty.setText(countyName);
                    break;
                default:
                    Toast.makeText(CityChoseActivity.this, "error", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                closeActivity();
                break;
            case R.id.btn_finish:
                getAddressInfo();
                break;
        }
    }

    private void closeActivity() {
        CityChoseActivity.this.finish();
    }

    private void getAddressInfo() {

        String province = tvProvince.getText().toString();
        String city = tvCity.getText().toString();
        String county = tvCounty.getText().toString();

        if (isEmpty(province) || isEmpty(city) || isEmpty(county)) {
            Toast.makeText(CityChoseActivity.this, R.string.post_toast, Toast.LENGTH_LONG).show();
            return;
        }

        String address = province + "/" + city + "/" + county;
        toPostActivity(address);
        closeActivity();

    }

    private void toPostActivity(String address) {

        Intent intent = new Intent(CityChoseActivity.this, CookPostActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("city", address);
        intent.putExtras(bundle);
        startActivity(intent);

    }


    private boolean isEmpty(String s) {
        return "".equals(s.trim()) || null == s;
    }

}
