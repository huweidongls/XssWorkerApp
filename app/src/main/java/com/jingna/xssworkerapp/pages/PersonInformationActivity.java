package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonInformationActivity extends BaseActivity {

    private Context context = PersonInformationActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_information);

        ButterKnife.bind(PersonInformationActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.ll_real_name, R.id.ll_work_experience})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_real_name:
                intent.setClass(context, RealNameActivity.class);
                startActivity(intent);
                break;
            case R.id.ll_work_experience:
                intent.setClass(context, WorkExperienceActivity.class);
                startActivity(intent);
                break;
        }
    }

}
