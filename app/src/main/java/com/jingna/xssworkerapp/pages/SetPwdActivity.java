package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
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

public class SetPwdActivity extends BaseActivity {

    private Context context = SetPwdActivity.this;

    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.iv_is_show_pwd)
    ImageView ivIsShowPwd;
    @BindView(R.id.et_pwd1)
    EditText etPwd1;
    @BindView(R.id.iv_is_show_pwd1)
    ImageView ivIsShowPwd1;

    private boolean isShowPwd = false;
    private boolean isShowPwd1 = false;

    private String type = "";
    private String tel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pwd);

        type = getIntent().getStringExtra("type");
        tel = getIntent().getStringExtra("tel");
        ButterKnife.bind(SetPwdActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.rl_is_show_pwd, R.id.rl_is_show_pwd1, R.id.btn_sure})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
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
            case R.id.rl_is_show_pwd1:
                if(isShowPwd1){
                    isShowPwd1 = false;
                    Glide.with(context).load(R.mipmap.bukejian).into(ivIsShowPwd1);
                    etPwd1.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPwd1.setSelection(etPwd1.getText().length());
                }else {
                    isShowPwd1 = true;
                    Glide.with(context).load(R.mipmap.kejian).into(ivIsShowPwd1);
                    etPwd1.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPwd1.setSelection(etPwd1.getText().length());
                }
                break;
            case R.id.btn_sure:
                if(type.equals("0")){
                    //注册
                    String pwd = etPwd.getText().toString();
                    String pwd1 = etPwd1.getText().toString();
                    if(StringUtils.isEmpty(pwd)||StringUtils.isEmpty(pwd1)){
                        ToastUtil.showShort(context, "密码不能为空");
                    }else if(!pwd.equals(pwd1)){
                        ToastUtil.showShort(context, "密码不一致");
                    }else {
                        ViseHttp.POST(NetUrl.registerUrl)
                                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.registerUrl))
                                .addParam("tel", tel)
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
                }else if(type.equals("1")){
                    //找回密码
                    String pwd = etPwd.getText().toString();
                    String pwd1 = etPwd1.getText().toString();
                    if(StringUtils.isEmpty(pwd)||StringUtils.isEmpty(pwd1)){
                        ToastUtil.showShort(context, "密码不能为空");
                    }else if(!pwd.equals(pwd1)){
                        ToastUtil.showShort(context, "密码不一致");
                    }else {
                        ViseHttp.POST(NetUrl.retrievePasswordUrl)
                                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.retrievePasswordUrl))
                                .addParam("tel", tel)
                                .addParam("x_password", pwd)
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.optInt("code") == 200){
                                                ToastUtil.showShort(context, jsonObject.optString("message"));
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
                }
                break;
        }
    }

}
