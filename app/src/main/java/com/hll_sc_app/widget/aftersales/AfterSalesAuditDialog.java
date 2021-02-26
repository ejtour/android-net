package com.hll_sc_app.widget.aftersales;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hll_sc_app.R;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.aftersales.RefundMethodBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.rest.AfterSales;
import com.hll_sc_app.widget.SingleSelectionDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/22
 */

public class AfterSalesAuditDialog extends BaseDialog {
    @BindView(R.id.asa_select_method)
    TextView mSelectMethod;
    @BindView(R.id.asa_remark)
    EditText mRemark;
    @BindView(R.id.asa_remain_length)
    TextView mRemainLength;
    private SingleSelectionDialog mDialog;
    private static final int LIMIT = 200;
    private AuditCallback mCallback;
    private List<RefundMethodBean> mBeans;
    private ILoadView mLoadView;
    private String mRefundBillID;

    public interface AuditCallback {
        void callback(String payType, String remark);
    }

    public AfterSalesAuditDialog setCallback(AuditCallback callback) {
        mCallback = callback;
        return this;
    }

    public AfterSalesAuditDialog withLoadView(ILoadView loadView) {
        mLoadView = loadView;
        return this;
    }

    public AfterSalesAuditDialog withRefundBillID(String refundBillID) {
        mRefundBillID = refundBillID;
        return this;
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

    private AfterSalesAuditDialog(@NonNull Activity context) {
        super(context);
        setOwnerActivity(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = View.inflate(getContext(), R.layout.window_after_sales_audit, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public static AfterSalesAuditDialog create(Activity context) {
        return new AfterSalesAuditDialog(context);
    }

    @OnClick(R.id.asa_select_method)
    public void select() {
        if (mBeans == null) {
            AfterSales.getRefundMethod(mRefundBillID, new SimpleObserver<SingleListResp<RefundMethodBean>>(mLoadView) {
                @Override
                public void onSuccess(SingleListResp<RefundMethodBean> refundMethodBeanSingleListResp) {
                    mBeans = refundMethodBeanSingleListResp.getRecords();
                    select();
                }
            });
            return;
        }
        if (mDialog == null) {
            mDialog = SingleSelectionDialog.newBuilder((getOwnerActivity()), RefundMethodBean::getTypeName)
                    .refreshList(mBeans)
                    .setTitleText("请选择退款方式")
                    .setOnSelectListener(nameValue -> {
                        mSelectMethod.setText(nameValue.getTypeName());
                        mSelectMethod.setTag(nameValue.getTypeID());
                    })
                    .create();
            mDialog.setOnDismissListener(dialog -> show());
        }
        mDialog.show();
    }

    @OnClick(R.id.asa_cancel)
    public void cancel() {
        dismiss();
    }

    @OnClick(R.id.asa_ok)
    public void ok() {
        if (mSelectMethod.getTag() != null) {
            if (mCallback != null)
                mCallback.callback(mSelectMethod.getTag().toString(), mRemark.getText().toString());
            dismiss();
        } else ToastUtils.showShort(getContext(), "请选择退款方式");
    }

    @OnTextChanged(R.id.asa_remark)
    public void textChanged(CharSequence charSequence) {
        mRemainLength.setText(String.valueOf(TextUtils.isEmpty(charSequence) ? LIMIT : LIMIT - charSequence.length()));
    }
}
