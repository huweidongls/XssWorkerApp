package com.jingna.xssworkerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.bean.IndexOrderBean;
import com.jingna.xssworkerapp.pages.OrderDetailsActivity;

import java.util.List;

/**
 * Created by Administrator on 2019/5/29.
 */

public class FragmentYijieOrderAdapter extends RecyclerView.Adapter<FragmentYijieOrderAdapter.ViewHolder> {

    private Context context;
    private List<IndexOrderBean.ObjBean.ListBean> data;

    public FragmentYijieOrderAdapter(List<IndexOrderBean.ObjBean.ListBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_yijie_order, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        holder.tvName.setText(data.get(position).getService_type());
        holder.tvAddtime.setText(data.get(position).getAddtime());
        holder.tvTime.setText(data.get(position).getPretime());
        holder.tvAddress.setText(data.get(position).getAddress());
        holder.tvCoupons.setText(data.get(position).getCoupon());
        final String radio = data.get(position).getRadio();
        if(radio.equals("0")){
            Glide.with(context).load(R.mipmap.weikaishi).into(holder.ivType);
        }else if(radio.equals("1")){
            Glide.with(context).load(R.mipmap.fuwuzhong).into(holder.ivType);
        }else if(radio.equals("2")){
            Glide.with(context).load(R.mipmap.yifuwu).into(holder.ivType);
        }else if(radio.equals("3")){
            Glide.with(context).load(R.mipmap.yiwancheng).into(holder.ivType);
        }else if(radio.equals("4")){
            Glide.with(context).load(R.mipmap.tuikuanzhong).into(holder.ivType);
        }else if(radio.equals("5")){
            Glide.with(context).load(R.mipmap.yituikuan).into(holder.ivType);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(radio.equals("0")||radio.equals("1")||radio.equals("2")||radio.equals("3")){
                    Intent intent = new Intent();
                    intent.setClass(context, OrderDetailsActivity.class);
                    intent.putExtra("id", data.get(position).getId());
                    context.startActivity(intent);
//                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private TextView tvAddtime;
        private TextView tvTime;
        private TextView tvAddress;
        private TextView tvCoupons;
        private ImageView ivType;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvAddtime = itemView.findViewById(R.id.tv_addtime);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvCoupons = itemView.findViewById(R.id.tv_coupons);
            ivType = itemView.findViewById(R.id.iv_type);
        }
    }

}
