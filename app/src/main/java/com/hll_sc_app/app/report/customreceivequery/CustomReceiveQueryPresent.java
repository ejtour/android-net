package com.hll_sc_app.app.report.customreceivequery;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

/***
 * 客户收货查询
 * */
public class CustomReceiveQueryPresent implements ICustomReceiveQueryContract.IPresent {
    private ICustomReceiveQueryContract.IView mView;
    private int pageSize = 20;
    private int pageNum = 1;
    private int pageTempNum = 1;

    public static CustomReceiveQueryPresent newInstance() {
        return new CustomReceiveQueryPresent();
    }

    @Override
    public void start() {
        queryList(true);
        queryCustomer();
    }

    @Override
    public void register(ICustomReceiveQueryContract.IView view) {
        mView = view;
    }

    @Override
    public void queryList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("pageNo", String.valueOf(pageTempNum))
                .put("pageSize", String.valueOf(pageSize))
                .put("groupID", mView.getOwnerId())
                .put("demandID", mView.getDemandID())
                .put("startDate", mView.getStartDate())
                .put("endDate", mView.getEndDate())
                .put("voucherTypes", mView.getType())
                .put("voucherStatus", mView.getStatus())
                .put("supplierID", userBean.getGroupID())
                .create();

        ReportService.INSTANCE
                .queryCustomReceiveList(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CustomReceiveListResp>() {
                    @Override
                    public void onSuccess(CustomReceiveListResp customReceiveListResp) {
                        mView.querySuccess(customReceiveListResp.getRecords(), pageTempNum > 1);
                        pageNum = pageTempNum;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                        mView.queryListFail();
                        pageTempNum = pageNum;
                    }
                });
    }


    @Override
    public void refresh(boolean isLoading) {
        pageTempNum = 1;
        queryList(isLoading);
    }

    @Override
    public void getMore() {
        pageTempNum++;
        queryList(false);
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void queryCustomer() {
        SimpleObserver<CooperationPurchaserDetail> observer = new SimpleObserver<CooperationPurchaserDetail>(mView) {
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
                mView.cacheShopList(list);
            }
        };
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("pageNo", "1")
                .put("pageSize", "9999")
                .put("originator", "1")
                .put("groupID", UserConfig.getGroupID())
                .put("purchaserID", mView.getPurchaserID())
                .create();
        CooperationPurchaserService.INSTANCE.queryCooperationPurchaserDetail(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(observer);
    }
}
