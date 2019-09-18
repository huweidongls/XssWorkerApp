package com.jingna.xssworkerapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jingna.xssworkerapp.app.MyApplication;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.fragment.FragmentIndex;
import com.jingna.xssworkerapp.fragment.FragmentMessage;
import com.jingna.xssworkerapp.fragment.FragmentMy;
import com.jingna.xssworkerapp.receiver.TagAliasOperatorHelper;
import com.jingna.xssworkerapp.service.ForegroundService;
import com.jingna.xssworkerapp.service.UploadLocationService;
import com.jingna.xssworkerapp.util.Logger;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.vise.xsnow.permission.OnPermissionCallback;
import com.vise.xsnow.permission.PermissionManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.JPushMessage;
import cn.jpush.android.service.JPushMessageReceiver;

public class MainActivity extends BaseActivity {

    private Context context = MainActivity.this;
    private static final int TAG = 1010;

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

    public static boolean isForeground = false;

    //for receive customer msg from jpush server
    private MessageReceiver mMessageReceiver;
    public static final String MESSAGE_RECEIVED_ACTION = "com.example.jpushdemo.MESSAGE_RECEIVED_ACTION";
    public static final String KEY_TITLE = "title";
    public static final String KEY_MESSAGE = "message";
    public static final String KEY_EXTRAS = "extras";
    private EditText msgText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SpUtils.setIsFirst(context, "1");
        MyApplication.getInstance().addActivity(MainActivity.this);
        ButterKnife.bind(MainActivity.this);
        init();
//        setAlias();
        TagAliasOperatorHelper.TagAliasBean tagAliasBean = new TagAliasOperatorHelper.TagAliasBean();
        tagAliasBean.isAliasAction = true;
        tagAliasBean.action = TagAliasOperatorHelper.ACTION_SET;
        tagAliasBean.alias = "j_"+ SpUtils.getUid(context);//别名
        TagAliasOperatorHelper.getInstance().handleAction(context, TAG, tagAliasBean);

        initD();

    }

    private void initD() {

        // 开启前台服务防止应用进入后台gps挂掉
        startService(new Intent(this, ForegroundService.class));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<>();
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);

            }
            if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }

            if (permissions.size() != 0) {
                requestPermissionsForM(permissions);
            }
        }

    }

    private void requestPermissionsForM(final ArrayList<String> per) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(per.toArray(new String[per.size()]), 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, ForegroundService.class));
        stopService(new Intent(this, UploadLocationService.class));
    }

//    private final Handler mHandler = new Handler() {
//        @Override
//        public void handleMessage(android.os.Message msg) {
//            super.handleMessage(msg);
//            if (msg.what == 100) {
//                setAlias();
//            }
//        }
//    };
//    private  JPushMessageReceiver jPushMessageReceiver = new JPushMessageReceiver() {
//        @Override
//        public void onAliasOperatorResult(Context context, JPushMessage jPushMessage) {
//            super.onAliasOperatorResult(context, jPushMessage);
//            if (jPushMessage.getErrorCode() == 6002) {//超时处理
//                Message obtain = Message.obtain();
//                obtain.what = 100;
//                mHandler.sendMessageDelayed(obtain, 1000 * 60);//60秒后重新验证
//            }else {
//                Logger.e("onAliasOperatorResult: ", jPushMessage.getErrorCode()+"");
//            }
//        }
//    };

    private void setAlias() {

        String alias = "j_"+ SpUtils.getUid(context);//别名
        if ( !alias.isEmpty()) {
            JPushInterface.setAlias(getApplicationContext(), TAG, alias);//设置别名或标签
            ToastUtil.showShort(context, "设置别名");
        }

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

    public class MessageReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {
                    String messge = intent.getStringExtra(KEY_MESSAGE);
                    String extras = intent.getStringExtra(KEY_EXTRAS);
                    StringBuilder showMsg = new StringBuilder();
                    showMsg.append(KEY_MESSAGE + " : " + messge + "\n");
                    if (!TextUtils.isEmpty(extras)) {
                        showMsg.append(KEY_EXTRAS + " : " + extras + "\n");
                    }
                    setCostomMsg(showMsg.toString());
                }
            } catch (Exception e){
            }
        }
    }

    private void setCostomMsg(String msg){
        if (null != msgText) {
            msgText.setText(msg);
            msgText.setVisibility(android.view.View.VISIBLE);
        }
    }

}
