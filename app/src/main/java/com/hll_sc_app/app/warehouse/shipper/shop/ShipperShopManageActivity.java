package com.hll_sc_app.app.warehouse.shipper.shop;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.WarehouseSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
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
import butterknife.OnClick;

/**
 * 代仓门店管理
 *
 * @author zhuyingsong
 * @date 2019/8/7
 */
@Route(path = RouterConfig.WAREHOUSE_SHIPPER_SHOP, extras = Constant.LOGIN_EXTRA)
public class ShipperShopManageActivity extends BaseLoadActivity implements ShipperShopManageContract.IShipperShopManageView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private EmptyView mEmptyView;
    private EmptyView mSearchEmptyView;
    private WarehouseListAdapter mAdapter;
    private ShipperShopManagePresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_shipper_shop_list);
        ButterKnife.bind(this);
        initView();
        mPresenter = ShipperShopManagePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mSearchEmptyView = EmptyView.newBuilder(this).setTips("搜索不到代仓公司数据").create();
        mEmptyView = EmptyView.newBuilder(this).setTips("当前还没有代仓公司数据").create();
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(ShipperShopManageActivity.this,
                        searchContent, WarehouseSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryWarehouseList(true);
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreWarehouseList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryWarehouseList(false);
            }
        });
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new WarehouseListAdapter();
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            PurchaserBean bean = (PurchaserBean) adapter.getItem(position);
            if (bean != null) {
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_SHIPPER_SHOP_DETAIL, bean.getGroupID(),
                    bean.getGroupName());
            }
        });
        mRecyclerView.setAdapter(mAdapter);
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

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void showWarehouseList(List<PurchaserBean> list, boolean append, int totalNum) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mSearchView.isSearchStatus() ? mSearchEmptyView : mEmptyView);
        mRefreshLayout.setEnableLoadMore(totalNum != mAdapter.getItemCount());
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        finish();
    }

    private class WarehouseListAdapter extends BaseQuickAdapter<PurchaserBean, BaseViewHolder> {

        WarehouseListAdapter() {
            super(R.layout.list_item_warehouse_shipper);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.content);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserBean item) {
            ((GlideImageView) helper.getView(R.id.img_logoUrl)).setImageURL(item.getLogoUrl());
            helper.setText(R.id.txt_groupName, item.getGroupName())
                .setText(R.id.txt_groupNum, "代仓集团数：" + item.getGroupNum())
                .setText(R.id.txt_shopNum, "代仓门店数：" + item.getShopNum());
        }
    }
}
