package com.hll_sc_app.app.crm.customer.record.add;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.customer.VisitRecordBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Customer;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/22
 */

public class AddVisitRecordPresenter implements IAddVisitRecordContract.IAddVisitRecordPresenter {
    private IAddVisitRecordContract.IAddVisitRecordView mView;

    private AddVisitRecordPresenter() {
    }

    public static AddVisitRecordPresenter newInstance() {
        return new AddVisitRecordPresenter();
    }

    @Override
    public void save(VisitRecordBean bean) {
        Customer.saveVisitRecord(bean, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.saveSuccess();
            }
        });
    }

    @Override
    public void register(IAddVisitRecordContract.IAddVisitRecordView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
