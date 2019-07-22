package com.hll_sc_app.app.aftersales.goodsoperation;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aftersales.AfterSalesActionReq;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.AfterSales;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/15
 */

public class GoodsOperationPresenter implements IGoodsOperationContract.IGoodsOperationPresenter {
    private AfterSalesBean mBean;
    private IGoodsOperationContract.IGoodsOperationView mView;

    public static GoodsOperationPresenter newInstance(AfterSalesBean bean) {
        GoodsOperationPresenter presenter = new GoodsOperationPresenter();
        presenter.mBean = bean;
        return presenter;
    }

    @Override
    public void doAction(List<AfterSalesActionReq.ActionBean> list) {
        AfterSales.afterSalesAction(
                mBean.getRefundBillStatus(),
                mBean.getId(),
                mBean.getRefundBillStatus(),
                mBean.getRefundBillType(),
                null,
                null,
                list,
                new SimpleObserver<MsgWrapper<Object>>(true, mView) {
                    @Override
                    public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                        mView.handleStatusChange();
                    }
                });
    }

    @Override
    public void register(IGoodsOperationContract.IGoodsOperationView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
