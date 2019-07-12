package com.jingna.xssworkerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.adapter.MessageAdapter;
import com.jingna.xssworkerapp.base.BaseFragment;
import com.jingna.xssworkerapp.bean.WorkerMessageListBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.SpUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/29.
 */

public class FragmentMessage extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private MessageAdapter adapter;
    private List<WorkerMessageListBean.ObjBean> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_message, null);

        ButterKnife.bind(this, view);
        initData();

        return view;
    }

    private void initData() {

        ViseHttp.POST(NetUrl.worker_message_listUrl)
                .addParam("uid", SpUtils.getUid(getContext()))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.optInt("code") == 200){
                                Gson gson = new Gson();
                                WorkerMessageListBean bean = gson.fromJson(data, WorkerMessageListBean.class);
                                mList = bean.getObj();
                                adapter = new MessageAdapter(mList);
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

}
