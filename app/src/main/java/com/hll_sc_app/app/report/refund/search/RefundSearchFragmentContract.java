package com.hll_sc_app.app.report.refund.search;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.search.SearchReq;
import com.hll_sc_app.bean.report.search.SearchResultItem;

import java.util.List;

public interface RefundSearchFragmentContract {

    interface IRefundSearchView extends ILoadView{

        SearchReq getRequestParams();

        /**
         * 设置搜索结果
         */
        void setSearchResultList(List<SearchResultItem> resultList);
    }

    interface IRefundSearchPresenter extends IPresenter<RefundSearchFragmentContract.IRefundSearchView> {
        /**
         * 查询集团门店搜索结果
         * @param showLoading true显示加载对话框
         */
        void querySearchList(boolean showLoading);

        /**
         * 加载更多集团门店搜索结果
         */
        void queryMoreSearchList();

    }

}
