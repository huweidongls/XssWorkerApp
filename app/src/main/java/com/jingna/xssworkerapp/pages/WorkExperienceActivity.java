package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.adapter.WorkExperienceAdapter;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.WorkExperienceListBean;
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
import butterknife.OnClick;

public class WorkExperienceActivity extends BaseActivity {

    private Context context = WorkExperienceActivity.this;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private WorkExperienceAdapter adapter;
    private List<WorkExperienceListBean.ObjBean> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_experience);

        ButterKnife.bind(WorkExperienceActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {

        ViseHttp.POST(NetUrl.workExperienceListUrl)
                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.workExperienceListUrl))
                .addParam("uid", SpUtils.getUid(context))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.optInt("code") == 200){
                                Gson gson = new Gson();
                                WorkExperienceListBean bean = gson.fromJson(data, WorkExperienceListBean.class);
                                mList = bean.getObj();
                                adapter = new WorkExperienceAdapter(mList);
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

    @OnClick({R.id.rl_back, R.id.rl_insert})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_insert:
                intent.setClass(context, InsertWorkExperienceActivity.class);
                startActivity(intent);
                break;
        }
    }

}
