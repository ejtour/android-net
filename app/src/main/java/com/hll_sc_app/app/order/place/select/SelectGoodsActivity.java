package com.hll_sc_app.app.order.place.select;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.place.confirm.PlaceOrderConfirmActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.order.place.PlaceOrderSpecBean;
import com.hll_sc_app.bean.order.place.ProductBean;
import com.hll_sc_app.bean.order.place.ProductSpecBean;
import com.hll_sc_app.bean.order.place.SelectGoodsParam;
import com.hll_sc_app.bean.order.place.SettlementInfoReq;
import com.hll_sc_app.bean.order.place.SettlementInfoResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.KeyboardWatcher;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnTouch;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/16
 */
@Route(path = RouterConfig.ORDER_PLACE_SELECT_GOODS)
public class SelectGoodsActivity extends BaseLoadActivity implements ISelectGoodsContract.ISelectGoodsView, KeyboardWatcher.SoftKeyboardStateListener {
    @BindView(R.id.osg_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.osg_search_view)
    SearchView mSearchView;
    @BindView(R.id.osg_tab_layout)
    CommonTabLayout mTabLayout;
    @BindView(R.id.osg_top_tab)
    CommonTabLayout mTopTab;
    @BindView(R.id.osg_left_list)
    RecyclerView mLeftList;
    @BindView(R.id.osg_right_list)
    RecyclerView mRightList;
    @BindView(R.id.osg_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "parcelable")
    SelectGoodsParam mParam;
    private EmptyView mEmptyView;
    private CategoryAdapter mCategoryAdapter;
    private SelectGoodsAdapter mAdapter;
    private ISelectGoodsContract.ISelectGoodsPresenter mPresenter;
    private List<CustomCategoryBean> mCategoryList;
    private List<PlaceOrderSpecBean> mSpecList = new ArrayList<>();
    private KeyboardWatcher mKeyboardWatcher;

