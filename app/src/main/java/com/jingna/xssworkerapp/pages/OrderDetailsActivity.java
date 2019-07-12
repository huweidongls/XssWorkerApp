package com.jingna.xssworkerapp.pages;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.navisdk.adapter.BNRoutePlanNode;
import com.baidu.navisdk.adapter.BNaviCommonParams;
import com.baidu.navisdk.adapter.BaiduNaviManagerFactory;
import com.baidu.navisdk.adapter.IBNRoutePlanManager;
import com.baidu.navisdk.adapter.IBaiduNaviManager;
import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.baidu.NormalUtils;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.BaiduPositionBean;
import com.jingna.xssworkerapp.bean.OrderContentBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.service.UploadLocationService;
import com.jingna.xssworkerapp.util.Gps;
import com.jingna.xssworkerapp.util.Logger;
import com.jingna.xssworkerapp.util.PositionUtil;
import com.jingna.xssworkerapp.util.StringUtils;
import com.jingna.xssworkerapp.util.TokenUtils;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OrderDetailsActivity extends BaseActivity {

    private Context context = OrderDetailsActivity.this;

    private static final String APP_FOLDER_NAME = "yijieworker";

    static final String ROUTE_PLAN_NODE = "routePlanNode";

    private static final int NORMAL = 0;
    private static final int EXTERNAL = 1;

    private static final String[] authBaseArr = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    private String mSDCardPath = null;

    private static final int authBaseRequestCode = 1;

    private boolean hasInitSuccess = false;

    private BNRoutePlanNode mStartNode = null;
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
            Logger.e("123123", "zoule"+mCurrentLat+"--"+mCurrentLng);
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

    @BindView(R.id.tv_service_type)
    TextView tvServiceType;
    @BindView(R.id.tv_order_id)
    TextView tvOrderId;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_remarks)
    TextView tvRemarks;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.btn_go)
    Button btnGo;

    private String id = "";
    private double lat;
    private double lng;
    private String address = "";

    private String radio = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);

        id = getIntent().getStringExtra("id");
        ButterKnife.bind(OrderDetailsActivity.this);

        initData();

        if (initDirs()) {
            initNavi();
        }
        initLocation();

    }

    private void initData() {

        ViseHttp.POST(NetUrl.order_ContentUrl)
                .addParam("app_key", TokenUtils.getToken(NetUrl.BASE_URL + NetUrl.order_ContentUrl))
                .addParam("oid", id)
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if (jsonObject.optInt("code") == 200) {
                                Gson gson = new Gson();
                                OrderContentBean bean = gson.fromJson(data, OrderContentBean.class);
                                tvServiceType.setText(bean.getObj().getService_type());
                                tvOrderId.setText(bean.getObj().getOrder_code());
                                tvTime.setText(bean.getObj().getPretime());
                                tvAddress.setText(bean.getObj().getAddress());
                                if (StringUtils.isEmpty(bean.getObj().getRemarks())) {
                                    tvRemarks.setText("无");
                                } else {
                                    tvRemarks.setText(bean.getObj().getRemarks());
                                }
                                lat = Double.valueOf(bean.getObj().getLat());
                                lng = Double.valueOf(bean.getObj().getLng());
                                address = bean.getObj().getAddress();
                                radio = bean.getObj().getRadio();
                                if(radio.equals("0")){
                                    btnStart.setText("服务开始");
                                    btnStart.setVisibility(View.VISIBLE);
                                }else if(radio.equals("1")){
                                    btnStart.setText("服务结束");
                                    btnStart.setVisibility(View.VISIBLE);
                                }else {
                                    btnStart.setVisibility(View.GONE);
                                }
                                if(radio.equals("0")){
                                    btnGo.setVisibility(View.VISIBLE);
                                }else {
                                    btnGo.setVisibility(View.GONE);
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

    private void calRoutePlanNode(final int coType) {
        if (!hasInitSuccess) {
            Toast.makeText(OrderDetailsActivity.this.getApplicationContext(), "还未初始化!", Toast
                    .LENGTH_SHORT).show();
        }

        BNRoutePlanNode sNode = new BNRoutePlanNode.Builder()
                .latitude(40.05087)
                .longitude(116.30142)
                .name("百度大厦")
                .description("百度大厦")
                .coordinateType(coType)
                .build();
        BNRoutePlanNode eNode = new BNRoutePlanNode.Builder()
                .latitude(39.90882)
                .longitude(116.39750)
                .name("北京天安门")
                .description("北京天安门")
                .coordinateType(coType)
                .build();
        switch (coType) {
            case BNRoutePlanNode.CoordinateType.GCJ02: {
                sNode = new BNRoutePlanNode.Builder()
                        .latitude(40.05087)
                        .longitude(116.30142)
                        .name("百度大厦")
                        .description("百度大厦")
                        .coordinateType(coType)
                        .build();
                eNode = new BNRoutePlanNode.Builder()
                        .latitude(39.90882)
                        .longitude(116.39750)
                        .name("北京天安门")
                        .description("北京天安门")
                        .coordinateType(coType)
                        .build();
                break;
            }
            case BNRoutePlanNode.CoordinateType.WGS84: {
                sNode = new BNRoutePlanNode.Builder()
                        .latitude(40.050969)
                        .longitude(116.300821)
                        .name("百度大厦")
                        .description("百度大厦")
                        .coordinateType(coType)
                        .build();
                eNode = new BNRoutePlanNode.Builder()
                        .latitude(39.908749)
                        .longitude(116.397491)
                        .name("北京天安门")
                        .description("北京天安门")
                        .coordinateType(coType)
                        .build();
                break;
            }
            case BNRoutePlanNode.CoordinateType.BD09_MC: {
                sNode = new BNRoutePlanNode.Builder()
                        .latitude(4846474)
                        .longitude(12947471)
                        .name("百度大厦")
                        .description("百度大厦")
                        .coordinateType(coType)
                        .build();
                eNode = new BNRoutePlanNode.Builder()
                        .latitude(4825947)
                        .longitude(12958160)
                        .name("北京天安门")
                        .description("北京天安门")
                        .coordinateType(coType)
                        .build();
                break;
            }
            case BNRoutePlanNode.CoordinateType.BD09LL: {
                sNode = new BNRoutePlanNode.Builder()
                        .latitude(40.057009624099436)
                        .longitude(116.30784537597782)
                        .name("百度大厦")
                        .description("百度大厦")
                        .coordinateType(coType)
                        .build();
                eNode = new BNRoutePlanNode.Builder()
                        .latitude(39.915160800132085)
                        .longitude(116.40386525193937)
                        .name("北京天安门")
                        .description("北京天安门")
                        .coordinateType(coType)
                        .build();
                break;
            }
            default:
                break;
        }

        mStartNode = sNode;
        routePlanToNavi(sNode, eNode, NORMAL);
    }

    private void initLocation() {
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (mLocationManager != null) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission
                    .ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat
                    .checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "没有权限", Toast.LENGTH_SHORT).show();
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    1000, 1000, mLocationListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(mLocationListener);
        }
    }

    private boolean initDirs() {
        mSDCardPath = getSdcardDir();
        if (mSDCardPath == null) {
            return false;
        }
        File f = new File(mSDCardPath, APP_FOLDER_NAME);
        if (!f.exists()) {
            try {
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    private String getSdcardDir() {
        if (Environment.getExternalStorageState().equalsIgnoreCase(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().toString();
        }
        return null;
    }

    private void initTTS() {
        // 使用内置TTS
        BaiduNaviManagerFactory.getTTSManager().initTTS(getApplicationContext(),
                getSdcardDir(), APP_FOLDER_NAME, NormalUtils.getTTSAppID());

    }

    @OnClick({R.id.rl_back, R.id.btn_go, R.id.btn_start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_go:
                if(lat != 0&&lng != 0){
                    Gps gps = PositionUtil.bd09_To_Gps84(lat, lng);
                    if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited()) {
                        if (mCurrentLat == 0 && mCurrentLng == 0) {
                            return;
                        }

//                        Intent intent = new Intent(context, UploadLocationService.class);
//                        intent.putExtra("id", id);
//                        startService(intent);

                        BNRoutePlanNode sNode = new BNRoutePlanNode.Builder()
                                .latitude(mCurrentLat)
                                .longitude(mCurrentLng)
                                .coordinateType(BNRoutePlanNode.CoordinateType.WGS84)
                                .build();
                        BNRoutePlanNode eNode = new BNRoutePlanNode.Builder()
                                .latitude(gps.getWgLat())
                                .longitude(gps.getWgLon())
                                .name(address)
                                .description(address)
                                .coordinateType(BNRoutePlanNode.CoordinateType.WGS84)
                                .build();
                        mStartNode = sNode;

                        routePlanToNavi(sNode, eNode, NORMAL);
                    }
                }
//                if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited()) {
//                    calRoutePlanNode(BNRoutePlanNode.CoordinateType.GCJ02);
//                }
                break;
            case R.id.btn_start:
                if(radio.equals("0")){
//                    stopService(new Intent(this, UploadLocationService.class));
                    ViseHttp.POST(NetUrl.service_startUrl)
                            .addParam("app_key", TokenUtils.getToken(NetUrl.BASE_URL+NetUrl.service_startUrl))
                            .addParam("oid", id)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.optInt("code") == 200){
                                            radio = "1";
                                            btnStart.setText("服务结束");
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onFail(int errCode, String errMsg) {

                                }
                            });
                }else if(radio.equals("1")){
                    ViseHttp.POST(NetUrl.service_endUrl)
                            .addParam("app_key", TokenUtils.getToken(NetUrl.BASE_URL+NetUrl.service_endUrl))
                            .addParam("oid", id)
                            .request(new ACallback<String>() {
                                @Override
                                public void onSuccess(String data) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(data);
                                        if(jsonObject.optInt("code") == 200){
                                            radio = "2";
                                            btnStart.setVisibility(View.GONE);
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
                break;
        }
    }

    private void routePlanToNavi(BNRoutePlanNode sNode, BNRoutePlanNode eNode, final int from) {
        List<BNRoutePlanNode> list = new ArrayList<>();
        list.add(sNode);
        list.add(eNode);

//        BaiduNaviManagerFactory.getCommonSettingManager().setCarNum(this, "粤B66666");
        BaiduNaviManagerFactory.getRoutePlanManager().routeplanToNavi(
                list,
                IBNRoutePlanManager.RoutePlanPreference.ROUTE_PLAN_PREFERENCE_DEFAULT,
                null,
                new Handler(Looper.getMainLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        switch (msg.what) {
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_START:
                                Toast.makeText(OrderDetailsActivity.this.getApplicationContext(),
                                        "算路开始", Toast.LENGTH_SHORT).show();
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_SUCCESS:
                                Toast.makeText(OrderDetailsActivity.this.getApplicationContext(),
                                        "算路成功", Toast.LENGTH_SHORT).show();
                                // 躲避限行消息
                                Bundle infoBundle = (Bundle) msg.obj;
                                if (infoBundle != null) {
                                    String info = infoBundle.getString(
                                            BNaviCommonParams.BNRouteInfoKey.TRAFFIC_LIMIT_INFO
                                    );
                                    Log.d("OnSdkDemo", "info = " + info);
                                }
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_FAILED:
                                Toast.makeText(OrderDetailsActivity.this.getApplicationContext(),
                                        "算路失败", Toast.LENGTH_SHORT).show();
                                BaiduNaviManagerFactory.getRoutePlanManager()
                                        .removeRequestByHandler(this);
                                break;
                            case IBNRoutePlanManager.MSG_NAVI_ROUTE_PLAN_TO_NAVI:
                                Toast.makeText(OrderDetailsActivity.this.getApplicationContext(),
                                        "算路成功准备进入导航", Toast.LENGTH_SHORT).show();

                                Intent intent = null;
                                if (from == NORMAL) {
                                    intent = new Intent(OrderDetailsActivity.this,
                                            DemoGuideActivity.class);
                                } else if (from == EXTERNAL) {
                                    intent = new Intent(OrderDetailsActivity.this,
                                            DemoExtGpsActivity.class);
                                }

                                Bundle bundle = new Bundle();
                                bundle.putSerializable(ROUTE_PLAN_NODE, mStartNode);
                                intent.putExtras(bundle);
                                startActivity(intent);
                                BaiduNaviManagerFactory.getRoutePlanManager()
                                        .removeRequestByHandler(this);
                                break;
                            default:
                                // nothing
                                break;
                        }
                    }
                });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[]
            grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == authBaseRequestCode) {
            for (int ret : grantResults) {
                if (ret == 0) {
                    continue;
                } else {
                    Toast.makeText(OrderDetailsActivity.this.getApplicationContext(),
                            "缺少导航基本的权限!", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            initNavi();
        }
    }

    private boolean hasBasePhoneAuth() {
        PackageManager pm = this.getPackageManager();
        for (String auth : authBaseArr) {
            if (pm.checkPermission(auth, this.getPackageName()) != PackageManager
                    .PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    private void initNavi() {
        // 申请权限
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (!hasBasePhoneAuth()) {
                this.requestPermissions(authBaseArr, authBaseRequestCode);
                return;
            }
        }

        if (BaiduNaviManagerFactory.getBaiduNaviManager().isInited()) {
            hasInitSuccess = true;
            return;
        }

        BaiduNaviManagerFactory.getBaiduNaviManager().init(this,
                mSDCardPath, APP_FOLDER_NAME, new IBaiduNaviManager.INaviInitListener() {

                    @Override
                    public void onAuthResult(int status, String msg) {
                        String result;
                        if (0 == status) {
                            result = "key校验成功!";
                        } else {
                            result = "key校验失败, " + msg;
                        }
//                        Toast.makeText(OrderDetailsActivity.this, result, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void initStart() {
                        Toast.makeText(OrderDetailsActivity.this.getApplicationContext(),
                                "百度导航引擎初始化开始", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void initSuccess() {
                        Toast.makeText(OrderDetailsActivity.this.getApplicationContext(),
                                "百度导航引擎初始化成功", Toast.LENGTH_SHORT).show();
                        hasInitSuccess = true;
                        // 初始化tts
                        initTTS();
                    }

                    @Override
                    public void initFailed(int errCode) {
                        Toast.makeText(OrderDetailsActivity.this.getApplicationContext(),
                                "百度导航引擎初始化失败 " + errCode, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
