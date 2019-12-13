package com.hll_sc_app.app.report.deliverytime;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.report.deliverytime.DeliveryTimeResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

/**
 *
 *日销售汇总
 * @author 初坤
 * @date 2019/7/20
 */
public class DeliveryTimePresenter implements DeliveryTimeContract.IDeliveryTimePresenter {
    private DeliveryTimeContract.IDeliveryTimeView mView;

    static DeliveryTimePresenter newInstance() {
        return new DeliveryTimePresenter();
    }

    @Override
    public void start() {
        queryDeliveryTimePieCharts(true);
    }

    @Override
    public void register(DeliveryTimeContract.IDeliveryTimeView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryDeliveryTimePieCharts(boolean showLoading) {
        toQueryDeliveryTimePieCharts(showLoading);
    }

    private void toQueryDeliveryTimePieCharts(boolean showLoading) {
        Report.queryDeliveryTimeContent(BaseMapReq.newBuilder()
                .put("groupID",UserConfig.getGroupID())
                .put("needNearlyData","1")
                .put("pageNum","1")
                .put("pageSize","1")
                .put("timeFlag","8")
                .create(), new SimpleObserver<DeliveryTimeResp>(mView,showLoading) {
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
