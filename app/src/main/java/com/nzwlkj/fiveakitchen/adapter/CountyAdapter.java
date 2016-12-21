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
public class CountyAdapter extends RecyclerView.Adapter<CountyAdapter.myViewHolder> {

    private static final int COUNTY_MESSAGE = 1003;

    private Context context;
    private List<String> listCounty;
    private LinearLayout layout;
    private Handler countyHandler;

    public CountyAdapter(Context contenxt, List<String> listCounty, Handler countyHandler) {
        this.context = contenxt;
        this.listCounty = listCounty;
        this.countyHandler = countyHandler;
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

        String test = listCounty.get(position);
        holder.name.setText(test);

    }

    @Override
    public int getItemCount() {
        return listCounty.size();
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
            String provinceName = listCounty.get(getPosition());
            Map.put("name", provinceName);
            Message message = new Message().obtain();
            message.obj = Map;
            message.what = COUNTY_MESSAGE;
            countyHandler.sendMessage(message);

        }
    }
}


