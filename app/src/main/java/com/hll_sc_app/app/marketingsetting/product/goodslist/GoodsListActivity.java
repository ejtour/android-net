package com.hll_sc_app.app.marketingsetting.product.goodslist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.adapter.MarketingProductAdapter;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.ProductSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.TipsDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.SwipeItemLayout;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.marketingsetting.MarketingDetailCheckResp;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 显示某一个商品促销选择的活动商品列表
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_SELECT_PRODUCT_LIST)
public class GoodsListActivity extends BaseLoadActivity implements IGoodsListContract.IGoodsListView {
    public static final int REQ_CODE = 0x680;
    @BindView(R.id.asl_search_view)
    SearchView mSearchView;
    @BindView(R.id.asl_list_view)
    RecyclerView mListView;
    @BindView(R.id.asl_title_bar)
    TitleBar mTitleBar;
    @Autowired(name = "parcelable")
    MarketingDetailCheckResp mResp;
    private Unbinder unbinder;

    private MarketingProductAdapter mAdapter;
    private SkuGoodsBean mBean;

    private IGoodsListContract.IGoodsListPresenter mPresenter;

    private boolean mHasChanged;

    public static void start(Activity activity, MarketingDetailCheckResp resp) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_MARKETING_SELECT_PRODUCT_LIST, activity, REQ_CODE, resp);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_search_list);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        mPresenter = GoodsListPresenter.newInstance();
        mPresenter.register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void delConfirm() {
        TipsDialog.newBuilder(this)
                .setTitle("删除商品")
                .setMessage(String.format("确认要删除商品【%s】嘛", mBean.getProductName()))
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 1) {
                        mPresenter.del(mResp.getId(), mBean.getId());
                    }
                }, "取消", "确定")
                .create().show();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("活动商品");
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(65), 0, 0, 0, Color.WHITE);
        mListView.addItemDecoration(decor);
        mListView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new MarketingProductAdapter(null, MarketingProductAdapter.Modal.SHOW);
        mAdapter.setNewData(mResp.getProductList());
        mAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("无数据").create());
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            mBean = mAdapter.getItem(position);
            if (mBean == null) return;
            if (view.getId() == R.id.imp_del) {
                delConfirm();
            }
        });

        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(GoodsListActivity.this,
                        searchContent, ProductSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mAdapter.setNewData(mResp.getProductList());
                } else {
                    List<SkuGoodsBean> searchResults = new ArrayList<>();
                    for (int i = 0; i < mResp.getProductList().size(); i++) {
                        if (mResp.getProductList().get(i).getProductName().contains(searchContent.trim())) {
                            searchResults.add(mResp.getProductList().get(i));
                        }
                    }
                    mAdapter.setNewData(searchResults);
                }
            }
        });

        if (mResp.getDiscountStatus() == 2) {
            mListView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
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
    public void success() {
        mHasChanged = true;
        if (mAdapter.getData().size() == 1) {
            mAdapter.setNewData(new ArrayList<>());
        } else {
            mAdapter.remove(mAdapter.getData().indexOf(mBean));
        }
        mResp.getProductList().remove(mBean);
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }
}
