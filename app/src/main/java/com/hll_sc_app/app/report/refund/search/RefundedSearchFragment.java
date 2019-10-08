package com.hll_sc_app.app.report.refund.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.sdk.android.ams.common.util.StringUtil;
import com.hll_sc_app.R;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.report.search.SearchReq;
import com.hll_sc_app.bean.report.search.SearchResultItem;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = RouterConfig.REPORT_REFUND_SEARCH_FRAGMENT)
public class RefundedSearchFragment extends BaseRefundSearchFragment implements RefundSearchFragmentContract.IRefundSearchView {

    private final static  String SEARCH_TYPE = "search_type";
    private RefundSearchFragmentContract.IRefundSearchPresenter mPresenter;
    private RefundSearchAdapter mAdapter;
    @BindView(R.id.report_search_recyclerView)
    RecyclerView recyclerView;

    SearchReq searchReq = new SearchReq();
    String searchType;
    Unbinder unbinder;
    public static RefundedSearchFragment newInstance(String actionType) {
        Bundle args = new Bundle();
        args.putString(SEARCH_TYPE, actionType);
        RefundedSearchFragment fragment = new RefundedSearchFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = RefundSearchFragmentPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    public SearchReq getRequestParams() {
        //0-供应商 1-采购商
        searchReq.setSource(0);
        return searchReq;
    }

    @Override
    public void setSearchResultList(List<SearchResultItem> resultList) {
        mAdapter.setNewData(resultList);
    }

    @Override
    public void showLoading() {}

    @Override
    public void hideLoading() {}

    @Override
    public void showError(UseCaseException e) {}

    @Override
    public void showToast(String message) {}

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_report_search, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        Bundle bundle = getArguments();
        searchType = bundle.getString(SEARCH_TYPE);
        initView();
        return rootView;
    }

    private void initView() {
        mAdapter = new RefundSearchAdapter();
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SearchResultItem item = (SearchResultItem) adapter.getItem(position);
            Intent intent = new Intent();
            item.setType(Integer.valueOf(searchType));
            intent.putExtra("result",item);
            getActivity().setResult(getActivity().RESULT_OK,intent);
            getActivity().finish();
        });
    }

    @Override
    protected void initData() {
        toSearch();
    }

    @Override
    public void toSearch() {
        searchReq.setSearchWords(getSearchParam());
        searchReq.setType(Integer.valueOf(searchType));
        mPresenter.querySearchList(true);
    }
}
