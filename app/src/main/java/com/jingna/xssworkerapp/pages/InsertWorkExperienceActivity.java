package com.jingna.xssworkerapp.pages;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.WorkExperienceInfoBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.StringUtils;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InsertWorkExperienceActivity extends BaseActivity {

    private Context context = InsertWorkExperienceActivity.this;

    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;
    @BindView(R.id.tv_pic)
    TextView tvPic;
    @BindView(R.id.et_company)
    EditText etCompany;
    @BindView(R.id.et_jobs)
    EditText etJobs;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.et_content)
    EditText etContent;

    private int mYear;
    private int mMonth;
    private int mDay;

    private int REQUEST_CODE = 101;

    private String id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert_work_experience);

        id = getIntent().getStringExtra("id");
        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        mDay = ca.get(Calendar.DAY_OF_MONTH);
        ButterKnife.bind(InsertWorkExperienceActivity.this);
        initData();

    }

    private void initData() {

        if(!StringUtils.isEmpty(id)){
            ViseHttp.POST(NetUrl.workExperienceInfoUrl)
                    .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.workExperienceInfoUrl))
                    .addParam("id", id)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if(jsonObject.optInt("code") == 200){
                                    Gson gson = new Gson();
                                    WorkExperienceInfoBean infoBean = gson.fromJson(data, WorkExperienceInfoBean.class);
                                    etCompany.setText(infoBean.getObj().getCompanyname());
                                    etJobs.setText(infoBean.getObj().getPosition());
                                    tvStartTime.setText(infoBean.getObj().getStart_time());
                                    tvEndTime.setText(infoBean.getObj().getEnd_time());
                                    etPrice.setText(infoBean.getObj().getSalary());
                                    etContent.setText(infoBean.getObj().getJobdescription());
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

    @OnClick({R.id.rl_back, R.id.rl_save, R.id.ll_start_time, R.id.ll_end_time, R.id.ll_pic})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_save:
                String company = etCompany.getText().toString();
                String jobs = etJobs.getText().toString();
                String startTime = tvStartTime.getText().toString();
                String endTime = tvEndTime.getText().toString();
                String price = etPrice.getText().toString();
                final String content = etContent.getText().toString();
                if(StringUtils.isEmpty(company)||StringUtils.isEmpty(jobs)||StringUtils.isEmpty(startTime)
                        ||StringUtils.isEmpty(endTime)||StringUtils.isEmpty(price)||StringUtils.isEmpty(content)){
                    ToastUtil.showShort(context, "请完善信息后提交");
                }else {
                    if(StringUtils.isEmpty(id)){
                        ViseHttp.POST(NetUrl.workExperienceAddUrl)
                                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.workExperienceAddUrl))
                                .addParam("uid", SpUtils.getUid(context))
                                .addParam("companyname", company)
                                .addParam("start_time", startTime)
                                .addParam("end_time", endTime)
                                .addParam("position", jobs)
                                .addParam("salary", price)
                                .addParam("jobdescription", content)
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
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onFail(int errCode, String errMsg) {

                                    }
                                });
                    }else {
                        ViseHttp.POST(NetUrl.workExperienceSaveUrl)
                                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.workExperienceSaveUrl))
                                .addParam("id", id)
                                .addParam("companyname", company)
                                .addParam("start_time", startTime)
                                .addParam("end_time", endTime)
                                .addParam("position", jobs)
                                .addParam("salary", price)
                                .addParam("jobdescription", content)
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
                break;
            case R.id.ll_start_time:
                new DatePickerDialog(context, onDateSetListener, mYear, mMonth, mDay).show();
                break;
            case R.id.ll_end_time:
                new DatePickerDialog(context, onDateSetListener1, mYear, mMonth, mDay).show();
                break;
            case R.id.ll_pic:
                //限数量的多选(比喻最多9张)
                ImageSelector.builder()
                        .useCamera(true) // 设置是否使用拍照
                        .setSingle(false)  //设置是否单选
                        .setMaxSelectCount(1) // 图片的最大选择数量，小于等于0时，不限数量。
                        .setViewImage(true) //是否点击放大图片查看,，默认为true
                        .start(InsertWorkExperienceActivity.this, REQUEST_CODE); // 打开相册
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
                tvPic.setText("已添加");
            }
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
                    days = new StringBuffer().append(mYear).append("/").append("0").
                            append(mMonth + 1).append("/").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("/").append("0").
                            append(mMonth + 1).append("/").append(mDay).append("").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("/").
                            append(mMonth + 1).append("/").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("/").
                            append(mMonth + 1).append("/").append(mDay).append("").toString();
                }

            }
            tvStartTime.setText(days);
        }
    };

    private DatePickerDialog.OnDateSetListener onDateSetListener1 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            mYear = year;
            mMonth = monthOfYear;
            mDay = dayOfMonth;
            final String days;
            if (mMonth + 1 < 10) {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("/").append("0").
                            append(mMonth + 1).append("/").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("/").append("0").
                            append(mMonth + 1).append("/").append(mDay).append("").toString();
                }

            } else {
                if (mDay < 10) {
                    days = new StringBuffer().append(mYear).append("/").
                            append(mMonth + 1).append("/").append("0").append(mDay).append("").toString();
                } else {
                    days = new StringBuffer().append(mYear).append("/").
                            append(mMonth + 1).append("/").append(mDay).append("").toString();
                }

            }
            tvEndTime.setText(days);
        }
    };

}
