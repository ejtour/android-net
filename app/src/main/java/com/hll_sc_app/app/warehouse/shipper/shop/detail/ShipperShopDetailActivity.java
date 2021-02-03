package com.hll_sc_app.app.warehouse.shipper.shop.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.WarehouseSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.warehouse.ShipperShopResp;
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
 * 代仓门店管理详情
 *
 * @author zhuyingsong
 * @date 2019/8/7
 */
@Route(path = RouterConfig.WAREHOUSE_SHIPPER_SHOP_DETAIL, extras = Constant.LOGIN_EXTRA)
public class ShipperShopDetailActivity extends BaseLoadActivity implements ShipperShopDetailContract.IShipperShopDetailView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @Autowired(name = "object0")
    String mWarehouseId;
    @Autowired(name = "object1")
    String mName;
    private EmptyView mEmptyView;
    private EmptyView mSearchEmptyView;
    private WarehouseListAdapter mAdapter;
    private ShipperShopDetailPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_warehouse_shipper_shop_detail);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = ShipperShopDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTxtTitle.setText(mName);
        mSearchEmptyView = EmptyView.newBuilder(this).setTips("搜索不到需要代仓的客户门店").create();
        mEmptyView = EmptyView.newBuilder(this)
            .setTipsTitle("您还没有设置需要代仓的客户门店")
            .setTips("您可以在您的合作客户中选择需要代仓的门店")
            .setTipsButton("选择需代仓的客户门店")
            .setOnClickListener(new EmptyView.OnActionClickListener() {
                @Override
                public void retry() {
                    // no-op
                }

                @Override
                public void action() {
                    toAdd();
                }
            })
            .create();
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(ShipperShopDetailActivity.this,
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
            ShipperShopResp.PurchaserBean bean = mAdapter.getItem(position);
            if (bean != null) {
                if (view.getId() == R.id.txt_del) {
                    showDelTipsDialog(bean);
                } else if (view.getId() == R.id.content) {
                    bean.setWarehouseId(mWarehouseId);
                    bean.setDetail(true);
                    RouterUtil.goToActivity(RouterConfig.WAREHOUSE_SHIPPER_SHOP_DETAIL_PURCHASER_SHOP, bean);
                }
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
    }

    private void toAdd() {
        RouterUtil.goToActivity(RouterConfig.WAREHOUSE_SHIPPER_SHOP_DETAIL_PURCHASER, mWarehouseId);
    }

    /**
     * 删除关系提示框
     *
     * @param bean 代仓集团
     */
    private void showDelTipsDialog(ShipperShopResp.PurchaserBean bean) {
        TipsDialog.newBuilder(this)
            .setTitle("确定要删除该集团么")
            .setMessage("删除该集团将附带删除该集团下所有已选需代仓的门店")
            .setButton((dialog, item) -> {
                if (item == 1) {
                    mPresenter.editWarehousePurchaser(bean, "delete");
                } else {
                    SwipeItemLayout.closeAllItems(mRecyclerView);
                }
                dialog.dismiss();
            }, "我再看看", "确定删除")
            .create().show();
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
    public String getWarehouseId() {
        return mWarehouseId;
    }

    @Override
    public void showWarehouseList(List<ShipperShopResp.PurchaserBean> list, boolean append, int totalNum) {
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

    @OnClick({R.id.img_close, R.id.txt_options})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        } else if (view.getId() == R.id.txt_options) {
            toAdd();
        }
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mPresenter.start();
    }

    private class WarehouseListAdapter extends BaseQuickAdapter<ShipperShopResp.PurchaserBean, BaseViewHolder> {

        WarehouseListAdapter() {
            super(R.layout.list_item_warehouse_shipper);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
            viewHolder.addOnClickListener(R.id.content).addOnClickListener(R.id.txt_del);
            return viewHolder;
        }

        @Override
        protected void convert(BaseViewHolder helper, ShipperShopResp.PurchaserBean item) {
            ((GlideImageView) helper.getView(R.id.img_logoUrl)).setImageURL(item.getPurchaserLogo());
            helper.setText(R.id.txt_groupName, item.getPurchaserName())
                .setText(R.id.txt_groupNum, "当前代仓门店数：" + item.getShopNum())
                .setGone(R.id.txt_shopNum, false)
                .setGone(R.id.img_arrow, false);
        }
    }
}
