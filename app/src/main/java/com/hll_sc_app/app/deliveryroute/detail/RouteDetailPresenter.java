package com.hll_sc_app.app.deliveryroute.detail;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.other.RouteDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/28
 */

public class RouteDetailPresenter implements IRouteDetailContract.IRouteDetailPresenter {
    private IRouteDetailContract.IRouteDetailView mView;
    private int mPageNum;

    private void load(boolean showLoading) {
        Other.queryRouteDetail(mPageNum, mView.getDeliveryNo(),
                mView.getShopID(), new SimpleObserver<RouteDetailResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(RouteDetailResp routeDetailResp) {
                        mView.setRouteDetailData(routeDetailResp, mPageNum > 1);
                        if (CommonUtils.isEmpty(routeDetailResp.getRecords())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void register(IRouteDetailContract.IRouteDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
