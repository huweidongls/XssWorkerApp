package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.PersonalDataBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.Logger;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonInformationActivity extends BaseActivity {

    private Context context = PersonInformationActivity.this;

    @BindView(R.id.iv_avatar)
    ImageView ivAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_is_real)
    TextView tvIsReal;
    @BindView(R.id.tv_phone_num)
    TextView tvPhoneNum;
    @BindView(R.id.tv_work_experience)
    TextView tvWorkExperience;

    private int REQUEST_CODE = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_information);

        ButterKnife.bind(PersonInformationActivity.this);
        initData();

    }

    private void initData() {

        ViseHttp.POST(NetUrl.personalDataUrl)
                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.personalDataUrl))
                .addParam("uid", SpUtils.getUid(context))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.optInt("code") == 200){
                                Gson gson = new Gson();
                                PersonalDataBean bean = gson.fromJson(data, PersonalDataBean.class);
                                Glide.with(context).load(NetUrl.BASE_URL+bean.getObj().getHeadimg()).into(ivAvatar);
                                tvName.setText(bean.getObj().getUsername());
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
                                tvPhoneNum.setText(bean.getObj().getUser());
                                if(bean.getObj().getWorkexperience().equals("0")){
                                    tvWorkExperience.setText("未填写");
                                }else if(bean.getObj().getWorkexperience().equals("1")) {
                                    tvWorkExperience.setText("已填写");
                                }
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

    @OnClick({R.id.rl_back, R.id.ll_real_name, R.id.ll_work_experience, R.id.ll_pic, R.id.ll_replace_phone_num})
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
            case R.id.ll_pic:
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(PersonInformationActivity.this, REQUEST_CODE); // 打开相册
                break;
            case R.id.ll_replace_phone_num:
                intent.setClass(context, ReplacePhoneNumActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            final ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);
            if(images.size()>0){
                File file = new File(images.get(0));
                ViseHttp.UPLOAD(NetUrl.uploadTheImgUrl)
                        .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.uploadTheImgUrl))
                        .addParam("uid", SpUtils.getUid(context))
                        .addFile("headimg", file)
                        .request(new ACallback<String>() {
                            @Override
                            public void onSuccess(String data) {
                                try {
                                    Logger.e("123123", data);
                                    JSONObject jsonObject = new JSONObject(data);
                                    if(jsonObject.optInt("code") == 200){
                                        ToastUtil.showShort(context, jsonObject.optString("message"));
                                        Glide.with(context).load(images.get(0)).into(ivAvatar);
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
        }
    }

}
