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
import com.nzwlkj.fiveakitchen.activity.CookPostActivity;
import com.nzwlkj.fiveakitchen.pojo.CookBeans;
import com.squareup.picasso.Picasso;

import java.util.List;


/**
 * Created by WuWenGuang on 2016/9/21.
 */
public class CookAdapter extends RecyclerView.Adapter<CookAdapter.myViewHolder> {

    //private final static String ImgURL = "http://169.254.4.219/FiveA/images/";
    private final static String ImgURL = "http://119.29.253.40/FiveA/images/";

    private Context context;
    private List<CookBeans> lCookBeans;
    private LayoutInflater mInflater;

    public CookAdapter(Context context, List<CookBeans> cookBeans) {
        this.context = context;
        this.lCookBeans = cookBeans;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item_cook_layout, null);
        myViewHolder viewHolder = new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {

        String cookName = lCookBeans.get(position).getCook_name();
        String cookInfo = lCookBeans.get(position).getInfo();
        String cookFrom_gs = lCookBeans.get(position).getFrom_gs();
        String cookPrice = lCookBeans.get(position).getPrice() + "";
        String cookImgs = ImgURL + lCookBeans.get(position).getImg();

        holder.tvName.setText(cookName);
        holder.tvInfo.setText(cookInfo);
        holder.tvFrom.setText(cookFrom_gs);
        holder.tvPrice.setText(cookPrice);
        Picasso.with(context).load(cookImgs).into(holder.inImg);

    }

    @Override
    public int getItemCount() {
        return lCookBeans.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvName;
        TextView tvInfo;
        TextView tvFrom;
        TextView tvPrice;
        ImageView inImg;

        public myViewHolder(View itemView) {

            super(itemView);

            tvName = (TextView) itemView.findViewById(R.id.tv_cook_show_name);
            tvInfo = (TextView) itemView.findViewById(R.id.tv_cook_show_info);
            tvFrom = (TextView) itemView.findViewById(R.id.tv_cook_show_from);
            tvPrice = (TextView) itemView.findViewById(R.id.tv_cook_show_price);
            inImg = (ImageView) itemView.findViewById(R.id.iv_cook_show_img);

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            String cookName = lCookBeans.get(getPosition()).getCook_name();
            String cookInfo = lCookBeans.get(getPosition()).getInfo();
            String cookFrom_gs = lCookBeans.get(getPosition()).getFrom_gs();
            String cookPrice = lCookBeans.get(getPosition()).getPrice() + "";
            String cookImgs = ImgURL + lCookBeans.get(getPosition()).getImg();

            Intent intent = new Intent(view.getContext(), CookPostActivity.class);
            Bundle mBundle = new Bundle();
            mBundle.putString("cookName", cookName);
            mBundle.putString("cookInfo", cookInfo);
            mBundle.putString("cookFrom_gs", cookFrom_gs);
            mBundle.putString("cookPrice", cookPrice);
            mBundle.putString("cookImgs", cookImgs);
            intent.putExtras(mBundle);
            view.getContext().startActivity(intent);
        }

    }

}


