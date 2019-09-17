package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.app.MyApplication;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.CodeSendBean;
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

public class InsertBankCardActivity extends BaseActivity {

    private Context context = InsertBankCardActivity.this;

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_bank_code)
    EditText etBankCode;
    @BindView(R.id.et_bank_name)
    EditText etBankName;
    @BindView(R.id.et_id_card)
    EditText etIdCard;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.et_code)
    EditText etCode;

    public TextView getCode_btn(){
        return tvCode;
    }

    private String phone = "";
    private String code = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_bank_card);

        ButterKnife.bind(InsertBankCardActivity.this);
        MyApplication.bankCardTimeCount.setActivity(InsertBankCardActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.tv_code, R.id.btn_sure})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_code:
                phone = etPhoneNum.getText().toString();
                if(StringUtils.isEmpty(phone)){
                    ToastUtil.showShort(context, "手机号码不能为空");
                }else if(!StringUtils.isPhoneNumberValid(phone)){
                    ToastUtil.showShort(context, "请输入正确格式的手机号码");
                }else {
                    MyApplication.bankCardTimeCount.start();
                    ViseHttp.POST(NetUrl.codeSendUrl)
                            .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.codeSendUrl))
                            .addParam("tel", phone)
                            .addParam("type", "3")
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.optInt("code") == 200){
                                            Gson gson = new Gson();
                                            CodeSendBean bean = gson.fromJson(data, CodeSendBean.class);
                                            ToastUtil.showShort(context, "验证码发送成功");
                                            code = bean.getObj().getCode();
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
            case R.id.btn_sure:
                String name = etName.getText().toString();
                String bankCode = etBankCode.getText().toString();
                String bankName = etBankName.getText().toString();
                String idCard = etIdCard.getText().toString();
                String eCode = etCode.getText().toString();
                if(StringUtils.isEmpty(name)||StringUtils.isEmpty(bankCode)||StringUtils.isEmpty(bankName)
                        ||StringUtils.isEmpty(idCard)||StringUtils.isEmpty(phone)||StringUtils.isEmpty(eCode)){
                    ToastUtil.showShort(context, "请完善信息后添加");
                }else if(!eCode.equals(code)){
                    ToastUtil.showShort(context, "验证码不正确");
                }else {
                    ViseHttp.POST(NetUrl.addWokerBankUrl)
                            .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.addWokerBankUrl))
                            .addParam("name", name)
                            .addParam("card", idCard)
                            .addParam("bank_name", bankName)
                            .addParam("tel", phone)
                            .addParam("code", bankCode)
                            .addParam("uid", SpUtils.getUid(context))
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.optInt("code") == 200){
                                            ToastUtil.showShort(context, "银行卡添加成功");
                                            finish();
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
