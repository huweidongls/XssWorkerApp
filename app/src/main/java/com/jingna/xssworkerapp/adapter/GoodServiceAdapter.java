package com.jingna.xssworkerapp.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.bean.WorkerTypeListBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2019/6/13.
 */

public class GoodServiceAdapter extends RecyclerView.Adapter<GoodServiceAdapter.ViewHolder> {

    private Context context;
    private List<WorkerTypeListBean.ObjBean> data;
    private ClickListener listener;

    public GoodServiceAdapter(List<WorkerTypeListBean.ObjBean> data, ClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_good_service, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(data.get(position).getTypename());
        List<WorkerTypeListBean.ObjBean.TwoBean> list = data.get(position).getTwo();
        GoodServiceItemAdapter itemAdapter = new GoodServiceItemAdapter(list, new GoodServiceItemAdapter.ClickListener() {
            @Override
            public void onItemClick(List<WorkerTypeListBean.ObjBean.TwoBean> l) {
                listener.onItemClick(position, l);
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        holder.rv.setLayoutManager(manager);
        holder.rv.setAdapter(itemAdapter);
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView tvName;
        private RecyclerView rv;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            rv = itemView.findViewById(R.id.rv);
        }
    }

    public interface ClickListener{
        void onItemClick(int pos, List<WorkerTypeListBean.ObjBean.TwoBean> l);
    }

}
