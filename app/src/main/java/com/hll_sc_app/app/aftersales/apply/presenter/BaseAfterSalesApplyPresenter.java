package com.hll_sc_app.app.aftersales.apply.presenter;

import com.hll_sc_app.app.aftersales.apply.IAfterSalesApplyContract;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyParam;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */

public abstract class BaseAfterSalesApplyPresenter implements IAfterSalesApplyContract.IAfterSalesApplyPresenter {
    protected IAfterSalesApplyContract.IAfterSalesApplyView mView;
    protected AfterSalesApplyParam mParam;

    BaseAfterSalesApplyPresenter(AfterSalesApplyParam param) {
        mParam = param;
    }

    @Override
    public void register(IAfterSalesApplyContract.IAfterSalesApplyView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
