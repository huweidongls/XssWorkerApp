package com.jingna.xssworkerapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.adapter.FragmentNewOrderAdapter;
import com.jingna.xssworkerapp.app.MyApplication;
import com.jingna.xssworkerapp.base.BaseFragment;
import com.jingna.xssworkerapp.bean.IndexOrderBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.pages.CityActivity;
import com.jingna.xssworkerapp.util.Logger;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/5/29.
 */

public class FragmentNewOrder extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_jiedan_type)
    TextView tvJiedanType;
    @BindView(R.id.refresh)
    SmartRefreshLayout smartRefreshLayout;

    private FragmentNewOrderAdapter adapter;
    private List<IndexOrderBean.ObjBean.ListBean> mList;

    private String user_radio = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_order, null);

        ButterKnife.bind(this, view);
        initRefresh();
        initData();

        return view;
    }

    private void initRefresh() {

        smartRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        smartRefreshLayout.setEnableLoadMore(false);
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetUrl.indexOrderUrl)
                        .addParam("app_key", getToken(NetUrl.BASE_URL + NetUrl.indexOrderUrl))
                        .addParam("uid", SpUtils.getUid(getContext()))
                        .addParam("type", "0")
                        .addParam("city_id", SpUtils.getCityId(getContext()))
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    Logger.e("123123", data);
                                    JSONObject jsonObject = new JSONObject(data);
                                    if (jsonObject.optInt("code") == 200) {
                                        Gson gson = new Gson();
                                        IndexOrderBean bean = gson.fromJson(data, IndexOrderBean.class);
                                        mList.clear();
                                        mList.addAll(bean.getObj().getList());
                                        adapter.notifyDataSetChanged();
                                    }
                                    refreshLayout.finishRefresh(1000);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onFail(int errCode, String errMsg) {
                                refreshLayout.finishRefresh(1000);
                            }
                        });
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {

        String[] s = SpUtils.getCityName(getContext()).split("-");
        tvCity.setText(s[1]);

        ViseHttp.POST(NetUrl.indexOrderUrl)
                .addParam("app_key", getToken(NetUrl.BASE_URL + NetUrl.indexOrderUrl))
                .addParam("uid", SpUtils.getUid(getContext()))
                .addParam("type", "0")
                .addParam("city_id", SpUtils.getCityId(getContext()))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.optInt("code") == 200) {
                                Gson gson = new Gson();
                                IndexOrderBean bean = gson.fromJson(data, IndexOrderBean.class);
                                user_radio = bean.getObj().getUser_radio();
                                if (user_radio.equals("0")) {
                                    tvJiedanType.setText("开始接单");
                                    tvJiedanType.setTextColor(Color.parseColor("#3296fa"));
                                } else if (user_radio.equals("1")) {
                                    tvJiedanType.setText("停止接单");
                                    tvJiedanType.setTextColor(Color.parseColor("#A9ABAE"));
                                }
                                mList = bean.getObj().getList();
                                adapter = new FragmentNewOrderAdapter(mList);
                                LinearLayoutManager manager = new LinearLayoutManager(getContext());
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
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

    @OnClick({R.id.rl_tingdan, R.id.rl_city, R.id.tv_jiedan_type})
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_tingdan:

                break;
            case R.id.rl_city:
                String[] s = SpUtils.getCityName(getContext()).split("-");
                intent.setClass(getContext(), CityActivity.class);
                intent.putExtra("city", s[1]);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
            case R.id.tv_jiedan_type:
                if(user_radio.equals("0")){
                    ViseHttp.POST(NetUrl.updateWorkerRadioUrl)
                            .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.updateWorkerRadioUrl))
                            .addParam("uid", SpUtils.getUid(getContext()))
                            .addParam("radio", "1")
                            .addParam("city", SpUtils.getCityId(getContext()))
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.optInt("code") == 200){
                                            ToastUtil.showShort(getContext(), "开始接单");
                                            initData();
                                        }else {
                                            ToastUtil.showShort(getContext(), jsonObject.optString("message"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
                }else if(user_radio.equals("1")){
                    ViseHttp.POST(NetUrl.updateWorkerRadioUrl)
                            .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.updateWorkerRadioUrl))
                            .addParam("uid", SpUtils.getUid(getContext()))
                            .addParam("radio", "0")
                            .addParam("city", SpUtils.getCityId(getContext()))
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.optInt("code") == 200){
                                            ToastUtil.showShort(getContext(), "停止接单");
                                            initData();
                                        }else {
                                            ToastUtil.showShort(getContext(), jsonObject.optString("message"));
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
                break;
        }
    }

}
