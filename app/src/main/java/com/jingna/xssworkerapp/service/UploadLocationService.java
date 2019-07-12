package com.jingna.xssworkerapp.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.Logger;
import com.jingna.xssworkerapp.util.TokenUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2019/7/4.
 */

public class UploadLocationService extends Service {

    private Timer timer;
    private String id = "";

//    public LocationClient mLocationClient = null;
//    public BDLocationListener myListener = new MyLocationListener();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private double mCurrentLat;
    private double mCurrentLng;

    @Override
    public void onCreate() {
        super.onCreate();
        startLocate();
        Log.e("123123", "oncreate");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        id = intent.getStringExtra("id");
        uploadLocation();
        Log.e("123123", "onstart"+"--"+id);
    }

    private void uploadLocation() {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(mCurrentLat!=0&&mCurrentLng!=0){
                    ViseHttp.POST(NetUrl.uploadCoordinatesUrl)
                            .addParam("app_key", TokenUtils.getToken(NetUrl.BASE_URL+NetUrl.uploadCoordinatesUrl))
                            .addParam("lat", mCurrentLat+"")
                            .addParam("lng", mCurrentLng+"")
                            .addParam("oid", id)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.optInt("code") == 200){
                                            Logger.e("123123", "坐标上传成功");
                                        }else {
                                            Logger.e("123123", "坐标上传失败");
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
        }, 1000, 3000);

        startLocate();

    }

    /**
     * 定位
     */
    private void startLocate() {
//        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
//        mLocationClient.registerLocationListener(myListener);    //注册监听函数
//        LocationClientOption option = new LocationClientOption();
//        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving
//        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
//        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
//        int span = 10000;
//        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
//        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
//        option.setOpenGps(true);//可选，默认false,设置是否使用gps
//        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
//        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
//        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
//        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
//        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
//        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
//        mLocationClient.setLocOption(option);
//        //开启定位
//        mLocationClient.start();
//        Log.e("123123", "start");
    }

//    private class MyLocationListener implements BDLocationListener {
//
//        @Override
//        public void onReceiveLocation(BDLocation bdLocation) {
//            Log.e("123123", "listener");
//            mCurrentLat = bdLocation.getLatitude();
//            mCurrentLng = bdLocation.getLongitude();
//            Log.e("123123", "lat" + bdLocation.getLatitude() + "long" + bdLocation.getLongitude());
//        }
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(timer != null){
            timer.cancel();
        }
//        mLocationClient.stop();
    }

}
