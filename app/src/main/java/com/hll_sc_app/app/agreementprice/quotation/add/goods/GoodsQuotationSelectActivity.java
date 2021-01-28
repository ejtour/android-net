package com.hll_sc_app.app.agreementprice.quotation.add.goods;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;

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
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.glide.GlideImageView;
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
 * 报价单-新增商品
 *
 * @author zhuyingsong
 * @date 2019/7/10
 */
@Route(path = RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD_GOODS, extras = Constant.LOGIN_EXTRA)
public class GoodsQuotationSelectActivity extends BaseLoadActivity implements GoodsQuotationSelectContract.IGoodsStickView {
    @BindView(R.id.ags_search_view)
    SearchView mSearchView;
    @BindView(R.id.ags_left_list)
    RecyclerView mRecyclerViewLevel1;
    @BindView(R.id.ags_right_list)
    RecyclerView mRecyclerViewProduct;
    @BindView(R.id.ags_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "object0")
    String mPurchaserId;
    @Autowired(name = "object1")
    String mExtGroupId;
    @Autowired(name = "parcelable")
    ArrayList<SkuGoodsBean> mSkuList;
    @BindView(R.id.ags_selected_num)
    TextView mTxtCheckNum;
    private GoodsQuotationSelectPresenter mPresenter;
    private CategoryAdapter mCategoryAdapter;
    private EmptyView mEmptyView;
    private GoodsSelectListAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_select);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        if (mSkuList == null) {
            mSkuList = new ArrayList<>();
        }
        mPresenter = GoodsQuotationSelectPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(GoodsQuotationSelectActivity.this,
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

    private void add(SkuGoodsBean bean) {
        bean.setSelected(true);
        mSkuList.add(bean);
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
    public String getCargoOwnId() {
        return mPurchaserId;
    }

    @Override
    public String getName() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getExtGroupID() {
        return mExtGroupId;
    }

    @OnClick(R.id.ags_ok)
    void toAdd() {
        EventBus.getDefault().post(mSkuList);
        finish();
    }

    /**
     * 分类适配器
     */
    static class CategoryAdapter extends BaseQuickAdapter<CategoryItem, BaseViewHolder> {

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
    static class GoodsSelectListAdapter extends BaseQuickAdapter<SkuGoodsBean, BaseViewHolder> {

        GoodsSelectListAdapter() {
            super(R.layout.item_goods_relevance_select_list);
        }

        @Override
        protected void convert(BaseViewHolder helper, SkuGoodsBean item) {
            List<String> list = new ArrayList<>();
            if (!TextUtils.isEmpty(item.getSpecContent())) list.add(item.getSpecContent());
            if (!TextUtils.isEmpty(item.getSaleUnitName())) list.add(item.getSaleUnitName());
            helper.setText(R.id.txt_productName, item.getProductName())
                    .setText(R.id.txt_specContent, list.size() > 0 ? TextUtils.join("  |  ", list) : "")
                    .setText(R.id.txt_productPrice, "¥" + (UserConfig.isOnlyReceive() ? " - -" : CommonUtils.formatNumber(item.getProductPrice())));
            helper.getView(R.id.img_check).setSelected(item.isSelected());
            ((GlideImageView) helper.getView(R.id.img_imgUrl)).setImageURL(item.getImgUrl());
        }
    }
}
