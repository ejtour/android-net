package com.hll_sc_app.app.report.produce.input.detail;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.purchase.ManHourBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public class ProduceInputDetailPresenter implements IProduceInputDetailContract.IProduceInputDetailPresenter {
    private IProduceInputDetailContract.IProduceInputDetailView mView;

    public static ProduceInputDetailPresenter newInstance() {
        return new ProduceInputDetailPresenter();
    }

    @Override
    public void start() {
        Report.queryManHour(1, new SimpleObserver<List<ManHourBean>>(mView) {
            @Override
            public void onSuccess(List<ManHourBean> manHourBeans) {
                mView.setCompanyNameData(manHourBeans);
            }
        });
    }

    @Override
    public void register(IProduceInputDetailContract.IProduceInputDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
