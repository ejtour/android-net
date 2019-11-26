package com.hll_sc_app.app.crm.customer.partner;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

public class CustomerPartnerPresenter implements ICustomerPartnerContract.ICustomerPartnerPresenter {
    private ICustomerPartnerContract.ICustomerPartnerView mView;
    private int mPageNum;

    private CustomerPartnerPresenter() {
    }

    public static CustomerPartnerPresenter newInstance() {
        return new CustomerPartnerPresenter();
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
        SimpleObserver<CooperationPurchaserResp> observer = new SimpleObserver<CooperationPurchaserResp>(mView, showLoading) {
            @Override
            public void onSuccess(CooperationPurchaserResp cooperationPurchaserResp) {
                mView.setData(cooperationPurchaserResp, mPageNum > 1);
                if (CommonUtils.isEmpty(cooperationPurchaserResp.getRecords())) return;
                mPageNum++;
            }
        };
        UserBean user = GreenDaoUtils.getUser();
        CooperationPurchaserService.INSTANCE.queryCooperationPurchaserList(BaseMapReq.newBuilder()
                .put("actionType", "formalCooperation")
                .put("groupID", user.getGroupID())
                .put("originator", "1")
                .put("pageNo", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .put("name", mView.getSearchWords())
                .put("ignoreGroupActive", "1")
                .put("salesmanID", mView.isAll() ? "" : user.getEmployeeID())
                .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(ICustomerPartnerContract.ICustomerPartnerView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
