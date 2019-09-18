package com.hll_sc_app.app.aftersales.entry;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aftersales.AfterSalesVerifyResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.AfterSales;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */

public class AfterSalesEntryPresenter implements IAfterSalesEntryContract.IAfterSalesEntryPresenter {
    private String mSubBillID;
    private IAfterSalesEntryContract.IAfterSalesEntryView mView;

    public static AfterSalesEntryPresenter newInstance(String subBillID) {
        return new AfterSalesEntryPresenter(subBillID);
    }

    private AfterSalesEntryPresenter(String subBillID) {
        mSubBillID = subBillID;
    }

    @Override
    public void verify(int refundBillType) {
        AfterSales.afterSalesVerify(refundBillType, mSubBillID, new SimpleObserver<AfterSalesVerifyResp>(mView) {
            @Override
            public void onSuccess(AfterSalesVerifyResp resp) {
                mView.handleVerifyResult(resp);
            }
        });
    }

    @Override
    public void register(IAfterSalesEntryContract.IAfterSalesEntryView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
