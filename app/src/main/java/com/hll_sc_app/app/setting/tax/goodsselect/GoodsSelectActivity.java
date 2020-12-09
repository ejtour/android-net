package com.hll_sc_app.app.setting.tax.goodsselect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.bean.user.SpecialTaxBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/1
 */

@Route(path = RouterConfig.SETTING_TAX_SELECT_GOODS)
public class GoodsSelectActivity extends BaseLoadActivity implements IGoodsSelectContract.IGoodsSelectView {
    private static final int REQ_CODE = 0x902;
    @BindView(R.id.ags_search_view)
    SearchView mSearchView;
    @BindView(R.id.ags_selected_num)
    TextView mSelectedNum;
    @BindView(R.id.ags_ok)
    TextView mOk;
    @BindView(R.id.ags_left_list)
    RecyclerView mLeftList;
    @BindView(R.id.ags_right_list)
    RecyclerView mRightList;
    @BindView(R.id.ags_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @Autowired(name = "parcelable")
    ArrayList<SpecialTaxBean> mTaxList;
    private GoodsCategoryAdapter mLeftAdapter;
    private GoodsSelectAdapter mRightAdapter;
    private IGoodsSelectContract.IGoodsSelectPresenter mPresenter;
    private String mCategoryID;
    private String mSearchWords;
    private EmptyView mEmptyView;

    /**
     * @param ids 已选择的商品id
     */
    public static void start(Activity activity, ArrayList<SpecialTaxBean> ids) {
        RouterUtil.goToActivity(RouterConfig.SETTING_TAX_SELECT_GOODS, activity, REQ_CODE, ids);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_goods_select);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initView() {
        mLeftAdapter = new GoodsCategoryAdapter();
        mLeftList.setAdapter(mLeftAdapter);
        mLeftAdapter.setOnItemClickListener((adapter, view, position) -> {
            CategoryItem item = mLeftAdapter.getItem(position);
            if (item == null) return;
            handleSelect(item);
        });
        mRightAdapter = new GoodsSelectAdapter();

        List<String> ids = new ArrayList<>();
        for (SpecialTaxBean bean : mTaxList) {
            ids.add(bean.getProductID());
        }

        mSelectedNum.setText(String.format("已选：%s", mTaxList.size()));
        updateBtnStatus();

        mRightAdapter.setCanNotSelectIds(ids);
        mRightAdapter.setOnItemClickListener((adapter, view, position) -> {
            GoodsBean item = mRightAdapter.getItem(position);
            if (item == null) return;
            item.setCheck(!item.isCheck());
            mRightAdapter.notifyItemChanged(position);
            if (item.isCheck()) {
                SpecialTaxBean specialTaxBean = new SpecialTaxBean();
                specialTaxBean.setProductName(item.getProductName());
                specialTaxBean.setImgUrl(item.getImgUrl());
                specialTaxBean.setSaleSpecNum(item.getSpecs().size());
                specialTaxBean.setStandardUnitName(item.getStandardUnitName());
                specialTaxBean.setProductID(item.getProductID());
                mTaxList.add(0, specialTaxBean);
            } else {
                for (SpecialTaxBean bean : mTaxList) {
                    if (bean.getProductID().equals(item.getProductID())) {
                        mTaxList.remove(bean);
                        break;
                    }
                }
            }
            mSelectedNum.setText(String.format("已选：%s", mTaxList.size()));
            updateBtnStatus();
        });
        mRightList.setAdapter(mRightAdapter);
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mRightList.getItemAnimator()).setSupportsChangeAnimations(false);
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

        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(GoodsSelectActivity.this, searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mSearchWords = searchContent;
                mPresenter.reload();
            }
        });
    }

    private void initData() {
        mPresenter = GoodsSelectPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @OnClick(R.id.ags_ok)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.putExtra("parcelable", mTaxList);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void setCategory(List<CategoryItem> list) {
        mLeftAdapter.setNewData(list);
        if (CommonUtils.isEmpty(list)) return;
        CategoryItem categoryItem = list.get(0);
        handleSelect(categoryItem);
    }

    private void handleSelect(CategoryItem categoryItem) {
        mLeftAdapter.select(categoryItem);
        mCategoryID = categoryItem.getCategoryID();
        mPresenter.reload();
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
    public void setData(List<GoodsBean> list, boolean append) {
        preProcess(list);
        if (append) {
            if (!CommonUtils.isEmpty(list)) mRightAdapter.addData(list);
        } else {
            mRightAdapter.setNewData(list);
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("该分类暂无商品");
            }
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
    }

    private void preProcess(List<GoodsBean> list) {
        if (!CommonUtils.isEmpty(list)) {
            for (GoodsBean bean : list) {
                for (SpecialTaxBean taxBean : mTaxList) {
                    if (bean.getProductID().equals(taxBean.getProductID())) {
                        bean.setCheck(true);
                        break;
                    }
                }
            }
        }
    }

    private void updateBtnStatus() {
        mOk.setEnabled(mTaxList.size() > 0);
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
    public String getID() {
        return mCategoryID;
    }

    @Override
    public String getSearchWords() {
        return mSearchWords;
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mRightAdapter.setEmptyView(mEmptyView);
        }
    }
}
