package com.hll_sc_app.app.aptitude.goods;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/9
 */

public class AptitudeGoodsFragment extends BaseLazyFragment implements RadioGroup.OnCheckedChangeListener, IAptitudeGoodsContract.IAptitudeGoodsView {

    @BindView(R.id.fag_radio_group)
    RadioGroup mRadioGroup;
    @BindView(R.id.fag_search_view)
    SearchView mSearchView;
    @BindView(R.id.fag_list_view)
    RecyclerView mListView;
    @BindView(R.id.fag_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    Unbinder unbinder;
    @BindView(R.id.fag_set)
    RadioButton mSet;
    @BindView(R.id.fag_not_set)
    RadioButton mNotSet;
    private IAptitudeGoodsContract.IAptitudeGoodsPresenter mPresenter;
    private EmptyView mEmptyView;
    private AptitudeGoodsAdapter mAdapter;

    public static AptitudeGoodsFragment newInstance() {
        return new AptitudeGoodsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = AptitudeGoodsPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_aptitude_goods, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        initView();
        return rootView;
    }

    private void initView() {
        mSearchView.setSearchBackgroundColor(android.R.color.transparent);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(requireActivity(), mSearchView.getTag().toString(), GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                initData();
            }
        });
        mRadioGroup.setOnCheckedChangeListener(this);
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(requireContext(), R.color.color_eeeeee), ViewUtils.dip2px(requireContext(), 0.5f));
        decor.setLineMargin(UIUtils.dip2px(85), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mAdapter = new AptitudeGoodsAdapter();
        mListView.setAdapter(mAdapter);
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
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    @Override
    protected void initData() {
        if (!mNotSet.isChecked() && !mSet.isChecked()) {
            mSet.setChecked(true);
        } else {
            mPresenter.start();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        initData();
    }

    @Override
    public void setData(List<GoodsBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTipsTitle(mSet.isChecked() ? "您还没有设置商品资质噢" : "暂无未设置资质的商品");
                mEmptyView.setTips(mSet.isChecked() ? "切换到未设置页面，点击商品开始新增资质吧" : "");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public boolean isChecked() {
        return mSet.isChecked();
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(requireActivity())
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }
}
