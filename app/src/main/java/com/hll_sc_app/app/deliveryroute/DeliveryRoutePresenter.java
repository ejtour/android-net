package com.hll_sc_app.app.deliveryroute;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.filter.DateStringParam;
import com.hll_sc_app.bean.other.RouteBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Other;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/27
 */

public class DeliveryRoutePresenter implements IDeliveryRouteContract.IDeliveryRoutePresenter {
    private IDeliveryRouteContract.IDeliveryRouteView mView;
    private int mPageNum;
    private DateStringParam mParam;

    private DeliveryRoutePresenter(DateStringParam param) {
        mParam = param;
    }

    public static DeliveryRoutePresenter newInstance(DateStringParam param) {
        return new DeliveryRoutePresenter(param);
    }

    private void load(boolean showLoading) {
        Other.queryRouteList(mPageNum,
                mParam.getFormatStartDate(),
                mParam.getExtra(),
                new SimpleObserver<SingleListResp<RouteBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<RouteBean> routeBeanSingleListResp) {
                        List<RouteBean> list = routeBeanSingleListResp == null ? null : routeBeanSingleListResp.getRecords();
                        mView.setRouteInfo(list, mPageNum > 1);
                        if (CommonUtils.isEmpty(list)) return;
                        mPageNum++;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        if (e.getCode().equals(CODE_NULL_POINTER)) onSuccess(null);
                        else super.onFailure(e);
                    }
                });
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
    public void start() {
        mPageNum = 1;
        load(true);
    }

    @Override
    public void register(IDeliveryRouteContract.IDeliveryRouteView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
