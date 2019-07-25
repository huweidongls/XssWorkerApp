package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.adapter.JiedanSetItemAdapter;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.OrderAcceptanceSettingsBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JiedanSetActivity extends BaseActivity {

    private Context context = JiedanSetActivity.this;

    @BindView(R.id.iv_default)
    ImageView ivDefault;
    @BindView(R.id.iv_set)
    ImageView ivSet;
    @BindView(R.id.tv_zao)
    TextView tvZao;
    @BindView(R.id.tv_wan)
    TextView tvWan;
    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private String isDefault = "0";
    private String isSet = "1";

    private JiedanSetItemAdapter adapter;
    private List<OrderAcceptanceSettingsBean.ObjBean.TypeBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiedan_set);

        ButterKnife.bind(JiedanSetActivity.this);
        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void initData() {

        ViseHttp.POST(NetUrl.orderAcceptanceSettingsUrl)
                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.orderAcceptanceSettingsUrl))
                .addParam("uid", SpUtils.getUid(context))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.optInt("code") == 200){
                                Gson gson = new Gson();
                                OrderAcceptanceSettingsBean bean = gson.fromJson(data, OrderAcceptanceSettingsBean.class);
                                if(bean.getObj().getGoodmorning().equals("0")){
                                    tvZao.setText("00:00");
                                }else {
                                    tvZao.setText(bean.getObj().getGoodmorning());
                                }
                                if(bean.getObj().getNight().equals("0")){
                                    tvWan.setText("00:00");
                                }else {
                                    tvWan.setText(bean.getObj().getNight());
                                }
                                isDefault = bean.getObj().getIs_default();
                                if(isDefault.equals("0")){
                                    Glide.with(context).load(R.mipmap.true_blue).into(ivDefault);
                                }else if(isDefault.equals("1")){
                                    Glide.with(context).load(R.mipmap.unselect).into(ivDefault);
                                }
                                isSet = bean.getObj().getService_time_radio();
                                if(isSet.equals("0")){
                                    Glide.with(context).load(R.mipmap.off).into(ivSet);
                                }else if(isSet.equals("1")){
                                    Glide.with(context).load(R.mipmap.on).into(ivSet);
                                }
                                mList = bean.getObj().getType();
                                adapter = new JiedanSetItemAdapter(mList, new JiedanSetItemAdapter.ClickListener() {
                                    @Override
                                    public void onInsert() {
                                        Intent intent = new Intent();
                                        intent.setClass(context, GoodServiceActivity.class);
                                        startActivityForResult(intent, 1002);
                                    }
                                });
                                GridLayoutManager manager = new GridLayoutManager(context, 3);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter);
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

    @OnClick({R.id.rl_back, R.id.rl_save, R.id.rl_default, R.id.rl_set, R.id.ll_time})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_save:
                ViseHttp.POST(NetUrl.orderAcceptanceSettingsSaveUrl)
                        .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.orderAcceptanceSettingsSaveUrl))
                        .addParam("uid", SpUtils.getUid(context))
                        .addParam("service_time_radio", isSet)
                        .addParam("goodmorning", tvZao.getText().toString())
                        .addParam("night", tvWan.getText().toString())
                        .addParam("is_default", isDefault)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.optInt("code") == 200){
                                        ToastUtil.showShort(context, jsonObject.optString("message"));
                                        finish();
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
                break;
            case R.id.rl_default:
                if(isDefault.equals("0")){
                    isDefault = "1";
                    Glide.with(context).load(R.mipmap.unselect).into(ivDefault);
                }else if(isDefault.equals("1")){
                    isDefault = "0";
                    Glide.with(context).load(R.mipmap.true_blue).into(ivDefault);
                }
                break;
            case R.id.rl_set:
                if(isSet.equals("0")){
                    isSet = "1";
                    Glide.with(context).load(R.mipmap.on).into(ivSet);
                }else if(isSet.equals("1")){
                    isSet = "0";
                    Glide.with(context).load(R.mipmap.off).into(ivSet);
                }
                break;
            case R.id.ll_time:
                intent.setClass(context, JiedanTimeSetActivity.class);
                startActivityForResult(intent, 1001);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1001){
            if(data != null){
                tvZao.setText(data.getStringExtra("zao"));
                tvWan.setText(data.getStringExtra("wan"));
            }
        }else if(requestCode == 1002){
            initData();
        }
    }

}
