package com.hll_sc_app.app.warehouse.shipper.shop.detail.purchasershop;

import android.os.Bundle;
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
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.event.SearchEvent;
import com.hll_sc_app.bean.warehouse.ShipperShopResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

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
public class ShipperPurchaserShopSelectActivity extends BaseLoadActivity implements ShipperPurchaserShopSelectContract.IPurchaserListView {
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
    ShipperShopResp.ShopBean mBean;

    private EmptyView mEmptyView;
    private List<PurchaserShopBean> mList;
    private PurchaserShopListAdapter mAdapter;
    private ShipperPurchaserShopSelectPresenter mPresenter;


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
        toQueryShopList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mTxtTitle.setText(mBean.getPurchaserName());
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new PurchaserShopListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            PurchaserShopBean bean = (PurchaserShopBean) adapter.getItem(position);
            if (bean == null) {
                return;
            }
            if (TextUtils.equals(STRING_ALL, bean.getShopName())) {
                selectAll(!bean.isSelect());
            } else {
                bean.setSelect(!bean.isSelect());
                checkSelectAll();
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
                searchShops(searchContent);
            }
        });
    }

    private void toQueryShopList() {
        mPresenter.queryWarehousePurchaserShopList(mBean.getPurchaserID());
    }

    private void selectAll(boolean select) {
        if (CommonUtils.isEmpty(mList)) {
            return;
        }
        for (PurchaserShopBean bean : mList) {
            bean.setSelect(select);
        }
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 判断是否设置全选
     */
    private void checkSelectAll() {
        if (CommonUtils.isEmpty(mList)) {
            return;
        }
        boolean select = true;
        for (PurchaserShopBean bean : mList) {
            if (!TextUtils.equals(bean.getShopName(), STRING_ALL)) {
                if (!bean.isSelect()) {
                    select = false;
                    break;
                }
            }
        }
        mList.get(0).setSelect(select);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * 本地搜索门店
     *
     * @param searchContent 搜索词
     */
    private void searchShops(String searchContent) {
        if (TextUtils.isEmpty(searchContent)) {
            mAdapter.setNewData(mList);
            return;
        }
        List<PurchaserShopBean> listFilter = new ArrayList<>();
        if (!CommonUtils.isEmpty(mList)) {
            for (PurchaserShopBean bean : mList) {
                if (!TextUtils.equals(bean.getShopName(), STRING_ALL)
                    && bean.getShopName().contains(searchContent)) {
                    listFilter.add(bean);
                }
            }
        }
        mAdapter.setNewData(listFilter);
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
    private List<String> getSelectList() {
        List<String> selectList = new ArrayList<>();
        if (!CommonUtils.isEmpty(mList)) {
            for (PurchaserShopBean shopBean : mList) {
                if (TextUtils.equals(STRING_ALL, shopBean.getShopName())) {
//                    mBean.setIsAllShop(shopBean.isSelect() ? "1" : "0");
                    continue;
                }
                if (shopBean.isSelect()) {
                    selectList.add(shopBean.getShopID());
                }
            }
        }
        return selectList;
    }

    @Override
    public void onBackPressed() {
        toClose();
    }

    @Override
    public void showPurchaserShopList(List<PurchaserShopBean> list) {
        mList = list;
        if (!CommonUtils.isEmpty(list)) {
//            String shopId = mBean.getShopIDs();
//            if (!TextUtils.isEmpty(shopId)) {
//                for (PurchaserShopBean shopBean : list) {
//                    if (TextUtils.equals(STRING_ALL, shopBean.getShopName())) {
//                        continue;
//                    }
//                    shopBean.setSelect(shopId.contains(shopBean.getShopID()));
//                }
//            }
        }
        mAdapter.setNewData(list);
        mAdapter.setEmptyView(mEmptyView);
        checkSelectAll();
    }

    @Override
    public String getSearchParam() {
        return mSearchView.getSearchContent();
    }

    class PurchaserShopListAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {

        PurchaserShopListAdapter() {
            super(R.layout.item_purchaser_shop_item);
        }

        @Override
        protected void convert(BaseViewHolder helper, PurchaserShopBean bean) {
            helper.setText(R.id.txt_shopName, bean.getShopName())
                .getView(R.id.img_select).setSelected(bean.isSelect());
        }
    }
}
