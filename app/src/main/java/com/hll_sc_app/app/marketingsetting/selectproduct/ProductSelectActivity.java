package com.hll_sc_app.app.marketingsetting.selectproduct;

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
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择商品
 * todo 这块的商品选择可以抽出成公共的--zc : 源代码是copy的 协议价
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_SELECT_PRODUCT, extras = Constant.LOGIN_EXTRA)
public class ProductSelectActivity extends BaseLoadActivity implements IProductSelectContract.IGoodsStickView {
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.recyclerView_level1)
    RecyclerView mRecyclerViewLevel1;
    @BindView(R.id.recyclerView_product)
    RecyclerView mRecyclerViewProduct;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "parcelable")
    ArrayList<SkuGoodsBean> mSkuList;
    @Autowired(name = "origin")
    String activityName;
    @Autowired(name = "pageTitle")
    String pageTitle;
    @BindView(R.id.txt_checkNum)
    TextView mTxtCheckNum;
    @BindView(R.id.txt_page_title)
    TextView mTitle;
    private ProductSelectPresenter mPresenter;
    private CategoryAdapter mCategoryAdapter;
    private EmptyView mEmptyView;
    private GoodsSelectListAdapter mAdapter;

    public static void start(String originPath, String pageTitle, ArrayList<SkuGoodsBean> skuGoodsBeans) {
        ARouter.getInstance().build(RouterConfig.ACTIVITY_MARKETING_SELECT_PRODUCT)
                .withString("origin", originPath)
                .withString("pageTitle", pageTitle)
                .withParcelableArrayList("parcelable", skuGoodsBeans)
                .setProvider(new LoginInterceptor()).navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_quotation_select);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
//        mSelectMap = new HashMap<>();
//        if (!CommonUtils.isEmpty(mSkuList)) {
//            for (SkuGoodsBean bean : mSkuList) {
//                add(bean);
//            }
//        }
        mPresenter = ProductSelectPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitle.setText(pageTitle);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(ProductSelectActivity.this,
                        searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryGoodsList(true);
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("该分类暂无商品数据").create();
        mRecyclerViewLevel1.setLayoutManager(new LinearLayoutManager(this));
        mCategoryAdapter = new CategoryAdapter();
        mRecyclerViewLevel1.setAdapter(mCategoryAdapter);
        mCategoryAdapter.setOnItemClickListener((adapter, view, position) -> {
            CategoryItem bean = (CategoryItem) adapter.getItem(position);
            if (bean == null || bean.isSelected()) {
                return;
            }
            List<CategoryItem> beanList = mCategoryAdapter.getData();
            if (CommonUtils.isEmpty(beanList)) {
                return;
            }
            for (CategoryItem customCategoryBean : beanList) {
                customCategoryBean.setSelected(false);
            }
            bean.setSelected(true);
            adapter.notifyDataSetChanged();
            mPresenter.queryGoodsList(true);
        });
        mRecyclerViewProduct.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewProduct.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this,
                R.color.base_color_divider), UIUtils.dip2px(1)));
        mAdapter = new GoodsSelectListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SkuGoodsBean bean = (SkuGoodsBean) adapter.getItem(position);
            if (bean != null) {
                if (bean.isSelected()) {
                    remove(bean);
                    adapter.notifyItemChanged(position);
                    showBottomCount();
                } else {
                    add(bean);
                    adapter.notifyItemChanged(position);
                    showBottomCount();
                }
            }
        });
        mRecyclerViewProduct.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreGoodsList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryGoodsList(false);
            }
        });
    }

    private void remove(SkuGoodsBean goodsBean) {
        goodsBean.setSelected(false);
        mSkuList.remove(goodsBean);
    }

    /**
     * 显示底部已选数量
     */
    private void showBottomCount() {
        mTxtCheckNum.setText(String.format(Locale.getDefault(), "已选：%d", mSkuList.size()));
    }

    private void add(SkuGoodsBean bean) {
        if (!mSkuList.contains(bean)) {
            bean.setSelected(true);
            mSkuList.add(bean);
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

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void showCategoryList(CategoryResp resp) {
        mCategoryAdapter.setNewData(resp.getList2());
        if (!CommonUtils.isEmpty(mCategoryAdapter.getData())) {
            // 默认去选中第一个
            CategoryItem bean = mCategoryAdapter.getItem(0);
            if (bean != null) {
                bean.setSelected(true);
                mCategoryAdapter.notifyItemChanged(0);
                mPresenter.queryGoodsList(true);
            }
        }
    }

    @Override
    public void showList(List<SkuGoodsBean> list, boolean append) {
        if (!CommonUtils.isEmpty(list)) {
            for (SkuGoodsBean bean : list) {
                if (contains(bean)) {
                    bean.setSelected(true);
                }
            }
        }
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            mAdapter.setNewData(list);
        }
        if (mSearchView.isSearchStatus()) {
            mEmptyView.setTips("搜索不到相关商品");
        } else {
            mEmptyView.setTips("该分类暂无商品数据");
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
        showBottomCount();
    }

    /**
     * 是否添加到集合中
     *
     * @param bean SkuGoodsBean
     * @return true-添加过
     */
    private boolean contains(SkuGoodsBean bean) {
        return mSkuList.contains(bean);
    }

    @Override
    public String getCategorySubId() {
        String categorySubId = null;
        List<CategoryItem> beanList = mCategoryAdapter.getData();
        if (!CommonUtils.isEmpty(beanList)) {
            for (CategoryItem bean : beanList) {
                if (bean.isSelected()) {
                    categorySubId = bean.getCategoryID();
                    break;
                }
            }
        }
        return categorySubId;
    }

    @Override
    public String getName() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getActivityName() {
        return activityName;
    }

    @OnClick({R.id.img_close, R.id.txt_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_save:
                toAdd();
                break;
            default:
                break;
        }
    }

    private void toAdd() {
        EventBus.getDefault().post(mSkuList);
        finish();
    }

    /**
     * 分类适配器
     */
    class CategoryAdapter extends BaseQuickAdapter<CategoryItem, BaseViewHolder> {

        CategoryAdapter() {
            super(R.layout.item_goods_custom_category_top);
        }

        @Override
        protected void convert(BaseViewHolder helper, CategoryItem item) {
            TextView txtCategoryName = helper.getView(R.id.txt_categoryName);
            txtCategoryName.setText(item.getCategoryName());
            txtCategoryName.setSelected(item.isSelected());
        }
    }

    /**
     * 商品列表适配器
     */
    class GoodsSelectListAdapter extends BaseQuickAdapter<SkuGoodsBean, BaseViewHolder> {
        GoodsSelectListAdapter() {
            super(R.layout.item_goods_relevance_select_list);
        }

        @Override
        protected void convert(BaseViewHolder helper, SkuGoodsBean item) {
            helper.setText(R.id.txt_productName, item.getProductName())
                    .setText(R.id.txt_specContent, item.getSpecContent())
                    .setText(R.id.txt_productPrice, "¥" + CommonUtils.formatNumber(item.getProductPrice()));
            helper.getView(R.id.img_check).setSelected(item.isSelected());
            ((GlideImageView) helper.getView(R.id.img_imgUrl)).setImageURL(item.getImgUrl());
        }
    }
}