    /**
     * @param purchaserID 采购商集团id
     * @param shopID      采购商门店id
     * @param shopName    采购商门店名称
     */
    public static void start(String purchaserID, String shopID, String shopName, int warehouse) {
        SelectGoodsParam param = new SelectGoodsParam();
        param.setPurchaserID(purchaserID);
        param.setShopID(shopID);
        param.setShopName(shopName);
        param.setWarehouse(warehouse);
        RouterUtil.goToActivity(RouterConfig.ORDER_PLACE_SELECT_GOODS, param);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_order_select_goods);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        mKeyboardWatcher.removeSoftKeyboardStateListener(this);
        super.onDestroy();
    }

    private void initData() {
        mPresenter = SelectGoodsPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::confirm);
        mKeyboardWatcher = new KeyboardWatcher(this);
        mKeyboardWatcher.addSoftKeyboardStateListener(this);
        mCategoryAdapter = new CategoryAdapter();
        mLeftList.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setOnItemClickListener((adapter, view, position) -> mCategoryAdapter.select(position));
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                CustomCategoryBean bean = mCategoryList.get(position);
                mCategoryAdapter.setNewData(bean.getSubList());
                mParam.setSubID(bean.getId());
                mParam.setThreeID(null);
                mPresenter.loadList();
            }

            @Override
            public void onTabReselect(int position) {
                // no-op
            }
        });
        mAdapter = new SelectGoodsAdapter((v, hasFocus) -> {
            if (!hasFocus) {
                ProductSpecBean bean = (ProductSpecBean) v.getTag();
                double inputNum = CommonUtils.getDouble(((EditText) v).getText().toString());
                double minNum = bean.getBuyMinNum();
                double minOrder = bean.getMinOrder();
                if (inputNum < minNum) {
                    showToast(String.format("该商品的最低起购数量是%s哦", CommonUtils.formatNum(minNum)));
                } else if (minOrder != 0 && inputNum % minOrder != 0) {
                    showToast(String.format("该商品的最小订购倍数是%s哦", CommonUtils.formatNum(minOrder)));
                } else bean.setShopcartNum(inputNum);
                RecyclerView.Adapter adapter = (RecyclerView.Adapter) v.getTag(R.id.sgs_spec);
                adapter.notifyDataSetChanged();
                updateNum(bean);
            }
        }, (adapter, view, position) -> {
            if (!mTitleBar.isFocused()) {
                onTouch(view);
                return;
            }
            ProductSpecBean item = (ProductSpecBean) adapter.getItem(position);
            if (item == null) return;
            double step = item.getMinOrder();
            double minNum = item.getBuyMinNum();
            double result = item.getShopcartNum();
            if (step == 0) step = 1;
            switch (view.getId()) {
                case R.id.sgs_add_btn:
                    result = CommonUtils.addDouble(result, step).doubleValue();
                    if (result < minNum) result = Math.ceil(minNum / step) * step;
                    break;
                case R.id.sgs_sub_btn:
                    result = CommonUtils.subDouble(result, step).doubleValue();
                    if (result < minNum) result = 0;
                    break;
            }
            item.setShopcartNum(result);
            adapter.notifyDataSetChanged();
            updateNum(item);
        });
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(90), 0, 0, 0, Color.WHITE);
        mRightList.addItemDecoration(decor);
        mRightList.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(SelectGoodsActivity.this,
                        searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mParam.setSearchWords(searchContent);
                mPresenter.loadList();
            }
        });

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
        if (mParam.getWarehouse() == 1) {
            mTopTab.setVisibility(View.VISIBLE);
            ArrayList<CustomTabEntity> arrayList = new ArrayList<>();
            arrayList.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return "自营商品";
                }

                @Override
                public int getTabSelectedIcon() {
                    return 0;
                }

                @Override
                public int getTabUnselectedIcon() {
                    return 0;
                }
            });
            arrayList.add(new CustomTabEntity() {
                @Override
                public String getTabTitle() {
                    return "代仓商品";
                }

                @Override
                public int getTabSelectedIcon() {
                    return 0;
                }

                @Override
                public int getTabUnselectedIcon() {
                    return 0;
                }
            });
            mTopTab.setTabData(arrayList);
            mTopTab.setOnTabSelectListener(new OnTabSelectListener() {
                @Override
                public void onTabSelect(int position) {
                    if (mTabLayout.getTabCount() > 0) {
                        mTabLayout.setCurrentTab(0);
                    }
                    mParam.setWarehouse(position == 1);
                    mPresenter.start();
                }

                @Override
                public void onTabReselect(int position) {
                    // no-op
                }
            });
        }
    }

    private void confirm(View view) {
        List<PlaceOrderSpecBean> list = new ArrayList<>();
        for (PlaceOrderSpecBean bean : mSpecList) {
            if (CommonUtils.getDouble(bean.getProductNum()) > 0) {
                list.add(bean);
            }
        }
        if (list.size() == 0) {
            showToast("请先添加商品");
        } else {
            SettlementInfoReq req = new SettlementInfoReq();
            req.setSpecs(list);
            req.setPurchaserID(mParam.getPurchaserID());
            req.setShopID(mParam.getShopID());
            req.setShopName(mParam.getShopName());
            mPresenter.confirm(req);
        }
    }

    private void updateNum(ProductSpecBean item) {
        PlaceOrderSpecBean select = null;
        for (PlaceOrderSpecBean bean : mSpecList) {
            if (bean.getProductSpecID().equals(item.getProductSpecID())) {
                select = bean;
                break;
            }
        }
        if (select == null) {
            select = new PlaceOrderSpecBean();
            select.setProductID(item.getProductID());
            select.setProductSpecID(item.getProductSpecID());
            mSpecList.add(select);
        }
        select.setProductNum(CommonUtils.formatNumber(item.getShopcartNum()));
    }

    @Override
    public void setCategoryInfo(List<CustomCategoryBean> list) {
        mCategoryList = list;
        if (!CommonUtils.isEmpty(list)) {
            ArrayList<CustomTabEntity> arrayList = new ArrayList<>();
            for (CustomCategoryBean bean : list) {
                arrayList.add(new CustomTabEntity() {
                    @Override
                    public String getTabTitle() {
                        return bean.getCategoryName();
                    }

                    @Override
                    public int getTabSelectedIcon() {
                        return 0;
                    }

                    @Override
                    public int getTabUnselectedIcon() {
                        return 0;
                    }
                });
            }
            mTabLayout.setTabData(arrayList);
            mCategoryAdapter.setNewData(list.get(0).getSubList());
        }
    }

    @OnTouch({R.id.osg_title_bar, R.id.osg_search_view, R.id.osg_left_list, R.id.osg_right_list, R.id.osg_refresh_layout, R.id.osg_scroll_view})
    public boolean onTouch(View view) {
        UIUtils.hideActivitySoftKeyboard(this);
        onSoftKeyboardClosed();
        return false;
    }

    @Override
    public void setGoodsList(List<ProductBean> list, boolean append) {
        preProcessData(list);
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("暂无商品列表");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void confirmSuccess(SettlementInfoResp resp) {
        PlaceOrderConfirmActivity.start(resp);
    }

    private void preProcessData(List<ProductBean> list) {
        if (CommonUtils.isEmpty(list) || CommonUtils.isEmpty(mSpecList)) return;
        for (ProductBean bean : list) {
            for (PlaceOrderSpecBean specBean : mSpecList) {
                if (specBean.getProductID().equals(bean.getProductID())) {
                    for (ProductSpecBean spec : bean.getSpecs()) {
                        if (spec.getProductSpecID().equals(specBean.getProductSpecID())) {
                            spec.setShopcartNum(CommonUtils.getDouble(specBean.getProductNum()));
                        }
                    }
                }
            }
        }
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
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

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void onSoftKeyboardOpened(int keyboardHeightInPx) {
        // no-op
    }

    @Override
    public void onSoftKeyboardClosed() {
        mTitleBar.requestFocus();
    }

    private class CategoryAdapter extends BaseQuickAdapter<CustomCategoryBean, BaseViewHolder> {
        private int mCurPos;

        CategoryAdapter() {
            super(R.layout.item_order_select_menu);
        }

        private void select(int position) {
            mCurPos = position;
            notifyDataSetChanged();
            mParam.setThreeID(mData.get(position).getId());
            mPresenter.loadList();
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomCategoryBean item) {
            TextView itemView = (TextView) helper.itemView;
            itemView.setText(item.getCategoryName());
            itemView.setSelected(mCurPos == mData.indexOf(item));
        }

        @Override
        public void setNewData(@Nullable List<CustomCategoryBean> data) {
            mCurPos = 0;
            List<CustomCategoryBean> temp = new ArrayList<>();
            CustomCategoryBean bean = new CustomCategoryBean();
            bean.setCategoryName("全部");
            temp.add(bean);
            if (data != null) temp.addAll(data);
            super.setNewData(temp);
        }
    }
}
