package com.hll_sc_app.app.aptitude.goods.add;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

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
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.aptitude.AptitudeProductBean;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.right.RightTextView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 12/1/20.
 */
@Route(path = RouterConfig.APTITUDE_GOODS_ADD)
public class AptitudeGoodsAddActivity extends BaseLoadActivity implements IAptitudeGoodsAddContract.IAptitudeGoodsAddView {
    public static final int REQ_CODE = 0x280;
    @Autowired(name = "parcelable")
    ArrayList<AptitudeProductBean> mSelectProduct;
    @BindView(R.id.title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.check_all)
    ImageView mCheckAll;
    @BindView(R.id.txt_move)
    RightTextView mTxtMove;
    private EmptyView mEmptyView;
    private ProductAdapter mAdapter;
    private IAptitudeGoodsAddContract.IAptitudeGoodsAddPresenter mPresenter;

    public static void start(Activity context, ArrayList<AptitudeProductBean> productBeans) {
        RouterUtil.goToActivity(RouterConfig.APTITUDE_GOODS_ADD, context, REQ_CODE, productBeans);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_stock_check_setting);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        mPresenter = AptitudeGoodsAddPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        initView();
        initData();
    }

    private void initData() {
        if (mSelectProduct == null) {
            mSelectProduct = new ArrayList<>();
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

    private void initView() {
        mTitleBar.setHeaderTitle("选择商品");
        mTxtMove.setRightCode(null);
        mTxtMove.setBackgroundResource(R.drawable.bg_button_mid_solid_primary);
        mTxtMove.setTextColor(Color.WHITE);
        mTxtMove.setText("确认");
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(AptitudeGoodsAddActivity.this, searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.start();
            }
        });
        mAdapter = new ProductAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            GoodsBean bean = mAdapter.getItem(position);
            if (bean == null) return;
            setCheck(bean, !bean.isCheck());
            mAdapter.notifyItemChanged(position);
            updateBottomBar();
        });
        // 避免 notifyItemChanged 闪烁
        ((SimpleItemAnimator) mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
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
    }

    private void setCheck(GoodsBean bean, boolean check) {
        bean.setCheck(check);
        int index = index(bean.getProductID());
        if (check) {
            if (index == -1) {
                AptitudeProductBean productBean = new AptitudeProductBean();
                productBean.setProductID(bean.getProductID());
                productBean.setExtGroupID(bean.getExtGroupID());
                AptitudeProductBean.ProductInfoBean info = new AptitudeProductBean.ProductInfoBean();
                productBean.setAptitudeProduct(info);
                info.setGroupID(bean.getGroupID());
                info.setImgUrl(bean.getImgUrl());
                info.setProductCode(bean.getProductCode());
                info.setProductID(bean.getProductID());
                info.setProductName(bean.getProductName());
                info.setSaleSpecNum(bean.getSaleSpecNum());
                mSelectProduct.add(productBean);
            }
        } else if (index > -1) {
            mSelectProduct.remove(index);
        }
    }

    @Override
    public void hideLoading() {
        mRefreshLayout.closeHeaderOrFooter();
        super.hideLoading();
    }

    @OnClick({R.id.check_all, R.id.txt_move})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.check_all:
                if (CommonUtils.isEmpty(mAdapter.getData())) {
                    return;
                }
                boolean selected = view.isSelected();
                for (GoodsBean bean : mAdapter.getData()) {
                    setCheck(bean, !selected);
                }
                view.setSelected(!selected);
                mAdapter.notifyDataSetChanged();
                break;
            case R.id.txt_move:
                if (CommonUtils.isEmpty(mSelectProduct)) {
                    showToast("请选择商品");
                    return;
                }
                Intent intent = new Intent();
                Bundle args = new Bundle();
                args.putParcelableArrayList("parcelable", mSelectProduct);
                intent.putExtras(args);
                setResult(RESULT_OK, intent);
                finish();
                break;
        }
    }

    private int index(String productID) {
        if (CommonUtils.isEmpty(mSelectProduct)) return -1;
        for (int i = 0; i < mSelectProduct.size(); i++) {
            AptitudeProductBean bean = mSelectProduct.get(i);
            if (TextUtils.equals(bean.getProductID(), productID)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void setData(List<GoodsBean> list, boolean append) {
        if (!CommonUtils.isEmpty(mSelectProduct) && !CommonUtils.isEmpty(list)) {
            for (GoodsBean goodsBean : list) {
                goodsBean.setCheck(false);
                if (index(goodsBean.getProductID()) > -1) {
                    goodsBean.setCheck(true);
                }
            }
        }
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("暂无数据");
            }
            mAdapter.setNewData(list);
        }
        mRefreshLayout.setEnableLoadMore(list != null && list.size() == 20);
        updateBottomBar();
    }

    private void updateBottomBar() {
        boolean selectAll = true;
        List<GoodsBean> list = mAdapter.getData();
        if (CommonUtils.isEmpty(list)) {
            selectAll = false;
        } else {
            for (GoodsBean bean : list) {
                if (!bean.isCheck()) {
                    selectAll = false;
                    break;
                }
            }
        }
        mCheckAll.setSelected(selectAll);
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
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    private static class ProductAdapter extends BaseQuickAdapter<GoodsBean, BaseViewHolder> {

        public ProductAdapter() {
            super(R.layout.list_item_stock_check_setting);
        }

        @Override
        protected void convert(BaseViewHolder helper, GoodsBean item) {
            helper.setText(R.id.txt_title, item.getProductName())
                    .setText(R.id.txt_code, "编码：" + item.getProductCode())
                    .setText(R.id.txt_spec, "规格：" + item.getSaleSpecNum() + "种");
            ((GlideImageView) helper.getView(R.id.glide_img)).setImageURL(item.getImgUrl());
            helper.getView(R.id.checkbox).setSelected(item.isCheck());
        }
    }
}
