package com.jingna.xssworkerapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.bean.WorkerTypeListBean;
import com.jingna.xssworkerapp.util.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/13.
 */

public class GoodServiceItemAdapter extends RecyclerView.Adapter<GoodServiceItemAdapter.ViewHolder> {

    private Context context;
    private List<WorkerTypeListBean.ObjBean.TwoBean> data;
    private ClickListener listener;

    public GoodServiceItemAdapter(List<WorkerTypeListBean.ObjBean.TwoBean> data, ClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_good_service_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(data.get(position).getType().equals("1")){
            holder.tv.setBackgroundResource(R.drawable.bg_3296fa_5dp_bord);
            holder.tv.setTextColor(Color.parseColor("#3296fa"));
        }else if(data.get(position).getType().equals("0")){
            holder.tv.setBackgroundResource(R.drawable.bg_929597_5dp_bord);
            holder.tv.setTextColor(Color.parseColor("#929597"));
        }
        holder.tv.setText(data.get(position).getTypename());
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(data.get(position).getType().equals("0")){
                    holder.tv.setBackgroundResource(R.drawable.bg_3296fa_5dp_bord);
                    holder.tv.setTextColor(Color.parseColor("#3296fa"));
                    data.get(position).setType("1");
                    listener.onItemClick(data);
                }else {
                    holder.tv.setBackgroundResource(R.drawable.bg_929597_5dp_bord);
                    holder.tv.setTextColor(Color.parseColor("#929597"));
                    data.get(position).setType("0");
                    listener.onItemClick(data);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
        }
    }

    public interface ClickListener{
        void onItemClick(List<WorkerTypeListBean.ObjBean.TwoBean> l);
    }

}
