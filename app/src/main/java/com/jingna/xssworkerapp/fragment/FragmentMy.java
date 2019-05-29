package com.jingna.xssworkerapp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseFragment;

import butterknife.ButterKnife;

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
}
