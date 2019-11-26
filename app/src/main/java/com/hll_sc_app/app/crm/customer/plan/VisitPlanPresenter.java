package com.hll_sc_app.app.crm.customer.plan;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.customer.VisitPlanBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Customer;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/23
 */

public class VisitPlanPresenter implements IVisitPlanContract.IVisitPlanPresenter {
    private IVisitPlanContract.IVisitPlanView mView;
    private int mPageNum;

    private VisitPlanPresenter() {
    }

    public static VisitPlanPresenter newInstance() {
        return new VisitPlanPresenter();
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
    public void delPlan(String id) {
        Customer.delVisitPlan(id, new SimpleObserver<Object>(mView) {
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
        Customer.queryVisitPlan(mView.isAll(), mPageNum, mView.getSearchWords(),
                new SimpleObserver<SingleListResp<VisitPlanBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<VisitPlanBean> visitPlanBeanSingleListResp) {
                        mView.setData(visitPlanBeanSingleListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(visitPlanBeanSingleListResp.getRecords())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void register(IVisitPlanContract.IVisitPlanView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
