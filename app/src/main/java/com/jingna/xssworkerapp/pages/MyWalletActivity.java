package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.MyWalletBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.SpUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyWalletActivity extends BaseActivity {

    private Context context = MyWalletActivity.this;

    @BindView(R.id.tv_all_money)
    TextView tvAllMoney;
    @BindView(R.id.tv_ke_money)
    TextView tvKeMoney;
    @BindView(R.id.tv_ing_money)
    TextView tvIngMoney;

    private String keMoney = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        ButterKnife.bind(MyWalletActivity.this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        initData();
    }

    private void initData() {

        ViseHttp.POST(NetUrl.myWalletUrl)
                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.myWalletUrl))
                .addParam("uid", SpUtils.getUid(context))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.optInt("code") == 200){
                                Gson gson = new Gson();
                                MyWalletBean bean = gson.fromJson(data, MyWalletBean.class);
                                tvAllMoney.setText(String.format("%.2f", Double.valueOf(bean.getObj().getBalance())));
                                keMoney = bean.getObj().getWithdrawable();
                                tvKeMoney.setText("可提现金额："+String.format("%.2f", Double.valueOf(bean.getObj().getWithdrawable()))+"元");
                                tvIngMoney.setText("结算中："+String.format("%.2f", Double.valueOf(bean.getObj().getSettlement()))+"元");
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

    @OnClick({R.id.rl_back, R.id.rl_tixian, R.id.rl_bank_card, R.id.rl_payment_details})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_tixian:
                intent.setClass(context, MyWalletTixianActivity.class);
                intent.putExtra("ke", keMoney);
                startActivity(intent);
                break;
            case R.id.rl_bank_card:
                intent.setClass(context, BankCardActivity.class);
                intent.putExtra("type", "0");
                startActivity(intent);
                break;
            case R.id.rl_payment_details:
                intent.setClass(context, PaymentDetailsActivity.class);
                startActivity(intent);
                break;
        }
    }

}
