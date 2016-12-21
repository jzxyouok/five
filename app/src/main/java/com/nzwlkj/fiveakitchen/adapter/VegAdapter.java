package com.nzwlkj.fiveakitchen.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nzwlkj.fiveakitchen.R;
import com.nzwlkj.fiveakitchen.activity.VegPostActivity;
import com.nzwlkj.fiveakitchen.pojo.VegBeans;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by WuWenGuang on 2016/9/21.
 */
public class VegAdapter extends RecyclerView.Adapter<VegAdapter.myViewHolder> {

    //private final static String ImgURL = "http://169.254.4.219/FiveA/images/";
    private final static String ImgURL = "http://119.29.253.40/FiveA/images/";

    private Context context;
    private List<VegBeans> lVegBeans;
    private LayoutInflater mInflater;

    public VegAdapter(Context context, List<VegBeans> vegBeans) {
        this.context = context;
        this.lVegBeans = vegBeans;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_veg_layout, null);
        myViewHolder viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        String vegName = lVegBeans.get(position).getName();
        String vegInfo = lVegBeans.get(position).getInfo();
        String vegPrice = lVegBeans.get(position).getPrice() + "";
        String vegImgs = ImgURL + lVegBeans.get(position).getImg();

        holder.tvName.setText(vegName);
        holder.tvInfo.setText(vegInfo);
        holder.tvPrice.setText(vegPrice);
        Picasso.with(context).load(vegImgs).into(holder.inImg);

    }

    @Override
    public int getItemCount() {
        return lVegBeans.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;
        TextView tvInfo;
        TextView tvPrice;
        ImageView inImg;

        public myViewHolder(View itemView) {

            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_veg_show_name);
            tvInfo = (TextView) itemView.findViewById(R.id.tv_veg_show_info);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_veg_show_price);
            inImg = (ImageView) itemView.findViewById(R.id.iv_veg_show_img);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            String vegName = lVegBeans.get(getPosition()).getName();
            String vegInfo = lVegBeans.get(getPosition()).getInfo();
            String vegPrice = lVegBeans.get(getPosition()).getPrice() + "";
            String vegImgs = ImgURL + lVegBeans.get(getPosition()).getImg();


            Intent intent = new Intent(view.getContext(), VegPostActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString("vegName", vegName);
            mBundle.putString("vegInfo", vegInfo);
            mBundle.putString("vegPrice", vegPrice);
            mBundle.putString("vegImgs", vegImgs);
            intent.putExtras(mBundle);
            view.getContext().startActivity(intent);

        }

    }

}


