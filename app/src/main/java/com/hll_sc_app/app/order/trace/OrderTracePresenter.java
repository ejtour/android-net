package com.hll_sc_app.app.order.trace;

import android.text.TextUtils;

import com.amap.api.maps.model.LatLng;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/17/20.
 */
class OrderTracePresenter implements IOrderTraceContract.IOrderTracePresenter {
    private final String plateNumber;
    private final String subBillID;
    private IOrderTraceContract.IOrderTraceView mView;

    public OrderTracePresenter(String plateNumber, String subBillID) {
        this.plateNumber = plateNumber;
        this.subBillID = subBillID;
    }

    @Override
    public void start() {
        if (TextUtils.isEmpty(plateNumber) || UserConfig.crm()) return;
        Order.queryDriverLocations(subBillID, plateNumber, new SimpleObserver<SingleListResp<List<Object>>>(mView) {
            @Override
            public void onSuccess(SingleListResp<List<Object>> listSingleListResp) {
                List<LatLng> list = new ArrayList<>();
                if (!CommonUtils.isEmpty(listSingleListResp.getRecords()))
                    for (List<Object> record : listSingleListResp.getRecords()) {
                        LatLng latLng = new LatLng(Double.parseDouble(record.get(1).toString()),
                                Double.parseDouble(record.get(0).toString()));
                        list.add(latLng);
                    }
                mView.setData(list);
            }

            @Override
            public void onFailure(UseCaseException e) {
                // no-op
            }
        });
    }

    @Override
    public void register(IOrderTraceContract.IOrderTraceView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
