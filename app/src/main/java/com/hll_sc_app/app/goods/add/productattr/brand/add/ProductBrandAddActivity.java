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
import com.hll_sc_app.bean.goods.ProductBrandBean;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

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
    private ProductBrandAdapter mAdapter;
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
        mEdtBrandName.addTextChangedListener((GoodsSpecsAddActivity.CheckTextWatcher) s
            -> mTxtCommit.setEnabled(!TextUtils.isEmpty(s)));
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryMoreProductBrandList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryProductBrandList(false);
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(this, R.color.base_color_divider)
            , UIUtils.dip2px(1)));
        mAdapter = new ProductBrandAdapter();
        mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.img_del) {
                    ProductBrandBean brandBean = (ProductBrandBean) adapter.getItem(position);
                    if (brandBean != null) {
                        // 删除
                    }
                }
            }
        });
        mEmptyView = EmptyView.newBuilder(this).setTips("您还没有商品品牌").create();
        mRecyclerView.setAdapter(mAdapter);
    }

    @OnClick({R.id.img_close})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        }
    }

    @Override
    public void showProductBrandList(List<ProductBrandBean> list, boolean append, int total) {
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

    class ProductBrandAdapter extends BaseQuickAdapter<ProductBrandBean, BaseViewHolder> {

        ProductBrandAdapter() {
            super(R.layout.item_product_brand);
        }

        @Override
        protected void convert(BaseViewHolder helper, ProductBrandBean bean) {
            helper.setText(R.id.txt_brandName, bean.getBrandName())
                .setText(R.id.txt_state, getStatusString(bean.getStatus()))
                .setTextColor(R.id.txt_state, getStatusColor(bean.getStatus()))
                .setGone(R.id.img_del, TextUtils.equals(bean.getStatus(), "3"))
                .setGone(R.id.txt_failureReason, TextUtils.equals(bean.getStatus(), "3"))
                .setText(R.id.txt_failureReason, bean.getFailureReason())
                .addOnClickListener(R.id.img_del);
        }

        private String getStatusString(String status) {
            switch (status) {
                case "1":
                    return "待审核";
                case "2":
                    return "审核通过";
                case "3":
                    return "审核失败";
                default:
                    return "";
            }
        }

        private int getStatusColor(String status) {
            switch (status) {
                case "1":
                    return 0xFF5695D2;
                case "2":
                    return 0xFF7ED321;
                case "3":
                    return 0xFFED5655;
                default:
                    return 0;
            }
        }
    }
}
