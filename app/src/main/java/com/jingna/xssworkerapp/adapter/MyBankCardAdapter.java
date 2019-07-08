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
import com.jingna.xssworkerapp.bean.WorkerBankListBean;
import com.jingna.xssworkerapp.pages.InsertBankCardActivity;

import java.util.List;

/**
 * Created by Administrator on 2019/6/19.
 */

public class MyBankCardAdapter extends RecyclerView.Adapter<MyBankCardAdapter.ViewHolder> {

    private Context context;
    private List<WorkerBankListBean.ObjBean> data;
    private ClickListener listener;

    public MyBankCardAdapter(List<WorkerBankListBean.ObjBean> data, ClickListener listener) {
        this.data = data;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_my_bank_card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if(getItemViewType(position) == 1){
            holder.rlBank.setVisibility(View.GONE);
            holder.tvAdd.setVisibility(View.VISIBLE);
        }else {
            holder.rlBank.setVisibility(View.VISIBLE);
            holder.tvAdd.setVisibility(View.GONE);
            holder.tvBankName.setText(data.get(position).getBank_name());
            String code = data.get(position).getCode();
            code = code.substring(code.length()-4, code.length());
            holder.tvCode.setText(code);
        }
        holder.rlBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(position);
            }
        });
        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, InsertBankCardActivity.class);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        if(position == data.size()){
            return 1;
        }else {
            return 0;
        }
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size()+1;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private RelativeLayout rlBank;
        private TextView tvAdd;
        private TextView tvBankName;
        private TextView tvCode;

        public ViewHolder(View itemView) {
            super(itemView);
            rlBank = itemView.findViewById(R.id.rl_bank);
            tvAdd = itemView.findViewById(R.id.tv_add);
            tvBankName = itemView.findViewById(R.id.tv_bank_name);
            tvCode = itemView.findViewById(R.id.tv_code);
        }
    }

    public interface ClickListener{
        void onItemClick(int pos);
    }

}
