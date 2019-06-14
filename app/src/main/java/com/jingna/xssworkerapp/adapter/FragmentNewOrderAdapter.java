package com.jingna.xssworkerapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.bean.IndexOrderBean;

import java.util.List;

/**
 * Created by Administrator on 2019/5/29.
 */

public class FragmentNewOrderAdapter extends RecyclerView.Adapter<FragmentNewOrderAdapter.ViewHolder> {

    private Context context;
    private List<IndexOrderBean.ObjBean.ListBean> data;

    public FragmentNewOrderAdapter(List<IndexOrderBean.ObjBean.ListBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_new_order, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(data.get(position).getService_type());
        holder.tvTime.setText(data.get(position).getPretime());
        holder.tvAddress.setText(data.get(position).getAddress());
        holder.tvCoupons.setText(data.get(position).getCoupon());
        holder.tvAddtime.setText(data.get(position).getAddtime());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private TextView tvTime;
        private TextView tvAddress;
        private TextView tvCoupons;
        private TextView tvAddtime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvCoupons = itemView.findViewById(R.id.tv_coupons);
            tvAddtime = itemView.findViewById(R.id.tv_addtime);
        }
    }

}
