package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JiedanSetActivity extends BaseActivity {

    private Context context = JiedanSetActivity.this;

    @BindView(R.id.iv_default)
    ImageView ivDefault;
    @BindView(R.id.iv_set)
    ImageView ivSet;

    private String isDefault = "0";
    private String isSet = "1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiedan_set);

        ButterKnife.bind(JiedanSetActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.rl_save, R.id.rl_default, R.id.rl_set, R.id.ll_time})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_save:

                break;
            case R.id.rl_default:
                if(isDefault.equals("0")){
                    isDefault = "1";
                    Glide.with(context).load(R.mipmap.unselect).into(ivDefault);
                }else if(isDefault.equals("1")){
                    isDefault = "0";
                    Glide.with(context).load(R.mipmap.true_blue).into(ivDefault);
                }
                break;
            case R.id.rl_set:
                if(isSet.equals("0")){
                    isSet = "1";
                    Glide.with(context).load(R.mipmap.on).into(ivSet);
                }else if(isSet.equals("1")){
                    isSet = "0";
                    Glide.with(context).load(R.mipmap.off).into(ivSet);
                }
                break;
            case R.id.ll_time:
                intent.setClass(context, JiedanTimeSetActivity.class);
                startActivity(intent);
                break;
        }
    }

}
