package com.hll_sc_app.app.complainmanage.productlist;

import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.complain.ReportFormSearchResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SelectProductListPresent implements ISelectProductListContract.IPresent {
    private final int PAGE_SIZE = 20;
    private int pageNum = 1;
    private int pageTempNum = 1;
    private ISelectProductListContract.IView mView;

    public static SelectProductListPresent newInstance() {
        return new SelectProductListPresent();
    }

    @Override
    public void register(ISelectProductListContract.IView view) {
        mView = view;
    }


    @Override
    public void queryList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("groupIDs", userBean.getGroupID())
                .put("searchType", String.valueOf(mView.getSearchType()))
                .put("type", "0")
                .put("searchWords", mView.getSearchWords())
                .put("purchaserID", mView.getPurchaserId())
                .create();

        ComplainManageService.INSTANCE
                .queryReportFormPurchaserList(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<ReportFormSearchResp>() {
                    @Override
                    public void onSuccess(ReportFormSearchResp reportFormSearchResp) {
                        mView.queySuccess(reportFormSearchResp, false);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }

    @Override
    public void getMore() {
        pageTempNum++;
        queryList(false);
    }

    @Override
    public void refresh() {
        pageTempNum = 1;
        queryList(false);

    }

    @Override
    public int getPageSize() {
        return PAGE_SIZE;
    }
}
