package com.hll_sc_app.app.goods.relevance.goods.select;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.GoodsRelevanceRefreshEvent;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.order.detail.TransferDetailBean;
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

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 第三方商品关联-选择关联商品
 *
 * @author zhuyingsong
 * @date 2019/7/5
 */
@Route(path = RouterConfig.GOODS_RELEVANCE_LIST_SELECT, extras = Constant.LOGIN_EXTRA)
public class GoodsRelevanceSelectActivity extends BaseLoadActivity implements GoodsRelevanceSelectContract.IGoodsStickView {
    public static final int REQ_CODE = 0x788;
    public static final String STRING_CATEGORY = "推荐";
    @BindView(R.id.ags_search_view)
    SearchView mSearchView;
    @BindView(R.id.ags_left_list)
    RecyclerView mRecyclerViewLevel1;
    @BindView(R.id.ags_right_list)
    RecyclerView mRecyclerViewProduct;
    @BindView(R.id.ags_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "parcelable")
    TransferDetailBean mBean;
    private GoodsRelevanceSelectPresenter mPresenter;
    private CategoryAdapter mCategoryAdapter;
    private EmptyView mEmptyView;
    private GoodsRelevanceSelectListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_select);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsRelevanceSelectPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(GoodsRelevanceSelectActivity.this,
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
        mAdapter = new GoodsRelevanceSelectListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SkuGoodsBean bean = (SkuGoodsBean) adapter.getItem(position);
            if (bean != null) {
                setUnSelect();
                bean.setSelected(!bean.isSelected());
                mAdapter.notifyDataSetChanged();
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

    /**
     * 清空当前界面选中的商品
     */
    private void setUnSelect() {
        if (mAdapter != null) {
            List<SkuGoodsBean> beans = mAdapter.getData();
            if (CommonUtils.isEmpty(beans)) {
                return;
            }
            for (SkuGoodsBean bean : beans) {
                bean.setSelected(false);
            }
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
    public TransferDetailBean getGoodsBean() {
        return mBean;
    }

    @Override
    public void showCategoryList(CategoryResp resp) {
        if (resp != null) {
            List<CategoryItem> list2 = resp.getList2();
            if (!CommonUtils.isEmpty(list2)) {
                // 添加推荐分类
                CategoryItem categoryItem = new CategoryItem();
                categoryItem.setCategoryName(STRING_CATEGORY);
                list2.add(0, categoryItem);
            }
            mCategoryAdapter.setNewData(list2);
            // 默认去选中第一个
            if (!CommonUtils.isEmpty(mCategoryAdapter.getData())) {
                CategoryItem bean = mCategoryAdapter.getItem(0);
                if (bean != null) {
                    bean.setSelected(true);
                    mCategoryAdapter.notifyItemChanged(0);
                    mPresenter.queryGoodsList(true);
                }
            }
        } else {
            mCategoryAdapter.setNewData(null);
        }
    }

    @Override
    public void showList(List<SkuGoodsBean> list, boolean append, int total) {
        if (mBean != null
                && !TextUtils.isEmpty(mBean.getProductID())
                && !TextUtils.equals("0", mBean.getProductID())
                && !CommonUtils.isEmpty(list)) {
            for (SkuGoodsBean skuGoodsBean : list) {
                if (TextUtils.equals(skuGoodsBean.getProductID(), mBean.getProductID())) {
                    skuGoodsBean.setSelected(true);
                    break;
                }
            }
        }
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        if (mSearchView.isSearchStatus()) {
            mEmptyView.setTips("搜索不到相关商品");
        } else {
            mEmptyView.setTips("该分类暂无商品数据");
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(total != mAdapter.getItemCount());
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
    public void addSuccess() {
        showToast("新增修改关联商品成功");
        EventBus.getDefault().post(new GoodsRelevanceRefreshEvent());
        setResult(RESULT_OK);
        finish();
    }

    @OnClick(R.id.ags_ok)
    void toAdd() {
        SkuGoodsBean bean = getSelectProductBean();
        if (bean == null) {
            showToast("您还没有选择要关联的商品");
            return;
        }
        mPresenter.addGoodsRelevance(bean);
    }

    private SkuGoodsBean getSelectProductBean() {
        SkuGoodsBean bean = null;
        if (mAdapter != null) {
            List<SkuGoodsBean> list = mAdapter.getData();
            if (!CommonUtils.isEmpty(list)) {
                for (SkuGoodsBean productBean : list) {
                    if (productBean.isSelected()) {
                        bean = productBean;
                        break;
                    }
                }
            }
        }
        return bean;
    }

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

    class GoodsRelevanceSelectListAdapter extends BaseQuickAdapter<SkuGoodsBean, BaseViewHolder> {
        GoodsRelevanceSelectListAdapter() {
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
