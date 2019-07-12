package com.jingna.xssworkerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.bean.WorkerMessageListBean;
import com.jingna.xssworkerapp.pages.OrderDetailsActivity;

import java.util.List;

/**
 * Created by Administrator on 2019/5/29.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context context;
    private List<WorkerMessageListBean.ObjBean> data;

    public MessageAdapter(List<WorkerMessageListBean.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_message, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(data.get(position).getTitle());
        holder.tvTime.setText(data.get(position).getAddtime());
        holder.tvText.setText(data.get(position).getText());
        holder.rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, OrderDetailsActivity.class);
                intent.putExtra("id", data.get(position).getOid());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvTime;
        private TextView tvText;
        private RelativeLayout rl;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvText = itemView.findViewById(R.id.tv_text);
            rl = itemView.findViewById(R.id.rl);
        }
    }

}
