package com.jingna.xssworkerapp.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.adapter.ViewpagerAdapter;
import com.jingna.xssworkerapp.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/5/29.
 */

public class FragmentIndex extends BaseFragment {

    @BindView(R.id.vp)
    ViewPager mViewPager;
    @BindView(R.id.tv_all)
    TextView tvAll;
    @BindView(R.id.view_all)
    View viewAll;
    @BindView(R.id.tv_ing)
    TextView tvIng;
    @BindView(R.id.view_ing)
    View viewIng;
    @BindView(R.id.tv_complete)
    TextView tvComplete;
    @BindView(R.id.view_complete)
    View viewComplete;

    private FragmentManager mFragmentManager;
    private ViewpagerAdapter mViewPagerFragmentAdapter;
    private List<Fragment> fragmentList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, null);

        ButterKnife.bind(this, view);
        mFragmentManager = getActivity().getSupportFragmentManager();
        initData();

        return view;
    }

    private void initData() {

        fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentNewOrder());
        fragmentList.add(new FragmentYijieOrder());
        fragmentList.add(new FragmentPaiqianOrder());
        mViewPagerFragmentAdapter = new ViewpagerAdapter(mFragmentManager, fragmentList);
        mViewPager.setAdapter(mViewPagerFragmentAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        tvAll.setTextColor(Color.parseColor("#3296FA"));
                        tvIng.setTextColor(Color.parseColor("#99191F25"));
                        tvComplete.setTextColor(Color.parseColor("#99191F25"));
                        viewAll.setVisibility(View.VISIBLE);
                        viewIng.setVisibility(View.GONE);
                        viewComplete.setVisibility(View.GONE);
                        break;
                    case 1:
                        tvAll.setTextColor(Color.parseColor("#99191F25"));
                        tvIng.setTextColor(Color.parseColor("#3296FA"));
                        tvComplete.setTextColor(Color.parseColor("#99191F25"));
                        viewAll.setVisibility(View.GONE);
                        viewIng.setVisibility(View.VISIBLE);
                        viewComplete.setVisibility(View.GONE);
                        break;
                    case 2:
                        tvAll.setTextColor(Color.parseColor("#99191F25"));
                        tvIng.setTextColor(Color.parseColor("#99191F25"));
                        tvComplete.setTextColor(Color.parseColor("#3296FA"));
                        viewAll.setVisibility(View.GONE);
                        viewIng.setVisibility(View.GONE);
                        viewComplete.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @OnClick({R.id.rl_all, R.id.rl_ing, R.id.rl_complete})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_all:
                mViewPager.setCurrentItem(0);
                break;
            case R.id.rl_ing:
                mViewPager.setCurrentItem(1);
                break;
            case R.id.rl_complete:
                mViewPager.setCurrentItem(2);
                break;
        }
    }

}
