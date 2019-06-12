package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.util.Logger;
import com.jingna.xssworkerapp.widget.DatePickerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class JiedanTimeSetActivity extends BaseActivity {

    private Context context = JiedanTimeSetActivity.this;

    @BindView(R.id.hour)
    DatePickerView hour;
    @BindView(R.id.minute)
    DatePickerView minute;

    private ArrayList<String> hourList, minuteList;
    private static final int MAX_MINUTE = 59;
    private static final int MAX_HOUR = 23;
    private static final int MIN_MINUTE = 0;
    private static final int MIN_HOUR = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiedan_time_set);

        ButterKnife.bind(JiedanTimeSetActivity.this);
        init();

    }

    private void init() {

        hourList = new ArrayList<>();
        for (int i = MIN_HOUR; i <= MAX_HOUR; i++) {
            hourList.add(formatTimeUnit(i));
        }
        minuteList = new ArrayList<>();
        for (int i = MIN_MINUTE; i <= MAX_MINUTE; i++) {
            minuteList.add(formatTimeUnit(i));
        }
        hour.setData(hourList);
        minute.setData(minuteList);
        hour.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                Logger.e("123123", text);
            }
        });
        minute.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                Logger.e("123123", text);
            }
        });

    }

    @OnClick({R.id.rl_back, R.id.rl_zao, R.id.rl_wan})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_zao:

                break;
            case R.id.rl_wan:

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
