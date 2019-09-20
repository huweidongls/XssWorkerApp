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
import android.view.View;
import android.widget.ImageView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.google.gson.Gson;
import com.jingna.xssworkerapp.MainActivity;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.BaiduCityBean;
import com.jingna.xssworkerapp.bean.LocationBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.Gps;
import com.jingna.xssworkerapp.util.GpsUtil;
import com.jingna.xssworkerapp.util.Logger;
import com.jingna.xssworkerapp.util.PositionUtil;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.util.StringUtils;
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

    private ImageView ivStart;

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    getCity();
                    break;
            }
        }

    };

    public LocationClient mLocationClient = null;
    public BDLocationListener myListener = new MyLocationListener();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        ivStart = findViewById(R.id.iv_start);
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

//        if (locationManager.getProvider(LocationManager.NETWORK_PROVIDER) != null || locationManager.getProvider(LocationManager.GPS_PROVIDER) != null) {
        if (GpsUtil.isLocServiceEnable(context)) {
            /*
             * 进行定位
             * provider:用于定位的locationProvider字符串:LocationManager.NETWORK_PROVIDER/LocationManager.GPS_PROVIDER
             * minTime:时间更新间隔，单位：ms
             * minDistance:位置刷新距离，单位：m
             * listener:用于定位更新的监听者locationListener
             */
            if(StringUtils.isEmpty(SpUtils.getIsFirst(context))){
                ivStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        getLocation();
                        startLocate();
                    }
                });
            }else {
//                getLocation();
                startLocate();
            }
        } else {
            //无法定位：1、提示用户打开定位服务；2、跳转到设置界面
            ToastUtil.showShort(context, "无法定位，请打开定位服务");
            Intent i = new Intent();
            i.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(i);
            finish();
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
            String url = "http://api.map.baidu.com/geocoder?output=json&location=" + latitude + "," + longitude + "&ak=5nRyBFW3zyya8gVbkUWtg7F6Oamna2aS";
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
                                                            if(SpUtils.getUid(context).equals("0")){
                                                                intent.setClass(context, LoginActivity.class);
                                                            }else {
                                                                intent.setClass(context, MainActivity.class);
                                                            }
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

    /**
     * 定位
     */
    private void startLocate() {
        mLocationClient = new com.baidu.location.LocationClient(context);     //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);    //注册监听函数
        com.baidu.location.LocationClientOption option = new com.baidu.location.LocationClientOption();
        option.setLocationMode(com.baidu.location.LocationClientOption.LocationMode.Battery_Saving
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("gcj02");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 2000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        //开启定位
        mLocationClient.start();
    }

    private class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (GpsUtil.isLocServiceEnable(context)) {
                latLongString = location.getProvince()+"-"+location.getCity();
                Logger.e("123123", latLongString+location.getLatitude()+location.getLongitude());
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
                                        if(SpUtils.getUid(context).equals("0")){
                                            intent.setClass(context, LoginActivity.class);
                                        }else {
                                            intent.setClass(context, MainActivity.class);
                                        }
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
        }
    }

}
