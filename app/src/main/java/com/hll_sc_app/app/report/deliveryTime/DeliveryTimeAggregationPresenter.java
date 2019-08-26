package com.hll_sc_app.app.report.deliveryTime;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeReq;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 *
 *日销售汇总
 * @author 初坤
 * @date 2019/7/20
 */
public class DeliveryTimeAggregationPresenter implements DeliveryTimeAggregationContract.IDeliveryTimeAggregationPresenter {
    private DeliveryTimeAggregationContract.IDeliveryTimeAggregationView mView;

    static DeliveryTimeAggregationPresenter newInstance() {
        return new DeliveryTimeAggregationPresenter();
    }

    @Override
    public void start() {
        queryDeliveryTimePieCharts(true);
    }

    @Override
    public void register(DeliveryTimeAggregationContract.IDeliveryTimeAggregationView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryDeliveryTimePieCharts(boolean showLoading) {
        toQueryDeliveryTimePieCharts(showLoading);
    }

    private void toQueryDeliveryTimePieCharts(boolean showLoading) {
        DeliveryTimeReq params = new DeliveryTimeReq();
        params.setGroupID(UserConfig.getGroupID());
        params.setNeedNearlyData(1);
        params.setPageNum(1);
        params.setPageSize(1);
        params.setTimeFlag(8);
        Report.queryDeliveryTimeContent(params, new SimpleObserver<DeliveryTimeResp>(mView,showLoading) {
            @Override
            public void onSuccess(DeliveryTimeResp deliveryTimeResp) {
                mView.showDeliveryTimePieCharts(deliveryTimeResp);
            }
            @Override
            public void onFailure(UseCaseException e) {
                mView.showError(e);
            }
        });
    }

}
