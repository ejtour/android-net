package com.hll_sc_app.app.goods.template;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.detail.GoodsDetailActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.LabelBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Tuple;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 从商品库导入
 *
 * @author zhuyingsong
 * @date 2019/6/27
 */
@Route(path = RouterConfig.GOODS_TEMPLATE_LIST, extras = Constant.LOGIN_EXTRA)
public class GoodsTemplateListActivity extends BaseLoadActivity implements GoodsTemplateListContract.IGoodsTemplateListView {
    @BindView(R.id.rl_toolbar)
    TitleBar mTitleBar;
    @BindView(R.id.ll_filter)
    LinearLayout mLlFilter;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.img_allCheck)
    ImageView mImgAllCheck;
    @BindView(R.id.text_commit)
    TextView mTextCommit;
    @BindView(R.id.fl_bottom)
    RelativeLayout mFlBottom;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.txt_allCheck)
    TextView mTxtAllCheck;
    @BindView(R.id.txt_category)
    TextView mTxtCategory;
    @BindView(R.id.img_category)
    ImageView mImgCategory;
    @BindView(R.id.rl_category)
    RelativeLayout mRlCategory;
    @BindView(R.id.txt_label)
    TextView mTxtLabel;
    @BindView(R.id.img_label)
    ImageView mImgLabel;
    @BindView(R.id.rl_label)
    RelativeLayout mRlLabel;
    @BindView(R.id.txt_filter)
    TextView mTxtFilter;
    @BindView(R.id.img_filter)
    ImageView mImgFilter;
    @BindView(R.id.rl_filter)
    RelativeLayout mRlFilter;
    @Autowired(name = "object0")
    boolean mOnlyView;
    private GoodsTemplateAdapter mAdapter;
    private GoodsTemplateListPresenter mPresenter;
    private EmptyView mEmptyView;

    /**
     * 选中的商品数据
     */
    private Set<GoodsBean> mSelectList;
    private CategoryFilterWindow mCategoryWindow;
    private LabelFilterWindow mLabelFilterWindow;
    private TemplateFilterWindow mTemplateFilterWindow;

    /**
     * @param onlyView 是否仅查看
     */
    public static void start(boolean onlyView) {
        RouterUtil.goToActivity(RouterConfig.GOODS_TEMPLATE_LIST, onlyView);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_template_list);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        mPresenter = GoodsTemplateListPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        mSelectList = new HashSet<>();
    }

    private void initView() {
        if (mOnlyView) {
            mTitleBar.setHeaderTitle("查看商品库");
            mFlBottom.setVisibility(View.GONE);
        }
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreGoodsTemplateList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryGoodsTemplateList(false);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new GoodsTemplateAdapter(mOnlyView);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            GoodsBean goodsBean = mAdapter.getItem(position);
            if (goodsBean == null) return;
            if (mOnlyView) {
                GoodsDetailActivity.start(goodsBean.getProductID(), false, false);
                return;
            }
            if (goodsBean.isCheck()) {
                // 之前状态为选中，再次点击状态改为未选中
                remove(goodsBean);
            } else {
                add(goodsBean);
            }
            adapter.notifyItemChanged(position);
            showBottomCount();
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有商品模板数据").create();
        mRecyclerView.setAdapter(mAdapter);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(GoodsTemplateListActivity.this,
                        searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.queryGoodsTemplateList(true);
            }
        });
    }

    private void remove(GoodsBean goodsBean) {
        goodsBean.setCheck(false);
        mSelectList.remove(goodsBean);
    }

    private void add(GoodsBean goodsBean) {
        goodsBean.setCheck(true);
        mSelectList.add(goodsBean);
    }

    private void showBottomCount() {
        int i = mSelectList.size();
        mTextCommit.setEnabled(i > 0);
        mTextCommit.setText(String.format(Locale.getDefault(), "确定选择（%d）", i));
    }

    @Override
    public void showGoodsTemplateList(List<GoodsBean> list, boolean append, int total) {
        if (!CommonUtils.isEmpty(list)) {
            for (GoodsBean goodsBean : list) {
                goodsBean.setProductTemplateID(goodsBean.getProductID());
                List<SpecsBean> specsBeanList = goodsBean.getSpecs();
                if (!CommonUtils.isEmpty(specsBeanList)) {
                    for (SpecsBean bean : specsBeanList) {
                        bean.setSpecTemplateID(bean.getSpecID());
                    }
                }
                if (mSelectList.contains(goodsBean)) {
                    goodsBean.setCheck(true);
                }
            }
        }
        if (append) {
            if (!CommonUtils.isEmpty(list))
                mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        if (isSearchStatus() || !TextUtils.isEmpty(getBrandName()) || !TextUtils.isEmpty(getProductPlace())) {
            mEmptyView.setTips("搜索不到相关商品");
        } else {
            mEmptyView.setTips("您还没有商品模板数据");
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != total);
    }

    @Override
    public String getSearchContent() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getBrandName() {
        return mTemplateFilterWindow != null ? mTemplateFilterWindow.getBrandName() : null;
    }

    @Override
    public String getProductPlace() {
        return mTemplateFilterWindow != null ? mTemplateFilterWindow.getProductPlace() : null;
    }

    @Override
    public String getLabelIds() {
        String ids = null;
        if (mLabelFilterWindow != null) {
            Tuple<List<String>, List<String>> tuple = mLabelFilterWindow.getSelectList();
            ids = TextUtils.join(",", tuple.getKey2());
            mTxtLabel.setText(TextUtils.join(",", tuple.getKey1()));
        }
        return ids;
    }

    @Override
    public String getCategoryThreeIds() {
        String ids = null;
        if (mCategoryWindow != null) {
            Tuple<List<String>, List<String>> tuple = mCategoryWindow.getCategoryThreeIds();
            ids = TextUtils.join(",", tuple.getKey2());
            mTxtCategory.setText(TextUtils.join(",", tuple.getKey1()));
        }
        return ids;
    }

    @Override
    public void showCategoryFilterWindow(CategoryResp resp) {
        mImgCategory.setRotation(-180F);
        mTxtCategory.setSelected(true);
        mImgCategory.setSelected(true);
        if (mCategoryWindow == null) {
            mCategoryWindow = new CategoryFilterWindow(this, resp);
            mCategoryWindow.setListener(beans -> mPresenter.queryGoodsTemplateList(true));
            mCategoryWindow.setOnDismissListener(() -> {
                mTxtCategory.setSelected(false);
                mImgCategory.setSelected(false);
                mImgCategory.setRotation(0F);
            });
        }
        mCategoryWindow.showAsDropDownFix(mLlFilter);
    }

    @Override
    public void showLabelFilterWindow(List<LabelBean> list) {
        mImgLabel.setRotation(-180F);
        mTxtLabel.setSelected(true);
        mImgLabel.setSelected(true);
        if (mLabelFilterWindow == null) {
            mLabelFilterWindow = new LabelFilterWindow(this);
            mLabelFilterWindow.setList(list);
            mLabelFilterWindow.setListener(() -> mPresenter.queryGoodsTemplateList(true));
            mLabelFilterWindow.setOnDismissListener(() -> {
                mImgLabel.setSelected(false);
                mTxtLabel.setSelected(false);
                mImgLabel.setRotation(0F);
            });
        }
        mLabelFilterWindow.showAsDropDownFix(mLlFilter);
    }

    /**
     * 是否处于搜索状态下
     *
     * @return true-搜索状态下
     */
    private boolean isSearchStatus() {
        return mSearchView.isSearchStatus();
    }

    @OnClick({R.id.text_commit, R.id.img_allCheck, R.id.txt_allCheck, R.id.rl_category, R.id.rl_label
        , R.id.rl_filter})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.text_commit:
                // 确定选择
                RouterUtil.goToActivity(RouterConfig.GOODS_TEMPLATE_EDIT, this, new ArrayList<>(mSelectList));
                break;
            case R.id.img_allCheck:
            case R.id.txt_allCheck:
                mImgAllCheck.setSelected(!mImgAllCheck.isSelected());
                checkAll(mImgAllCheck.isSelected());
                break;
            case R.id.rl_category:
                mPresenter.queryCategory();
                break;
            case R.id.rl_label:
                mPresenter.queryLabelList();
                break;
            case R.id.rl_filter:
                showFilterWindow();
                break;
            default:
                break;
        }
    }

    private void checkAll(boolean check) {
        if (mAdapter != null && !CommonUtils.isEmpty(mAdapter.getData())) {
            for (GoodsBean bean : mAdapter.getData()) {
                if (check) {
                    add(bean);
                } else {
                    remove(bean);
                }
            }
            mAdapter.notifyDataSetChanged();
        }
        showBottomCount();
    }

    private void showFilterWindow() {
        mTxtFilter.setSelected(true);
        mImgFilter.setSelected(true);
        mImgFilter.setRotation(-180F);
        if (mTemplateFilterWindow == null) {
            mTemplateFilterWindow = new TemplateFilterWindow(this);
            mTemplateFilterWindow.setConfirmListener(label -> {
                mTxtFilter.setText(label);
                mPresenter.queryGoodsTemplateList(true);
            });
            mTemplateFilterWindow.setOnDismissListener(() -> {
                mTxtFilter.setSelected(false);
                mImgFilter.setSelected(false);
                mImgFilter.setRotation(0F);
            });
        }
        mTemplateFilterWindow.showAtLocation(getWindow().getDecorView(), Gravity.END, 0, 0);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
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

    private static class GoodsTemplateAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

        private boolean onlyView;

        GoodsTemplateAdapter(boolean onlyView) {
            super(R.layout.item_goods_template_list);
            this.onlyView = onlyView;
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            helper.setGone(R.id.img_check, !onlyView);
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean bean) {
            helper.getView(R.id.img_check).setSelected(bean.isCheck());
            ((GlideImageView) helper.setText(R.id.txt_specsContent, !CommonUtils.isEmpty(bean.getSpecs()) ?
                    "规格：" + bean.getSpecs().get(0).getSpecContent() : "")
                    .setText(R.id.txt_productName, bean.getProductName())
                    .getView(R.id.img_imgUrl)).setImageURL(bean.getImgUrl());
        }
    }
}
