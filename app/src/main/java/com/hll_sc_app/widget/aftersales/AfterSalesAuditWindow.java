package com.hll_sc_app.widget.aftersales;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/22
 */

public class AfterSalesAuditWindow extends BaseShadowPopupWindow implements PopupWindow.OnDismissListener {
    @BindView(R.id.asa_select_method)
    TextView mSelectMethod;
    @BindView(R.id.asa_remark)
    EditText mRemark;
    @BindView(R.id.asa_remain_length)
    TextView mRemainLength;
    private SingleSelectionDialog mDialog;
    private static final int LIMIT = 200;
    private AuditCallback mCallback;

    public interface AuditCallback {
        void callback(String payType, String remark);
    }

    public AfterSalesAuditWindow canModify(boolean modify) {
        mSelectMethod.setVisibility(modify ? View.VISIBLE : View.GONE);
        return this;
    }

    public AfterSalesAuditWindow setCallback(AuditCallback callback) {
        mCallback = callback;
        return this;
    }

    private AfterSalesAuditWindow(@NonNull Activity context) {
        super(context);
        View view = View.inflate(context, R.layout.window_after_sales_audit, null);
        ButterKnife.bind(this, view);
        setContentView(view);
        setWidth(UIUtils.getScreenWidth(context) - UIUtils.dip2px(110));
        setBackgroundDrawable(new ColorDrawable());
        setFocusable(true);
    }

    public static AfterSalesAuditWindow create(Activity context) {
        return new AfterSalesAuditWindow(context);
    }


    @OnClick(R.id.asa_select_method)
    public void select() {
        if (mDialog == null) {
            List<NameValue> list = new ArrayList<>();
            list.add(new NameValue("货到付款", "1"));
            list.add(new NameValue("账期支付", "2"));
            mDialog = SingleSelectionDialog.newBuilder(mActivity, NameValue::getName)
                    .refreshList(list)
                    .setTitleText("请选择退款方式")
                    .setOnSelectListener(nameValue -> {
                        mSelectMethod.setText(nameValue.getName());
                        mSelectMethod.setTag(nameValue.getValue());
                    })
                    .create();
            mDialog.setOnDismissListener(dialog -> showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0));
        }
        mDialog.show();
    }

    @OnClick(R.id.asa_cancel)
    public void cancel() {
        dismiss();
    }

    @OnClick(R.id.asa_ok)
    public void ok() {
        if (mSelectMethod.getVisibility() == View.VISIBLE) {
            if (mSelectMethod.getTag() != null) {
                if (mCallback != null) {
                    mCallback.callback(mSelectMethod.getTag().toString(), mRemark.getText().toString());
                }
                dismiss();
            } else {
                ToastUtils.showShort(mActivity, "请选择退款方式");
            }
        } else {
            if (mCallback != null) {
                mCallback.callback(null, mRemark.getText().toString());
            }
            dismiss();
        }
    }

    @OnTextChanged(R.id.asa_remark)
    public void textChanged(CharSequence charSequence) {
        mRemainLength.setText(String.valueOf(TextUtils.isEmpty(charSequence) ? LIMIT : LIMIT - charSequence.length()));
    }

    @Override
    public void onDismiss() {
        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.CENTER, 0, 0);
    }
}
