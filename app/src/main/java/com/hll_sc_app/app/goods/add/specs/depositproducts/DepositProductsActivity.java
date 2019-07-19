package com.hll_sc_app.app.goods.add.specs.depositproducts;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 选择押金商品列表
 *
 * @author zhuyingsong
 * @date 2019/6/20
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_SPECS_DEPOSIT_PRODUCT, extras = Constant.LOGIN_EXTRA)
public class DepositProductsActivity extends BaseLoadActivity implements DepositProductsContract.ISaleUnitNameAddView {
    public static final String INTENT_TAG = "intent_tag_deposit_products";
    public static final int INT_MAX_COUNT = 3;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    private DepositProductAdapter mAdapter;
    private DepositProductsPresenter mPresenter;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_products);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = DepositProductsPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreDepositProducts();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryDepositProducts(false);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new DepositProductAdapter();
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            SkuGoodsBean depositProductBean = (SkuGoodsBean) adapter.getItem(position);
            if (depositProductBean != null) {
                depositProductBean.setSelected(!depositProductBean.isSelected());
                if (getSelectDepositProductList().size() > INT_MAX_COUNT) {
                    // 恢复
                    depositProductBean.setSelected(!depositProductBean.isSelected());
                    // 提示
                    showToast("最多选择" + INT_MAX_COUNT + "个押金商品");
                    return;
                }
                mAdapter.notifyItemChanged(position);
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有押金商品").create();
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 获取选中的押金商品
     *
     * @return 押金商品列表
     */
    private ArrayList<SkuGoodsBean> getSelectDepositProductList() {
        ArrayList<SkuGoodsBean> list = new ArrayList<>();
        if (mAdapter != null && !CommonUtils.isEmpty(mAdapter.getData())) {
            for (SkuGoodsBean bean : mAdapter.getData()) {
                if (bean.isSelected()) {
                    list.add(bean);
                }
            }
        }
        return list;
    }

    @OnClick({R.id.img_close, R.id.txt_save})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        } else if (view.getId() == R.id.txt_save) {
            Intent intent = new Intent();
            intent.putParcelableArrayListExtra(INTENT_TAG, getSelectDepositProductList());
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Override
    public void showDepositProductsList(List<SkuGoodsBean> list, boolean append, int total) {
        if (append) {
            mAdapter.addData(list);
        } else {
            mAdapter.setNewData(list);
        }
        mAdapter.setEmptyView(mEmptyView);
        mRefreshLayout.setEnableLoadMore(mAdapter.getItemCount() != total);
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    class DepositProductAdapter extends BaseQuickAdapter<SkuGoodsBean, BaseViewHolder> {

        DepositProductAdapter() {
            super(R.layout.item_deposit_product);
        }

        @Override
        protected void convert(BaseViewHolder helper, SkuGoodsBean bean) {
            helper.setText(R.id.txt_productName, bean.getProductName())
                .setText(R.id.txt_specContent, getContent(bean))
                .getView(R.id.img_select).setSelected(bean.isSelected());
        }

        private String getContent(SkuGoodsBean bean) {
            StringBuilder builder = new StringBuilder();
            if (!TextUtils.isEmpty(bean.getSpecContent())) {
                builder.append(bean.getSpecContent());
            }
            if (!TextUtils.isEmpty(bean.getSaleUnitName())) {
                if (builder.length() != 0) {
                    builder.append("，");
                }
                builder.append(bean.getSaleUnitName());
            }
            return builder.toString();
        }
    }
}
