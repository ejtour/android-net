package com.hll_sc_app.app.report.customersettle;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.QueryGroupListResp;
import com.hll_sc_app.bean.report.customersettle.CustomerSettleResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/24
 */

class CustomerSettlePresenter implements ICustomerSettleContract.ICustomerSettlePresenter {
    private int mPageNo;
    private ICustomerSettleContract.ICustomerSettleView mView;

    public static CustomerSettlePresenter newInstance() {
        return new CustomerSettlePresenter();
    }

    private CustomerSettlePresenter() {
    }

    @Override
    public void start() {
        mPageNo = 1;
        windowLoad(true);
    }

    private void windowLoad(boolean showLoading) {
        SimpleObserver<QueryGroupListResp> observer = new SimpleObserver<QueryGroupListResp>(mView, showLoading) {
            @Override
            public void onSuccess(QueryGroupListResp queryGroupListResp) {
                mView.setPurchaserData(queryGroupListResp.getGroupList(), mPageNo > 1);
                if (CommonUtils.isEmpty(queryGroupListResp.getGroupList())) return;
                mPageNo++;
            }
        };
        CooperationPurchaserService.INSTANCE
                .queryGroupList(BaseMapReq.newBuilder()
                        .put("actionType", "customer_receiving_query")
                        .put("requestOriginator", "1")
                        .put("resourceType", "1")
                        .put("pageSize", "10")
                        .put("pageNum", String.valueOf(mPageNo))
                        .put("groupID", UserConfig.getGroupID())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(ICustomerSettleContract.ICustomerSettleView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void loadInfo() {
        Report.queryCustomerSettle(mView.getReq().create(), new SimpleObserver<CustomerSettleResp>(mView) {
            @Override
            public void onSuccess(CustomerSettleResp customerSettleResp) {
                mView.setData(customerSettleResp.convertToBeanList());
            }
        });
    }

    @Override
    public void windowLoadMore() {
        windowLoad(false);
    }
}
