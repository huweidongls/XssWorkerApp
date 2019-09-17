package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.adapter.PaymentDetailsAdapter;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.bean.BudgetListBean;
import com.jingna.xssworkerapp.net.NetUrl;
import com.jingna.xssworkerapp.util.SpUtils;
import com.jingna.xssworkerapp.widget.DatePickerView;
import com.vise.xsnow.http.ViseHttp;
import com.vise.xsnow.http.callback.ACallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaymentDetailsActivity extends BaseActivity {

    private Context context = PaymentDetailsActivity.this;

    @BindView(R.id.rv)
    RecyclerView recyclerView;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_zhichu)
    TextView tvZhichu;
    @BindView(R.id.tv_shouru)
    TextView tvShouru;

    private PaymentDetailsAdapter adapter;
    private List<BudgetListBean.ObjBean.ListBean> mList;

    private int mYear;
    private int mMonth;

    private PopupWindow popupWindow;
    private View timeView;
    private DatePickerView year;
    private DatePickerView month;
    private TextView tvSure;
    private ArrayList<String> yearList, monthList;
    private static final int MAX_MONTH = 12;
    private static final int MIN_MONTH = 1;

    private String selectYear = "";
    private String selectMonth = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_details);

        Calendar ca = Calendar.getInstance();
        mYear = ca.get(Calendar.YEAR);
        mMonth = ca.get(Calendar.MONTH);
        ButterKnife.bind(PaymentDetailsActivity.this);
        initView();
        initData();

    }

    private void initView() {

        timeView = LayoutInflater.from(context).inflate(R.layout.popupwindow_payment_details, null);
        year = timeView.findViewById(R.id.hour);
        month = timeView.findViewById(R.id.minute);
        tvSure = timeView.findViewById(R.id.tv_sure);
        yearList = new ArrayList<>();
        for (int i = mYear; i > 2000; i--) {
            yearList.add(formatTimeUnit(i));
        }
        monthList = new ArrayList<>();
        for (int i = MIN_MONTH; i <= MAX_MONTH; i++) {
            monthList.add(formatTimeUnit(i));
        }
        year.setData(yearList);
        month.setData(monthList);
        year.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectYear = text;
            }
        });
        month.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                selectMonth = text;
            }
        });
        tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updata(selectYear+"-"+selectMonth);
                popupWindow.dismiss();
            }
        });

    }

    private void updata(final String date){
        ViseHttp.POST(NetUrl.budgetListUrl)
                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.budgetListUrl))
                .addParam("time", date)
                .addParam("uid", SpUtils.getUid(context))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String d) {
                        try {
                            JSONObject jsonObject = new JSONObject(d);
                            if(jsonObject.optInt("code") == 200){
                                Gson gson = new Gson();
                                BudgetListBean bean = gson.fromJson(d, BudgetListBean.class);
                                mList.clear();
                                mList.addAll(bean.getObj().getList());
                                adapter.notifyDataSetChanged();
                                tvZhichu.setText("支出 ￥"+String.format("%.2f", bean.getObj().getCount_money().getZ()));
                                tvShouru.setText("收入 ￥"+String.format("%.2f", bean.getObj().getCount_money().getS()));
                                tvTime.setText(date);
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

    private void initData() {

        tvTime.setText(mYear+"-"+formatTimeUnit(mMonth+1));
        selectYear = mYear+"";
        selectMonth = formatTimeUnit(mMonth+1);
        String time = mYear+"-"+formatTimeUnit(mMonth+1);

        ViseHttp.POST(NetUrl.budgetListUrl)
                .addParam("app_key", getToken(NetUrl.BASE_URL+NetUrl.budgetListUrl))
                .addParam("time", time)
                .addParam("uid", SpUtils.getUid(context))
                .request(new ACallback<String>() {
                    @Override
                    public void onSuccess(String data) {
                        try {
                            JSONObject jsonObject = new JSONObject(data);
                            if(jsonObject.optInt("code") == 200){
                                Gson gson = new Gson();
                                BudgetListBean bean = gson.fromJson(data, BudgetListBean.class);
                                mList = bean.getObj().getList();
                                adapter = new PaymentDetailsAdapter(mList);
                                LinearLayoutManager manager = new LinearLayoutManager(context);
                                manager.setOrientation(LinearLayoutManager.VERTICAL);
                                recyclerView.setLayoutManager(manager);
                                recyclerView.setAdapter(adapter);
                                tvZhichu.setText("支出 ￥"+String.format("%.2f", bean.getObj().getCount_money().getZ()));
                                tvShouru.setText("收入 ￥"+String.format("%.2f", bean.getObj().getCount_money().getS()));
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

    /**
     * 显示时间选择pop
     */
    private void showTimeSelect(){

        popupWindow = new PopupWindow(timeView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setFocusable(true);
        // 设置点击窗口外边窗口消失
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
//        popupWindow.showAsDropDown(rlPro);
        // 设置popWindow的显示和消失动画
        popupWindow.setAnimationStyle(R.style.mypopwindow_anim_style_bottom);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.update();

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams params = getWindow().getAttributes();
                params.alpha = 1f;
                getWindow().setAttributes(params);
            }
        });
    }

    @OnClick({R.id.rl_back, R.id.rl_time})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_time:
                year.setSelected(mYear+"");
                month.setSelected(formatTimeUnit(mMonth+1));
                showTimeSelect();
                break;
        }
    }

    /**
     * 将“0-9”转换为“00-09”
     */
    private String formatTimeUnit(int unit) {
        return unit < 10 ? "0" + String.valueOf(unit) : String.valueOf(unit);
    }

}
