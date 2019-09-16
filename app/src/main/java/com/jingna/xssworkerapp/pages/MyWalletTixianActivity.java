package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.StringUtils;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyWalletTixianActivity extends BaseActivity {

    private Context context = MyWalletTixianActivity.this;

    @BindView(R.id.tv_ke_money)
    TextView tvKeMoney;
    @BindView(R.id.et_money)
    EditText etMoney;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.tv_bank_name)
    TextView tvBankName;
    @BindView(R.id.tv_bank_code)
    TextView tvBankCode;

    private String keMoney = "";
    private String bankname = "";
    private String bankcode = "";
    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet_tixian);

        keMoney = getIntent().getStringExtra("ke");
        ButterKnife.bind(MyWalletTixianActivity.this);
        initData();

    }

    private void initData() {

        tvKeMoney.setText("可提现金额"+keMoney+"元");

    }

    @OnClick({R.id.rl_back, R.id.tv_all, R.id.rl_select_bank_card, R.id.btn_tixian})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_all:
                etMoney.setText(keMoney);
                break;
            case R.id.rl_select_bank_card:
                intent.setClass(context, BankCardActivity.class);
                intent.putExtra("type", "1");
                startActivityForResult(intent, 100);
                break;
            case R.id.btn_tixian:
                String money = etMoney.getText().toString();
                double ke = Double.valueOf(keMoney);
                double m = Double.valueOf(money);
                if(StringUtils.isEmpty(id)){
                    ToastUtil.showShort(context, "请选择提现银行卡");
                }else if(StringUtils.isEmpty(money)){
                    ToastUtil.showShort(context, "请输入提现金额");
                }else if(ke == 0){
                    ToastUtil.showShort(context, "可提现金额为0，不能提现");
                }else if(m>ke){
                    ToastUtil.showShort(context, "不能超过可提现金额");
                }else {
                    ViseHttp.POST(NetUrl.cash_WokerUrl)
                            .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.cash_WokerUrl))
                            .addParam("uid", SpUtils.getUid(context))
                            .addParam("cid", id)
                            .addParam("price", money)
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
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == 101){
            bankname = data.getStringExtra("bankname");
            bankcode = data.getStringExtra("bankcode");
            id = data.getStringExtra("id");
            tvBankName.setText(bankname);
            tvBankCode.setText("尾号"+bankcode.substring(bankcode.length()-4, bankcode.length())+"储蓄卡");
            tv.setVisibility(View.GONE);
        }
    }
}
