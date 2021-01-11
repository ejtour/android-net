package com.hll_sc_app.app.goodsdemand.special.list;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goodsdemand.special.detail.SpecialDemandDetailActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsDemandSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goodsdemand.SpecialDemandBean;
import com.hll_sc_app.bean.goodsdemand.SpecialDemandEntryBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/11
 */

@Route(path = RouterConfig.GOODS_SPECIAL_DEMAND_LIST)
public class SpecialDemandListActivity extends BaseLoadActivity implements ISpecialDemandListContract.ISpecialDemandListView {
    @BindView(R.id.sdl_image)
    GlideImageView mImage;
    @BindView(R.id.sdl_name)
    TextView mName;
    @BindView(R.id.sdl_num)
    TextView mNum;
    @BindView(R.id.sdl_search_view)
    SearchView mSearchView;
    @BindView(R.id.sds_list_view)
    RecyclerView mListView;
    @BindView(R.id.sds_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "parcelable")
    SpecialDemandEntryBean mBean;
    private SpecialDemandListAdapter mAdapter;
    private ISpecialDemandListContract.ISpecialDemandListPresenter mPresenter;
    private EmptyView mEmptyView;

    public static void start(SpecialDemandEntryBean bean) {
        RouterUtil.goToActivity(RouterConfig.GOODS_SPECIAL_DEMAND_LIST, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_special_demand_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }

    private void initView() {
        mImage.setImageURL(mBean.getPurchaserLogo());
        mName.setText(mBean.getPurchaserName());
        mNum.setText(String.format("已设置%s个特殊需求商品", mBean.getProductNum()));
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SpecialDemandListActivity.this, searchContent, GoodsDemandSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.start();
            }
        });
        mAdapter = new SpecialDemandListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SpecialDemandDetailActivity.start(mAdapter.getItem(position));
        });
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

    private void initData() {
        mPresenter = SpecialDemandListPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @Override
    public void setData(List<SpecialDemandBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) mAdapter.addData(list);
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("该客户没有商品特殊需求");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getPurchaserID() {
        return mBean.getPurchaserID();
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
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }
}
