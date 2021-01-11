package com.hll_sc_app.app.select.product.goodsassign;

import android.content.Intent;
import android.graphics.Color;
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
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.app.setting.tax.goodsselect.GoodsCategoryAdapter;
import com.hll_sc_app.app.setting.tax.goodsselect.GoodsSelectPresenter;
import com.hll_sc_app.app.setting.tax.goodsselect.IGoodsSelectContract;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.SingleListEvent;
import com.hll_sc_app.bean.goods.GoodsAssignBean;
import com.hll_sc_app.bean.goods.GoodsAssignDetailBean;
import com.hll_sc_app.bean.goods.GoodsAssignSpecBean;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.bean.user.CategoryItem;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/26
 */
@Route(path = RouterConfig.SELECT_PRODUCT_GOODS_ASSIGN)
public class SelectProductActivity extends BaseLoadActivity implements IGoodsSelectContract.IGoodsSelectView {
    @BindView(R.id.ags_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.ags_search_view)
    SearchView mSearchView;
    @BindView(R.id.ags_left_list)
    RecyclerView mLeftList;
    @BindView(R.id.ags_right_list)
    RecyclerView mRightList;
    @BindView(R.id.ags_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.ags_selected_num)
    TextView mSelectedNum;
    @Autowired(name = "parcelable")
    GoodsAssignBean mBean;
    List<GoodsAssignDetailBean> mList;
    private IGoodsSelectContract.IGoodsSelectPresenter mPresenter;
    private GoodsCategoryAdapter mLeftAdapter;
    private SelectProductAdapter mRightAdapter;
    private String mCategoryID;
    private EmptyView mEmptyView;

    public static void start(GoodsAssignBean bean) {
        RouterUtil.goToActivity(RouterConfig.SELECT_PRODUCT_GOODS_ASSIGN, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_select);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        mPresenter = GoodsSelectPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setHeaderTitle(mBean.getAssignType().getTitle());
        mList = mBean.getProductList() == null ? new ArrayList<>() : mBean.getProductList();
        mLeftAdapter = new GoodsCategoryAdapter();
        mLeftList.setAdapter(mLeftAdapter);
        mLeftAdapter.setOnItemClickListener((adapter, view, position) -> {
            CategoryItem item = mLeftAdapter.getItem(position);
            if (item == null) return;
            handleSelect(item);
        });

        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(64), 0, 0, 0, Color.WHITE);
        mRightList.addItemDecoration(decor);
        mSelectedNum.setText(String.format("已选：%s", mList.size()));
        mRightAdapter = new SelectProductAdapter(mBean.getAssignType());
        mRightAdapter.setOnItemClickListener((adapter, view, position) -> {
            Object o = adapter.getItem(position);
            if (o instanceof SpecsBean) {
                SpecsBean bean = (SpecsBean) o;
                if (bean.isSelect()) {
                    remove(bean);
                } else {
                    add((GoodsBean) view.getTag(), bean);
                }
                bean.setSelect(!bean.isSelect());
                adapter.notifyItemChanged(position);
                mSelectedNum.setText(String.format("已选：%s", mList.size()));
            }
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
                SearchActivity.start(SelectProductActivity.this, searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.reload();
            }
        });
    }

    private void remove(SpecsBean bean) {
        for (int i = 0; i < mList.size(); i++) {
            GoodsAssignDetailBean detailBean = mList.get(i);
            List<GoodsAssignSpecBean> specs = detailBean.getSpecs();
            for (int j = 0; j < specs.size(); j++) {
                GoodsAssignSpecBean spec = specs.get(j);
                if (TextUtils.equals(spec.getSpecID(), bean.getSpecID())) {
                    if (detailBean.getSpecs().size() == 1) {
                        mList.remove(detailBean);
                    } else {
                        detailBean.getSpecs().remove(spec);
                    }
                    break;
                }
            }
        }
    }

    private void add(GoodsBean goodsBean, SpecsBean specsBean) {
        for (GoodsAssignDetailBean detailBean : mList) {
            if (TextUtils.equals(detailBean.getProductID(), goodsBean.getProductID())) {
                detailBean.getSpecs().add(GoodsAssignSpecBean.convertFromSpecsBean(specsBean));
                return;
            }
        }
        mList.add(GoodsAssignDetailBean.convertFromGoodsBean(goodsBean, specsBean));
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
    public void setCategory(List<CategoryItem> list) {
        mLeftAdapter.setNewData(list);
        if (CommonUtils.isEmpty(list)) return;
        CategoryItem categoryItem = list.get(0);
        handleSelect(categoryItem);
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
            for (GoodsBean goodsList : list) {
                for (GoodsAssignDetailBean detailBean : mList) {
                    if (TextUtils.equals(goodsList.getProductID(), detailBean.getProductID())) {
                        for (SpecsBean specsBean : goodsList.getSpecs()) {
                            for (GoodsAssignSpecBean assignSpec : detailBean.getSpecs()) {
                                if (TextUtils.equals(specsBean.getSpecID(), assignSpec.getSpecID())) {
                                    specsBean.setSelect(true);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @OnClick(R.id.ags_ok)
    void onViewClicked() {
        EventBus.getDefault().post(new SingleListEvent<>(mList, GoodsAssignDetailBean.class));
        finish();
    }

    @Override
    public String getID() {
        return mCategoryID;
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
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

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mRightAdapter.setEmptyView(mEmptyView);
        }
    }
}
