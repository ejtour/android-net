package com.hll_sc_app.app.cardmanage.cardlog;

import com.hll_sc_app.api.CardManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.cardmanage.CardLogResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class CardLogPresent implements ICardLogContract.IPresent {

    private static final int PAGESIZE = 20;
    private ICardLogContract.IView mView;
    private int pageNum = 1;
    private int pageTempNum = 1;

    static CardLogPresent newInstance() {
        return new CardLogPresent();
    }

    @Override
    public void register(ICardLogContract.IView view) {
        mView = view;
    }

    @Override
    public void queryLogList(boolean isLoading) {
        CardManageService.INSTANCE
                .queryCardLog(BaseMapReq.newBuilder()
                        .put("cardNo", mView.getCardNo())
                        .put("endDate", mView.getEndDate())
                        .put("startDate", mView.getStartDate())
                        .put("pageNo", String.valueOf(pageTempNum))
                        .put("pageSize", String.valueOf(PAGESIZE))
                        .create())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CardLogResp>() {
                    @Override
                    public void onSuccess(CardLogResp cardLogResp) {
                        mView.querySuccess(cardLogResp.getRecords(), pageTempNum > 1);
                        pageNum = pageTempNum;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                        pageTempNum = pageNum;
                    }
                });
    }

    @Override
    public void refresh() {
        pageTempNum = 1;
        queryLogList(false);
    }

    @Override
    public void getMore() {
        pageTempNum++;
        queryLogList(false);
    }

    @Override
    public int getPageSize() {
        return PAGESIZE;
    }
}
