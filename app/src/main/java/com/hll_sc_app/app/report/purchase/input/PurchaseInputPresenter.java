package com.hll_sc_app.app.report.purchase.input;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/28
 */

public class PurchaseInputPresenter implements IPurchaseInputContract.IPurchaseInputPresenter {
    private IPurchaseInputContract.IPurchaseInputView mView;
    private String mID;

    public static PurchaseInputPresenter newInstance(String id) {
        return new PurchaseInputPresenter(id);
    }

    private PurchaseInputPresenter(String ID) {
        mID = ID;
    }

    @Override
    public void save(int type, String num, String amount) {
        Report.recordPurchaseInfo(BaseMapReq.newBuilder()
                        .put("type", String.valueOf(type))
                        .put("id", mID)
                        .put(type == 0 ? "purchaseAmount" : "logisticsCost", amount)
                        .put(type == 0 ? "purchaserNum" : "carNums", num),
                new SimpleObserver<MsgWrapper<Object>>(true, mView) {
                    @Override
                    public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                        mView.saveSuccess();
                    }
                });
    }

    @Override
    public void register(IPurchaseInputContract.IPurchaseInputView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
