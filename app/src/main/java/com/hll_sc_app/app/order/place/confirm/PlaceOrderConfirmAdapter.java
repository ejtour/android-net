package com.hll_sc_app.app.order.place.confirm;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.place.DiscountPlanBean;
import com.hll_sc_app.bean.order.place.ProductBean;
import com.hll_sc_app.bean.order.place.SupplierGroupBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/2/10
 */

public class PlaceOrderConfirmAdapter extends BaseQuickAdapter<SupplierGroupBean, BaseViewHolder> {
    private static String START_FORMAT = "MM月dd日 HH:mm";
    private int mItemSize;

    PlaceOrderConfirmAdapter(@Nullable List<SupplierGroupBean> data, int itemSize) {
        super(R.layout.item_order_place_confirm, data);
        mItemSize = itemSize;
        if (!CommonUtils.isEmpty(data)) {
            for (SupplierGroupBean item : data) {
                item.initExecuteDate();
                item.initDiscount();
                item.initPayType();
            }
        }
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        View goodsNum = helper.getView(R.id.opc_goods_num);
        ViewGroup.LayoutParams params = goodsNum.getLayoutParams();
        params.width = params.height = mItemSize;
        helper.addOnClickListener(R.id.opc_goods_num)
                .addOnClickListener(R.id.opc_discount)
                .addOnClickListener(R.id.opc_pay_method)
                .addOnClickListener(R.id.opc_request_date)
                .addOnClickListener(R.id.opc_remark);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, SupplierGroupBean item) {
        DiscountPlanBean discountPlan = item.getDiscountPlan();
        List<ProductBean> list = item.getProductList();
        boolean lift = item.getDeliverType() == 2;
        helper.setText(R.id.opc_supplier_shop, item.getSupplierShopName())
                .setText(R.id.opc_subtotal, ConfirmHelper.getAmount(helper.itemView.getContext(), item.getTotalAmount(),
                        item.getDepositAmount(), 0))
                .setText(R.id.opc_goods_num, String.format("共%s种", list.size()))
                .setGone(R.id.opc_warehouse, 1 == item.getWareHourseStatus())
                .setGone(R.id.opc_total_amount_group, 0 == item.getWareHourseStatus())
                .setGone(R.id.opc_self_lift_tag, lift)
                .setGone(R.id.opc_lift_address_group, lift)
                .setText(R.id.opc_request_date_label, lift ? "要求提货日期" : "要求到货日期")
                .setGone(R.id.opc_discount_group, discountPlan != null && !CommonUtils.isEmpty(discountPlan.getShopDiscounts()))
                .setText(R.id.opc_remark, item.getRemark())
                .setText(R.id.opc_pay_method, getPayTypeLabel(item.getPayType(), item.getPayment()))
        ;
        if (lift) {
            helper.setText(R.id.opc_lift_address, item.getHouseAddress());
        }

        TextView requestDate = helper.getView(R.id.opc_request_date);
        requestDate.setHint(lift ? "请选择要求提货日期" : "请选择要求到货日期");
        if ("0".equals(item.getStartDate()) && "0".equals(item.getEndDate())) {
            requestDate.setText("按照供应商时间配送");
            requestDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0);
            requestDate.setClickable(false);
        } else {
            requestDate.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_gray, 0);
            requestDate.setClickable(true);
            requestDate.setText(String.format("%s - %s", DateUtil.getReadableTime(item.getStartDate(), START_FORMAT),
                    DateUtil.getReadableTime(item.getEndDate(), Constants.SIGNED_HH_MM)));
        }

        LinearLayout goodsGroup = helper.getView(R.id.opc_goods_group);
        if (goodsGroup.getChildCount() != 1) {
            View child = goodsGroup.getChildAt(goodsGroup.getChildCount() - 1);
            goodsGroup.removeAllViews();
            goodsGroup.addView(child);
        }
        int count = Math.min(4, list.size());
        for (int i = 0; i < count; i++) {
            ProductBean goodsBean = list.get(i);
            goodsGroup.addView(generateProduct(helper.itemView.getContext(), goodsBean.getImgUrl(),
                    goodsBean.getSpecs().get(0).getShopcartNum(), goodsBean.getDiscountRuleTypeName()),
                    goodsGroup.getChildCount() - 1);
        }

        DiscountPlanBean.DiscountBean discountBean = item.getDiscountBean();
        helper.setTag(R.id.opc_discount, discountBean);
        if (discountBean != null) {
            helper.setText(R.id.opc_discount, discountBean.getRuleName());
        }
        helper.setText(R.id.opc_total_amount, ConfirmHelper.getAmount(helper.itemView.getContext(),
                item.getSubTotalAmount(), item.getDepositAmount(), 1));
    }

    private String getPayTypeLabel(int payType, SupplierGroupBean.PaymentBean payment) {
        switch (payType) {
            case 1:
                return "货到付款";
            case 2:
                return "账期支付";
            case 3:
                return "在线支付";
            default:
                return "";
        }
    }

    private FrameLayout generateProduct(Context context, String url, double count, String discountName) {
        FrameLayout frameLayout = new FrameLayout(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(mItemSize, mItemSize);
        layoutParams.leftMargin = UIUtils.dip2px(10);
        frameLayout.setLayoutParams(layoutParams);
        GlideImageView imageView = new GlideImageView(context);
        imageView.setRadius(2);
        imageView.setImageURL(url);
        frameLayout.addView(imageView, FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        TextView textView = new TextView(context);
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
            TextView discount = new TextView(context);
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
}
