package com.hll_sc_app.app.stockmanage.selectproduct;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
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
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.SingleListEvent;
import com.hll_sc_app.bean.goods.CustomCategoryBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
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
    @BindView(R.id.ags_search_view)
    SearchView mSearchView;
    @BindView(R.id.ags_left_list)
    RecyclerView mRecyclerViewLevel1;
    @BindView(R.id.ags_right_list)
    RecyclerView mRecyclerViewProduct;
    @BindView(R.id.ags_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "action")
    String mActionType;
    @Autowired(name = "parcelable")
    ArrayList<GoodsBean> mGoodList;
    @Autowired(name = "pageTitle")
    String pageTitle;
    @Autowired(name = "containThree")
    boolean mContainThree;
    @BindView(R.id.ags_selected_num)
    TextView mTxtCheckNum;
    @BindView(R.id.ags_title_bar)
    TitleBar mTitleBar;
    private ProductSelectPresenter mPresenter;
    private CategoryAdapter mCategoryAdapter;
    private ThreeCategoryAdapter mThreeCategoryAdapter;
    private EmptyView mEmptyView;
    private GoodsSelectListAdapter mAdapter;
    private CustomCategoryBean mCurrentCategory;

    public static void start(String pageTitle, String actionType, ArrayList<GoodsBean> goodsBeans) {
        start(pageTitle, actionType, false, goodsBeans);
    }

    public static void start(String pageTitle, String actionType, boolean containThree, ArrayList<GoodsBean> goodsBeans) {
        if (goodsBeans == null) {
            goodsBeans = new ArrayList<>();
        }
        ARouter.getInstance().build(RouterConfig.ACTIVITY_STOCK_CHECK_SELECT_PRODUCT)
                .withString("pageTitle", pageTitle)
                .withString("action", actionType)
                .withBoolean("containThree", containThree)
                .withParcelableArrayList("parcelable", goodsBeans)
                .setProvider(new LoginInterceptor()).navigation();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_select);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
        mPresenter = ProductSelectPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setHeaderTitle(pageTitle);
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
        initCategory();
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

    private void initCategory() {
        if (mContainThree) {
            mThreeCategoryAdapter = new ThreeCategoryAdapter();
            mRecyclerViewLevel1.setAdapter(mThreeCategoryAdapter);
            mThreeCategoryAdapter.setOnItemClickListener((adapter, view, position) -> {
                mCurrentCategory = mThreeCategoryAdapter.select(position);
                mPresenter.queryGoodsList(true);
            });
        } else {
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
        }
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
        if (mContainThree) {
            resp.processList();
            mThreeCategoryAdapter.setNewData(resp.getList2());
            mCurrentCategory = mThreeCategoryAdapter.getCurBean();
            if (mCurrentCategory != null)
                mPresenter.queryGoodsList(true);
        } else {
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
    public String getActionType() {
        return mActionType;
    }

    @Override
    public String getCategorySubID() {
        if (mCurrentCategory == null) {
            return "";
        } else {
            return TextUtils.equals("0", mCurrentCategory.getShopCategoryPID()) ?
                    mCurrentCategory.getId() :
                    mCurrentCategory.getShopCategoryPID();
        }
    }

    @Override
    public String getCategoryThreeID() {
        if (mCurrentCategory == null || TextUtils.equals("0", mCurrentCategory.getShopCategoryPID())) {
            return "";
        } else {
            return mCurrentCategory.getId();
        }
    }

    @OnClick(R.id.ags_ok)
    void toAdd() {
        EventBus.getDefault().post(new SingleListEvent<>(mGoodList, GoodsBean.class));
        finish();
    }

    /**
     * 分类适配器
     */
    public static class CategoryAdapter extends BaseQuickAdapter<CustomCategoryBean, BaseViewHolder> {

        public CategoryAdapter() {
            super(R.layout.item_goods_custom_category_top);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomCategoryBean item) {
            TextView txtCategoryName = helper.getView(R.id.txt_categoryName);
            txtCategoryName.setText(item.getCategoryName());
            txtCategoryName.setSelected(item.isChecked());
        }
    }

    public static class ThreeCategoryAdapter extends BaseQuickAdapter<CustomCategoryBean, BaseViewHolder> {

        private CustomCategoryBean mCurSub;
        private CustomCategoryBean mCurBean;

        public ThreeCategoryAdapter() {
            super(null);
        }

        public CustomCategoryBean getCurBean() {
            return mCurBean;
        }

        CustomCategoryBean select(int pos) {
            CustomCategoryBean item = getItem(pos);
            if (item != null) {
                if (mCurBean == null || !mCurBean.getId().equals(item.getId())) {
                    if (TextUtils.equals("0", item.getShopCategoryPID())) {
                        selectSub(item);
                    } else {
                        selectThree(item);
                    }
                }
            }
            mCurBean = item;
            return item;
        }

        private void selectSub(CustomCategoryBean bean) {
            if (mCurSub != null) {
                mCurSub.setChecked(false);
                mData.removeAll(mCurSub.getSubList());
            }
            bean.setChecked(true);
            if (!CommonUtils.isEmpty(bean.getSubList())) {
                for (CustomCategoryBean categoryBean : bean.getSubList()) {
                    categoryBean.setChecked(false);
                }
            }
            addData(mData.indexOf(bean) + 1, bean.getSubList());
            mCurSub = bean;
            notifyDataSetChanged();
        }

        private void selectThree(CustomCategoryBean bean) {
            if (mCurSub != null) {
                for (CustomCategoryBean categoryBean : mCurSub.getSubList()) {
                    categoryBean.setChecked(TextUtils.equals(categoryBean.getId(), bean.getId()));
                }
                notifyDataSetChanged();
            }
        }


        @SuppressLint("ResourceType")
        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(parent.getContext());
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(45)));
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(13);
            textView.setTextColor(ContextCompat.getColorStateList(parent.getContext(), R.drawable.base_color_state_on_pri_off_666));
            return new BaseViewHolder(textView);
        }

        @Override
        protected void convert(BaseViewHolder helper, CustomCategoryBean item) {
            TextView textView = (TextView) helper.itemView;
            textView.setBackgroundResource(TextUtils.equals("0", item.getShopCategoryPID()) ? R.drawable.bg_goods_select_category : android.R.color.white);
            textView.setText(item.getCategoryName());
            textView.setSelected(item.isChecked());
        }

        @Override
        public void setNewData(@Nullable List<CustomCategoryBean> data) {
            super.setNewData(data);
            select(0);
        }
    }

    /**
     * 商品列表适配器
     */
    static class GoodsSelectListAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
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
