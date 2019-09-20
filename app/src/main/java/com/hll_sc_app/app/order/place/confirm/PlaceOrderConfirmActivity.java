package com.hll_sc_app.app.order.place.confirm;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.order.place.DiscountPlanBean;
import com.hll_sc_app.bean.order.place.SettlementInfoResp;
import com.hll_sc_app.bean.order.place.SupplierGroupBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/16
 */
@Route(path = RouterConfig.ORDER_PLACE_CONFIRM)
public class PlaceOrderConfirmActivity extends BaseLoadActivity {

    @BindView(R.id.opc_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.opc_purchaser_shop)
    TextView mPurchaserShop;
    @BindView(R.id.opc_receiver)
    TextView mReceiver;
    @BindView(R.id.opc_address)
    TextView mAddress;
    @BindView(R.id.opc_supplier_shop)
    TextView mSupplierShop;
    @BindView(R.id.opc_goods_num)
    TextView mGoodsNum;
    @BindView(R.id.opc_goods_group)
    LinearLayout mGoodsGroup;
    @BindView(R.id.opc_subtotal)
    TextView mSubtotal;
    @BindView(R.id.opc_discount)
    TextView mDiscount;
    @BindView(R.id.opc_discount_group)
    Group mDiscountGroup;
    @BindView(R.id.opc_pay_method)
    TextView mPayMethod;
    @BindView(R.id.opc_request_date)
    TextView mRequestDate;
    @BindView(R.id.opc_remark)
    TextView mRemark;
    @BindView(R.id.opc_total_amount)
    TextView mTotalAmount;
    @BindView(R.id.opc_total)
    TextView mTotal;
    @Autowired(name = "parcelable")
    SettlementInfoResp mResp;
    private SupplierGroupBean mSupplierBean;
    private int mItemSize;

    public static void start(SettlementInfoResp resp) {
        RouterUtil.goToActivity(RouterConfig.ORDER_PLACE_CONFIRM, resp);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_place_confirm);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {
        if (!CommonUtils.isEmpty(mResp.getSupplierGroupList()))
            mSupplierBean = mResp.getSupplierGroupList().get(0);
        mItemSize = (int) (((float) UIUtils.getScreenWidth(this) - UIUtils.dip2px(60)) / 5);
        ViewGroup.LayoutParams layoutParams = mGoodsNum.getLayoutParams();
        layoutParams.width = layoutParams.height = mItemSize;
        mGoodsNum.setLayoutParams(layoutParams);
        mTitleBar.setRightBtnClick(this::edit);
        mPurchaserShop.setText(mResp.getPurchaserShopName());
        mReceiver.setText(String.format("收货人：%s / %s", mResp.getReceiverName(), PhoneUtil.formatPhoneNum(mResp.getReceiverMobile())));
        mAddress.setText(String.format("地址：%s", mResp.getReceiverAddress()));
        setSupplierData();
    }

    private void setSupplierData() {
        if (mSupplierBean == null) return;
        mSupplierShop.setText(mSupplierBean.getSupplierShopName());

        mSubtotal.setText(getAmount(mSupplierBean.getTotalAmount(), mSupplierBean.getDepositAmount(), 0));

        DiscountPlanBean discountPlan = mSupplierBean.getDiscountPlan();
        double discount = discountPlan == null ? 0 : discountPlan.getProductDiscountMoney();
        mTotalAmount.setText(getAmount(CommonUtils.subDouble(mSupplierBean.getTotalAmount(), discount).doubleValue()
                , mSupplierBean.getDepositAmount(), 1));
        mTotal.setText(getAmount(CommonUtils.subDouble(mSupplierBean.getTotalAmount(), discount).doubleValue()
                , mSupplierBean.getDepositAmount(), 2));

        addProduct(mSupplierBean.getProductList());

        mGoodsNum.setText(String.format("共%s种", mSupplierBean.getProductList().size()));

        SupplierGroupBean.PaymentBean payment = mSupplierBean.getPayment();
        switch (mSupplierBean.getPayType()) {
            case 1:
                if (payment.getCashPayment() == 1)
                    mPayMethod.setText("货到付款");
                break;
            case 2:
                if (payment.getAccountPayment() == 1)
                    mPayMethod.setText("账期支付");
                break;
            case 3:
                if (payment.getOnlinePayment() == 1)
                    mPayMethod.setText("在线支付");
                break;
        }

        if (mSupplierBean.getDiscountPlan() == null) {
            mDiscountGroup.setVisibility(View.GONE);
        } else {
            mDiscountGroup.setVisibility(View.VISIBLE);
            mDiscount.setText(mSupplierBean.getDiscountPlan().getProductDiscounts().get(0).getRuleName());
        }
    }

