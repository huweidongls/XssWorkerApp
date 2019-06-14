package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jingna.xssworkerapp.MainActivity;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.RegisterBean;
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

public class LoginActivity extends BaseActivity {

    private Context context = LoginActivity.this;

    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_is_show_pwd)
    ImageView ivIsShowPwd;
    @BindView(R.id.et_phone)
    EditText etPhone;

    private boolean isShowPwd = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(LoginActivity.this);

    }

    @OnClick({R.id.rl_is_show_pwd, R.id.tv_register, R.id.tv_forgot_pwd, R.id.btn_login})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_is_show_pwd:
                if(isShowPwd){
                    isShowPwd = false;
                    Glide.with(context).load(R.mipmap.bukejian).into(ivIsShowPwd);
                    etPwd.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPwd.setSelection(etPwd.getText().length());
                }else {
                    isShowPwd = true;
                    Glide.with(context).load(R.mipmap.kejian).into(ivIsShowPwd);
                    etPwd.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPwd.setSelection(etPwd.getText().length());
                }
                break;
            case R.id.tv_register:
                intent.setClass(context, RegisterActivity.class);
                intent.putExtra("type", "0");
                startActivity(intent);
                finish();
                break;
            case R.id.tv_forgot_pwd:
                intent.setClass(context, RegisterActivity.class);
                intent.putExtra("type", "1");
                startActivity(intent);
                finish();
                break;
            case R.id.btn_login:
                final String tel = etPhone.getText().toString();
                String pwd = etPwd.getText().toString();
                if(StringUtils.isEmpty(tel)||StringUtils.isEmpty(pwd)){
                    ToastUtil.showShort(context, "手机号或密码不能为空");
                }else if(!StringUtils.isPhoneNumberValid(tel)){
                    ToastUtil.showShort(context, "请输入正确的手机号码");
                }else {
                    ViseHttp.POST(NetUrl.loginUrl)
                            .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.loginUrl))
                            .addParam("user", tel)
                            .addParam("password", pwd)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.optInt("code") == 200){
                                            ToastUtil.showShort(context, jsonObject.optString("message"));
                                            Gson gson = new Gson();
                                            RegisterBean bean = gson.fromJson(data, RegisterBean.class);
                                            SpUtils.setUid(context, bean.getObj().getUid());
                                            SpUtils.setPhoneNum(context, tel);
                                            Intent intent1 = new Intent();
                                            intent1.setClass(context, MainActivity.class);
                                            startActivity(intent1);
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
