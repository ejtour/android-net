package com.hll_sc_app.app.orientationsale.product;

import android.content.Intent;
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
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.bean.orientation.OrientationDetailBean;
import com.hll_sc_app.bean.orientation.OrientationProductSpecBean;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = RouterConfig.ORIENTATION_PRODUCT, extras = Constant.LOGIN_EXTRA)
public class ProductActivity extends BaseLoadActivity implements IProductContract.IProductView {

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
    @Autowired(name = "parcelable")
    ArrayList<OrientationDetailBean> mProductList;

    private IProductContract.IProductPresenter mPresenter;
    private ProductAdapter mProductAdapter;
    private ProductCategoryAdapter mCategoryAdapter;
    private EmptyView mProductEmptyView;
    private EmptyView mCategoryEmptyView;
    private String selectCategory;



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
    }

    private void initView() {
        if (mProductList == null) {
            mProductList = new ArrayList<>();
        }

        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(ProductActivity.this,
                        searchContent, GoodsSearch.class.getSimpleName());
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
        mProductAdapter = new ProductAdapter();
        mProductAdapter.setOnSpecClickListener(goodsBean -> {
            int index = -1;
            for (int i = 0; i < mProductList.size(); i++) {
                if (TextUtils.equals(goodsBean.getProductID(), mProductList.get(i).getProductID())) {
                    index = i;
                }
            }
            if (goodsBean.isCheck()) {//商品下有至少一个规格被选中
                if (index > -1) {
                    mProductList.remove(index);
                }
                mProductList.add(goodsBeanToOrientationDetailBean(goodsBean));
            } else {//商品任何规格都没有被选中
                if (index > -1) {
                    mProductList.remove(index);
                }
            }
            mSelectView.setText("已选：" + mProductList.size() + "个商品");
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
        mSelectView.setText("已选：" + mProductList.size() + "个商品");
        mPresenter.queryCategory();
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
            specBean.setAppointSellType(spec.getAppointSellType());
            specBean.setSpecID(spec.getSpecID());
            list.add(specBean);
        }
        bean.setSpecs(list);
        return bean;
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
            for (SpecsBean specsBean : goodsBean.getSpecs()) {
                specsBean.setAppointSellType(0);
            }
            for (int i = 0; i < mProductList.size(); i++) {
                if (TextUtils.equals(goodsBean.getProductID(), mProductList.get(i).getProductID())) {
                    List<OrientationProductSpecBean> orientationProductSpecBeans = mProductList.get(i).getSpecs();
                    List<SpecsBean> specsBeans = goodsBean.getSpecs();
                    for (OrientationProductSpecBean orientationProductSpecBean : orientationProductSpecBeans) {
                        for (int j = 0; j < specsBeans.size(); j++) {
                            if (TextUtils.equals(specsBeans.get(j).getSpecID(), orientationProductSpecBean.getSpecID())) {
                                specsBeans.get(j).setAppointSellType(orientationProductSpecBean.getAppointSellType());
                            }
                        }
                    }
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
    public String getCategorySubId() {
        return selectCategory;
    }

    @Override
    public String getName() {
        return mSearchView.getSearchContent();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
    }
}
