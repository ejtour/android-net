package com.hll_sc_app.app.report.ordergoods.detail;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsDetailBean;
import com.hll_sc_app.bean.report.ordergoods.OrderGoodsDetailParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/23
 */

public class OrderGoodsDetailPresenter implements IOrderGoodsDetailContract.IOrderGoodsDetailPresenter {

    private OrderGoodsDetailParam mParam;
    private int mPageNum;
    private IOrderGoodsDetailContract.IOrderGoodsDetailView mView;

    public static OrderGoodsDetailPresenter newInstance(OrderGoodsDetailParam param) {
        OrderGoodsDetailPresenter presenter = new OrderGoodsDetailPresenter();
        presenter.mParam = param;
        return presenter;
    }

    @Override
    public void start() {
        mPageNum = 1;
        queryList(true);
    }

    private void queryList(boolean showLoading) {
        OrderGoodsBean bean = mParam.getBean();
        Report.queryOrderGoodsDetail(bean.getShopID(),
                mParam.getFormatStartDate(), mParam.getFormatEndDate(),
                mPageNum, new SimpleObserver<SingleListResp<OrderGoodsDetailBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<OrderGoodsDetailBean> orderGoodsDetailBeanOrderGoodsResp) {
                        mView.setList(orderGoodsDetailBeanOrderGoodsResp.getRecords(), mPageNum > 1);
                        if (!CommonUtils.isEmpty(orderGoodsDetailBeanOrderGoodsResp.getRecords())) {
                            mPageNum++;
                        }
                    }
                });
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        queryList(false);
    }

    @Override
    public void loadMore() {
        queryList(false);
    }

    @Override
    public void register(IOrderGoodsDetailContract.IOrderGoodsDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
