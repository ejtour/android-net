package com.hll_sc_app.app.cardmanage.transactiondetail;

import com.hll_sc_app.api.CardManageService;
import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.cardmanage.CardTransactionListResp;
import com.hll_sc_app.bean.complain.ReportFormSearchResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class TransactionListPresent implements ITransactionListContract.IPresent {

    private ITransactionListContract.IView mView;

    private int pageNum = 1;
    private int pageTempNum = 1;
    private int pageSize = 20;

    public static TransactionListPresent newInstance() {
        return new TransactionListPresent();
    }

    @Override
    public void start() {

    }

    @Override
    public void register(ITransactionListContract.IView view) {
        mView = view;
    }

    @Override
    public void queryDetailList(boolean isLoading) {
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("cardNo", mView.getCardNo())
                .put("endDate", mView.getEndDate())
                .put("startDate", mView.getStartDate())
                .put("pageNo", String.valueOf(pageTempNum))
                .put("pageSize", String.valueOf(pageSize))
                .put("shopID", mView.getShopID())
                .put("tradeType", mView.getTradeType())
                .create();

        CardManageService.INSTANCE
                .queryTransationList(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> {
                    if (mView.isActive()) {
                        mView.hideLoading();
                    }
                }).subscribe(new BaseCallback<CardTransactionListResp>() {
            @Override
            public void onSuccess(CardTransactionListResp resp) {
                if (mView.isActive()) {
                    mView.queryListSuccess(resp, pageTempNum > 1);
                    pageNum = pageTempNum;
                }
            }

            @Override
            public void onFailure(UseCaseException e) {
                if (mView.isActive()) {
                    mView.showError(e);
                    pageTempNum = pageNum;
                }
            }
        });
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public void getMore() {
        pageTempNum++;
        queryDetailList(false);
    }

    @Override
    public void refresh() {
        pageTempNum = 1;
        queryDetailList(false);
    }

    @Override
    public void filter() {
        pageTempNum = 1;
        queryDetailList(true);
    }


    @Override
    public void queryPurchaseList(String purchaserID, String searchContent) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("groupIDs", userBean.getGroupID())
                .put("searchType", "1")
                .put("size", "10")
                .put("type", "0")
                .put("searchWords", searchContent)
                .put("purchaserID", purchaserID)
                .create();

        ComplainManageService.INSTANCE
                .queryReportFormPurchaserList(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<ReportFormSearchResp>() {
                    @Override
                    public void onSuccess(ReportFormSearchResp reportFormSearchResp) {
                        mView.queryPurchaseSuccess(reportFormSearchResp);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }
}
