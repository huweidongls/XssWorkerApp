package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

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
    @BindView(R.id.rl_zao)
    RelativeLayout rlZao;
    @BindView(R.id.rl_wan)
    RelativeLayout rlWan;
    @BindView(R.id.tv_start_time)
    TextView tvStartTime;
    @BindView(R.id.tv_end_time)
    TextView tvEndTime;

    private ArrayList<String> hourList, minuteList;
    private static final int MAX_MINUTE = 59;
    private static final int MAX_HOUR = 23;
    private static final int MIN_MINUTE = 0;
    private static final int MIN_HOUR = 0;

    private boolean isZao = true;

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
                if(isZao){
                    String cur = tvStartTime.getText().toString();
                    StringBuilder sb = new StringBuilder(cur);
                    sb.delete(0, 2);
                    sb.insert(0, text);
                    tvStartTime.setText(sb.toString());
                }else {
                    String cur = tvEndTime.getText().toString();
                    StringBuilder sb = new StringBuilder(cur);
                    sb.delete(0, 2);
                    sb.insert(0, text);
                    tvEndTime.setText(sb.toString());
                }
            }
        });
        minute.setOnSelectListener(new DatePickerView.onSelectListener() {
            @Override
            public void onSelect(String text) {
                if(isZao){
                    String cur = tvStartTime.getText().toString();
                    StringBuilder sb = new StringBuilder(cur);
                    sb.delete(3, 5);
                    sb.insert(3, text);
                    tvStartTime.setText(sb.toString());
                }else {
                    String cur = tvEndTime.getText().toString();
                    StringBuilder sb = new StringBuilder(cur);
                    sb.delete(3, 5);
                    sb.insert(3, text);
                    tvEndTime.setText(sb.toString());
                }
            }
        });

    }

    @OnClick({R.id.rl_back, R.id.rl_zao, R.id.rl_wan, R.id.rl_save})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_zao:
                hour.setSelected(tvStartTime.getText().toString().substring(0, 2));
                minute.setSelected(tvStartTime.getText().toString().substring(3, 5));
                rlZao.setBackgroundColor(Color.parseColor("#C6E2FE"));
                rlWan.setBackgroundColor(Color.parseColor("#ffffff"));
                isZao = true;
                break;
            case R.id.rl_wan:
                hour.setSelected(tvEndTime.getText().toString().substring(0, 2));
                minute.setSelected(tvEndTime.getText().toString().substring(3, 5));
                rlZao.setBackgroundColor(Color.parseColor("#ffffff"));
                rlWan.setBackgroundColor(Color.parseColor("#C6E2FE"));
                isZao = false;
                break;
            case R.id.rl_save:
                Intent intent = new Intent();
                intent.putExtra("zao", tvStartTime.getText().toString());
                intent.putExtra("wan", tvEndTime.getText().toString());
                setResult(100, intent);
                finish();
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
