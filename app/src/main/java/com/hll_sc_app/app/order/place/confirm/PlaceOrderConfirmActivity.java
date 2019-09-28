package com.hll_sc_app.app.order.place.confirm;

import android.content.Intent;
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
import com.hll_sc_app.app.order.place.commit.PlaceOrderCommitActivity;
import com.hll_sc_app.app.order.place.confirm.details.PlaceOrderDetailsActivity;
import com.hll_sc_app.app.order.place.confirm.remark.OrderConfirmRemarkActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.order.place.DiscountPlanBean;
import com.hll_sc_app.bean.order.place.ExecuteDateBean;
import com.hll_sc_app.bean.order.place.OrderCommitReq;
import com.hll_sc_app.bean.order.place.ProductBean;
import com.hll_sc_app.bean.order.place.SettlementInfoResp;
import com.hll_sc_app.bean.order.place.SupplierGroupBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;
import com.hll_sc_app.widget.SingleSelectionDialog;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.order.ExecuteDateDialog;
import com.hll_sc_app.widget.order.ShopDiscountDialog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/16
 */
@Route(path = RouterConfig.ORDER_PLACE_CONFIRM)
public class PlaceOrderConfirmActivity extends BaseLoadActivity implements IPlaceOrderConfirmContract.IPlaceOrderConfirmView {
    private static String START_FORMAT = "MM月dd日 HH:mm";
    private static String LOCALE_DATE = "MM月dd日";
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
    @BindView(R.id.opc_self_lift_tag)
    TextView mSelfLiftTag;
    @BindView(R.id.opc_request_date_label)
    TextView mRequestDateLabel;
    @BindView(R.id.opc_lift_address)
    TextView mLiftAddress;
    @BindView(R.id.opc_lift_address_group)
    Group mLiftAddressGroup;
    private OrderCommitReq mConfirmReq = new OrderCommitReq();
    private SupplierGroupBean mSupplierBean;
    private int mItemSize;
    private ShopDiscountDialog mDiscountDialog;
    private SingleSelectionDialog mPayMethodDialog;
    private ExecuteDateDialog mDateDialog;
    private IPlaceOrderConfirmContract.IPlaceOrderConfirmPresenter mPresenter;
    private List<String> mDayList = new ArrayList<>();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OrderConfirmRemarkActivity.REQ_CODE && resultCode == RESULT_OK && data != null) {
            updateRemark(data.getStringExtra("remark"));
        }
    }

    private void initData() {
        mConfirmReq.setShopCartKey(mResp.getShopCartKey());
        mConfirmReq.setIsFromShopcart(3);
        mConfirmReq.setPurchaserID(mResp.getPurchaserID());
        mConfirmReq.setPurchaserShopID(mResp.getPurchaserShopID());
        setSupplierData();
        mPresenter = PlaceOrderConfirmPresenter.newInstance();
        mPresenter.register(this);
    }

    private void initView() {
        if (!CommonUtils.isEmpty(mResp.getSupplierGroupList()))
            mSupplierBean = mResp.getSupplierGroupList().get(0);
        mItemSize = (int) (((float) UIUtils.getScreenWidth(this) - UIUtils.dip2px(60)) / 5);
        ViewGroup.LayoutParams layoutParams = mGoodsNum.getLayoutParams();
        layoutParams.width = layoutParams.height = mItemSize;
        mGoodsNum.setLayoutParams(layoutParams);
        mTitleBar.setRightBtnClick(v -> RouterUtil.goToActivity(RouterConfig.ROOT_HOME));
        mPurchaserShop.setText(mResp.getPurchaserShopName());
        mReceiver.setText(String.format("收货人：%s / %s", mResp.getReceiverName(), PhoneUtil.formatPhoneNum(mResp.getReceiverMobile())));
        mAddress.setText(String.format("地址：%s", mResp.getReceiverAddress()));
    }

    private void setSupplierData() {
        if (mSupplierBean == null) return;
        mSupplierShop.setText(mSupplierBean.getSupplierShopName());

        mSubtotal.setText(getAmount(mSupplierBean.getTotalAmount(), mSupplierBean.getDepositAmount(), 0));

        DiscountPlanBean discountPlan = mSupplierBean.getDiscountPlan();

        addProduct(mSupplierBean.getProductList());

        mGoodsNum.setText(String.format("共%s种", mSupplierBean.getProductList().size()));

        if (mSupplierBean.getDeliverType() == 2) {
            mSelfLiftTag.setVisibility(View.VISIBLE);
            mLiftAddressGroup.setVisibility(View.VISIBLE);
            mRequestDateLabel.setText("要求提货日期");
            mRequestDate.setHint("请选择要求提货日期");
            mLiftAddress.setText(mSupplierBean.getHouseAddress());
        } else {
            mSelfLiftTag.setVisibility(View.GONE);
            mLiftAddressGroup.setVisibility(View.GONE);
            mRequestDateLabel.setText("要求到货日期");
            mRequestDate.setHint("请选择要求到货日期");
        }

        initDiscount(discountPlan);
        updatePayMethod(mSupplierBean.getPayType(), null);
        if (mSupplierBean.getExecuteDateList() == null)
            updateExecuteDate("0", "0");
        else updateExecuteDate(null, null);
        updateRemark(null);
    }

    private void initDiscount(DiscountPlanBean discountPlan) {
        DiscountPlanBean.DiscountBean discountBean = null;
        if (discountPlan == null || CommonUtils.isEmpty(discountPlan.getShopDiscounts())) {
            mDiscountGroup.setVisibility(View.GONE);
        } else {
            mDiscountGroup.setVisibility(View.VISIBLE);
            List<DiscountPlanBean.DiscountBean> discounts = discountPlan.getShopDiscounts();
            if (discountPlan.getUseDiscountType() == DiscountPlanBean.DISCOUNT_PRODUCT) {
                for (DiscountPlanBean.DiscountBean bean : discounts) {
                    if (bean.getRuleType() == -1) {
                        discountBean = bean;
                        break;
                    }
                }
            } else if (discountPlan.getUseDiscountType() == DiscountPlanBean.DISCOUNT_NONE) {
                for (DiscountPlanBean.DiscountBean bean : discounts) {
                    if (bean.getRuleType() == 0) {
                        discountBean = bean;
                    }
                }
            } else discountBean = discounts.get(1);
        }
        updateDiscount(discountBean);
    }

    private void updatePayMethod(int payType, String payMethod) {
        OrderCommitReq.PayBean bean;
        if (!CommonUtils.isEmpty(mConfirmReq.getPayList())) {
            bean = mConfirmReq.getPayList().get(0);
        } else {
            bean = new OrderCommitReq.PayBean();
            mConfirmReq.setPayList(Collections.singletonList(bean));
            bean.setSupplierID(mSupplierBean.getSupplierID());
            bean.setWareHourseStatus(mSupplierBean.getWareHourseStatus());
        }
        bean.setPayType(payType);
        if (TextUtils.isEmpty(payMethod)) {
            SupplierGroupBean.PaymentBean payment = mSupplierBean.getPayment();
            switch (bean.getPayType()) {
                case 1:
                    if (payment.getCashPayment() == 1) mPayMethod.setText("货到付款");
                    break;
                case 2:
                    if (payment.getAccountPayment() == 1) mPayMethod.setText("账期支付");
                    break;
                case 3:
                    if (payment.getOnlinePayment() == 1) mPayMethod.setText("在线支付");
                    break;
            }
        } else mPayMethod.setText(payMethod);
    }

    private void updateDiscount(DiscountPlanBean.DiscountBean discountBean) {
        double discount = 0;
        if (discountBean != null) {
            mDiscount.setTag(discountBean);
            discount = discountBean.getDiscountValue();
            mDiscount.setText(discountBean.getRuleName());
            if (discountBean.getRuleType() == 0) { // 不使用店铺优惠
                mConfirmReq.setDiscountList(null);
            } else {
                OrderCommitReq.DiscountBean bean;
                if (!CommonUtils.isEmpty(mConfirmReq.getDiscountList())) {
                    bean = mConfirmReq.getDiscountList().get(0);
                } else {
                    bean = new OrderCommitReq.DiscountBean();
                    mConfirmReq.setDiscountList(Collections.singletonList(bean));
                    bean.setGroupID(mSupplierBean.getSupplierID());
                }
                bean.setDiscountAmount(discountBean.getDiscountValue());
                bean.setDiscountID(discountBean.getDiscountID());
                bean.setRuleID(discountBean.getRuleID());
                bean.setSpecList(discountBean.getSpecList());
            }
        }
        mTotalAmount.setText(getAmount(CommonUtils.subDouble(mSupplierBean.getTotalAmount(), discount).doubleValue()
                , mSupplierBean.getDepositAmount(), 1));
        mTotal.setText(getAmount(CommonUtils.subDouble(mSupplierBean.getTotalAmount(), discount).doubleValue()
                , mSupplierBean.getDepositAmount(), 2));
    }

    private void updateExecuteDate(String startDate, String endDate) {
        OrderCommitReq.ExecuteDateBean bean;
        if (!CommonUtils.isEmpty(mConfirmReq.getExecuteDateDtoList())) {
            bean = mConfirmReq.getExecuteDateDtoList().get(0);
        } else {
            bean = new OrderCommitReq.ExecuteDateBean();
            mConfirmReq.setExecuteDateDtoList(Collections.singletonList(bean));
            bean.setIsWareHouse(mSupplierBean.getWareHourseStatus());
            bean.setSupplierID(mSupplierBean.getSupplierID());
        }
        bean.setSubBillExecuteDate(startDate);
        bean.setSubBillExecuteEndDate(endDate);
        if (!TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(endDate)) {
            if ("0".equals(startDate) && "0".equals(endDate)) {
                mRequestDate.setText("按照供应商时间配送");
                mRequestDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
                mRequestDate.setClickable(false);
            } else {
                mRequestDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_gray, 0);
                mRequestDate.setClickable(true);
                mRequestDate.setText(String.format("%s - %s", DateUtil.getReadableTime(startDate, START_FORMAT),
                        DateUtil.getReadableTime(endDate, Constants.SIGNED_HH_MM)));
            }
        } else {
            mRequestDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_gray, 0);
            mRequestDate.setClickable(true);
        }
    }

    private void updateRemark(String remark) {
        mRemark.setText(remark);
        if (TextUtils.isEmpty(remark)) {
            mConfirmReq.setRemarkDtoList(null);
            return;
        }
        OrderCommitReq.RemarkBean bean;
        if (!CommonUtils.isEmpty(mConfirmReq.getRemarkDtoList())) {
            bean = mConfirmReq.getRemarkDtoList().get(0);
        } else {
            bean = new OrderCommitReq.RemarkBean();
            mConfirmReq.setRemarkDtoList(Collections.singletonList(bean));
            bean.setIsWareHouse(mSupplierBean.getWareHourseStatus());
            bean.setSupplyShopID(mSupplierBean.getSupplierShopID());
            bean.setPurchaserShopID(mResp.getPurchaserShopID());
        }
        bean.setRemark(remark);

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

    private void addProduct(List<ProductBean> list) {
        int count = Math.min(4, list.size());
        for (int i = 0; i < count; i++) {
            ProductBean goodsBean = list.get(i);
            mGoodsGroup.addView(generateProduct(goodsBean.getImgUrl(),
                    goodsBean.getSpecs().get(0).getShopcartNum(), goodsBean.getDiscountRuleTypeName()),
                    mGoodsGroup.getChildCount() - 1);
        }
    }

    private FrameLayout generateProduct(String url, double count, String discountName) {
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
        textView.setText(String.format("x%s", CommonUtils.formatNum(count)));
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
        if (TextUtils.isEmpty(mPayMethod.getText())) {
            showToast(mPayMethod.getHint().toString());
            return;
        }
        if (TextUtils.isEmpty(mRequestDate.getText())) {
            showToast(mRequestDate.getHint().toString());
            return;
        }
        mPresenter.commitOrder(mConfirmReq);
    }

    @OnClick(R.id.opc_edit)
    public void edit(View view) {
        finish();
    }

    @OnClick(R.id.opc_goods_num)
    public void produceList() {
        if (mSupplierBean != null)
            PlaceOrderDetailsActivity.start(mSupplierBean);
    }

    @OnClick(R.id.opc_discount)
    public void showDiscountDialog(View view) {
        if (mDiscountDialog == null) {
            mDiscountDialog = new ShopDiscountDialog(this)
                    .refreshList(mSupplierBean.getDiscountPlan().getShopDiscounts())
                    .select((DiscountPlanBean.DiscountBean) view.getTag())
                    .setOnItemClickListener((adapter, view1, position) -> {
                        mDiscountDialog.dismiss();
                        DiscountPlanBean.DiscountBean item = (DiscountPlanBean.DiscountBean) adapter.getItem(position);
                        if (item == null) return;
                        mDiscountDialog.select(item);
                        updateDiscount(item);
                    });
        }
        mDiscountDialog.show();
    }

    @OnClick(R.id.opc_pay_method)
    public void selectPayMethod() {
        SupplierGroupBean.PaymentBean payment = mSupplierBean.getPayment();
        if (payment == null) {
            showToast("没有可用的支付方式");
            return;
        }
        if (mPayMethodDialog == null) {
            List<NameValue> list = new ArrayList<>();
            NameValue select = null;
            if (payment.getAccountPayment() == 1) list.add(new NameValue("账期支付", "2"));
            if (payment.getCashPayment() == 1) list.add(new NameValue("货到付款", "1"));
            if (payment.getOnlinePayment() == 1) list.add(new NameValue("在线支付", "3"));
            if (list.size() == 0) {
                showToast("没有可用的支付方式");
                return;
            }
            for (NameValue value : list) {
                if (value.getValue().equals(String.valueOf(mSupplierBean.getPayType()))) {
                    select = value;
                    break;
                }
            }
            mPayMethodDialog = SingleSelectionDialog.newBuilder(this, NameValue::getName)
                    .refreshList(list)
                    .setTitleText("选择支付方式")
                    .select(select)
                    .setOnSelectListener(nameValue -> updatePayMethod(Integer.parseInt(nameValue.getValue()), nameValue.getName()))
                    .create();
        }
        mPayMethodDialog.show();
    }

    @OnClick(R.id.opc_request_date)
    public void showDateDialog() {
        if (mDateDialog == null) {
            Map<String, List<String>> map = getExecuteList();
            if (map.isEmpty()) {
                return;
            }
            mDateDialog = new ExecuteDateDialog(this,mRequestDate.getHint().toString(), map);
            mDateDialog.setDayTimeCallback((day, time) -> {
                String dayStr = mDayList.get(day);
                ExecuteDateBean.FirstDay firstDay = mSupplierBean.getExecuteDateList().getFirstDay();
                ExecuteDateBean.TimeBean timeBean;
                if (firstDay != null && dayStr.equals(firstDay.getDate())) {
                    timeBean = firstDay.getFirstTimeList().get(time);
                } else {
                    timeBean = mSupplierBean.getExecuteDateList().getTimeList().get(time);
                }
                updateExecuteDate(dayStr + timeBean.getArrivalStartTime().replace(":", ""),
                        dayStr + timeBean.getArrivalEndTime().replace(":", ""));
            });
        }
        mDateDialog.show();
    }

    private Map<String, List<String>> getExecuteList() {
        Map<String, List<String>> map = new LinkedHashMap<>();
        mDayList.clear();
        if (mSupplierBean.getExecuteDateList() != null) {
            ExecuteDateBean.FirstDay firstDayBean = mSupplierBean.getExecuteDateList().getFirstDay();
            if (firstDayBean != null) {
                List<String> listFirstTime = new ArrayList<>();
                if (!CommonUtils.isEmpty(firstDayBean.getFirstTimeList())) {
                    for (ExecuteDateBean.TimeBean firstTimeListBean : firstDayBean.getFirstTimeList()) {
                        listFirstTime.add(firstTimeListBean.getArrivalStartTime() + " - " + firstTimeListBean.getArrivalEndTime());
                    }
                }
                if (!TextUtils.isEmpty(firstDayBean.getDate()) && !CommonUtils.isEmpty(listFirstTime)) {
                    mDayList.add(firstDayBean.getDate());
                    map.put(DateUtil.getReadableTime(firstDayBean.getDate(), LOCALE_DATE), listFirstTime);
                }
            }

            List<String> listTime = new ArrayList<>();
            if (!CommonUtils.isEmpty(mSupplierBean.getExecuteDateList().getTimeList())) {
                for (ExecuteDateBean.TimeBean timeListBean : mSupplierBean.getExecuteDateList().getTimeList()) {
                    listTime.add(timeListBean.getArrivalStartTime() + " - " + timeListBean.getArrivalEndTime());
                }
            }
            if (CommonUtils.isEmpty(listTime)) return map;
            if (!TextUtils.isEmpty(mSupplierBean.getExecuteDateList().getTimes())) {
                String[] timeStr = mSupplierBean.getExecuteDateList().getTimes().split(",");
                for (String s : timeStr) {
                    if (!TextUtils.isEmpty(s) && !CommonUtils.isEmpty(listTime)) {
                        mDayList.add(s);
                        map.put(DateUtil.getReadableTime(s, LOCALE_DATE), listTime);
                    }
                }
            }
        }
        return map;
    }

    @OnClick(R.id.opc_remark)
    public void editRemark() {
        OrderConfirmRemarkActivity.start(this, mSupplierBean.getSupplierShopName(), mRemark.getText().toString());
    }

    @Override
    public void commitSuccess(String masterBillIDs) {
        PlaceOrderCommitActivity.start(masterBillIDs);
    }
}
