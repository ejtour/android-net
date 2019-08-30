package com.hll_sc_app.app.report.produce.input;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.report.produce.ProduceDetailBean;
import com.hll_sc_app.bean.report.produce.ProduceInputReq;
import com.hll_sc_app.bean.report.purchase.ManHourBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public class ProduceInputPresenter implements IProduceInputContract.IProduceInputPresenter {
    private final String mClasses;
    private final String mDate;
    private IProduceInputContract.IProduceInputView mView;

    public static ProduceInputPresenter newInstance(String classes, String date) {
        return new ProduceInputPresenter(classes, date);
    }

    private ProduceInputPresenter(String classes, String date) {
        mClasses = classes;
        mDate = date;
    }

    @Override
    public void save(ProduceInputReq req) {
        Report.recordProduceInfo(req, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.saveSuccess();
            }
        });
    }

    @Override
    public void reqShiftList() {
        if (mClasses == null) {
            Report.queryManHour(2, new SimpleObserver<List<ManHourBean>>(mView) {
                @Override
                public void onSuccess(List<ManHourBean> manHourBeans) {
                    mView.setShiftData(manHourBeans);
                }
            });
        }
    }

    @Override
    public void start() {
        if (mClasses != null) {
            Report.queryProduceDetails(mClasses, mDate, 1, new SimpleObserver<SingleListResp<ProduceDetailBean>>(mView) {
                @Override
                public void onSuccess(SingleListResp<ProduceDetailBean> produceDetailBeanSingleListResp) {

                    mView.setData(produceDetailBeanSingleListResp.getRecords());
                }
            });
        }
        reqShiftList();
    }

    @Override
    public void register(IProduceInputContract.IProduceInputView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
