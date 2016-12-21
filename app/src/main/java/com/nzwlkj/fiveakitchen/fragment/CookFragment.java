package com.nzwlkj.fiveakitchen.fragment;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.nzwlkj.fiveakitchen.R;
import com.nzwlkj.fiveakitchen.adapter.CookAdapter;
import com.nzwlkj.fiveakitchen.pojo.CookBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;

public class CookFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View view;
    private RecyclerView mRecycleView;
    //private final static String mURL = "http://169.254.4.219/FiveA/cook/allCook.do?format=json";
    private final static String mURL = "http://119.29.253.40/FiveA/cook/allCook.do?format=json";

    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    ProgressDialog dialog = null;

    public CookFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cook, container, false);
        initView();
        judgeInternet();
        return view;

    }

    private void initView() {
        mRecycleView = (RecyclerView) view.findViewById(R.id.rv);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(this);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        new MyAsync(this).execute(mURL);
    }


    class MyAsync extends AsyncTask<String, Void, List<CookBeans>> {

        private CookFragment cookFragment;

        public MyAsync(CookFragment fragment) {
            this.cookFragment = fragment;
            dialog = new ProgressDialog(getActivity());
        }

        @Override
        protected List<CookBeans> doInBackground(String... strings) {
            return getJsonDatas(strings[0]);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPreExecute() {
            dialog.show(getActivity(), "", "Loding...", true, true);
        }

        @Override
        protected void onPostExecute(List<CookBeans> cookBeans) {

            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            if (cookBeans == null) {
                Toast.makeText(getActivity(), R.string.web_error, Toast.LENGTH_LONG).show();
                return;
            }
            /*****************************************************************************/
            /**********************************dialog无法关闭******************************/
//            dialog.cancel();
//            if (dialog.isShowing()) {
//                dialog.dismiss();
//            }
            CookAdapter cookAdapter = new CookAdapter(getActivity(), cookBeans);
            mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity()));
            mRecycleView.setAdapter(cookAdapter);
            /****************************************************************************/
        }

    }

    private List<CookBeans> getJsonDatas(String string) {
        List<CookBeans> listCookBeans = new ArrayList<>();
        try {
            String jsonString = readJson(string);
            if (jsonString == null) {
                return null;
            }
            CookBeans cookBean;
            CookBeans mCookBean;
            try {
                List<CookBeans> lCookBeans = JSONArray.parseArray(jsonString, CookBeans.class);
                for (int i = 0; i <= lCookBeans.size(); i++) {
                    mCookBean = lCookBeans.get(i);
                    cookBean = new CookBeans();
                    cookBean.setCook_name(mCookBean.Cook_name);
                    cookBean.setInfo(mCookBean.Info);
                    cookBean.setFrom_gs(mCookBean.From_gs);
                    cookBean.setPrice(mCookBean.Price);
                    cookBean.setImg(mCookBean.Img);
                    listCookBeans.add(cookBean);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listCookBeans;
    }

    private String readJson(String path) {
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


    /**
     * 判断网络连接状态
     */
    private void judgeInternet() {
        //获取当前的网络连接服务
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(getContext().CONNECTIVITY_SERVICE);
        //获取活动的网络连接信息
        NetworkInfo info = connMgr.getActiveNetworkInfo();
        //判断
        if (info == null) {
            //当前没有已激活的网络连接（表示用户关闭了数据流量服务，也没有开启WiFi等别的数据服务）
            Toast.makeText(getActivity(), R.string.internet, Toast.LENGTH_LONG).show();
            view.setBackground(getActivity().getResources().getDrawable(R.mipmap.not_internet));
        } else {
            //当前有已激活的网络连接，但是否可用还需判断
            //boolean isAlive = info.isAvailable();
            new MyAsync(this).execute(URL);
        }
    }

}

