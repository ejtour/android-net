package com.hll_sc_app.widget.aftersales;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.apply.IAfterSalesApplyContract;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyParam;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/12
 */

public class AfterSaleApplyFooter extends ConstraintLayout {
    @BindView(R.id.saf_money_label)
    TextView mMoneyLabel;
    @BindView(R.id.saf_money)
    TextView mMoney;
    @BindView(R.id.saf_money_group)
    Group mMoneyGroup;
    @BindView(R.id.saf_tips)
    TextView mTips;

    public AfterSaleApplyFooter(Context context) {
        this(context, null);
    }

    public AfterSaleApplyFooter(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AfterSaleApplyFooter(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_after_sales_apply_footer, this);
        ButterKnife.bind(this, view);
    }

    public void init(IAfterSalesApplyContract.IAfterSalesApplyText afterSalesText) {
        if (!TextUtils.isEmpty(afterSalesText.getMoneyLabel())) {
            mMoneyGroup.setVisibility(VISIBLE);
            mMoneyLabel.setText(afterSalesText.getMoneyLabel());
        } else mMoneyGroup.setVisibility(GONE);
        mTips.setText(afterSalesText.getWarmTip());
        mMoneyGroup.getParent().requestLayout();
    }

    public void updateMoney(AfterSalesApplyParam param) {
        if (mMoneyGroup.getVisibility() == VISIBLE) {
            double total = 0;
            if (!CommonUtils.isEmpty(param.getAfterSalesDetailList())) {
                for (AfterSalesDetailsBean bean : param.getAfterSalesDetailList()) {
                    total = CommonUtils.addDouble(total, bean.getPendingRefundAmount(), 0).doubleValue();
                }
            }
            mMoney.setText(String.format("¥%s", CommonUtils.formatMoney(total)));
        }
    }
}
