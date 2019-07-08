package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.adapter.MyBankCardAdapter;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.WorkerBankListBean;
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

public class BankCardActivity extends BaseActivity {

    private Context context = BankCardActivity.this;

    @BindView(R.id.rv)
    RecyclerView recyclerView;

    private MyBankCardAdapter adapter;
    private List<WorkerBankListBean.ObjBean> mList;

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card);

        type = getIntent().getStringExtra("type");
        ButterKnife.bind(BankCardActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {

        ViseHttp.POST(NetUrl.worker_bank_listUrl)
                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.worker_bank_listUrl))
                .addParam("uid", SpUtils.getUid(context))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.optInt("code") == 200){
                                Gson gson = new Gson();
                                WorkerBankListBean bean = gson.fromJson(data, WorkerBankListBean.class);
                                mList = bean.getObj();
                                adapter = new MyBankCardAdapter(mList, new MyBankCardAdapter.ClickListener() {
                                    @Override
                                    public void onItemClick(int pos) {
                                        if(type.equals("0")){
                                            Intent intent = new Intent();
                                            intent.setClass(context, RemoveBankCardActivity.class);
                                            intent.putExtra("id", mList.get(pos).getId());
                                            intent.putExtra("bankname", mList.get(pos).getBank_name());
                                            intent.putExtra("bankcode", mList.get(pos).getCode());
                                            startActivity(intent);
                                        }else if(type.equals("1")){
                                            Intent intent = new Intent();
                                            intent.putExtra("id", mList.get(pos).getId());
                                            intent.putExtra("bankname", mList.get(pos).getBank_name());
                                            intent.putExtra("bankcode", mList.get(pos).getCode());
                                            setResult(101, intent);
                                            finish();
                                        }
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

    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }

}
