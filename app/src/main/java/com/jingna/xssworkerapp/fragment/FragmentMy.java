package com.jingna.xssworkerapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.app.MyApplication;
import com.jingna.xssworkerapp.base.BaseFragment;
import com.jingna.xssworkerapp.bean.UserInfoBean;
import com.jingna.xssworkerapp.dialog.CustomDialog;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.pages.AboutActivity;
import com.jingna.xssworkerapp.pages.JiedanSetActivity;
import com.jingna.xssworkerapp.pages.LoginActivity;
import com.jingna.xssworkerapp.pages.MyWalletActivity;
import com.jingna.xssworkerapp.pages.PersonInformationActivity;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.StringUtils;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2019/5/29.
 */

public class FragmentMy extends BaseFragment {

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_is_real)
    TextView tvIsReal;
    @BindView(R.id.tv_all_num)
    TextView tvAllNum;
    @BindView(R.id.tv_complete_num)
    TextView tvCompleteNum;
    @BindView(R.id.tv_new_num)
    TextView tvNewNum;
    @BindView(R.id.tv_price)
    TextView tvPrice;

    private String uid = "";
    private String tel = "";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, null);

        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        uid = SpUtils.getUid(getContext());
        ViseHttp.POST(NetUrl.userInfoUrl)
                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.userInfoUrl))
                .addParam("uid", SpUtils.getUid(getContext()))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.optInt("code") == 200){
                                Gson gson = new Gson();
                                UserInfoBean bean = gson.fromJson(data, UserInfoBean.class);
                                Glide.with(getContext()).load(NetUrl.BASE_URL+bean.getObj().getHeadimg()).into(ivAvatar);
                                tvName.setText(bean.getObj().getName());
                                if(bean.getObj().getReal().equals("0")){
                                    tvIsReal.setText("未实名认证");
                                    tvIsReal.setBackgroundResource(R.drawable.bg_66191f25_2dp);
                                    tvIsReal.setTextColor(Color.parseColor("#66191f25"));
                                }else if(bean.getObj().getReal().equals("1")){
                                    tvIsReal.setText("等待认证");
                                    tvIsReal.setBackgroundResource(R.drawable.bg_66191f25_2dp);
                                    tvIsReal.setTextColor(Color.parseColor("#66191f25"));
                                }else if(bean.getObj().getReal().equals("2")){
                                    tvIsReal.setText("已实名认证");
                                    tvIsReal.setBackgroundResource(R.drawable.bg_3296fa_2dp);
                                    tvIsReal.setTextColor(Color.parseColor("#3296fa"));
                                }else if(bean.getObj().getReal().equals("3")){
                                    tvIsReal.setText("认证失败");
                                    tvIsReal.setBackgroundResource(R.drawable.bg_3296fa_2dp);
                                    tvIsReal.setTextColor(Color.parseColor("#3296fa"));
                                }
                                tvAllNum.setText(bean.getObj().getOrder_count()+"单");
                                tvCompleteNum.setText(bean.getObj().getTheorder()+"单");
                                tvNewNum.setText(bean.getObj().getOrder_task()+"单");
                                tvPrice.setText(String.format("%.2f", Double.valueOf(bean.getObj().getPrice()))+"元");
                                tel = bean.getObj().getTel();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFail(int errCode, String errMsg) {

                    }
                });
    }

    @OnClick({R.id.rl_my_wallet, R.id.rl_jiedan_set, R.id.rl_version, R.id.rl_about, R.id.tv_exit, R.id.rl_person_information
    , R.id.iv_kefu})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_my_wallet:
                if(uid.equals("0")){
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent.setClass(getContext(), MyWalletActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_jiedan_set:
                if(uid.equals("0")){
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent.setClass(getContext(), JiedanSetActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.rl_version:
                ToastUtil.showShort(getContext(), "已是最新版本！");
                break;
            case R.id.rl_about:
                if(uid.equals("0")){
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent.setClass(getContext(), AboutActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_exit:
                CustomDialog dialog = new CustomDialog(getContext(), "是否退出登录", new CustomDialog.ClickListener() {
                    @Override
                    public void onSure() {
                        SpUtils.clear(getContext());
                        getActivity().finish();
                    }

                    @Override
                    public void onCancel() {

                    }
                });
                dialog.show();
                break;
            case R.id.rl_person_information:
                if(uid.equals("0")){
                    intent.setClass(getContext(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    intent.setClass(getContext(), PersonInformationActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.iv_kefu:
                if(!StringUtils.isEmpty(tel)){
                    Intent dialIntent =  new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel));//跳转到拨号界面，同时传递电话号码
                    startActivity(dialIntent);
                }
                break;
        }
    }

}
