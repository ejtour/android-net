package com.hll_sc_app.widget.order;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.impl.IStringListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/10
 */

public class OrderCancelDialog extends BaseDialog {
    @BindView(R.id.doc_group)
    TextView mGroup;
    @BindView(R.id.doc_shop)
    TextView mShop;
    @BindView(R.id.doc_edit)
    EditText mEdit;
    @BindView(R.id.doc_remain_num)
    TextView mRemainNum;
    private final IStringListener mListener;

    public OrderCancelDialog(@NonNull Activity context, IStringListener listener) {
        super(context);
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindow();
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_order_cancel, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public OrderCancelDialog setData(String group, String shop) {
        mGroup.setText(String.format("集团：%s", group));
        mShop.setText(String.format("门店：%s", shop));
        return this;
    }

    private void initWindow() {
        if (getWindow() == null) {
            return;
        }
        getWindow().setBackgroundDrawableResource(R.drawable.base_bg_white_radius_5_solid);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        getWindow().getAttributes().width = UIUtils.getScreenWidth(getContext()) - UIUtils.dip2px(110);
        setCancelable(false);
    }

    @OnTextChanged(R.id.doc_edit)
    public void onTextChanged(CharSequence s) {
        mRemainNum.setText(String.valueOf(200 - s.length()));
    }

    @OnClick({R.id.doc_cancel, R.id.doc_ok})
    public void onViewClicked(View view) {
        dismiss();
        if (view.getId() == R.id.doc_ok) {
            mListener.callback(mEdit.getText().toString().trim());
        }
    }
}
