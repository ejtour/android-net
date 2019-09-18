package com.hll_sc_app.app.order.place.select;

import android.graphics.Color;
import android.os.Bundle;
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
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.GoodsSearchEvent;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.bean.order.place.GoodsCategoryBean;
import com.hll_sc_app.bean.order.place.PlaceOrderSpecBean;
import com.hll_sc_app.bean.order.place.SelectGoodsParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.KeyboardWatcher;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    private List<GoodsCategoryBean> mCategoryList;
    private List<PlaceOrderSpecBean> mSpecList = new ArrayList<>();
    private KeyboardWatcher mKeyboardWatcher;
    private KeyboardWatcher.SoftKeyboardStateListener mListener;

    public static void start(String purchaserID, String purchaserShopID) {
        SelectGoodsParam param = new SelectGoodsParam();
        param.setPurchaserID(purchaserID);
        param.setPurchaserShopID(purchaserShopID);
        RouterUtil.goToActivity(RouterConfig.ORDER_PLACE_SELECT_GOODS, param);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_order_select_goods);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        mKeyboardWatcher.removeSoftKeyboardStateListener(this);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initData() {
        mPresenter = SelectGoodsPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mKeyboardWatcher = new KeyboardWatcher(this);
        mKeyboardWatcher.addSoftKeyboardStateListener(this);
        mCategoryAdapter = new CategoryAdapter();
        mLeftList.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setOnItemClickListener((adapter, view, position) -> mCategoryAdapter.select(position));
        mTabLayout.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                GoodsCategoryBean bean = mCategoryList.get(position);
                mCategoryAdapter.setNewData(bean.getSubList());
                mParam.setSubID(bean.getId());
                mParam.setThreeID(null);
                mPresenter.loadList();
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
        mAdapter = new SelectGoodsAdapter((v, hasFocus) -> {
            if (!hasFocus) {
                SpecsBean bean = (SpecsBean) v.getTag();
                double inputNum = CommonUtils.getDouble(((EditText) v).getText().toString());
                double minNum = CommonUtils.getDouble(bean.getBuyMinNum());
                double minOrder = CommonUtils.getDouble(bean.getMinOrder());
                if (inputNum < minNum) {
                    showToast(String.format("该商品的最低起购数量是%s哦", CommonUtils.formatNum(minNum)));
                } else if (minOrder != 0 && inputNum % minOrder != 0) {
                    showToast(String.format("该商品的最小订购倍数是%s哦", CommonUtils.formatNum(minOrder)));
                } else bean.setBuyQty(CommonUtils.formatNumber(inputNum));
                RecyclerView.Adapter adapter = (RecyclerView.Adapter) v.getTag(R.id.sgs_spec);
                adapter.notifyDataSetChanged();
            }
        }, (adapter, view, position) -> {
            SpecsBean item = (SpecsBean) adapter.getItem(position);
            if (item == null) return;
            double step = CommonUtils.getDouble(item.getMinOrder());
            if (step == 0) step = 1;
            switch (view.getId()) {
                case R.id.sgs_add_btn:
                    item.setBuyQty(CommonUtils.addDouble(CommonUtils.getDouble(item.getBuyQty())
                            , step).toPlainString());
                    break;
                case R.id.sgs_sub_btn:
                    double result = CommonUtils.subDouble(CommonUtils.getDouble(item.getBuyQty())
                            , step).doubleValue();
                    item.setBuyQty(CommonUtils.formatNumber(Math.max(0, result)));
                    break;
            }
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
                SearchActivity.start(searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mParam.setSearchWords(searchContent);
                mPresenter.loadList();
            }
        });
    }

    private void updateNum(SpecsBean item) {
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
        select.setProductNum(item.getBuyQty());
    }

    @Override
    public void setCategoryInfo(List<GoodsCategoryBean> list) {
        mCategoryList = list;
        if (!CommonUtils.isEmpty(list)) {
            ArrayList<CustomTabEntity> arrayList = new ArrayList<>();
            for (GoodsCategoryBean bean : list) {
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
            mTabLayout.setCurrentTab(0);
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
    public void setGoodsList(List<GoodsBean> list) {
        if (CommonUtils.isEmpty(list)) {
            initEmptyView();
            mEmptyView.reset();
            mEmptyView.setTips("暂无商品列表");
        }
        preProcessData(list);
        mAdapter.setNewData(list);
    }

    private void preProcessData(List<GoodsBean> list) {
        if (CommonUtils.isEmpty(list) || CommonUtils.isEmpty(mSpecList)) return;
        for (GoodsBean bean : list) {
            for (PlaceOrderSpecBean specBean : mSpecList) {
                if (specBean.getProductID().equals(bean.getProductID())) {
                    for (SpecsBean spec : bean.getSpecs()) {
                        if (spec.getProductSpecID().equals(specBean.getProductSpecID())) {
                            spec.setBuyQty(specBean.getProductNum());
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleGoodsEvent(GoodsSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
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

    private class CategoryAdapter extends BaseQuickAdapter<GoodsCategoryBean, BaseViewHolder> {
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
        protected void convert(BaseViewHolder helper, GoodsCategoryBean item) {
            TextView itemView = (TextView) helper.itemView;
            itemView.setText(item.getCategoryName());
            itemView.setSelected(mCurPos == mData.indexOf(item));
        }

        @Override
        public void setNewData(@Nullable List<GoodsCategoryBean> data) {
            mCurPos = 0;
            List<GoodsCategoryBean> temp = new ArrayList<>();
            GoodsCategoryBean bean = new GoodsCategoryBean();
            bean.setCategoryName("全部");
            temp.add(bean);
            if (data != null) temp.addAll(data);
            super.setNewData(temp);
        }
    }
}
