package com.hll_sc_app.app.report.produce.manhour;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.purchase.ManHourBean;
import com.hll_sc_app.bean.report.purchase.ManHourReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/29
 */

public class ManHourSettingPresenter implements IManHourSettingContract.IManHourSettingPresenter {
    private IManHourSettingContract.IManHourSettingView mView;

    public static ManHourSettingPresenter newInstance() {
        return new ManHourSettingPresenter();
    }

    private ManHourSettingPresenter() {
    }

    @Override
    public void start() {
        Report.queryManHour(1, new SimpleObserver<List<ManHourBean>>(mView) {
            @Override
            public void onSuccess(List<ManHourBean> manHourBeans) {
                mView.setData(manHourBeans, true);
            }
        });

        Report.queryManHour(2, new SimpleObserver<List<ManHourBean>>(mView) {
            @Override
            public void onSuccess(List<ManHourBean> manHourBeans) {
                mView.setData(manHourBeans, false);
            }
        });
    }

    @Override
    public void save(ManHourReq req) {
        Report.saveManHour(req, new SimpleObserver<MsgWrapper<Object>>(true, mView) {
            @Override
            public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                mView.saveSuccess();
            }
        });
    }

    @Override
    public void register(IManHourSettingContract.IManHourSettingView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
