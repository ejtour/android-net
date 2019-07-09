package com.hll_sc_app.app.agreementprice.quotation.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 协议价管理-添加报价单
 *
 * @author zhuyingsong
 * @date 2019/7/9
 */
@Route(path = RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD, extras = Constant.LOGIN_EXTRA)
public class QuotationAddActivity extends BaseLoadActivity implements QuotationAddContract.IPurchaseView {
    @BindView(R.id.txt_isWarehouse)
    TextView mTxtIsWarehouse;
    @BindView(R.id.txt_select_purchaser)
    TextView mTxtSelectPurchaser;
    @BindView(R.id.txt_priceDate)
    TextView mTxtPriceDate;
    @BindView(R.id.txt_templateID)
    TextView mTxtTemplateId;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_add_product)
    TextView mTxtAddProduct;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    @BindView(R.id.include_title)
    ConstraintLayout mListTitle;
    private QuotationAddPresenter mPresenter;
    private GoodsListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_add);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        mPresenter = QuotationAddPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        initView();
    }

    private void initView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GoodsListAdapter();
        View emptyView = LayoutInflater.from(this).inflate(R.layout.view_quotation_add_empty, mRecyclerView, false);
        emptyView.findViewById(R.id.txt_product).setOnClickListener(v -> addProduct());
        mAdapter.setEmptyView(emptyView);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void addProduct() {

    }


    @Override
    public void showGoodsDetail(QuotationDetailResp resp) {

    }

    @OnClick({R.id.img_close, R.id.txt_add_product})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_add_product:
                addProduct();
                break;
            default:
                break;
        }
    }

    public class GoodsListAdapter extends BaseQuickAdapter<QuotationDetailBean, BaseViewHolder> {

        GoodsListAdapter() {
            super(R.layout.item_agreement_price_quotation_detail);
        }

        @Override
        protected void convert(BaseViewHolder helper, QuotationDetailBean item) {
            ((GlideImageView) helper.getView(R.id.img_imgUrl)).setImageURL(item.getImgUrl());
            helper.setText(R.id.txt_productName, item.getProductName())
                .setText(R.id.txt_productDesc, item.getProductDesc())
                .setText(R.id.txt_price, CommonUtils.formatNumber(item.getPrice()))
                .setText(R.id.txt_costPrice, "成本价：" + CommonUtils.formatNumber(item.getCostPrice()))
                .setText(R.id.txt_productPrice, "平台价：" + CommonUtils.formatNumber(item.getProductPrice()));
        }
    }
}
