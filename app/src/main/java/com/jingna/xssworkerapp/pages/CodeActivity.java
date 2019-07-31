package com.jingna.xssworkerapp.pages;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jingna.xssworkerapp.R;
import com.jingna.xssworkerapp.base.BaseActivity;
import com.jingna.xssworkerapp.util.StringUtils;
import com.jingna.xssworkerapp.util.ToastUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CodeActivity extends BaseActivity {

    private Context context = CodeActivity.this;

    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_tel)
    TextView tvTel;

    private String type = "";
    private String code = "";
    private String tel = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code);

        type = getIntent().getStringExtra("type");
        code = getIntent().getStringExtra("code");
        tel = getIntent().getStringExtra("tel");
        ButterKnife.bind(CodeActivity.this);

        initData();

    }

    private void initData() {

        tvTel.setText(tel);

    }

    @OnClick({R.id.rl_back, R.id.btn_next})
    public void onClick(View view){
        Intent intent = new Intent();
        switch (view.getId()){
            case R.id.rl_back:
                finish();
                break;
            case R.id.btn_next:
                String str = etCode.getText().toString();
                if(StringUtils.isEmpty(str)){
                    ToastUtil.showShort(context, "验证码不能为空");
                }else if(code.equals(str)) {
                    intent.setClass(context, SetPwdActivity.class);
                    intent.putExtra("type", type);
                    intent.putExtra("tel", tel);
                    startActivity(intent);
                    finish();
                }else {
                    ToastUtil.showShort(context, "验证码不正确");
                }
                break;
        }
    }

}
