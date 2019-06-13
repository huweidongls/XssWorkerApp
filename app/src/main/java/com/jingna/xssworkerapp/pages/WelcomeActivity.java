package com.jingna.xssworkerapp.pages;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import com.google.gson.Gson;
import com.jingna.xssworkerapp.MainActivity;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.BaiduCityBean;
import com.jingna.xssworkerapp.bean.LocationBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.Gps;
import com.jingna.xssworkerapp.util.Logger;
import com.jingna.xssworkerapp.util.PositionUtil;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.ToastUtil;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;
import com.vise.xsnow.permission.OnPermissionCallback;
import com.vise.xsnow.permission.PermissionManager;

import org.json.JSONException;
import org.json.JSONObject;

public class WelcomeActivity extends BaseActivity {

    private Context context = WelcomeActivity.this;

    private LocationManager locationManager;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private String latLongString = "";

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getCity();
                    break;
            }
        }

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        PermissionManager.instance().request(this, new OnPermissionCallback() {
                    @Override
                    public void onRequestAllow(String permissionName) {
//                DialogUtil.showTips(mContext, getString(R.string.permission_control),
//                        getString(R.string.permission_allow) + "\n" + permissionName);
                    }

                    @Override
                    public void onRequestRefuse(String permissionName) {
//                DialogUtil.showTips(mContext, getString(R.string.permission_control),
//                        getString(R.string.permission_refuse) + "\n" + permissionName);
                    }

                    @Override
                    public void onRequestNoAsk(String permissionName) {
//                DialogUtil.showTips(mContext, getString(R.string.permission_control),
//                        getString(R.string.permission_noAsk) + "\n" + permissionName);
                    }
                }, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE);
        initData();

    }

    private void initData() {

        if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null || locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
            /*
             * 进行定位
             * provider:用于定位的locationProvider字符串:LocationManager.NETWORK_PROVIDER/LocationManager.GPS_PROVIDER
             * minTime:时间更新间隔，单位：ms
             * minDistance:位置刷新距离，单位：m
             * listener:用于定位更新的监听者locationListener
             */
            getLocation();
        } else {
            //无法定位：1、提示用户打开定位服务；2、跳转到设置界面
            ToastUtil.showShort(context, "无法定位，请打开定位服务");
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(i);
        }

    }

    public void getLocation() {
        new Thread() {
            @Override
            public void run() {
                if (ActivityCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(WelcomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ToastUtil.showShort(context, "请开启定位权限");
                    return;
                }
                Logger.i("123123", "有权限");
                Location location = locationManager
                        .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                if (location != null) {
                    Gps gps = PositionUtil.Gps84_To_bd09(location.getLatitude(), location.getLongitude());
                    latitude = gps.getWgLat();
                    longitude = gps.getWgLon();
                    Logger.e("123123", latitude+"----"+longitude);
                    handler.sendEmptyMessage(1);
                }
            }
        }.start();
    }

    public void getCity() {
        try {
            String url = "http://api.map.baidu.com/geocoder?output=json&location=" + latitude + "," + longitude + "&key=8dDPAEEMwPNZgxg4YhNUXqWoV8GNItO1";
            ViseHttp.GET(url)
                    .request(new ACallback<String>() {
                        @Override
                        public void onSuccess(String data) {
                            try {
                                JSONObject jsonObject = new JSONObject(data);
                                if (jsonObject.getString("status").equals("OK")) {
                                    Gson gson = new Gson();
                                    BaiduCityBean model = gson.fromJson(data, BaiduCityBean.class);
                                    latLongString = model.getResult().getAddressComponent().getProvince()+"-"+model.getResult().getAddressComponent().getCity();
                                    ViseHttp.POST(NetUrl.locationUrl)
                                            .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.locationUrl))
                                            .addParam("text", latLongString)
                                            .request(new ACallback<String>() {
                                                @Override
                                                public void onSuccess(String data) {
                                                    try {
                                                        JSONObject jsonObject1 = new JSONObject(data);
                                                        if(jsonObject1.optInt("code") == 200){
                                                            Gson gson1 = new Gson();
                                                            LocationBean bean = gson1.fromJson(data, LocationBean.class);
                                                            Logger.e("2222", bean.getObj().getId());
                                                            Intent intent = new Intent();
                                                            intent.setClass(context, MainActivity.class);
                                                            SpUtils.setCityId(context, bean.getObj().getId());
                                                            SpUtils.setCityName(context, bean.getObj().getCity_area());
                                                            startActivity(intent);
                                                            finish();
                                                        }else {
                                                            ToastUtil.showShort(context, "当前城市未开通");
                                                            String[] s = latLongString.split("-");
                                                            Intent intent = new Intent();
                                                            intent.setClass(context, CityActivity.class);
                                                            intent.putExtra("city", s[1]);
                                                            startActivity(intent);
                                                            finish();
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
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFail(int errCode, String errMsg) {

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
