package com.hll_sc_app.base.dialog;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.hll_sc_app.base.R;
import com.hll_sc_app.base.utils.UIUtils;


/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/2/11
 */

public class MakeSureDialog extends BaseDialog {

    private final OnClickListener mListener;

    TextView mTitle;
    TextView mContent;
    TextView mCancel;
    TextView mSure;

    public MakeSureDialog(@NonNull Activity context, OnClickListener listener) {
        super(context);
        initView();
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
    }

    private void initWindow() {
        if (getWindow() == null) {
            return;
        }
        getWindow().setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
        getWindow().getAttributes().width = UIUtils.getScreenWidth(getContext()) - UIUtils.dip2px(110);
        setCancelable(false);
    }

    private void initView() {

        mTitle = mRootView.findViewById(R.id.dp_title);
        mTitle.setText("提示");
        mContent = mRootView.findViewById(R.id.dp_content);
        mContent.setText("即将在浏览器下载或打开，点击确认前往");
        mCancel = mRootView.findViewById(R.id.dp_cancel);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mCancel.setText("取消");
        mCancel.setTextColor(ContextCompat.getColor(mCancel.getContext(), R.color.base_text999));
        mSure = mRootView.findViewById(R.id.dp_sure);
        mSure.setText("确定");
        mSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                mListener.onClick();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.base_dialog_make_sure, null);
        return view;
    }

    public interface OnClickListener {

        void onClick();
    }

}
