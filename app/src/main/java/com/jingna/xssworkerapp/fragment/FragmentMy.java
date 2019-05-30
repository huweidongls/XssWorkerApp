package com.jingna.xssworkerapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseFragment;
import com.jingna.xssworkerapp.pages.AboutActivity;
import com.jingna.xssworkerapp.pages.JiedanSetActivity;
import com.jingna.xssworkerapp.pages.LoginActivity;
import com.jingna.xssworkerapp.pages.MyWalletActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/5/29.
 */

public class FragmentMy extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @OnClick({R.id.rl_my_wallet, R.id.rl_jiedan_set, R.id.rl_version, R.id.rl_about})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_my_wallet:
                intent.setClass(getContext(), MyWalletActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_jiedan_set:
                intent.setClass(getContext(), JiedanSetActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_version:
                intent.setClass(getContext(), LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_about:
                intent.setClass(getContext(), AboutActivity.class);
                startActivity(intent);
                break;
        }
    }

}
