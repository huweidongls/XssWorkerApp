package com.jingna.xssworkerapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.adapter.FragmentNewOrderAdapter;
import com.jingna.xssworkerapp.base.BaseFragment;
import com.jingna.xssworkerapp.pages.CityActivity;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/5/29.
 */

public class FragmentNewOrder extends BaseFragment {

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.tv_city)
    TextView tvCity;

    private FragmentNewOrderAdapter adapter;
    private List<String> mList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_order, null);

        ButterKnife.bind(this, view);
        initData();

        return view;
    }

    private void initData() {

        String[] s = SpUtils.getCityName(getContext()).split("-");
        tvCity.setText(s[1]);

        mList = new ArrayList<>();
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        mList.add("");
        adapter = new FragmentNewOrderAdapter(mList);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);

    }

    @OnClick({R.id.rl_tingdan, R.id.rl_city})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_tingdan:

                break;
            case R.id.rl_city:
                String[] s = SpUtils.getCityName(getContext()).split("-");
                intent.setClass(getContext(), CityActivity.class);
                intent.putExtra("city", s[1]);
                intent.putExtra("type", 1);
                startActivity(intent);
                break;
        }
    }

}
