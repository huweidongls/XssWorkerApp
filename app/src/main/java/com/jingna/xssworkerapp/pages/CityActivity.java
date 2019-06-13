package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingna.xssworkerapp.MainActivity;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.adapter.CityAdapter;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.OpenCityListBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.SpUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CityActivity extends BaseActivity {

    private Context context = CityActivity.this;

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.tv_cur_city)
    TextView tvCurCity;

    private CityAdapter adapter;
    private List<OpenCityListBean.ObjBean> mList;

    private String curCity = "";
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        type = getIntent().getIntExtra("type", 0);
        curCity = getIntent().getStringExtra("city");
        ButterKnife.bind(CityActivity.this);
        initData();

    }

    private void initData() {

        tvCurCity.setText(curCity);

        ViseHttp.POST(NetUrl.openCityListUrl)
                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.openCityListUrl))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.optInt("code") == 200){
                                Gson gson = new Gson();
                                OpenCityListBean bean = gson.fromJson(data, OpenCityListBean.class);
                                mList = bean.getObj();
                                adapter = new CityAdapter(mList, new CityAdapter.ClickListener() {
                                    @Override
                                    public void onItemClick(int pos) {
                                        if(type == 1){
                                            SpUtils.setCityId(context, mList.get(pos).getId());
                                            SpUtils.setCityName(context, mList.get(pos).getCity_area());
                                            finish();
                                        }else if(type == 0){
                                            Intent intent = new Intent();
                                            intent.setClass(context, MainActivity.class);
                                            SpUtils.setCityId(context, mList.get(pos).getId());
                                            SpUtils.setCityName(context, mList.get(pos).getCity_area());
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                                LinearLayoutManager manager = new LinearLayoutManager(context){
                                    @Override
                                    public boolean canScrollVertically() {
                                        return false;
                                    }
                                };
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

    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }

}
