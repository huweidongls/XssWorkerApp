package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bumptech.glide.Glide;
import com.donkingliang.imageselector.utils.ImageSelector;
import com.donkingliang.imageselector.utils.ImageSelectorUtils;
import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.JsonBean;
import com.jingna.xssworkerapp.bean.PersonalDataBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.GetJsonDataUtil;
import com.jingna.xssworkerapp.util.Logger;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONArray;
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
    @BindView(R.id.tv_city)
    TextView tvCity;

    private int REQUEST_CODE = 101;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private String real = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_information);

        ButterKnife.bind(PersonInformationActivity.this);
        initJsonData();

    }

    @Override
    protected void onStart() {
        super.onStart();
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
                                real = bean.getObj().getReal();
                                if(real.equals("0")){
                                    tvIsReal.setText("未实名认证");
                                    tvIsReal.setBackgroundResource(R.drawable.bg_66191f25_2dp);
                                    tvIsReal.setTextColor(Color.parseColor("#66191f25"));
                                }else if(real.equals("1")){
                                    tvIsReal.setText("等待认证");
                                    tvIsReal.setBackgroundResource(R.drawable.bg_66191f25_2dp);
                                    tvIsReal.setTextColor(Color.parseColor("#66191f25"));
                                }else if(real.equals("2")){
                                    tvIsReal.setText("已实名认证");
                                    tvIsReal.setBackgroundResource(R.drawable.bg_3296fa_2dp);
                                    tvIsReal.setTextColor(Color.parseColor("#3296fa"));
                                }else if(real.equals("3")){
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
                                tvCity.setText(bean.getObj().getHabitualresidence());
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

    @OnClick({R.id.rl_back, R.id.ll_real_name, R.id.ll_work_experience, R.id.ll_pic, R.id.ll_replace_phone_num, R.id.ll_city})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.ll_real_name:
                if(real.equals("0")||real.equals("3")){
                    intent.setClass(context, RealNameActivity.class);
                    startActivity(intent);
                }else if(real.equals("1")){
                    ToastUtil.showShort(context, "认证中，请等待...");
                }else if(real.equals("2")){
                    ToastUtil.showShort(context, "实名认证已通过!");
                }
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
            case R.id.ll_city:
                OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1).getPickerViewText() + "-" +
                                options2Items.get(options1).get(options2) + "-" +
                                options3Items.get(options1).get(options2).get(options3);
                        tvCity.setText(tx);
                        ViseHttp.POST(NetUrl.habitualresidenceUrl)
                                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.habitualresidenceUrl))
                                .addParam("uid", SpUtils.getUid(context))
                                .addParam("city", tx)
                                .request(new ACallback<String>() {
                                    @Override
                                    public void onSuccess(String data) {
                                        try {
                                            JSONObject jsonObject = new JSONObject(data);
                                            if(jsonObject.optInt("code") == 200){
                                                ToastUtil.showShort(context, jsonObject.optString("message"));
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
                })
                        .setTitleText("城市选择")
                        .setDividerColor(Color.BLACK)
                        .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                        .setContentTextSize(20)
                        .build();

        /*pvOptions.setPicker(options1Items);//一级选择器
        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
                pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
                pvOptions.show();
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

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

}
