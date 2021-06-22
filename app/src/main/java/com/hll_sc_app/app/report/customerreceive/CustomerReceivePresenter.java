package com.hll_sc_app.app.report.customerreceive;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.cooperation.QueryGroupListResp;
import com.hll_sc_app.bean.report.customerreceive.ReceiveCustomerBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

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
            SimpleObserver<CooperationPurchaserDetail> observer = new SimpleObserver<CooperationPurchaserDetail>(mView, showLoading) {
                @Override
                public void onSuccess(CooperationPurchaserDetail cooperationPurchaserDetail) {
                    List<PurchaserShopBean> list = new ArrayList<>();
                    for (com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean bean : cooperationPurchaserDetail.getShopDetailList()) {
                        PurchaserShopBean shop = new PurchaserShopBean();
                        list.add(shop);
                        shop.setShopID(bean.getShopID());
                        shop.setShopName(bean.getShopName());
                        shop.setExtShopID(bean.getExtShopID());
                    }
                    mView.setShopData(list);
                }
            };
            BaseMapReq req = BaseMapReq.newBuilder()
                    .put("pageNo", "1")
                    .put("pageSize", "9999")
                    .put("originator", "1")
                    .put("groupID", UserConfig.getGroupID())
                    .put("purchaserID", mView.getPurchaserID())
                    .put("searchParams", mView.getSearchWords())
                    .create();
            CooperationPurchaserService.INSTANCE.queryCooperationPurchaserDetail(req)
                    .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                    .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                    .subscribe(observer);
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
