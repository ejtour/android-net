package com.hll_sc_app.app.goods.relevance;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.search.OrderSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.GoodsInvWarnSearchEvent;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.GoodsListReq;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 第三方商品关联-采购商列表
 *
 * @author zhuyingsong
 * @date 2019/7/4
 */
@Route(path = RouterConfig.GOODS_RELEVANCE_PURCHASER_LIST, extras = Constant.LOGIN_EXTRA)
public class GoodsRelevancePurchaserActivity extends BaseLoadActivity implements GoodsRelevancePurchaserContract.IGoodsInvWarnView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.img_close)
    ImageView mImgClose;
    @BindView(R.id.txt_houseName)
    TextView mTxtHouseName;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlToolbar;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private GoodsListAdapter mAdapter;
    private GoodsRelevancePurchaserPresenter mPresenter;
    private HouseSelectWindow mHouseSelectWindow;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_relevance_purchaser);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = GoodsRelevancePurchaserPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                OrderSearchActivity.start(searchContent, OrderSearchActivity.FROM_GOODS_INV_WARN);
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mPresenter.queryGoodsInvList(true);
                } else {
                    mPresenter.searchGoodsInvList(true);
                }
            }
        });
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (mSearchView.isSearchStatus()) {
                    mPresenter.searchMoreGoodsInvList();
                } else {
                    mPresenter.queryMoreGoodsInvList();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (mSearchView.isSearchStatus()) {
                    mPresenter.searchGoodsInvList(false);
                } else {
                    mPresenter.queryGoodsInvList(false);
                }
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("还没有采购商数据").create();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new GoodsListAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }

    @Subscribe
    public void onEvent(GoodsInvWarnSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public void showHouseWindow(List<HouseBean> list) {
        if (CommonUtils.isEmpty(list)) {
            showToast("没有仓库数据");
            return;
        }
        if (mHouseSelectWindow == null) {
            mHouseSelectWindow = new HouseSelectWindow(this, list);
            mHouseSelectWindow.setListener(this::showSelectHouse);
        }
        mHouseSelectWindow.showAsDropDownFix(mRlToolbar, Gravity.NO_GRAVITY);
    }

    @Override
    public void showSelectHouse(HouseBean houseBean) {
        mTxtHouseName.setText(houseBean.getHouseName());
        mTxtHouseName.setTag(houseBean.getId());
        mPresenter.queryGoodsInvList(true);
    }

    @Override
    public String getHouseId() {
        String houseId = null;
        if (mTxtHouseName.getTag() != null) {
            houseId = (String) mTxtHouseName.getTag();
        }
        return houseId;
    }

    @Override
    public String getName() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void showGoodsInvList(List<GoodsBean> list, boolean append, int total) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        if (mSearchView.isSearchStatus()) {
            mEmptyView.setTips("搜索不到相关采购商数据");
        } else {
            mEmptyView.setTips("还没有采购商数据");
        }
        mAdapter.setEmptyView(mEmptyView);
        if (total != 0) {
            mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != total);
        } else {
            mRefreshLayout.setEnableLoadMore(list.size() == GoodsListReq.PAGE_SIZE);
        }
    }

    @OnClick({R.id.img_close, R.id.ll_house})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.ll_house:
                mPresenter.queryHouseList(true);
                break;
            default:
                break;
        }
    }

    class GoodsListAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

        GoodsListAdapter() {
            super(R.layout.item_goods_inv_warn);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean item) {
            ((GlideImageView) (helper.setText(R.id.txt_productName, item.getProductName())
                .setText(R.id.txt_specsSize, "规格：" + getString(item.getSaleSpecNum()))
                .setText(R.id.txt_standardUnit, "标准单位：" + getString(item.getStandardUnitName()))
                .setText(R.id.txt_cargoOwnerName, "货主：" + getString(item.getCargoOwnerName()))
                .setText(R.id.txt_usableStock, "可用库存：" + CommonUtils.formatNumber(item.getUsableStock()))
                .setText(R.id.txt_stockWarnNum_unit, getString(item.getStandardUnitName()))
                .setText(R.id.txt_stockWarnNum, CommonUtils.formatNumber(item.getStockWarnNum()))
                .addOnClickListener(R.id.txt_stockWarnNum)
                .getView(R.id.img_imgUrl))).setImageURL(item.getImgUrl());
        }

        private String getString(String str) {
            return TextUtils.isEmpty(str) ? "无" : str;
        }
    }
}
