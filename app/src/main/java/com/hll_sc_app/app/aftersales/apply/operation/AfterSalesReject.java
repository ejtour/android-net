package com.hll_sc_app.app.aftersales.apply.operation;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.app.aftersales.apply.IAfterSalesApplyContract;
import com.hll_sc_app.app.aftersales.apply.presenter.AfterSalesRejectPresenter;
import com.hll_sc_app.app.aftersales.apply.text.AfterSalesRejectText;
import com.hll_sc_app.app.order.details.OrderDetailAdapter;
import com.hll_sc_app.app.submit.SubmitSuccessActivity;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyParam;
import com.hll_sc_app.bean.order.detail.OrderDepositBean;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */

public class AfterSalesReject extends BaseAfterSalesApply {

    private AfterSalesApplyParam mResp;

    public AfterSalesReject() {
        super(new AfterSalesRejectText());
        mReasonList = new ArrayList<>();
        String[] items = {"与供应商协商拒收", "商品质量问题", "商品与描述不符", "不想要了", "其他"};
        for (int i = 0; i < items.length; i++) {
            NameValue e = new NameValue(items[i], String.valueOf(i + 1));
            mReasonList.add(e);
        }
    }

    @Override
    public void init(AfterSalesApplyParam resp) {
        mResp = resp;
    }

    @Override
    public IAfterSalesApplyContract.IAfterSalesApplyPresenter getPresenter() {
        return new AfterSalesRejectPresenter(mResp);
    }

    @Override
    public void submitSuccess(String id) {
        SubmitSuccessActivity.start("订单拒收提交成功");
    }

    @Override
    public BaseQuickAdapter createAdapter() {
        for (OrderDetailBean bean : mResp.getOrderDetailList()) {
            bean.setInspectionNum(0);
            if (CommonUtils.isEmpty(bean.getDepositList())) continue;
            for (OrderDepositBean depositBean : bean.getDepositList()) {
                depositBean.setProductNum(0);
            }
        }
        return new OrderDetailAdapter(mResp.getOrderDetailList(), OrderDetailAdapter.REJECT_TEXT);
    }
}
