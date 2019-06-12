package com.jingna.xssworkerapp.pages;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.StringUtils;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.jingna.xssworkerapp.util.WeiboDialogUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RealNameActivity extends BaseActivity {

    private Context context = RealNameActivity.this;

    @BindView(R.id.iv1)
    ImageView iv1;
    @BindView(R.id.iv2)
    ImageView iv2;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_id_card)
    EditText etIdCard;
    @BindView(R.id.et_issuing_authority)
    EditText etIssuingAuthority;
    @BindView(R.id.et_youxiao)
    EditText etYouxiao;

    private int REQUEST_CODE = 101;
    private int REQUEST_CODE1 = 102;

    private String pic1 = "";
    private String pic2 = "";

    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name);

        ButterKnife.bind(RealNameActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.rl_real1, R.id.rl_real2, R.id.rl_save})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_real1:
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(RealNameActivity.this, REQUEST_CODE); // 打开相册
                break;
            case R.id.rl_real2:
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(RealNameActivity.this, REQUEST_CODE1); // 打开相册
                break;
            case R.id.rl_save:
                dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");
                String name = etName.getText().toString();
                String idCard = etIdCard.getText().toString();
                String issuingAuthority = etIssuingAuthority.getText().toString();
                String youxiao = etYouxiao.getText().toString();
                if(StringUtils.isEmpty(pic1)||StringUtils.isEmpty(pic2)||StringUtils.isEmpty(name)
                        ||StringUtils.isEmpty(idCard)||StringUtils.isEmpty(issuingAuthority)||StringUtils.isEmpty(youxiao)){
                    ToastUtil.showShort(context, "请完善信息之后提交");
                }else {
                    File file = new File(pic1);
                    File file1 = new File(pic2);
                    ViseHttp.UPLOAD(NetUrl.real_Name_AuthenticationUrl)
                            .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.real_Name_AuthenticationUrl))
                            .addParam("uid", SpUtils.getUid(context))
                            .addFile("imgz", file)
                            .addFile("imgf", file1)
                            .addParam("real_name", name)
                            .addParam("card_code", idCard)
                            .addParam("issue", issuingAuthority)
                            .addParam("validity", youxiao)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.optInt("code") == 200){
                                            ToastUtil.showShort(context, jsonObject.optString("message"));
                                            WeiboDialogUtils.closeDialog(dialog);
                                            finish();
                                        }else {
                                            ToastUtil.showShort(context, jsonObject.optString("message"));
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {
                                    WeiboDialogUtils.closeDialog(dialog);
                                }
                            });
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && data != null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);
            if(images.size()>0){
                Glide.with(context).load(images.get(0)).into(iv1);
                pic1 = images.get(0);
            }
        }else if (requestCode == REQUEST_CODE1 && data != null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);
            if(images.size()>0){
                Glide.with(context).load(images.get(0)).into(iv2);
                pic2 = images.get(0);
            }
        }
    }

}
