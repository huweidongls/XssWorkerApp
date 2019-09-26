package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.adapter.PeixunAdapter;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.PeixunBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.StringUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PeixunActivity extends BaseActivity {

    private Context context = PeixunActivity.this;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private PeixunAdapter adapter;
    private List<PeixunBean.ObjBean> mList;

    private String tel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peixun);

        ButterKnife.bind(PeixunActivity.this);
        initData();

    }

    private void initData() {

        ViseHttp.POST(NetUrl.PeixunUrl)
                .addParam("uid", SpUtils.getUid(context))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.optInt("code") == 200){
                                Gson gson = new Gson();
                                PeixunBean bean = gson.fromJson(data, PeixunBean.class);
                                mList = bean.getObj();
                                adapter = new PeixunAdapter(mList);
                                LinearLayoutManager manager = new LinearLayoutManager(context);
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter);
                                tel = bean.getTel();
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

    @OnClick({R.id.rl_back, R.id.rl_kefu})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_kefu:
                if(!StringUtils.isEmpty(tel)){
                    Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));//跳转到拨号界面，同时传递电话号码
                    startActivity(dialIntent);
                }
                break;
        }
    }

}
