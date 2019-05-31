package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pwd);

        ButterKnife.bind(SetPwdActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.rl_is_show_pwd, R.id.rl_is_show_pwd1})
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
        }
    }

}
