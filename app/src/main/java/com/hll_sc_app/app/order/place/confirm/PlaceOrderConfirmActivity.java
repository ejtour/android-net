package com.hll_sc_app.app.order.place.confirm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
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
        mItemSize = (int) (((float) UIUtils.getScreenWidth(this) - UIUtils.dip2px(60)) / 5);
        ViewGroup.LayoutParams layoutParams = mGoodsNum.getLayoutParams();
        layoutParams.width = layoutParams.height = mItemSize;
        mGoodsNum.setLayoutParams(layoutParams);
        mTitleBar.setRightBtnClick(this::edit);
        mPurchaserShop.setText(mResp.getPurchaserShopName());
        mReceiver.setText(String.format("收货人：%s / %s", mResp.getReceiverName(), PhoneUtil.formatPhoneNum(mResp.getReceiverMobile())));
        mAddress.setText(String.format("地址：%s", mResp.getReceiverAddress()));
        if (!CommonUtils.isEmpty(mResp.getSupplierGroupList())) {
            SupplierGroupBean supplierInfo = mResp.getSupplierGroupList().get(0);
            mSupplierShop.setText(supplierInfo.getSupplierShopName());

            mSubtotal.setText(getAmount(supplierInfo.getTotalAmount(), supplierInfo.getDepositAmount(), true));

            DiscountPlanBean discountPlan = supplierInfo.getDiscountPlan();
            double discount = discountPlan == null ? 0 : discountPlan.getProductDiscountMoney();
            SpannableString amount = getAmount(CommonUtils.subDouble(supplierInfo.getTotalAmount(), discount).doubleValue()
                    , supplierInfo.getDepositAmount(), false);
            mTotal.setText(amount);
            mTotalAmount.setText(amount);
        }
    }

    private SpannableString getAmount(double amount, double depositAmount, boolean subtotal) {
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
        ss.setSpan(new RelativeSizeSpan(1.3f), subtotal ? 0 : 1, subtotal ? endPos : source.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (subtotal) {
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_222222)),
                    0, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }

    private void addProduct(List<GoodsBean> list) {
        int count = Math.min(4, list.size());
        for (int i = 0; i < count; i++) {

        }
    }

    private FrameLayout generateProduct(String url, String count) {
        return null;
    }

    @OnClick(R.id.opc_commit)
    public void commit() {
    }

    @OnClick(R.id.opc_edit)
    public void edit(View view) {
        finish();
    }
}
