package com.hll_sc_app.app.report.deliverytime;

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
public class DeliveryTimePresenter implements IDeliveryTimeContract.IDeliveryTimePresenter {
    private IDeliveryTimeContract.IDeliveryTimeView mView;

    static DeliveryTimePresenter newInstance() {
        return new DeliveryTimePresenter();
    }

    private DeliveryTimePresenter() {
    }

    @Override
    public void start() {
        Report.queryDeliveryTimeContent(BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("needNearlyData", "1")
                .put("pageNum", "1")
                .put("pageSize", "1")
                .put("timeFlag", "8")
                .create(), new SimpleObserver<DeliveryTimeResp>(mView) {
            @Override
            public void onSuccess(DeliveryTimeResp deliveryTimeResp) {
                mView.setData(deliveryTimeResp);
            }
        });
    }

    @Override
    public void register(IDeliveryTimeContract.IDeliveryTimeView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }
}
