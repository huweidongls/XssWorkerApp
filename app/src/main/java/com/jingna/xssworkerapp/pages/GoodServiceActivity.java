package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.adapter.GoodServiceAdapter;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.WorkerTypeListBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.Logger;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodServiceActivity extends BaseActivity {

    private Context context = GoodServiceActivity.this;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private GoodServiceAdapter adapter;
    private List<WorkerTypeListBean.ObjBean> mList;

    private Map<String, List<WorkerTypeListBean.ObjBean.TwoBean>> idMap;
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_service);

        ButterKnife.bind(GoodServiceActivity.this);
        initData();

    }

    private void initData() {

        idMap = new LinkedHashMap<>();
        ViseHttp.POST(NetUrl.workerTypeListUrl)
                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.workerTypeListUrl))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.optInt("code") == 200){
                                Gson gson = new Gson();
                                WorkerTypeListBean bean = gson.fromJson(data, WorkerTypeListBean.class);
                                mList = bean.getObj();
                                adapter = new GoodServiceAdapter(mList, new GoodServiceAdapter.ClickListener() {
                                    @Override
                                    public void onItemClick(int pos, List<WorkerTypeListBean.ObjBean.TwoBean> l) {
                                        idMap.put(pos+"", l);
                                    }
                                });
                                LinearLayoutManager manager = new LinearLayoutManager(context);
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

    @OnClick({R.id.rl_back, R.id.rl_save})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_save:
                for (int i = 0; i<mList.size(); i++){
                    if(idMap.get(i+"") != null){
                        List<WorkerTypeListBean.ObjBean.TwoBean> list = idMap.get(i+"");
                        for (int a = 0; a<list.size(); a++){
                            if(list.get(a).getIsSelect() == 1){
                                id = id + list.get(a).getId() + ",";
                            }
                        }
                    }
                }
                id = id.substring(0, id.length()-1);
                Logger.e("123123", id);
                ViseHttp.POST(NetUrl.saveWorkerTypeListUrl)
                        .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.saveWorkerTypeListUrl))
                        .addParam("uid", SpUtils.getUid(context))
                        .addParam("text", id)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.optInt("code") == 200){
                                        ToastUtil.showShort(context, jsonObject.optString("message"));
                                        Intent intent = new Intent();
                                        setResult(101, intent);
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
        }
    }

}
