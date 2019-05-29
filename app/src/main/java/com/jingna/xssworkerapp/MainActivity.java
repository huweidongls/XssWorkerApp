package com.jingna.xssworkerapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jingna.xssworkerapp.app.MyApplication;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.fragment.FragmentIndex;
import com.jingna.xssworkerapp.fragment.FragmentMessage;
import com.jingna.xssworkerapp.fragment.FragmentMy;
import com.jingna.xssworkerapp.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private Context context = MainActivity.this;

    @BindView(R.id.menu_index)
    ImageButton ibIndex;
    @BindView(R.id.menu_message)
    ImageButton ibMessage;
    @BindView(R.id.menu_my)
    ImageButton ibMy;
    @BindView(R.id.menu1)
    RelativeLayout rl1;
    @BindView(R.id.menu2)
    RelativeLayout rl2;
    @BindView(R.id.menu3)
    RelativeLayout rl3;
    @BindView(R.id.tv_index)
    TextView tvIndex;
    @BindView(R.id.tv_message)
    TextView tvMessage;
    @BindView(R.id.tv_my)
    TextView tvMy;

    private long exitTime = 0;

    private List<Fragment> fragmentList = new ArrayList<>();
    private MenuOnClickListener listener = new MenuOnClickListener();

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(MainActivity.this);
        init();

    }

    /**
     * 初始化各个组件
     */
    private void init() {

        ibIndex.setOnClickListener(listener);
        ibMessage.setOnClickListener(listener);
        ibMy.setOnClickListener(listener);

        rl1.setOnClickListener(listener);
        rl2.setOnClickListener(listener);
        rl3.setOnClickListener(listener);
        Fragment fragmentIndex = new FragmentIndex();
        Fragment fragmentFenlei = new FragmentMessage();
        Fragment fragmentGouwuche = new FragmentMy();

        fragmentList.add(fragmentIndex);
        fragmentList.add(fragmentFenlei);
        fragmentList.add(fragmentGouwuche);

        fragmentTransaction.add(R.id.fl_container, fragmentIndex);
        fragmentTransaction.add(R.id.fl_container, fragmentFenlei);
        fragmentTransaction.add(R.id.fl_container, fragmentGouwuche);

        fragmentTransaction.show(fragmentIndex).hide(fragmentFenlei).hide(fragmentGouwuche);
        fragmentTransaction.commit();

        selectButton(ibIndex);
        selectText(tvIndex);

    }

    private class MenuOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            switch (v.getId()) {
                case R.id.menu_index:
                    selectButton(ibIndex);
                    selectText(tvIndex);
                    switchFragment(0);
                    break;
                case R.id.menu_message:
                    selectButton(ibMessage);
                    selectText(tvMessage);
                    switchFragment(1);
                    break;
                case R.id.menu_my:
                    selectButton(ibMy);
                    selectText(tvMy);
                    switchFragment(2);
                    break;
                case R.id.menu1:
                    selectButton(ibIndex);
                    selectText(tvIndex);
                    switchFragment(0);
                    break;
                case R.id.menu2:
                    selectButton(ibMessage);
                    selectText(tvMessage);
                    switchFragment(1);
                    break;
                case R.id.menu3:
                    selectButton(ibMy);
                    selectText(tvMy);
                    switchFragment(2);
                    break;
            }

        }
    }

    /**
     * 选择隐藏与显示的Fragment
     *
     * @param index 显示的Frgament的角标
     */
    public void switchFragment(int index) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        for (int i = 0; i < fragmentList.size(); i++) {
            if (index == i) {
                fragmentTransaction.show(fragmentList.get(index));
            } else {
                fragmentTransaction.hide(fragmentList.get(i));
            }
        }
        fragmentTransaction.commit();
    }

    public void selectText(View v) {
        tvIndex.setSelected(false);
        tvMessage.setSelected(false);
        tvMy.setSelected(false);
        v.setSelected(true);
    }

    /**
     * 控制底部菜单按钮的选中
     *
     * @param v
     */
    public void selectButton(View v) {
        ibIndex.setSelected(false);
        ibMessage.setSelected(false);
        ibMy.setSelected(false);
        v.setSelected(true);
    }

    @Override
    public void onBackPressed() {
        backtrack();
    }

    /**
     * 退出销毁所有activity
     */
    private void backtrack() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            ToastUtil.showShort(context, "再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            MyApplication.getInstance().exit();
            exitTime = 0;
        }
    }

}
