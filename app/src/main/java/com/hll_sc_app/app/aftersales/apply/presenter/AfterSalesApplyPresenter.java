package com.hll_sc_app.app.aftersales.apply.presenter;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyParam;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyReq;
import com.hll_sc_app.bean.aftersales.AfterSalesApplyResp;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.bean.aftersales.AfterSalesReasonBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.rest.AfterSales;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/11
 */

public class AfterSalesApplyPresenter extends BaseAfterSalesApplyPresenter {

    public AfterSalesApplyPresenter(AfterSalesApplyParam param) {
        super(param);
    }

    @Override
    public void submit() {
        AfterSales.applyAfterSales(
                !TextUtils.isEmpty(mParam.getId()),
                AfterSalesApplyReq.createFromAfterSalesParam(mParam), new SimpleObserver<AfterSalesApplyResp>(mView) {
                    @Override
                    public void onSuccess(AfterSalesApplyResp afterSalesApplyResp) {
                        mView.submitSuccess(afterSalesApplyResp.getId());
                    }
                });
    }

    @Override
    public void getAfterSalesReasonList() {
        AfterSales.getReasonList(new SimpleObserver<SingleListResp<AfterSalesReasonBean>>(mView) {
            @Override
            public void onSuccess(SingleListResp<AfterSalesReasonBean> afterSalesReasonBeanSingleListResp) {
                mView.updateReasonList(afterSalesReasonBeanSingleListResp.getRecords());
            }
        });
    }

    @Override
    public void getReturnableGoodsList() {
        AfterSales.getReturnableGoods(mParam.getSubBillID(), mParam.getAfterSalesType(), new SimpleObserver<SingleListResp<AfterSalesDetailsBean>>(mView) {
            @Override
            public void onSuccess(SingleListResp<AfterSalesDetailsBean> afterSalesDetailsBeanSingleListResp) {
                mView.updateDetailsData(afterSalesDetailsBeanSingleListResp.getRecords());
            }
        });
    }

    @Override
    public void start() {
        getAfterSalesReasonList();
        getReturnableGoodsList();
    }
}
