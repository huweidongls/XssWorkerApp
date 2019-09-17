package com.jingna.xssworkerapp.pages;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.RealNameHuiXianBean;
import com.jingna.xssworkerapp.bean.WorkerBankListBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.Logger;
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
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

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
    @BindView(R.id.tv_youxiao)
    TextView tvYouxiao;

    private int REQUEST_CODE = 101;
    private int REQUEST_CODE1 = 102;

    private String pic1 = "";
    private String pic2 = "";

    private Dialog dialog;

    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_name);

        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);

        ButterKnife.bind(RealNameActivity.this);

    }

    @OnClick({R.id.rl_back, R.id.rl_real1, R.id.rl_real2, R.id.rl_save, R.id.ll_youxiao})
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
                Save_Info();
                break;
            case R.id.ll_youxiao:
                //new DatePickerDialog(context, onDateSetListener, mYear, mMonth, mDay).show();
                break;
        }
    }
    private void Save_Info(){
        dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");
        final String name = etName.getText().toString();
        final String idCard = etIdCard.getText().toString();
        final String issuingAuthority = etIssuingAuthority.getText().toString();
        final String youxiao = tvYouxiao.getText().toString();
        ViseHttp.POST(NetUrl.real_Name_AuthenticationUrl)
                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.real_Name_AuthenticationUrl))
                .addParam("uid", SpUtils.getUid(context))
                .addParam("imgz",pic1)
                .addParam("imgf",pic2)
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
                                finish();
                            }else {
                                ToastUtil.showShort(context, jsonObject.optString("message"));
                            }
                            WeiboDialogUtils.closeDialog(dialog);
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
    private void RealnameauThenticationEcho(){
        if (StringUtils.isEmpty(pic1)||StringUtils.isEmpty(pic2)){
//            ToastUtil.showShort(context, "请选择身份证照片!");
        }else{
            dialog = WeiboDialogUtils.createLoadingDialog(context, "请等待...");
            List<String> list = new ArrayList<>();
            list.add(pic1);
            list.add(pic2);
            final List<File> fileList = new ArrayList<>();
            Luban.with(context)
                    .load(list)
                    .ignoreBy(100)
                    .filter(new CompressionPredicate() {
                        @Override
                        public boolean apply(String path) {
                            return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                        }
                    })
                    .setCompressListener(new OnCompressListener() {
                        @Override
                        public void onStart() {
                        }
                        @Override
                        public void onSuccess(File file) {
                            fileList.add(file);
                            if(fileList.size() == 2){
                                ViseHttp.UPLOAD(NetUrl.RealHuiXianUrl)
                                        .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.real_Name_AuthenticationUrl))
                                        .addParam("uid", SpUtils.getUid(context))
                                        .addFile("imgz", fileList.get(0))
                                        .addFile("imgf", fileList.get(1))
                                        .request(new ACallback<String>() {
                                            @Override
                                            public void onSuccess(String data) {
                                                try {
                                                    JSONObject jsonObject = new JSONObject(data);
                                                if(jsonObject.optInt("code") == 200){
                                                    Gson gson = new Gson();
                                                    RealNameHuiXianBean bean = gson.fromJson(data, RealNameHuiXianBean.class);
                                                    etName.setText(bean.getObj().getName());
                                                    etIdCard.setText(bean.getObj().getIdcard());
                                                    etIssuingAuthority.setText(bean.getObj().getDepartment());
                                                    tvYouxiao.setText(bean.getObj().getEnd());
                                                    Glide.with(context).load(NetUrl.BASE_URL+bean.getObj().getImgz()).into(iv1);
                                                    Glide.with(context).load(NetUrl.BASE_URL+bean.getObj().getImgf()).into(iv2);
                                                    pic1 = bean.getObj().getImgz();
                                                    pic2 = bean.getObj().getImgf();
                                                }else {
                                                    ToastUtil.showShort(context, jsonObject.optString("message"));
                                                }
                                                    WeiboDialogUtils.closeDialog(dialog);
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
                        }

                        @Override
                        public void onError(Throwable e) {
                            WeiboDialogUtils.closeDialog(dialog);
                        }
                    }).launch();
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
            RealnameauThenticationEcho();
        }else if (requestCode == REQUEST_CODE1 && data != null) {
            //获取选择器返回的数据
            ArrayList<String> images = data.getStringArrayListExtra(
                    ImageSelectorUtils.SELECT_RESULT);
            if(images.size()>0){
                Glide.with(context).load(images.get(0)).into(iv2);
                pic2 = images.get(0);
            }
            RealnameauThenticationEcho();
        }
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            final String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("").append("0").
                            append(mMonth + 1).append("").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("").append("0").
                            append(mMonth + 1).append("").append(mDay).append("").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("").
                            append(mMonth + 1).append("").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("").
                            append(mMonth + 1).append("").append(mDay).append("").toString();
                }

            }
            tvYouxiao.setText(days);
        }
    };

}
