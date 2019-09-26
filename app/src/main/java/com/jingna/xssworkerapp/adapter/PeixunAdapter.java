package com.jingna.xssworkerapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.bean.PeixunBean;

import java.util.List;

/**
 * Created by Administrator on 2019/9/26.
 */

public class PeixunAdapter extends RecyclerView.Adapter<PeixunAdapter.ViewHolder> {

    private Context context;
    private List<PeixunBean.ObjBean> data;

    public PeixunAdapter(List<PeixunBean.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_peixun, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tvName.setText(data.get(position).getOrganizationname());
        holder.tvTime.setText(data.get(position).getStart_time()+"-"+data.get(position).getEnd_time());
        holder.tvContent.setText(data.get(position).getDescription());
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private TextView tvTime;
        private TextView tvContent;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvContent = itemView.findViewById(R.id.tv_content);
        }
    }

}
