package com.hll_sc_app.app.crm.customer.seas.detail.visit;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.customer.VisitRecordBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Customer;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

public class CustomerSeasVisitPresenter implements ICustomerSeasVisitContract.ICustomerSeasVisitPresenter {
    private ICustomerSeasVisitContract.ICustomerSeasVisitView mView;
    private int mPageNum;

    private CustomerSeasVisitPresenter() {
    }

    public static CustomerSeasVisitPresenter newInstance() {
        return new CustomerSeasVisitPresenter();
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
        Customer.queryVisitRecordDetail(mPageNum, 2, mView.getID(), new SimpleObserver<SingleListResp<VisitRecordBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<VisitRecordBean> visitRecordBeanSingleListResp) {
                mView.setData(visitRecordBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(visitRecordBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(ICustomerSeasVisitContract.ICustomerSeasVisitView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
