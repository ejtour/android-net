package com.hll_sc_app.app.warehouse.shipper.shop.detail.purchaser;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.PurchaserNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
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
 * 选择合作采购商
 *
 * @author zhuyingsong
 * @date 2019/8/7
 */
@Route(path = RouterConfig.WAREHOUSE_SHIPPER_SHOP_DETAIL_PURCHASER, extras = Constant.LOGIN_EXTRA)
public class ShipperPurchaserSelectActivity extends BaseLoadActivity implements ShipperPurchaserSelectContract.IPurchaserView {
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @Autowired(name = "object0")
    String mWarehouseId;

    private EmptyView mEmptyView;
    private EmptyView mSearchEmptyView;
    private PurchaserListAdapter mAdapter;
    private ShipperPurchaserSelectPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_add_purchaser);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = ShipperPurchaserSelectPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(ShipperPurchaserSelectActivity.this,
                        searchContent, PurchaserNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryPurchaserList(true);
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMorePurchaserList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryPurchaserList(false);
            }
        });
        mSearchEmptyView = EmptyView.newBuilder(this).setTips("搜索不到合作客户数据").create();
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有合作客户").create();
        mRecyclerView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        mAdapter = new PurchaserListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ShipperShopResp.PurchaserBean purchaserBean = (ShipperShopResp.PurchaserBean) adapter.getItem(position);
            if (purchaserBean != null) {
                purchaserBean.setWarehouseId(mWarehouseId);
                RouterUtil.goToActivity(RouterConfig.WAREHOUSE_SHIPPER_SHOP_DETAIL_PURCHASER_SHOP, purchaserBean);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
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

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        finish();
    }

    @Override
    public void showPurchaserList(List<ShipperShopResp.PurchaserBean> list, boolean append, int totalNum) {
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

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    class PurchaserListAdapter extends BaseQuickAdapter<ShipperShopResp.PurchaserBean, BaseViewHolder> {

        PurchaserListAdapter() {
            super(R.layout.item_purchaser_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, ShipperShopResp.PurchaserBean bean) {
            helper.setText(R.id.txt_purchaserName, bean.getPurchaserName());
        }
    }
}
