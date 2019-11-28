package com.hll_sc_app.app.crm.customer.search.plan;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.customer.VisitPlanBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Customer;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/28
 */

public class SearchPlanPresenter implements ISearchPlanContract.ISearchPlanPresenter {
    private ISearchPlanContract.ISearchPlanView mView;
    private int mPageNum;

    private SearchPlanPresenter() {
    }

    public static SearchPlanPresenter newInstance() {
        return new SearchPlanPresenter();
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
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Customer.queryVisitPlan(false, mPageNum, mView.getSearchWords(),
                mView.getCustomerType(), null, mView.getStartDate(), mView.getEndDate(),
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
    public void register(ISearchPlanContract.ISearchPlanView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
