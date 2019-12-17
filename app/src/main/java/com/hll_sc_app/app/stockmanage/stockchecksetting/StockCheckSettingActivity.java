package com.hll_sc_app.app.stockmanage.stockchecksetting;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.SimpleSearch;
import com.hll_sc_app.app.stockmanage.selectproduct.ProductSelectActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;
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
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.ll_data)
    LinearLayout mLlData;
    @BindView(R.id.ll_empty)
    LinearLayout mLlEmpty;
    @BindView(R.id.check_all)
    CheckBox mCheckAll;
    @BindView(R.id.title_bar)
    TitleBar mTitle;

    private IStockCheckSettingContract.IPresent mPresent;
    private Unbinder unbinder;
    private ProductAdpter mAdapter;

    /*需要移除的ids*/
    private Set<String> mProductIds = new HashSet<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_check_setting);
        unbinder = ButterKnife.bind(this);
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
        mTitle.setRightBtnClick(v -> {
            ProductSelectActivity.start("新增库存校验商品", null);
        });
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

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ProductAdpter(null);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            switch (view.getId()) {
                case R.id.checkbox:
                    onSelect((CheckBox) view, position);
                    break;
                default:
                    break;
            }

        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            onSelect(view.findViewById(R.id.checkbox), position);
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
    public void onEvent(List<GoodsBean> goodsBeans) {
        List<String> ids = new ArrayList<>();
        for (GoodsBean goodsBean : goodsBeans) {
            ids.add(goodsBean.getProductID());
        }
        mPresent.add(ids);
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
                    mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("当前条件下没有商品库存校验数据噢~").create());
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
        mCheckAll.setChecked(mAdapter.getData().size() == getProductIds().size());
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
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @OnClick({R.id.check_all, R.id.ll_check_all, R.id.txt_move, R.id.txt_add})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_all:
            case R.id.ll_check_all:
                if (mCheckAll.isChecked()) {
                    mProductIds.clear();
                } else {
                    for (GoodsBean goodsBean : mAdapter.getData()) {
                        mProductIds.add(goodsBean.getProductID());
                    }
                }
                mCheckAll.setChecked(!mCheckAll.isChecked());
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.txt_move:
                if (getProductIds().size() == 0) {
                    return;
                }
                SuccessDialog.newBuilder(this)
                        .setImageTitle(R.drawable.ic_dialog_failure)
                        .setImageState(R.drawable.ic_dialog_state_failure)
                        .setMessageTitle("确定要移除这些商品么")
                        .setMessage("移除选中的商品将使其不再校验库存\n请慎重操作")
                        .setButton(((dialog, item) -> {
                            dialog.dismiss();
                            if (item == 1) {
                                mPresent.remove();
                            }
                        }), "我再看看", "确定移除")
                        .create()
                        .show();

                break;
            case R.id.txt_add:
                ProductSelectActivity.start("新增库存校验商品", null);
                break;
            default:
                break;
        }
    }

    private void onSelect(CheckBox checkBox, int position) {
        boolean isSelect = checkBox.isChecked();
        if (isSelect) {
            mProductIds.remove(mAdapter.getItem(position).getProductID());
            mCheckAll.setChecked(false);
        } else {
            mProductIds.add(mAdapter.getItem(position).getProductID());
            mCheckAll.setChecked(mProductIds.size() == mAdapter.getData().size());
        }
        mAdapter.notifyDataSetChanged();
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
            ((CheckBox) helper.getView(R.id.checkbox)).setChecked(mProductIds.contains(item.getProductID()));
            helper.addOnClickListener(R.id.checkbox);
        }
    }
}
