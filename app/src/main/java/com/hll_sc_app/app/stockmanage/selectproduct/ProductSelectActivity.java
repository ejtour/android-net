package com.hll_sc_app.app.stockmanage.selectproduct;

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
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.GoodsBean;
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
@Route(path = RouterConfig.ACTIVITY_STOCK_CHECK_SELECT_PRODUCT, extras = Constant.LOGIN_EXTRA)
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
    ArrayList<GoodsBean> mGoodList;
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

    private CustomCategoryBean mCurrentCategory;

    public static void start(String pageTitle, ArrayList<GoodsBean> goodsBeans) {
        if (goodsBeans == null) {
            goodsBeans = new ArrayList<>();
        }
        ARouter.getInstance().build(RouterConfig.ACTIVITY_STOCK_CHECK_SELECT_PRODUCT)
                .withString("pageTitle", pageTitle)
                .withParcelableArrayList("parcelable", goodsBeans)
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
            CustomCategoryBean bean = (CustomCategoryBean) adapter.getItem(position);
            if (bean == null || bean.isChecked()) {
                return;
            }
            List<CustomCategoryBean> beanList = mCategoryAdapter.getData();
            if (CommonUtils.isEmpty(beanList)) {
                return;
            }
            for (CustomCategoryBean customCategoryBean : beanList) {
                customCategoryBean.setChecked(false);
            }
            mCurrentCategory = bean;
            bean.setChecked(true);
            adapter.notifyDataSetChanged();
            mPresenter.queryGoodsList(true);
        });
        mRecyclerViewProduct.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerViewProduct.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this,
                R.color.base_color_divider), UIUtils.dip2px(1)));
        mAdapter = new GoodsSelectListAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            GoodsBean bean = (GoodsBean) adapter.getItem(position);
            if (bean != null) {
                if (bean.isCheck()) {
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

    private void remove(GoodsBean goodsBean) {
        goodsBean.setCheck(false);
        mGoodList.remove(goodsBean);
    }

    /**
     * 显示底部已选数量
     */
    private void showBottomCount() {
        mTxtCheckNum.setText(String.format(Locale.getDefault(), "已选：%d", mGoodList.size()));
    }

    private void add(GoodsBean bean) {
        if (!mGoodList.contains(bean)) {
            bean.setCheck(true);
            mGoodList.add(bean);
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
    public void showCategoryList(CustomCategoryResp resp) {
        mCategoryAdapter.setNewData(resp.getList2());
        if (!CommonUtils.isEmpty(mCategoryAdapter.getData())) {
            // 默认去选中第一个
            CustomCategoryBean bean = mCategoryAdapter.getItem(0);
            if (bean != null) {
                mCurrentCategory = bean;
                bean.setChecked(true);
                mCategoryAdapter.notifyItemChanged(0);
                mPresenter.queryGoodsList(true);
            }
        }
    }

    @Override
    public void showList(List<GoodsBean> list, boolean append) {
        if (!CommonUtils.isEmpty(list)) {
            for (GoodsBean bean : list) {
                if (contains(bean)) {
                    bean.setCheck(true);
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
     * @param bean GoodsBean
     * @return true-添加过
     */
    private boolean contains(GoodsBean bean) {
        return mGoodList.contains(bean);
    }

    @Override
    public String getName() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getCategorySubID() {
        if (mCurrentCategory == null) {
            return "";
        } else {
            return mCurrentCategory.getId();
        }
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
        EventBus.getDefault().post(mGoodList);
        finish();
    }

    /**
     * 分类适配器
     */
    class CategoryAdapter extends BaseQuickAdapter<CustomCategoryBean, BaseViewHolder> {

        CategoryAdapter() {
            super(R.layout.item_goods_custom_category_top);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomCategoryBean item) {
            TextView txtCategoryName = helper.getView(R.id.txt_categoryName);
            txtCategoryName.setText(item.getCategoryName());
            txtCategoryName.setSelected(item.isChecked());
        }
    }

    /**
     * 商品列表适配器
     */
    class GoodsSelectListAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
        GoodsSelectListAdapter() {
            super(R.layout.list_item_goods_select);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean item) {
            helper.setText(R.id.txt_productName, item.getProductName())
                    .setText(R.id.txt_code, "编码：" + item.getProductCode());
            helper.getView(R.id.img_check).setSelected(item.isCheck());
            ((GlideImageView) helper.getView(R.id.img_imgUrl)).setImageURL(item.getImgUrl());
        }
    }
}
