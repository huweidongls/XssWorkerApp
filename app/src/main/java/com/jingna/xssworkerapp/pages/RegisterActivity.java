package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.CodeSendBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.Logger;
import com.jingna.xssworkerapp.util.StringUtils;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity {

    private Context context = RegisterActivity.this;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_phone)
    EditText etPhone;

    private String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        type = getIntent().getStringExtra("type");
        ButterKnife.bind(RegisterActivity.this);
        initData();

    }

    private void initData() {

        if(type.equals("0")){
            tvTitle.setText("手机号注册");
        }else if(type.equals("1")){
            tvTitle.setText("找回密码");
        }

    }

    @OnClick({R.id.rl_back, R.id.btn_next, R.id.tv_user_agreement, R.id.tv_privacy})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                intent.setClass(context, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.btn_next:
                final String phone = etPhone.getText().toString();
                if(StringUtils.isEmpty(phone)){
                    ToastUtil.showShort(context, "手机号码不能为空");
                }else if(!StringUtils.isPhoneNumberValid(phone)){
                    ToastUtil.showShort(context, "请输入正确格式的手机号");
                }else {
                    ViseHttp.POST(NetUrl.codeSendUrl)
                            .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.codeSendUrl))
                            .addParam("tel", phone)
                            .addParam("type", "1")
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        Logger.e("123123", data);
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.optInt("code") == 200){
                                            Gson gson = new Gson();
                                            CodeSendBean bean = gson.fromJson(data, CodeSendBean.class);
                                            Intent intent = new Intent();
                                            intent.setClass(context, CodeActivity.class);
                                            intent.putExtra("type", type);
                                            intent.putExtra("code", bean.getObj().getCode());
                                            intent.putExtra("tel", phone);
                                            startActivity(intent);
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
            case R.id.tv_user_agreement:
                intent.setClass(context, UserAgreementActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_privacy:
                intent.setClass(context, PrivacyPolicyActivity.class);
                startActivity(intent);
                break;
        }
    }

}
