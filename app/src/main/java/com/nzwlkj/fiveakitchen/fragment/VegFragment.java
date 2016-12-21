package com.nzwlkj.fiveakitchen.fragment;


import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.nzwlkj.fiveakitchen.R;
import com.nzwlkj.fiveakitchen.adapter.VegAdapter;
import com.nzwlkj.fiveakitchen.pojo.VegBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class VegFragment extends Fragment {

    //private final static String mURL = "http://169.254.4.219/FiveA/veg/allVeg.do?format=json";
    private final static String mURL = "http://119.29.253.40/FiveA/veg/allVeg.do?format=json";

    private RecyclerView mRecyclerView;
    private View view;

    public VegFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_veg, container, false);
        initView();
        judgeInternet();
        return view;
    }

    private void initView() {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_veg);
    }

    private void judgeInternet() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().
                getSystemService(getContext().CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info == null) {
            Toast.makeText(getActivity(), R.string.internet, Toast.LENGTH_SHORT).show();
            view.setBackground(getActivity().getResources().getDrawable(R.mipmap.not_internet));

        } else {
            new MyAsync().execute(mURL);
        }
    }

    class MyAsync extends AsyncTask<String, Void, List<VegBeans>> {

        @Override
        protected List<VegBeans> doInBackground(String... strings) {
            return getJSON(strings[0]);
        }

        @Override
        protected void onPostExecute(List<VegBeans> vegBeans) {

            if (vegBeans == null) {
                Toast.makeText(getActivity(), R.string.web_error, Toast.LENGTH_LONG).show();
                return;
            }
            VegAdapter vegAdapter = new VegAdapter(getActivity(), vegBeans);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecyclerView.setAdapter(vegAdapter);
        }
    }

    private List<VegBeans> getJSON(String string) {
        List<VegBeans> listVegBeans = new ArrayList<>();
        try {
            String jsonString = readJSON(string);
            if (jsonString == null) {
                return null;
            }
            VegBeans vegBeans;
            VegBeans mVegBeans;
            try {
                List<VegBeans> lVegCooks = JSONArray.parseArray(jsonString, VegBeans.class);
                for (int i = 0; i < lVegCooks.size(); i++) {
                    mVegBeans = lVegCooks.get(i);
                    vegBeans = new VegBeans();
                    vegBeans.setId(mVegBeans.Id);
                    vegBeans.setName(mVegBeans.Name);
                    vegBeans.setInfo(mVegBeans.Info);
                    vegBeans.setImg(mVegBeans.Img);
                    vegBeans.setPrice(mVegBeans.Price);
                    listVegBeans.add(vegBeans);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listVegBeans;
    }

    private String readJSON(String path) {

        String result = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(path).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                result = response.body().string();
            } else {
                throw new IOException(R.string.internet_error + "" + response);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

}
