package com.jingna.xssworkerapp.service;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;

import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.Gps;
import com.jingna.xssworkerapp.util.Logger;
import com.jingna.xssworkerapp.util.PositionUtil;
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private double mCurrentLat;
    private double mCurrentLng;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            mCurrentLat = location.getLatitude();
            mCurrentLng = location.getLongitude();
//            Toast.makeText(OrderDetailsActivity.this, mCurrentLat
//                    + "--" + mCurrentLng, Toast.LENGTH_LONG).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        initLocation();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        id = intent.getStringExtra("id");
        uploadLocation();
    }

    private void uploadLocation() {

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(mCurrentLat!=0&&mCurrentLng!=0){
                    Gps gps = PositionUtil.Gps84_To_bd09(mCurrentLat, mCurrentLng);
                    ViseHttp.POST(NetUrl.uploadCoordinatesUrl)
                            .addParam("app_key", TokenUtils.getToken(NetUrl.BASE_URL+NetUrl.uploadCoordinatesUrl))
                            .addParam("lat", gps.getWgLat()+"")
                            .addParam("lng", gps.getWgLon()+"")
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
        }, 1000, 10000);

    }

    private void initLocation() {
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission
                    .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                    .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
//                Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    2000, 1000, mLocationListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
        if(timer != null){
            timer.cancel();
        }
    }

}
