package com.jingna.xssworkerapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.bean.BudgetListBean;

import java.util.List;

/**
 * Created by Administrator on 2019/7/11.
 */

public class PaymentDetailsAdapter extends RecyclerView.Adapter<PaymentDetailsAdapter.ViewHolder> {

    private Context context;
    private List<BudgetListBean.ObjBean.ListBean> data;

    public PaymentDetailsAdapter(List<BudgetListBean.ObjBean.ListBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_payment_details, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String type = data.get(position).getType();
        if(type.equals("1")){
            Glide.with(context).load(R.mipmap.shouru).into(holder.iv);
            holder.tvPrice.setText("+ "+data.get(position).getPrice());
        }else if(type.equals("2")){
            Glide.with(context).load(R.mipmap.zhichu).into(holder.iv);
            holder.tvPrice.setText("- "+data.get(position).getPrice());
        }
        holder.tvTitle.setText(data.get(position).getText());
        holder.tvTime.setText(data.get(position).getAddtime());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv;
        private TextView tvTitle;
        private TextView tvTime;
        private TextView tvPrice;

        public ViewHolder(View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.iv);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvPrice = itemView.findViewById(R.id.tv_price);
        }
    }

}
