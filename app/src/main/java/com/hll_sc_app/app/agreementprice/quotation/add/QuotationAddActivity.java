package com.hll_sc_app.app.agreementprice.quotation.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
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
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.DateSelectWindow;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailResp;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SingleSelectionDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

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
    private SingleSelectionDialog mWarehouseDialog;
    private QuotationBean mQuotationBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_add);
        ButterKnife.bind(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        mPresenter = QuotationAddPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        mQuotationBean = new QuotationBean();
        initView();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
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

    @Subscribe
    public void onEvent(QuotationBean event) {
        mQuotationBean.setPurchaserID(event.getPurchaserID());
        mQuotationBean.setPurchaserName(event.getPurchaserName());
        mQuotationBean.setIsAllShop(event.getIsAllShop());
        mQuotationBean.setShopIDs(event.getShopIDs());
        mQuotationBean.setShopIDNum(event.getShopIDNum());
        mTxtSelectPurchaser.setText(String.format("%s%s家门店", event.getPurchaserName(), event.getShopIDNum()));
    }

    @Override
    public void showGoodsDetail(QuotationDetailResp resp) {
    }

    @OnClick({R.id.img_close, R.id.txt_add_product, R.id.rl_isWarehouse, R.id.rl_select_purchaser, R.id.rl_priceDate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_add_product:
                addProduct();
                break;
            case R.id.rl_isWarehouse:
                showWarehouseSelect();
                break;
            case R.id.rl_select_purchaser:
                toSelectPurchaser();
                break;
            case R.id.rl_priceDate:
                showPriceDateWindow();
                break;
            default:
                break;
        }
    }

    private void showWarehouseSelect() {
        if (mWarehouseDialog == null) {
            List<NameValue> values = new ArrayList<>();
            values.add(new NameValue("自营客户", "0"));
            values.add(new NameValue("代仓客户", "1"));
            mWarehouseDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                .setTitleText("报价类别")
                .refreshList(values)
                .setOnSelectListener(nameValue -> {
                    mQuotationBean.setIsWarehouse(nameValue.getValue());
                    mTxtIsWarehouse.setText(nameValue.getName());
                }).create();
        }
        mWarehouseDialog.show();
    }

    private void toSelectPurchaser() {
        if (TextUtils.isEmpty(mTxtIsWarehouse.getText().toString())) {
            showToast("请先选择报价类别");
            return;
        }
        if (TextUtils.isEmpty(mQuotationBean.getShopIDs())) {
            boolean warehouse = TextUtils.equals("代仓客户", mTxtIsWarehouse.getText().toString());
            RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD_PURCHASER, warehouse);
        } else {
            RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD_PURCHASER_SHOP, mQuotationBean);
        }
    }

    private void showPriceDateWindow() {
        DateSelectWindow window = new DateSelectWindow(this);
        window.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
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
