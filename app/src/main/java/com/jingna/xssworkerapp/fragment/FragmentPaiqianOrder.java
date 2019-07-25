package com.jingna.xssworkerapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.adapter.FragmentYijieOrderAdapter;
import com.jingna.xssworkerapp.base.BaseFragment;
import com.jingna.xssworkerapp.base.OrderBaseFragment;
import com.jingna.xssworkerapp.bean.IndexOrderBean;
import com.jingna.xssworkerapp.net.NetUrl;
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

/**
 * Created by Administrator on 2019/5/29.
 */

public class FragmentPaiqianOrder extends OrderBaseFragment {

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.refresh)
    SmartRefreshLayout smartRefreshLayout;

    private FragmentYijieOrderAdapter adapter;
    private List<IndexOrderBean.ObjBean.ListBean> mList;

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_paiqian_order, null);
//
//        ButterKnife.bind(this, view);
//
//        return view;
//    }

    @Override
    public View initView() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_paiqian_order, null);

        ButterKnife.bind(this, view);
        initRefresh();

        return view;
    }

    private void initRefresh() {

        smartRefreshLayout.setRefreshHeader(new MaterialHeader(getContext()));
        smartRefreshLayout.setRefreshFooter(new ClassicsFooter(getContext()));
        smartRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull final RefreshLayout refreshLayout) {
                ViseHttp.POST(NetUrl.indexOrderUrl)
                        .addParam("app_key", getToken(NetUrl.BASE_URL + NetUrl.indexOrderUrl))
                        .addParam("uid", SpUtils.getUid(getContext()))
                        .addParam("type", "2")
                        .addParam("city_id", SpUtils.getCityId(getContext()))
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
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
        smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.finishLoadMore(1000);
            }
        });

    }

//    @Override
//    public void onStart() {
//        super.onStart();
//        initData();
//    }

    @Override
    public void initData() {

        ViseHttp.POST(NetUrl.indexOrderUrl)
                .addParam("app_key", getToken(NetUrl.BASE_URL + NetUrl.indexOrderUrl))
                .addParam("uid", SpUtils.getUid(getContext()))
                .addParam("type", "2")
                .addParam("city_id", SpUtils.getCityId(getContext()))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.optInt("code") == 200) {
                                Gson gson = new Gson();
                                IndexOrderBean bean = gson.fromJson(data, IndexOrderBean.class);
                                mList = bean.getObj().getList();
                                adapter = new FragmentYijieOrderAdapter(mList);
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

    @Override
    public void hide() {

    }

}
