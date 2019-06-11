package com.jingna.xssworkerapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.bean.WorkExperienceListBean;
import com.jingna.xssworkerapp.dialog.CustomDialog;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.pages.InsertWorkExperienceActivity;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.jingna.xssworkerapp.util.TokenUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Administrator on 2019/6/5.
 */

public class WorkExperienceAdapter extends RecyclerView.Adapter<WorkExperienceAdapter.ViewHolder> {

    private Context context;
    private List<WorkExperienceListBean.ObjBean> data;

    public WorkExperienceAdapter(List<WorkExperienceListBean.ObjBean> data) {
        this.data = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_work_experience, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvName.setText(data.get(position).getCompanyname());
        holder.tvTime.setText(data.get(position).getStart_time()+"-"+data.get(position).getEnd_time());
        holder.tvPrice.setText(data.get(position).getPosition()+" ￥"+data.get(position).getSalary());
        holder.tvContent.setText(data.get(position).getJobdescription());
        holder.ivEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, InsertWorkExperienceActivity.class);
                intent.putExtra("id", data.get(position).getId());
                context.startActivity(intent);
            }
        });
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomDialog customDialog = new CustomDialog(context, "是否删除", new CustomDialog.ClickListener() {
                    @Override
                    public void onSure() {
                        ViseHttp.POST(NetUrl.delete_WorkExperienceUrl)
                                .addParam("app_key", TokenUtils.getToken(NetUrl.BASE_URL+NetUrl.delete_WorkExperienceUrl))
                                .addParam("id", data.get(position).getId())
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

                    @Override
                    public void onCancel() {

                    }
                });
                customDialog.show();
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
        private TextView tvPrice;
        private TextView tvContent;
        private ImageView ivEdit;
        private ImageView ivDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvTime = itemView.findViewById(R.id.tv_time);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvContent = itemView.findViewById(R.id.tv_content);
            ivEdit = itemView.findViewById(R.id.iv_edit);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }
    }

}
