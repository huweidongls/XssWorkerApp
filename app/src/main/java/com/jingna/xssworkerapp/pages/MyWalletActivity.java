package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyWalletActivity extends BaseActivity {

    private Context context = MyWalletActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_wallet);

        ButterKnife.bind(MyWalletActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.rl_tixian})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_tixian:
                intent.setClass(context, MyWalletTixianActivity.class);
                startActivity(intent);
                break;
        }
    }

}
