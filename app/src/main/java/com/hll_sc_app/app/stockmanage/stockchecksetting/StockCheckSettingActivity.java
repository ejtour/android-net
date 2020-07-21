package com.hll_sc_app.app.stockmanage.stockchecksetting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.SimpleSearch;
import com.hll_sc_app.app.stockmanage.selectproduct.ProductSelectActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.CountlyMgr;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.SingleListEvent;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.right.RightTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 商品库存校验
 */
@Route(path = RouterConfig.ACTIVITY_STOCK_CHECK_SETTING)
public class StockCheckSettingActivity extends BaseLoadActivity implements IStockCheckSettingContract.IView {
    public static final String ACTION_STOCK_CHECK = "stockCheck";
    public static final String ACTION_NEXT_DAY = "nextDayDelivery";
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_data)
    LinearLayout mLlData;
    @BindView(R.id.empty_view)
    EmptyView mLlEmpty;
    @BindView(R.id.check_all)
    View mCheckAll;
    @BindView(R.id.title_bar)
    TitleBar mTitle;
    @BindView(R.id.txt_move)
    RightTextView mRemove;
    @Autowired(name = "object0")
    String mReqActionType;

    private IStockCheckSettingContract.IPresent mPresent;
    private Unbinder unbinder;
    private ProductAdpter mAdapter;

    /*需要移除的ids*/
    private Set<String> mProductIds = new HashSet<>();

    public static void start(String actionType) {
        if (ACTION_STOCK_CHECK.equals(actionType) && !RightConfig.checkRight(App.INSTANCE.getString(R.string.right_setUpVerify_query))) {
            ToastUtils.showShort(App.INSTANCE.getString(R.string.right_tips));
            return;
        }
        CountlyMgr.recordView(ACTION_STOCK_CHECK.equals(actionType)?"商品库存校验设置":"隔日配送商品管理");
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_STOCK_CHECK_SETTING, actionType);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_stock_check_setting);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        mPresent = StockCheckSettingPresent.newInstance();
        EventBus.getDefault().register(this);
        mPresent.register(this);
        initView();
        mPresent.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        if (ACTION_NEXT_DAY.equals(mReqActionType)) {
            mRemove.setRightCode(null);
            mTitle.setHeaderTitle("隔日配送商品管理");
        }
        mTitle.setRightBtnClick(v -> addProduct());
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.getMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });

        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ProductAdpter(null);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            onSelect(position);
        });
        mRecyclerView.setAdapter(mAdapter);

        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(StockCheckSettingActivity.this,
                        searchContent, SimpleSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresent.refresh();
            }
        });

        mLlEmpty.setTipsButton(isNextDay() ? "选择隔日配送的商品" : "选择库存校验的商品");
        mLlEmpty.setOnActionClickListener(new EmptyView.OnActionClickListener() {
            @Override
            public void retry() {
                // no-op
            }

            @Override
            public void action() {
                addProduct();
            }
        });
        mLlEmpty.setTipsTitle(isNextDay() ? "您还没有设置需要隔日配送的商品" : "您还没有设置需要校验库存的商品");
        mLlEmpty.setTips(isNextDay() ? "点击下面按钮选择需要设置的商品" : "点击下面按钮选择需要校验的商品");
    }

    private void addProduct() {
        if (isNextDay()) {
            ProductSelectActivity.start("新增隔日配送商品", "notNextDayDelivery", true, null);
        } else {
            ProductSelectActivity.start("新增库存校验商品", "noStockCheck", null);
        }
    }

    private boolean isNextDay() {
        return TextUtils.equals(ACTION_NEXT_DAY, mReqActionType);
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

    @Subscribe(sticky = true)
    public void onEvent(SingleListEvent<GoodsBean> event) {
        if (event.getClazz() == GoodsBean.class) {
            List<String> ids = new ArrayList<>();
            for (GoodsBean goodsBean : event.getList()) {
                ids.add(goodsBean.getProductID());
            }
            mPresent.add(ids);
        }
    }

    @Override
    public String getSearchContent() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void queryGoodsSuccess(List<GoodsBean> goodsBeans, boolean isMore) {
        if (isMore && goodsBeans != null && goodsBeans.size() > 0) {
            mAdapter.addData(goodsBeans);
        } else if (!isMore) {
            if (goodsBeans.size() == 0) {
                /*没有搜索词的情况下*/
                if (TextUtils.isEmpty(mSearchView.getSearchContent())) {
                    mLlEmpty.setVisibility(View.VISIBLE);
                    mLlData.setVisibility(View.GONE);
                } else {
                    mAdapter.setEmptyView(EmptyView.newBuilder(this)
                            .setTipsTitle(isNextDay() ? "没有找到相关的隔日配送商品噢" : "当前条件下没有商品库存校验数据噢~").create());
                }
                mTitle.setRightBtnVisible(false);
            } else {
                mLlEmpty.setVisibility(View.GONE);
                mLlData.setVisibility(View.VISIBLE);
                mTitle.setRightBtnVisible(true);
            }
            mAdapter.setNewData(goodsBeans);
        }

        if (goodsBeans != null) {
            mRefreshLayout.setEnableLoadMore(goodsBeans.size() == mPresent.getPageSize());
        }
        boolean selectAll = true;
        if (CommonUtils.isEmpty(mAdapter.getData())) {
            selectAll = false;
        } else {
            for (GoodsBean bean : mAdapter.getData()) {
                if (!mProductIds.contains(bean.getProductID())) {
                    selectAll = false;
                    break;
                }
            }
        }
        mCheckAll.setSelected(selectAll);
    }

    @Override
    public ArrayList<String> getProductIds() {
        return new ArrayList<>(mProductIds);
    }

    @Override
    public void addSuccess() {
        mPresent.refresh();
        showToast("新增商品成功");
    }

    @Override
    public void removeSuccess() {
        showToast("移除商品成功");
        mPresent.refresh();
        mProductIds.clear();
    }

    @Override
    public String getActionType() {
        return mReqActionType;
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @OnClick({R.id.ll_check_all, R.id.txt_move})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_check_all:
                if (mCheckAll.isSelected()) {
                    mProductIds.clear();
                } else {
                    for (GoodsBean goodsBean : mAdapter.getData()) {
                        mProductIds.add(goodsBean.getProductID());
                    }
                }
                if (!CommonUtils.isEmpty(mAdapter.getData())) {
                    mCheckAll.setSelected(!mCheckAll.isSelected());
                    mAdapter.notifyDataSetChanged();
                }
                break;
            case R.id.txt_move:
                if (getProductIds().size() == 0) {
                    return;
                }
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setMessageTitle("确定要移除这些商品么")
                        .setMessage(isNextDay() ? "移除选中的商品将使其恢复为正常配送\n不再是隔日配送" : "移除选中的商品将使其不再校验库存\n请慎重操作")
                        .setButton(((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                mPresent.remove();
                            }
                        }), "我再看看", "确定移除")
                        .create()
                        .show();

                break;
            default:
                break;
        }
    }

    private void onSelect(int position) {
        GoodsBean item = mAdapter.getItem(position);
        if (item == null) return;
        if (mProductIds.contains(item.getProductID())) {
            mProductIds.remove(item.getProductID());
            mCheckAll.setSelected(false);
        } else {
            mProductIds.add(item.getProductID());
            mCheckAll.setSelected(mProductIds.size() == mAdapter.getData().size());
        }
        mAdapter.notifyItemChanged(position);
    }

    private class ProductAdpter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
        public ProductAdpter(@Nullable List<GoodsBean> data) {
            super(R.layout.list_item_stock_check_setting, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean item) {
            helper.setText(R.id.txt_title, item.getProductName())
                    .setText(R.id.txt_code, "编码：" + item.getProductCode())
                    .setText(R.id.txt_spec, "规格：" + item.getSaleSpecNum() + "种");
            ((GlideImageView) helper.getView(R.id.glide_img)).setImageURL(item.getImgUrl());
            helper.getView(R.id.checkbox).setSelected(mProductIds.contains(item.getProductID()));
        }
    }
}
