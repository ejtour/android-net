package com.hll_sc_app.app.report.customreceivequery;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.cooperation.QueryGroupListResp;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/***
 * 客户收货查询
 * */
public class CustomReceiveQueryPresent implements ICustomReceiveQueryContract.IPresent {
    private ICustomReceiveQueryContract.IView mView;
    private int pageSize = 20;
    private int pageNum = 1;
    private int pageTempNum = 1;

    private int pageNum_custom = 1;
    private int pageTempNum_custom = 1;
    private int pageSize_custom = 10;


    public static CustomReceiveQueryPresent newInstance() {
        return new CustomReceiveQueryPresent();
    }

    @Override
    public void start() {

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
    public void queryCustomer(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("actionType", "customer_receiving_query")
                .put("requestOriginator", "1")
                .put("pageSize", String.valueOf(pageSize_custom))
                .put("pageNum", String.valueOf(pageTempNum_custom))
                .put("groupID", userBean.getGroupID())
                .create();

        CooperationPurchaserService.INSTANCE
                .queryGroupList(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<QueryGroupListResp>() {
                    @Override
                    public void onSuccess(QueryGroupListResp queryGroupListResp) {
                        mView.queryCustomerListSuccess(queryGroupListResp.getGroupList(), pageTempNum_custom > 1);
                        pageNum_custom = pageTempNum_custom;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                        pageTempNum_custom = pageNum_custom;
                    }
                });
    }

    @Override
    public void refreshCustomer() {
        pageTempNum_custom = 1;
        queryCustomer(false);
    }

    @Override
    public void getMoreCustomer() {
        pageTempNum_custom++;
        queryCustomer(false);
    }

    @Override
    public int getPageSizeCustom() {
        return pageSize_custom;
    }
}
