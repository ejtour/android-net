package com.hll_sc_app.app.invoice.detail.record;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.invoice.ReturnRecordResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Invoice;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/17
 */

public class ReturnRecordPresenter implements IReturnRecordContract.IReturnRecordPresenter {
    private int mAction;
    private String mID;
    private IReturnRecordContract.IReturnRecordView mView;

    public static ReturnRecordPresenter newInstance(int action, String id) {
        ReturnRecordPresenter presenter = new ReturnRecordPresenter();
        presenter.mAction = action;
        presenter.mID = id;
        return presenter;
    }

    @Override
    public void commit(String id, String time, String money, String type) {
        Invoice.updateReturnRecord(mAction, id, mID, time, money, type, new SimpleObserver<MsgWrapper<ReturnRecordResp>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<ReturnRecordResp> returnRecordRespMsgWrapper) {
                mView.success();
            }
        });
    }

    @Override
    public void register(IReturnRecordContract.IReturnRecordView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
