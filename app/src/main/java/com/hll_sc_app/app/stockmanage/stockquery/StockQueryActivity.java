package com.hll_sc_app.app.stockmanage.stockquery;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.invwarn.TopSingleSelectWindow;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

@Route(path = RouterConfig.ACTIVITY_STOCK_QUERY_LIST)
public class StockQueryActivity extends BaseLoadActivity implements IStockQueryContract.IView {
    @BindView(R.id.txt_house_name)
    TextView mHouseName;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefresh;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.search)
    SearchView mSearchView;
    private Unbinder unbinder;
    private IStockQueryContract.IPresent mPresent;
    private GoodAdapter mAdapter;
    private TopSingleSelectWindow<HouseBean> mSelectHouseWindow;
    private ContextOptionsWindow mOptionsWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_stock_query);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresent = StockQueryPresent.newInstance();
        mPresent.register(this);
        mPresent.queryHouseList();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.getMoreGoodsList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refreshGoodsList();
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new GoodAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(StockQueryActivity.this,
                        searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresent.refreshGoodsList();
            }
        });
        mHouseName.setOnClickListener(v -> {
            if (mSelectHouseWindow != null) {
                mSelectHouseWindow.showAsDropDown(((ViewGroup) mHouseName.getParent()));
            }
        });
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
    public void getHouseListSuccess(List<HouseBean> houseBeans) {
        HouseBean currentBean = houseBeans.get(0);
        mHouseName.setText(currentBean.getHouseName());
        mHouseName.setTag(currentBean);
        mPresent.queryGoodsInvList(true);
        mSelectHouseWindow = new TopSingleSelectWindow<>(this, item -> item.getHouseName());
        mSelectHouseWindow.refreshList(houseBeans);
        mSelectHouseWindow.setListener(houseBean -> {
            mHouseName.setText(houseBean.getHouseName());
            mHouseName.setTag(houseBean);
            mPresent.refreshGoodsList();
        });
    }

    @Override
    public void getGoodsListSuccess(List<GoodsBean> goodsBeans, boolean isMore) {
        if (isMore && goodsBeans != null && goodsBeans.size() > 0) {
            mAdapter.addData(goodsBeans);
        } else if (!isMore) {
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("喔唷，居然是「 空 」的").create());
            mAdapter.setNewData(goodsBeans);
        }
        if (goodsBeans != null) {
            mRefresh.setEnableLoadMore(goodsBeans.size() == mPresent.getPageSize());
        }
    }

    @Override
    public String getHouseId() {
        Object tag = mHouseName.getTag();
        if (tag == null) {
            return "";
        } else {
            return ((HouseBean) tag).getId();
        }
    }

    @Override
    public String getProductName() {
        return mSearchView.getSearchContent();
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefresh.closeHeaderOrFooter();
    }

    @OnClick(R.id.img_close)
    @Override
    public void finish() {
        super.finish();
    }

    @OnClick(R.id.img_option)
    public void showOptionsWindow(View view) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this);
            mOptionsWindow.refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_STOCK_INFO)));
            mOptionsWindow.setListener((adapter, view1, position) -> {
                mOptionsWindow.dismiss();
                mPresent.export(null);
            });
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.END);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, mPresent::export);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
    }

    private class GoodAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {
        public GoodAdapter(@Nullable List<GoodsBean> data) {
            super(R.layout.list_item_stock_query_good, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean item) {
            helper.setText(R.id.txt_good_name, item.getProductName())
                    .setText(R.id.txt_useble, CommonUtils.formatNumber(item.getUsableStock()))
                    .setText(R.id.txt_used, CommonUtils.formatNumber(item.getOccupiedStock()))
                    .setText(R.id.txt_stock, CommonUtils.formatNumber(item.getTotalStock()))
                    .setText(R.id.txt_unit, item.getStandardUnitName())
                    .setText(R.id.txt_owner, item.getCargoOwnerName())
                    .setText(R.id.txt_spec, item.getSaleSpecNum() + "种");

            GlideImageView imageView = helper.getView(R.id.img_good);
            imageView.setImageURL(item.getImgUrl());

        }
    }
}
