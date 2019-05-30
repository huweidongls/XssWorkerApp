package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class JiedanSetActivity extends BaseActivity {

    private Context context = JiedanSetActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiedan_set);

        ButterKnife.bind(JiedanSetActivity.this);

    }

    @OnClick({R.id.rl_back})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
        }
    }

}
