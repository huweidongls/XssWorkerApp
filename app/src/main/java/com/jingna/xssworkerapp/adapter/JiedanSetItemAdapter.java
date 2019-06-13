package com.jingna.xssworkerapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.bean.OrderAcceptanceSettingsBean;

import java.util.List;

/**
 * Created by Administrator on 2019/6/13.
 */

public class JiedanSetItemAdapter extends RecyclerView.Adapter<JiedanSetItemAdapter.ViewHolder> {

    private Context context;
    private List<OrderAcceptanceSettingsBean.ObjBean.TypeBean> data;
    private ClickListener listener;

    public JiedanSetItemAdapter(List<OrderAcceptanceSettingsBean.ObjBean.TypeBean> data, ClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_jiedan_set_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position == data.size()) {
            holder.tvInsert.setVisibility(View.VISIBLE);
            holder.tv.setVisibility(View.GONE);
        }else {
            holder.tvInsert.setVisibility(View.GONE);
            holder.tv.setVisibility(View.VISIBLE);
            holder.tv.setText(data.get(position).getTypename());
        }
        holder.tvInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onInsert();
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size() == 0 ? 1 : data.size() + 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;
        private TextView tvInsert;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv);
            tvInsert = itemView.findViewById(R.id.tv_insert);
        }
    }

    public interface ClickListener{
        void onInsert();
    }

}
