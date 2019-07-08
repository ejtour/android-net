package com.hll_sc_app.app.agreementprice.quotation.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hll_sc_app.app.agreementprice.quotation.QuotationListAdapter.getPriceDate;

/**
 * 协议价管理-报价单详情
 *
 * @author zhuyingsong
 * @date 2019/7/8
 */
@Route(path = RouterConfig.MINE_AGREEMENT_PRICE_DETAIL, extras = Constant.LOGIN_EXTRA)
public class QuotationDetailActivity extends BaseLoadActivity implements QuotationDetailContract.IPurchaseView {
    @Autowired(name = "parcelable", required = true)
    QuotationBean mBean;
    @BindView(R.id.img_close)
    ImageView mImgClose;
    @BindView(R.id.rl_title)
    RelativeLayout mRlTitle;
    @BindView(R.id.txt_isWarehouse_title)
    TextView mTxtIsWarehouseTitle;
    @BindView(R.id.txt_isWarehouse)
    TextView mTxtIsWarehouse;
    @BindView(R.id.rl_isWarehouse)
    RelativeLayout mRlIsWarehouse;
    @BindView(R.id.txt_select_purchaser_title)
    TextView mTxtSelectPurchaserTitle;
    @BindView(R.id.txt_select_purchaser)
    TextView mTxtSelectPurchaser;
    @BindView(R.id.rl_select_purchaser)
    RelativeLayout mRlSelectPurchaser;
    @BindView(R.id.txt_priceDate_title)
    TextView mTxtPriceDateTitle;
    @BindView(R.id.txt_priceDate)
    TextView mTxtPriceDate;
    @BindView(R.id.rl_priceDate)
    RelativeLayout mRlPriceDate;
    @BindView(R.id.txt_templateID_title)
    TextView mTxtTemplateIDTitle;
    @BindView(R.id.txt_templateID)
    TextView mTxtTemplateID;
    @BindView(R.id.rl_templateID)
    RelativeLayout mRlTemplateID;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.txt_disable)
    TextView mTxtDisable;
    @BindView(R.id.txt_add_product)
    TextView mTxtAddProduct;
    @BindView(R.id.ll_bottom)
    LinearLayout mLlBottom;
    private QuotationDetailPresenter mPresenter;
    private GoodsListAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        mPresenter = QuotationDetailPresenter.newInstance(mBean.getId());
        mPresenter.register(this);
        mPresenter.start();
        showView();
    }

    private void showView() {
        mTxtPriceDate.setText(getPriceDate(mBean.getPriceStartDate(), mBean.getPriceEndDate()));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new GoodsListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void showGoodsDetail(QuotationDetailResp resp) {
        mAdapter.setNewData(resp.getRecords());
        if (!CommonUtils.isEmpty(resp.getRecords())) {
            int status = resp.getRecords().get(0).getBillStatus();
        }
    }

    @OnClick({R.id.img_close, R.id.txt_disable, R.id.txt_add_product})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_disable:
                break;
            case R.id.txt_add_product:
                break;
            default:
                break;
        }
    }

    public class GoodsListAdapter extends BaseQuickAdapter<QuotationDetailBean, BaseViewHolder> {

        GoodsListAdapter(@Nullable List<QuotationDetailBean> data) {
            super(R.layout.item_agreement_price_quotation_detail, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, QuotationDetailBean item) {
            ((GlideImageView) helper.getView(R.id.img_logoUrl)).setImageURL(item.getImgUrl());
//            TextView txtProductDesc = helper.getView(R.id.txt_productDesc);
//            txtProductDesc.setVisibility(TextUtils.isEmpty(item.getProductDesc()) ? View.GONE : View.VISIBLE);
//            txtProductDesc.setText(item.getProductDesc());
//            helper.setText(R.id.txt_productName, item.getProductName())
//                .setText(R.id.txt_price, CommonUitls.formatMoney(item.getPrice()));
        }
    }
}
