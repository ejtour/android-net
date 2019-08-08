package com.hll_sc_app.app.warehouse.shipper.shop.detail.purchasershop;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.SearchEvent;
import com.hll_sc_app.bean.warehouse.ShipperShopResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择合作采购商-选择门店
 *
 * @author zhuyingsong
 * @date 2019/8/8
 */
@Route(path = RouterConfig.WAREHOUSE_SHIPPER_SHOP_DETAIL_PURCHASER_SHOP, extras = Constant.LOGIN_EXTRA)
public class ShipperPurchaserShopSelectActivity extends BaseLoadActivity implements ShipperPurchaserShopSelectContract.IShopListView {
    public static final String STRING_ALL = "全部";
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Autowired(name = "parcelable")
    ShipperShopResp.PurchaserBean mBean;

    private EmptyView mEmptyView;
    private ShopListAdapter mAdapter;
    private ShipperPurchaserShopSelectPresenter mPresenter;
    private Map<String, ShipperShopResp.ShopBean> mSelectMap;

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipper_purchaser_shop_select);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = ShipperPurchaserShopSelectPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mSelectMap = new HashMap<>();
        mTxtTitle.setText(mBean.getPurchaserName());
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreShopList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryShopList(false);
            }
        });
        mRecyclerView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(1)));
        mAdapter = new ShopListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ShipperShopResp.ShopBean bean = (ShipperShopResp.ShopBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            if (TextUtils.equals(STRING_ALL, bean.getShopName())) {
                selectAll(!bean.isSelect());
            } else {
                bean.setSelect(!bean.isSelect());
                addOrRemove(bean);
                checkSelectAll();
                adapter.notifyItemChanged(position);
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有合作采购商门店数据").create();
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, CommonSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryShopList(true);
            }
        });
    }

    private void selectAll(boolean select) {
        List<ShipperShopResp.ShopBean> shopBeans = mAdapter.getData();
        if (CommonUtils.isEmpty(shopBeans)) {
            return;
        }
        for (ShipperShopResp.ShopBean bean : shopBeans) {
            bean.setSelect(select);
            addOrRemove(bean);
        }
        mAdapter.notifyDataSetChanged();
    }

    private void addOrRemove(ShipperShopResp.ShopBean bean) {
        if (bean == null) {
            return;
        }
        if (bean.isSelect()) {
            if (!mSelectMap.containsKey(bean.getId())) {
                mSelectMap.put(bean.getId(), bean);
            }
        } else {
            mSelectMap.remove(bean.getId());
        }
    }

    private void checkSelectAll() {
        List<ShipperShopResp.ShopBean> shopBeans = mAdapter.getData();
        if (CommonUtils.isEmpty(shopBeans)) {
            return;
        }
        boolean select = true;
        for (ShipperShopResp.ShopBean bean : shopBeans) {
            if (!TextUtils.equals(bean.getShopName(), STRING_ALL)) {
                if (!bean.isSelect()) {
                    select = false;
                    break;
                }
            }
        }
        ShipperShopResp.ShopBean firstBean = shopBeans.get(0);
        if (firstBean != null && TextUtils.equals(STRING_ALL, firstBean.getShopName())) {
            firstBean.setSelect(select);
            mAdapter.notifyItemChanged(0);
        }
    }

    @Subscribe
    public void onEvent(SearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @OnClick({R.id.img_close, R.id.txt_confirm})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            toClose();
        } else if (view.getId() == R.id.txt_confirm) {
            getSelectList();
        }
    }

    /**
     * 返回时候逻辑处理
     */
    private void toClose() {
        finish();
    }

    /**
     * 获取选中的门店
     *
     * @return 选中的门店列表
     */
    private List<ShipperShopResp.ShopBean> getSelectList() {
        return new ArrayList<>(mSelectMap.values());
    }

    @Override
    public void onBackPressed() {
        toClose();
    }

    @Override
    public void showShopList(List<ShipperShopResp.ShopBean> list, boolean append, int total) {
        if (!CommonUtils.isEmpty(list)) {
            for (ShipperShopResp.ShopBean shopBean : list) {
                if (mSelectMap.containsKey(shopBean.getId())) {
                    shopBean.setSelect(true);
                }
            }
        }
        if (append) {
            mAdapter.addData(list);
        } else {
            if (!CommonUtils.isEmpty(list)) {
                ShipperShopResp.ShopBean shopBeanAll = new ShipperShopResp.ShopBean();
                shopBeanAll.setShopName(STRING_ALL);
                list.add(0, shopBeanAll);
            }
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != (total + 1));
        checkSelectAll();
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getPurchaserId() {
        String id = null;
        if (mBean != null) {
            id = mBean.getPurchaserID();
        }
        return id;
    }

    class ShopListAdapter extends BaseQuickAdapter<ShipperShopResp.ShopBean, BaseViewHolder> {

        ShopListAdapter() {
            super(R.layout.item_purchaser_shop_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, ShipperShopResp.ShopBean bean) {
            helper.setText(R.id.txt_shopName, bean.getShopName())
                .getView(R.id.img_select).setSelected(bean.isSelect());
        }
    }
}
