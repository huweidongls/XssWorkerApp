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

public class ReplacePhoneNumActivity extends BaseActivity {

    private Context context = ReplacePhoneNumActivity.this;

    @BindView(R.id.tv_code)
    TextView tvCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_new_num)
    EditText etNewNum;
    @BindView(R.id.et_code)
    EditText etCode;

    public TextView getCode_btn(){
        return tvCode;
    }

    private String code = "";
    private String phone = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_replace_phone_num);

        ButterKnife.bind(ReplacePhoneNumActivity.this);
        MyApplication.editPwdTimeCount.setActivity(ReplacePhoneNumActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.btn_sure, R.id.tv_code})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_sure:
                String pwd = etPwd.getText().toString();
                String etC = etCode.getText().toString();
                if(StringUtils.isEmpty(pwd)||StringUtils.isEmpty(etC)){
                    ToastUtil.showShort(context, "密码或验证码不能为空");
                }else if(!code.equals(etC)){
                    ToastUtil.showShort(context, "验证码不正确");
                }else {
                    ViseHttp.POST(NetUrl.updateUserPhoneUrl)
                            .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.updateUserPhoneUrl))
                            .addParam("uid", SpUtils.getUid(context))
                            .addParam("tel", phone)
                            .addParam("password", pwd)
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
                break;
            case R.id.tv_code:
                phone = etNewNum.getText().toString();
                if(StringUtils.isEmpty(phone)){
                    ToastUtil.showShort(context, "手机号码不能为空");
                }else if(!StringUtils.isPhoneNumberValid(phone)){
                    ToastUtil.showShort(context, "请输入正确格式的手机号码");
                }else {
                    MyApplication.editPwdTimeCount.start();
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
                                            ToastUtil.showShort(context, jsonObject.optString("message"));
                                            Gson gson = new Gson();
                                            CodeSendBean bean = gson.fromJson(data, CodeSendBean.class);
                                            code = bean.getObj().getCode();
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
