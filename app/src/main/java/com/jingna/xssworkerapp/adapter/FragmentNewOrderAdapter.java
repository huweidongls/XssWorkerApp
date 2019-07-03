package com.jingna.xssworkerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.bean.IndexOrderBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.pages.LoginActivity;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.jingna.xssworkerapp.util.TokenUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(data.get(position).getService_type());
        holder.tvTime.setText(data.get(position).getPretime());
        holder.tvAddress.setText(data.get(position).getAddress());
        holder.tvCoupons.setText(data.get(position).getCoupon());
        holder.tvAddtime.setText(data.get(position).getAddtime());
        holder.tvJiedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(SpUtils.getUid(context).equals("0")){
                    Intent intent = new Intent();
                    intent.setClass(context, LoginActivity.class);
                    context.startActivity(intent);
                }else {
                    ViseHttp.POST(NetUrl.ReceiptUrl)
                            .addParam("app_key", TokenUtils.getToken(NetUrl.BASE_URL+NetUrl.ReceiptUrl))
                            .addParam("oid", data.get(position).getId())
                            .addParam("uid", SpUtils.getUid(context))
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String d) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(d);
                                        if(jsonObject.optInt("code") == 200){
                                            ToastUtil.showShort(context, jsonObject.optString("message"));
                                            data.remove(position);
                                            notifyDataSetChanged();
                                        }else {
                                            ToastUtil.showShort(context, jsonObject.optString("message"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
                }
            }
        });
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
        private TextView tvJiedan;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvCoupons = itemView.findViewById(R.id.tv_coupons);
            tvAddtime = itemView.findViewById(R.id.tv_addtime);
            tvJiedan = itemView.findViewById(R.id.tv_jiedan);
        }
    }

}
