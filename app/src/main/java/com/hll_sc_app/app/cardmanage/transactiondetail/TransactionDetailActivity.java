package com.hll_sc_app.app.cardmanage.transactiondetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.detail.AfterSalesDetailActivity;
import com.hll_sc_app.app.order.details.OrderDetailActivity;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.cardmanage.CardTransactionListResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = RouterConfig.ACTIVITY_CARD_MANAGE_TRANSACTION_LIST_DETAIL)
public class TransactionDetailActivity extends BaseActivity {
    @BindView(R.id.txt_shop_name)
    TextView mTxtShop;
    @BindView(R.id.txt_price)
    TextView mTxtPrice;
    @BindView(R.id.txt_time)
    TextView mTxtTime;
    @BindView(R.id.txt_no)
    TextView mTxtNo;
    @BindView(R.id.txt_cash)
    TextView mTxtCash;
    @BindView(R.id.txt_gift)
    TextView mTxtGift;
    @BindView(R.id.txt_bz)
    TextView mTxtBZ;
    @BindView(R.id.txt_title)
    TextView mTxtTitle;
    @BindView(R.id.txt_title_no)
    TextView mTxtTitleNo;
    @BindView(R.id.txt_cash_remainder)
    TextView mTxtCashRemainder;
    @BindView(R.id.txt_gift_remainder)
    TextView mTxtGiftRemainder;

    @Autowired(name = "parcelable")
    CardTransactionListResp.CardDealDetail mDetail;
    private Unbinder unbinder;

    public static void start(CardTransactionListResp.CardDealDetail cardDealDetail) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_CARD_MANAGE_TRANSACTION_LIST_DETAIL, cardDealDetail);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_transaction_detail);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mTxtShop.setText(mDetail.getTradeType() == 1 ? "集团充值" : mDetail.getShopName());
        mTxtTitle.setText(mDetail.getStradeType());
        mTxtPrice.setText((mDetail.getTradeType() == 2 ? "-" : "+") + CommonUtils.formatMoney(mDetail.getTradeAmount()));
        mTxtTime.setText(CalendarUtils.getDateFormatString(mDetail.getTradeTime(), "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm"));
        mTxtCash.setText("¥" + CommonUtils.formatMoney(mDetail.getTradeCashAmount()));
        mTxtGift.setText("¥" + CommonUtils.formatMoney(mDetail.getTradeGiftAmount()));

        mTxtCashRemainder.setText("¥" + CommonUtils.formatMoney(mDetail.getCashBalance()));
        mTxtGiftRemainder.setText("¥" + CommonUtils.formatMoney(mDetail.getGiftBalance()));

        mTxtBZ.setText(mDetail.getRemark());

        //充值模式不显示订单号
        if (mDetail.getTradeType() == 1) {
            mTxtNo.setVisibility(View.GONE);
            mTxtTitleNo.setVisibility(View.GONE);
        } else if (mDetail.getTradeType() == 2 || mDetail.getTradeType() == 3) {
            mTxtTitleNo.setText(mDetail.getTradeType() == 2 ? "订单号" : "退款单号");
            SpannableString billNo = new SpannableString(mDetail.getTradeType() == 2 ? mDetail.getSubBillNo() : mDetail.getRefundBillNo());
            billNo.setSpan(new UnderlineSpan(), 0, billNo.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
            mTxtNo.setText(billNo);
            mTxtNo.setOnClickListener(v -> {
                if (mDetail.getTradeType() == 2) {
                    OrderDetailActivity.startByBillNo(mDetail.getSubBillNo());
                } else if (mDetail.getTradeType() == 3) {
                    AfterSalesDetailActivity.startByNo(mDetail.getRefundBillNo());
                }
            });
        }
    }
}
