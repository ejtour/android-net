package com.hll_sc_app.app.stockmanage.storehousemanage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.stockmanage.storehousemanage.edit.StoreHouseEditActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.stockmanage.StorehouseListResp;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 仓库管理
 */
@Route(path = RouterConfig.ACTIVITY_STORE_HOUSE_MANAGE)
public class StoreHouseManageActivity extends BaseLoadActivity implements IStoreHouseManageContract.IView {
    private static final int REQUEST_CODE_TO_EDIT = 100;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    private Unbinder unbinder;
    private StoreHouseAdpter mAdapter;
    private IStoreHouseManageContract.IPresent mPresent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_store_house_manage);
        unbinder = ButterKnife.bind(this);
        mPresent = StoreHouseManagePresent.newInstance();
        mPresent.register(this);
        initView();
        mPresent.getStoreHouseList(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(v -> {
            StoreHouseEditActivity.start(this, REQUEST_CODE_TO_EDIT, null);
        });

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new StoreHouseAdpter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            StoreHouseEditActivity.start(this, REQUEST_CODE_TO_EDIT, mAdapter.getItem(position));
        });
        mRecyclerView.setAdapter(mAdapter);
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.getMoreList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refreshList();
            }
        });

    }

    @Override
    public void showStoreHouseList(StorehouseListResp storehouseListResp, boolean isMore) {
        if (isMore && storehouseListResp.getList().size() > 0) {
            mAdapter.addData(storehouseListResp.getList());
        } else if (!isMore) {
            mAdapter.setNewData(storehouseListResp.getList());
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("喔唷，居然是「 空 」的").create());
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefresh.closeHeaderOrFooter();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_TO_EDIT:
                mPresent.refreshList();
                break;
            default:
                break;
        }
    }

    public class StoreHouseAdpter extends BaseQuickAdapter<StorehouseListResp.Storehouse, BaseViewHolder> {
        public StoreHouseAdpter(@Nullable List<StorehouseListResp.Storehouse> data) {
            super(R.layout.list_item_store_house, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, StorehouseListResp.Storehouse item) {
            helper.setText(R.id.txt_name, item.getHouseName())
                    .setText(R.id.txt_code, item.getHouseCode())
                    .setText(R.id.txt_link_name, item.getCharge())
                    .setText(R.id.txt_link_phone, item.getLinkTel())
                    .setText(R.id.txt_addr, item.getAddress());

        }
    }
}