    private SpannableString getAmount(double amount, double depositAmount, int type) {
        String source;
        int endPos;
        if (depositAmount > 0) {
            source = String.format("¥%s(含押金：¥%s)", CommonUtils.formatMoney(amount),
                    CommonUtils.formatMoney(depositAmount));
            endPos = source.indexOf("(");
        } else {
            source = String.format("¥%s", CommonUtils.formatMoney(amount));
            endPos = source.length();
        }
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(type == 2 ? 1.6f : 1.3f), type == 2 ? 1 : 0, type == 2 ? source.indexOf(".") : endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (type == 0) {
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_222222)),
                    0, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    private void addProduct(List<GoodsBean> list) {
        int count = Math.min(4, list.size());
        for (int i = 0; i < count; i++) {
            GoodsBean goodsBean = list.get(i);
            mGoodsGroup.addView(generateProduct(goodsBean.getImgUrl(),
                    goodsBean.getSpecs().get(0).getShopcartNum(), goodsBean.getDiscountRuleTypeName()),
                    mGoodsGroup.getChildCount() - 1);
        }
    }

    private FrameLayout generateProduct(String url, String count, String discountName) {
        FrameLayout frameLayout = new FrameLayout(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mItemSize, mItemSize);
        layoutParams.leftMargin = UIUtils.dip2px(10);
        frameLayout.setLayoutParams(layoutParams);
        GlideImageView imageView = new GlideImageView(this);
        imageView.setRadius(2);
        imageView.setImageURL(url);
        frameLayout.addView(imageView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        TextView textView = new TextView(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.BOTTOM;
        textView.setPadding(UIUtils.dip2px(5), UIUtils.dip2px(1), UIUtils.dip2px(5), UIUtils.dip2px(1));
        textView.setBackgroundResource(R.drawable.bg_thumbnail_text);
        textView.setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        textView.setTextColor(Color.WHITE);
        textView.setTextSize(12);
        textView.setText(String.format("x%s", CommonUtils.formatNum(CommonUtils.getDouble(count))));
        frameLayout.addView(textView, params);
        if (!TextUtils.isEmpty(discountName)) {
            TextView discount = new TextView(this);
            discount.setBackgroundResource(R.drawable.ic_promotion_flag);
            discount.setGravity(Gravity.CENTER_HORIZONTAL);
            discount.setPadding(UIUtils.dip2px(1), 0, UIUtils.dip2px(1), 0);
            discount.setTextColor(Color.WHITE);
            discount.setTextSize(10);
            discount.setText(discountName);
            FrameLayout.LayoutParams discountParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            discountParams.leftMargin = UIUtils.dip2px(5);
            frameLayout.addView(discount, discountParams);
        }
        return frameLayout;
    }

    @OnClick(R.id.opc_commit)
    public void commit() {

    }

    @OnClick(R.id.opc_edit)
    public void edit(View view) {
        finish();
    }

    @OnClick(R.id.opc_discount)
    public void selectDiscount() {
    }

    @OnClick(R.id.opc_pay_method)
    public void selectPayMethod() {

    }

    @OnClick(R.id.opc_request_date)
    public void selectRequestDate() {
    }

    @OnClick(R.id.opc_remark)
    public void editRemark() {
    }
}
