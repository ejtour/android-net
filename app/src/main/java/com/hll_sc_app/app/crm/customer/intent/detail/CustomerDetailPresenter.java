package com.hll_sc_app.app.crm.customer.intent.detail;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.customer.VisitRecordBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Customer;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/21
 */

public class CustomerDetailPresenter implements ICustomerDetailContract.ICustomerDetailPresenter {
    private ICustomerDetailContract.ICustomerDetailView mView;
    private int mPageNum;

    private CustomerDetailPresenter() {
    }

    public static CustomerDetailPresenter newInstance() {
        return new CustomerDetailPresenter();
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
    public void transfer() {
        Customer.transferToSeas(mView.getID(), new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.showToast("客户已转到公海");
                mView.handleSuccess();
            }
        });
    }

    @Override
    public void receive() {
        UserBean user = GreenDaoUtils.getUser();
        Customer.distributeCustomer(mView.getID(), user.getEmployeeID(), user.getEmployeeName(), new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.showToast("领取客户成功");
                mView.handleSuccess();
            }
        });
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Customer.queryVisitRecordDetail(mPageNum, 1, mView.getID(),
                new SimpleObserver<SingleListResp<VisitRecordBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<VisitRecordBean> visitRecordBeanSingleListResp) {
                        mView.setData(visitRecordBeanSingleListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(visitRecordBeanSingleListResp.getRecords())) return;
                        mPageNum++;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        super.onFailure(e);
                        mView.loadError(e);
                    }
                });
    }

    @Override
    public void register(ICustomerDetailContract.ICustomerDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
