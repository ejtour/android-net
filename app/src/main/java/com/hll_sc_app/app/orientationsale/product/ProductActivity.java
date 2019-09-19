package com.hll_sc_app.app.orientationsale.product;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.GoodsSearchEvent;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.bean.orientation.OrientationDetailBean;
import com.hll_sc_app.bean.orientation.OrientationProductSpecBean;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.ORIENTATION_PRODUCT, extras = Constant.LOGIN_EXTRA)
public class ProductActivity extends BaseLoadActivity implements IProductContract.IProductView {

    private IProductContract.IProductPresenter mPresenter;

    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.categoryRecyclerView)
    RecyclerView mCategoryRecyclerView;
    @BindView(R.id.productRecyclerView)
    RecyclerView mProductRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.txt_select)
    TextView mSelectView;

    private ProductAdapter mProductAdapter;
    private ProductCategoryAdapter mCategoryAdapter;

    private EmptyView mProductEmptyView;
    private EmptyView mCategoryEmptyView;

    private String selectCategory;

    @Autowired(name = "parcelable")
    ArrayList<OrientationDetailBean> mProductList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation_product_list);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        mPresenter = ProductPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        initView();
        EventBus.getDefault().register(this);
    }

    private void initView() {
        if (mProductList == null) {
            mProductList = new ArrayList<>();
        }
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryGoodsList(1, true);
            }
        });

        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryGoodsList(null, false);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryGoodsList(1, false);
            }
        });

        mProductRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mProductRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mProductAdapter = new ProductAdapter(mProductList);
        mProductAdapter.setOnItemClickListener((adapter, view, position) -> {
            GoodsBean goodsBean = (GoodsBean) adapter.getData().get(position);
            if (goodsBean.isCheck()) {
                for (OrientationDetailBean bean : mProductList) {
                    if (bean.getProductID().equalsIgnoreCase(goodsBean.getProductID())) {
                        mProductList.remove(bean);
                        break;
                    }
                }
            } else {
                OrientationDetailBean bean = goodsBeanToOrientationDetailBean(goodsBean);
                mProductList.add(bean);
            }
            goodsBean.setCheck(!goodsBean.isCheck());
            mSelectView.setText("已选：" + mProductList.size());
            adapter.notifyItemChanged(position);
        });
        mProductEmptyView = EmptyView.newBuilder(this).setTips("该分类暂无商品").create();
        mProductRecyclerView.setAdapter(mProductAdapter);

        mCategoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mCategoryRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
                , UIUtils.dip2px(1)));
        mCategoryAdapter = new ProductCategoryAdapter();
        mCategoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            List<CategoryItem> list = adapter.getData();
            for (int i = 0; i < list.size(); i++) {
                CategoryItem item = list.get(i);
                if (item.getCategoryID().equalsIgnoreCase(selectCategory)) {
                    item.setSelected(false);
                    mCategoryAdapter.notifyItemChanged(i);
                }
                if (i == position) {
                    item.setSelected(true);
                    mCategoryAdapter.notifyItemChanged(i);
                }
            }
            selectCategory = list.get(position).getCategoryID();
            mPresenter.queryGoodsList(1, true);

        });
        mCategoryEmptyView = EmptyView.newBuilder(this).setTips("暂无分类").create();
        mCategoryAdapter.setEmptyView(mCategoryEmptyView);
        mCategoryRecyclerView.setAdapter(mCategoryAdapter);
        mSelectView.setText("已选：" + mProductList.size());
        mPresenter.queryCategory();
    }

    @Override
    public void showCategoryList(CategoryResp resp) {
        selectCategory = resp.getList2().get(0).getCategoryID();
        resp.getList2().get(0).setSelected(true);
        mCategoryAdapter.setNewData(resp.getList2());
        mPresenter.queryGoodsList(1, true);
    }

    @Override
    public void showList(List<GoodsBean> list, boolean append) {
        for (GoodsBean goodsBean : list) {
            for (OrientationDetailBean bean : mProductList) {
                if (goodsBean.getProductID().equalsIgnoreCase(bean.getProductID())) {
                    goodsBean.setCheck(true);
                    break;
                }
            }
        }
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mProductAdapter.addData(list);
        } else {
            mProductAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list.size() != 0);
        mProductAdapter.setEmptyView(mProductEmptyView);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @OnClick({R.id.img_close, R.id.txt_finish_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_finish_select:
                EventBus.getDefault().post(mProductList);
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public String getCategorySubId() {
        return selectCategory;
    }

    @Override
    public String getName() {
        return mSearchView.getSearchContent();
    }

    private OrientationDetailBean goodsBeanToOrientationDetailBean(GoodsBean goodsBean) {
        OrientationDetailBean bean = new OrientationDetailBean();
        bean.setImgUrl(goodsBean.getImgUrl());
        bean.setProductID(goodsBean.getProductID());
        bean.setProductName(goodsBean.getProductName());
        bean.setSupplierName(goodsBean.getSupplierName());
        ArrayList<OrientationProductSpecBean> list = new ArrayList<>();
        for (SpecsBean spec : goodsBean.getSpecs()) {
            OrientationProductSpecBean specBean = new OrientationProductSpecBean();
            specBean.setSaleUnitName(spec.getSaleUnitName());
            specBean.setProductPrice(BigDecimal.valueOf(Double.parseDouble(spec.getProductPrice())));
            specBean.setSpecContent(spec.getSpecContent());
            specBean.setSaleUnitID(spec.getSaleUnitID());
            list.add(specBean);
        }
        bean.setSpecs(list);
        return bean;
    }

    @Subscribe
    public void onEvent(GoodsSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
