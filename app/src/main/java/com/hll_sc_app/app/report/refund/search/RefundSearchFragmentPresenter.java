package com.hll_sc_app.app.report.refund.search;

import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.report.search.SearchReq;
import com.hll_sc_app.bean.report.search.SearchResultResp;
import com.hll_sc_app.rest.Report;

public class RefundSearchFragmentPresenter implements RefundSearchFragmentContract.IRefundSearchPresenter {
    private RefundSearchFragmentContract.IRefundSearchView mView;


    @Override
    public void start() {
        querySearchList(true);
    }

    @Override
    public void querySearchList(boolean showLoading) {
       toQuerySearchList(showLoading);
    }

    @Override
    public void queryMoreSearchList() {}

    private void toQuerySearchList(boolean showLoading) {
        SearchReq requestParams = mView.getRequestParams();
        requestParams.setShopMallID(UserConfig.getGroupID());
        requestParams.setSize(50);
        Report.querySearchList(requestParams, new SimpleObserver<SearchResultResp>(mView,showLoading) {
            @Override
            public void onSuccess(SearchResultResp searchResultResp) {
                mView.setSearchResultList(searchResultResp.getList());
            }
            @Override
            public void onFailure(UseCaseException e) {
                mView.showError(e);
            }
        });
    }

    @Override
    public void register(RefundSearchFragmentContract.IRefundSearchView view) {
          this.mView = view;
    }

    public static RefundSearchFragmentPresenter newInstance(){
        return new RefundSearchFragmentPresenter();
    }
}
