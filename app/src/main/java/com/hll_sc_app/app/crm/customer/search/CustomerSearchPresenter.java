package com.hll_sc_app.app.crm.customer.search;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.bean.customer.CustomerBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Customer;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/27
 */

public class CustomerSearchPresenter implements ICustomerSearchContract.ICustomerSearchPresenter {
    private ICustomerSearchContract.ICustomerSearchView mView;
    private int mPageNum;

    private CustomerSearchPresenter() {
    }

    public static CustomerSearchPresenter newInstance() {
        return new CustomerSearchPresenter();
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
        if (mView.getType() == 0) {
            Customer.queryIntentCustomer(false, mPageNum, mView.getSearchWords(), new SimpleObserver<SingleListResp<CustomerBean>>(mView, showLoading) {
                @Override
                public void onSuccess(SingleListResp<CustomerBean> customerBeanSingleListResp) {
                    mView.setData(customerBeanSingleListResp.getRecords(), mPageNum > 1);
                    if (CommonUtils.isEmpty(customerBeanSingleListResp.getRecords())) return;
                    mPageNum++;
                }
            });
        } else if (mView.getType() == 1) {
            UserBean user = GreenDaoUtils.getUser();
            Common.queryCooperationShop(BaseMapReq.newBuilder()
                    .put("actionType", "visitPlan")
                    .put("groupID", user.getGroupID())
                    .put("pageNo", String.valueOf(mPageNum))
                    .put("pageSize", "20")
                    .put("salesmanID", user.getEmployeeID())
                    .put("searchParam", mView.getSearchWords())
                    .create(), new SimpleObserver<CooperationShopListResp>(mView) {
                @Override
                public void onSuccess(CooperationShopListResp cooperationShopListResp) {
                    mView.setData(cooperationShopListResp.getShopList(), mPageNum > 1);
                    if (CommonUtils.isEmpty(cooperationShopListResp.getShopList())) return;
                    mPageNum++;
                }
            });
        } else if (mView.getType() == 2) {
            SimpleObserver<CooperationPurchaserResp> observer = new SimpleObserver<CooperationPurchaserResp>(mView, showLoading) {
                @Override
                public void onSuccess(CooperationPurchaserResp cooperationPurchaserResp) {
                    mView.setData(cooperationPurchaserResp.getRecords(), mPageNum > 1);
                    if (CommonUtils.isEmpty(cooperationPurchaserResp.getRecords())) return;
                    mPageNum++;
                }
            };
            UserBean user = GreenDaoUtils.getUser();
            CooperationPurchaserService.INSTANCE
                    .queryCooperationPurchaserList(BaseMapReq.newBuilder()
                            .put("actionType", "formalCooperation")
                            .put("groupID", user.getGroupID())
                            .put("originator", "1")
                            .put("pageNo", String.valueOf(mPageNum))
                            .put("salesmanID", user.getEmployeeID())
                            .put("pageSize", "20")
                            .put("name", mView.getSearchWords())
                            .create())
                    .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                    .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                    .subscribe(observer);
        }
    }

    @Override
    public void register(ICustomerSearchContract.ICustomerSearchView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
