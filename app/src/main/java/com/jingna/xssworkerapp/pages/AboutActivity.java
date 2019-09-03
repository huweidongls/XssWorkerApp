package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class AboutActivity extends BaseActivity {

    private Context context = AboutActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        ButterKnife.bind(AboutActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.tv_privacy, R.id.tv_user_agreement})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_privacy:
                intent.setClass(context, PrivacyPolicyActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_user_agreement:
                intent.setClass(context, UserAgreementActivity.class);
                startActivity(intent);
                break;
        }
    }

}
