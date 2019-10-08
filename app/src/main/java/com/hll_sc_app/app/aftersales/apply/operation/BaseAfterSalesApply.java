package com.hll_sc_app.app.aftersales.apply.operation;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.apply.IAfterSalesApplyContract;
import com.hll_sc_app.app.aftersales.apply.presenter.AfterSalesApplyPresenter;
import com.hll_sc_app.app.aftersales.detail.AfterSalesDetailAdapter;
import com.hll_sc_app.app.submit.SubmitSuccessActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyParam;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.bean.window.NameValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */

public abstract class BaseAfterSalesApply implements IAfterSalesApplyContract.IAfterSalesApplyStrategy {

    private AfterSalesApplyParam mParam;
    private IAfterSalesApplyContract.IAfterSalesApplyText mApplyText;
    List<NameValue> mReasonList;
    private AfterSalesDetailAdapter mAdapter;

    BaseAfterSalesApply(IAfterSalesApplyContract.IAfterSalesApplyText applyText) {
        mApplyText = applyText;
    }

    @Override
    public IAfterSalesApplyContract.IAfterSalesApplyText getApplyText() {
        return mApplyText;
    }

    @Override
    public void init(AfterSalesApplyParam param) {
        mParam = param;
    }

    @Override
    public List<NameValue> getReasonList() {
        return mReasonList;
    }

    public void setReasonList(List<NameValue> reasonList) {
        mReasonList = reasonList;
    }

    @Override
    public IAfterSalesApplyContract.IAfterSalesApplyPresenter getPresenter() {
        return new AfterSalesApplyPresenter(mParam);
    }

    @Override
    public void submitSuccess(String id) {
        SubmitSuccessActivity.start(mApplyText.getTitle() + "申请提交成功",
                null, RouterConfig.AFTER_SALES_DETAIL, id);
    }

    @Override
    public BaseQuickAdapter createAdapter() {
        mAdapter = new AfterSalesDetailAdapter(mParam.getAfterSalesDetailList(), false);
        mAdapter.setRefundBillType(mParam.getAfterSalesType());
        return mAdapter;
    }

    @Override
    public void updateAdapter(List<AfterSalesDetailsBean> list) {
        List<AfterSalesDetailsBean> result = new ArrayList<>();
        for (AfterSalesDetailsBean bean : list) {
            if (bean.isSelected()) result.add(bean);
        }
        mParam.setAfterSalesDetailList(result);
        mAdapter.setNewData(mParam.getAfterSalesDetailList());
    }
}
