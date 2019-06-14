package com.hll_sc_app.widget.order;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.order.deliver.ExpressResp;
import com.hll_sc_app.citymall.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/14
 */

public class ExpressInfoDialog extends BaseDialog {
    @BindView(R.id.dei_select_company)
    TextView mSelectCompany;
    @BindView(R.id.dei_no)
    EditText mNo;
    private ExpressCallback mExpressCallback;
    private List<ExpressResp.ExpressBean> mExpressBeans;
    private ExpressResp.ExpressBean mCompany;

    public ExpressInfoDialog(@NonNull Activity context, ExpressCallback callback) {
        super(context);
        mExpressCallback = callback;
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
        getWindow().setBackgroundDrawableResource(R.drawable.bg_white_radius_5_solid);
        getWindow().getAttributes().width = UIUtils.getScreenWidth(getContext()) - UIUtils.dip2px(110);
        setCancelable(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_express_info, null);
        ButterKnife.bind(this, view);
        return view;
    }

    public interface ExpressCallback {
        void onGetExpressInfo(String name, String orderNo);

        void onSelectCompany(ExpressResp.ExpressBean company, List<ExpressResp.ExpressBean> beans);
    }

    public void setCompany(ExpressResp.ExpressBean company, List<ExpressResp.ExpressBean> beans) {
        mCompany = company;
        mSelectCompany.setText(company.getDeliveryCompanyName());
        mExpressBeans = beans;
    }

    @OnClick({R.id.dei_select_company, R.id.dei_cancel, R.id.dei_ok})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.dei_select_company:
                mExpressCallback.onSelectCompany(mCompany, mExpressBeans);
                break;
            case R.id.dei_cancel:
                dismiss();
                break;
            case R.id.dei_ok:
                String name = mSelectCompany.getText().toString();
                String orderNo = mNo.getText().toString();
                if (TextUtils.isEmpty(name)) {
                    ToastUtils.showShort(getContext(), mSelectCompany.getHint().toString());
                    break;
                }
                if (TextUtils.isEmpty(orderNo)) {
                    ToastUtils.showShort(getContext(), mNo.getHint().toString());
                    break;
                }
                mExpressCallback.onGetExpressInfo(name, orderNo);
                dismiss();
                break;
        }
    }
}
