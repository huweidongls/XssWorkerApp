package com.jingna.xssworkerapp.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jingna.xssworkerapp.R;

/**
 * Created by Administrator on 2019/5/17.
 */

public class CustomDialog extends Dialog {

    private Context context;
    private String content;
    private ClickListener listener;
    private TextView tvContent;
    private ImageView ivCancel;
    private ImageView ivSure;

    public CustomDialog(@NonNull Context context, String content, ClickListener listener) {
        super(context, R.style.RoundCornerDialog);
        this.context = context;
        this.content = content;
        this.listener = listener;
        initView();
    }

    private void initView() {

        View view = LayoutInflater.from(context).inflate(R.layout.dialog_custom, null);
        setContentView(view);

        tvContent = view.findViewById(R.id.tv_content);
        ivSure = view.findViewById(R.id.iv_sure);
        ivCancel = view.findViewById(R.id.iv_cancel);
        tvContent.setText(content);

        ivSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onSure();
                dismiss();
            }
        });

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onCancel();
                dismiss();
            }
        });

    }

    public interface ClickListener{
        void onSure();
        void onCancel();
    }

}
