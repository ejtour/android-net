package com.hll_sc_app.app.crm.customer.record;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.customer.VisitRecordBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Customer;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/21
 */

public class VisitRecordPresenter implements IVisitRecordContract.IVisitRecordPresenter {
    private IVisitRecordContract.IVisitRecordView mView;
    private int mPageNum;

    private VisitRecordPresenter() {
    }

    public static VisitRecordPresenter newInstance() {
        return new VisitRecordPresenter();
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void delRecord(String id) {
        Customer.delVisitRecord(id, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.delSuccess();
            }
        });
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Customer.queryVisitRecord(mView.isAll(), mPageNum, mView.getSearchWords(),
                new SimpleObserver<SingleListResp<VisitRecordBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<VisitRecordBean> visitRecordBeanSingleListResp) {
                        mView.setData(visitRecordBeanSingleListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(visitRecordBeanSingleListResp.getRecords())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void register(IVisitRecordContract.IVisitRecordView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
