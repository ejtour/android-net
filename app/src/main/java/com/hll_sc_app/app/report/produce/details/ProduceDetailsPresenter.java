package com.hll_sc_app.app.report.produce.details;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.produce.ProduceDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/31
 */

public class ProduceDetailsPresenter implements IProduceDetailsContract.IProduceDetailsPresenter {
    private IProduceDetailsContract.IProduceDetailsView mView;
    private String mDate;

    public static ProduceDetailsPresenter newInstance(String date) {
        return new ProduceDetailsPresenter(date);
    }

    private ProduceDetailsPresenter(String date) {
        mDate = date;
    }

    @Override
    public void start() {
        load(true);
    }

    @Override
    public void register(IProduceDetailsContract.IProduceDetailsView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void load(boolean showLoading) {
        Report.queryProduceDetails(null, mDate, 0, new SimpleObserver<List<ProduceDetailBean>>(mView, showLoading) {
            @Override
            public void onSuccess(List<ProduceDetailBean> beanList) {
                mView.setData(beanList);
            }
        });
    }
}
