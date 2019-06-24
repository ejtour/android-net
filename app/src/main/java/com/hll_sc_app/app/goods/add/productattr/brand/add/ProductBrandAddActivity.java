package com.hll_sc_app.app.goods.add.productattr.brand.add;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.add.specs.GoodsSpecsAddActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.goods.DepositProductBean;
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
 * 商品品牌-新增
 *
 * @author zhuyingsong
 * @date 2019/6/24
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS_PRODUCT_ATTR_BRAND_ADD, extras = Constant.LOGIN_EXTRA)
public class ProductBrandAddActivity extends BaseLoadActivity implements ProductBrandAddContract.ISaleUnitNameAddView {
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.img_close)
    ImageView mImgClose;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlToolbar;
    @BindView(R.id.edt_brandName)
    EditText mEdtBrandName;
    @BindView(R.id.text_input_tips)
    TextView mTextInputTips;
    @BindView(R.id.text_commit)
    TextView mTxtCommit;
    @BindView(R.id.fl_bottom)
    FrameLayout mFlBottom;
    private DepositProductAdapter mAdapter;
    private ProductBrandAddPresenter mPresenter;
    private EmptyView mEmptyView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_attr_brand_add);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = ProductBrandAddPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mEdtBrandName.addTextChangedListener((GoodsSpecsAddActivity.CheckTextWatcher) s -> mTxtCommit.setEnabled(!TextUtils.isEmpty(s)));
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
            DepositProductBean depositProductBean = (DepositProductBean) adapter.getItem(position);
            if (depositProductBean != null) {
                depositProductBean.setSelected(!depositProductBean.isSelected());
                mAdapter.notifyItemChanged(position);
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有商品品牌").create();
        mRecyclerView.setAdapter(mAdapter);
    }

    /**
     * 获取选中的押金商品
     *
     * @return 押金商品列表
     */
    private ArrayList<DepositProductBean> getSelectDepositProductList() {
        ArrayList<DepositProductBean> list = new ArrayList<>();
        if (mAdapter != null && !CommonUtils.isEmpty(mAdapter.getData())) {
            for (DepositProductBean bean : mAdapter.getData()) {
                if (bean.isSelected()) {
                    list.add(bean);
                }
            }
        }
        return list;
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        }
    }

    @Override
    public void showDepositProductsList(List<DepositProductBean> list, boolean append, int total) {
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

    class DepositProductAdapter extends BaseQuickAdapter<DepositProductBean, BaseViewHolder> {

        DepositProductAdapter() {
            super(R.layout.item_deposit_product);
        }

        @Override
        protected void convert(BaseViewHolder helper, DepositProductBean bean) {
            helper.setText(R.id.txt_productName, bean.getProductName())
                .setText(R.id.txt_specContent, getContent(bean))
                .getView(R.id.img_select).setSelected(bean.isSelected());
        }

        private String getContent(DepositProductBean bean) {
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
