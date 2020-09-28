package com.hll_sc_app.app.price.local;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.ProductNameSearch;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.AreaBean;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.price.CategoryBean;
import com.hll_sc_app.bean.price.LocalPriceBean;
import com.hll_sc_app.bean.price.MarketBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/28
 */

public class PriceLocalFragment extends BaseLazyFragment implements IPriceLocalContract.IPriceLocalView {
    @BindView(R.id.fpl_region)
    TextView mRegion;
    @BindView(R.id.fpl_market)
    TextView mMarket;
    @BindView(R.id.fpl_category)
    TextView mCategory;
    @BindView(R.id.fpl_list_view)
    RecyclerView mListView;
    @BindView(R.id.fpl_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    Unbinder unbinder;
    private IPriceLocalContract.IPriceLocalPresenter mPresenter;
    private PriceLocalAdapter mAdapter;
    private EmptyView mEmptyView;
    private String mProvinceCode = "11";
    private String mMarketCode = "";
    private String mCategoryCode;
    private SingleSelectionDialog mCategoryDialog;
    private SingleSelectionDialog mRegionDialog;
    private SingleSelectionDialog mMarketDialog;
    private List<CategoryBean> mCategoryList;
    private Map<String, List<MarketBean>> mMarketMap = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = PriceLocalPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_price_local, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        mAdapter = new PriceLocalAdapter();
        mListView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(requireContext(), R.color.color_eeeeee),
                ViewUtils.dip2px(requireContext(), 0.5f)));
        mListView.setAdapter(mAdapter);

        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(getActivity(),
                        searchContent, ProductNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.loadList();
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fpl_region_group, R.id.fpl_market_group, R.id.fpl_category_group})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fpl_region_group:
                showRegionDialog();
                break;
            case R.id.fpl_market_group:
                showMarketDialog();
                break;
            case R.id.fpl_category_group:
                showCategoryDialog();
                break;
        }
    }

    private void showMarketDialog() {
        List<MarketBean> beans = mMarketMap.get(mProvinceCode);
        if (beans == null) {
            mPresenter.queryMarket(mProvinceCode);
            return;
        }
        if (mMarketDialog == null) {
            mMarketDialog = SingleSelectionDialog.newBuilder(requireActivity(), MarketBean::getMarketName)
                    .setTitleText("选择市场")
                    .setOnSelectListener(marketBean -> {
                        if (!marketBean.getMarketCode().equals(mMarketCode)) {
                            mMarket.setText(marketBean.getMarketName());
                            mMarketCode = marketBean.getMarketCode();
                            mPresenter.loadList();
                        }
                    })
                    .create();
        }
        List<MarketBean> list = new ArrayList<>();
        MarketBean all = new MarketBean();
        all.setMarketName("全部");
        all.setMarketCode("");
        list.add(all);
        list.addAll(beans);
        mMarketDialog.refreshList(list);
        mMarketDialog.show();
    }

    private void showRegionDialog() {
        if (mRegionDialog == null) {
            List<AreaBean> areaList = UIUtils.getAreaList(requireContext(), false);
            mRegionDialog = SingleSelectionDialog.newBuilder(requireActivity(), AreaBean::getName)
                    .setTitleText("选择地区")
                    .refreshList(areaList)
                    .setOnSelectListener(areaBean -> {
                        if (!areaBean.getCode().equals(mProvinceCode)) {
                            mMarketCode = null;
                            mMarket.setText("全部");
                            mProvinceCode = areaBean.getCode();
                            mRegion.setText(areaBean.getName());
                            mPresenter.loadList();
                        }
                    })
                    .create();
        }
        mRegionDialog.show();
    }

    private void showCategoryDialog() {
        if (mCategoryList == null) {
            mPresenter.queryCategory();
            return;
        }
        if (mCategoryDialog == null) {
            mCategoryDialog = SingleSelectionDialog.newBuilder(requireActivity(), CategoryBean::getFatherName)
                    .setTitleText("选择品类")
                    .refreshList(mCategoryList)
                    .setOnSelectListener(categoryBean -> {
                        if (!categoryBean.getFatherCode().equals(mCategoryCode)) {
                            mCategory.setText(categoryBean.getFatherName());
                            mCategoryCode = categoryBean.getFatherCode();
                            mPresenter.loadList();
                        }
                    })
                    .create();
        }
        mCategoryDialog.show();
    }

    @Override
    public void handleMarket(List<MarketBean> list) {
        mMarketMap.put(mProvinceCode, list);
        showMarketDialog();
    }

    @Override
    public void cacheCategory(List<CategoryBean> list) {
        mCategoryList = list;
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setData(List<LocalPriceBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("没有数据哦");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(requireActivity())
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public String getProvinceCode() {
        return mProvinceCode;
    }

    @Override
    public String getMarketCode() {
        return mMarketCode;
    }

    @Override
    public String getCategoryCode() {
        return mCategoryCode;
    }


    @Override
    public void search(String content) {
        mSearchView.showSearchContent(true, content);
    }

    @Override
    public String getSearchContent() {
        return mSearchView.getSearchContent();
    }
}
