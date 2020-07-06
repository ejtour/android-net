package com.hll_sc_app.app.report.customerreceive;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.cooperation.QueryGroupListResp;
import com.hll_sc_app.bean.report.customerreceive.ReceiveCustomerBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Report;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/30
 */

public class CustomerReceivePresenter implements ICustomerReceiveContract.ICustomerReceivePresenter {
    private ICustomerReceiveContract.ICustomerReceiveView mView;
    private int mPageNum;
    private int mPageNo;

    private CustomerReceivePresenter() {
    }

    public static CustomerReceivePresenter newInstance() {
        return new CustomerReceivePresenter();
    }

    @Override
    public void loadList() {
        mPageNum = 1;
        load(true);
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
    public void windowRefresh() {
        mPageNo = 1;
        windowLoad(false);
    }

    @Override
    public void windowLoadMore() {
        windowLoad(false);
    }

    @Override
    public void windowLoad() {
        mPageNo = 1;
        windowLoad(true);
    }

    private void windowLoad(boolean showLoading) {
        if (mView.isShop()) {
            Common.queryPurchaserShopList(mView.getPurchaserID(), "SHOP_AND_DISTRIBUTION", mView.getSearchWords(),
                    new SimpleObserver<List<PurchaserShopBean>>(mView,showLoading) {
                @Override
                public void onSuccess(List<PurchaserShopBean> purchaserShopBeans) {
                    mView.setShopData(purchaserShopBeans);
                }
            });
        } else {
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
                            .put("searchParams", mView.getSearchWords())
                            .create())
                    .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                    .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                    .subscribe(observer);
        }
    }

    @Override
    public void start() {
        loadList();
        windowLoad();
    }

    private void load(boolean showLoading) {
        Report.queryReceiptList(mView.getReq()
                .put("pageNo", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .create(), new SimpleObserver<SingleListResp<ReceiveCustomerBean>>(mView, showLoading) {
            @Override
            public void onSuccess(SingleListResp<ReceiveCustomerBean> receiveCustomerBeanSingleListResp) {
                mView.setData(receiveCustomerBeanSingleListResp.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(receiveCustomerBeanSingleListResp.getRecords())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(ICustomerReceiveContract.ICustomerReceiveView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
