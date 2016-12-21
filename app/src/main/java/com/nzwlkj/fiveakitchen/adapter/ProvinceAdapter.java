package com.nzwlkj.fiveakitchen.adapter;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nzwlkj.fiveakitchen.R;

import java.util.HashMap;
import java.util.List;


/**
 * Created by WuWenGuang on 2016/9/21.
 */
public class ProvinceAdapter extends RecyclerView.Adapter<ProvinceAdapter.myViewHolder> {

    private static final int PROVINCE_MESSAGE = 1001;

    private Context context;
    private List<String> listProvince;
    private LinearLayout layout;
    private Handler provinceHandler;

    public ProvinceAdapter(Context contenxt, List<String> listProvince, Handler provinceHandler) {
        this.context = contenxt;
        this.listProvince = listProvince;
        this.provinceHandler = provinceHandler;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        layout = (LinearLayout) inflater.inflate(R.layout.item_list_layout, null);
        myViewHolder viewHolder = new myViewHolder(layout);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        String test = listProvince.get(position);
        holder.name.setText(test);

    }

    @Override
    public int getItemCount() {
        return listProvince.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView name;
        public myViewHolder(View itemView) {
            super(itemView);
            name = (TextView) layout.findViewById(R.id.tv_cook_item);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            final HashMap<String, String> Map = new HashMap<>();
            String provinceName = listProvince.get(getPosition());
            Map.put("name", provinceName);
            Message message = new Message().obtain();
            message.obj = Map;
            message.what = PROVINCE_MESSAGE;
            provinceHandler.sendMessage(message);

        }
    }
}


