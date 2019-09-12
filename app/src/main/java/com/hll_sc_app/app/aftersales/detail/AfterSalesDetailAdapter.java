package com.hll_sc_app.app.aftersales.detail;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.common.AfterSalesHelper;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/4/25
 */

public class AfterSalesDetailAdapter extends BaseQuickAdapter<AfterSalesDetailsBean, BaseViewHolder> {

    /**
     * 售后订单类型
     */
    private int mRefundBillType;

    private boolean mOnlyShow;

    /**
     * 是否可以修改价格
     */
    private boolean mCanModify;

    private RelevanceCallback mCallback;

    interface RelevanceCallback {
        void toRelate(AfterSalesDetailsBean bean);
    }

    AfterSalesDetailAdapter() {
        this(true);
    }

    public AfterSalesDetailAdapter(boolean onlyShow) {
        super(R.layout.item_after_sales_detail);
        mOnlyShow = onlyShow;
    }

    public void setRefundBillType(int refundBillType) {
        mRefundBillType = refundBillType;
    }

    void setCanModify(boolean canModify) {
        mCanModify = canModify;
    }

    void setCallback(RelevanceCallback callback) {
        mCallback = callback;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.asd_delete_btn)
                .setGone(R.id.asd_delete_btn, !mOnlyShow);
        if (mOnlyShow && mCanModify)
            helper.addOnClickListener(R.id.asd_change_price);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, AfterSalesDetailsBean item) {
        ((GlideImageView) helper.getView(R.id.asd_img)).setImageURL(item.getImgUrl()); // 商品图片
        String refundText = "退款：¥" + CommonUtils.formatMoney(mOnlyShow ? item.getRefundAmount() : item.getPendingRefundAmount()); // 售后金额
        helper.setText(R.id.asd_productName, item.getProductName()) // 商品名
                .setText(R.id.asd_spec_content, "规格："+item.getProductSpec()) // 商品规格
                .setText(R.id.asd_spec_price, "¥" + CommonUtils.formatMoney(
                        mOnlyShow ? item.getProductPrice() : item.getNewPrice()) +
                        "/" + (mOnlyShow ? item.getRefundUnit() : item.getStandardUnit())) // 商品单价
                .setText(R.id.asd_order_num, "订货：" + CommonUtils.formatNum(item.getProductNum()) + item.getSaleUnitName()) // 订货数量
                .setText(R.id.asd_order_delivery_num, "发货：" + CommonUtils.formatNum(item.getAdjustmentNum()) + item.getAdjustmentUnit()) // 发货数量
                .setText(R.id.asd_order_confirmed_num, "签收：" + CommonUtils.formatNum(item.getInspectionNum()) + item.getInspectionUnit()) // 签收数量
                .setText(R.id.asd_refund_amount, mOnlyShow ? processAmount(refundText) : refundText)// 退款总金额
                .setVisible(R.id.asd_order_operation_num, mRefundBillType == 3 || mRefundBillType == 4)
                // 售后操作数
                .setText(R.id.asd_order_operation_num, AfterSalesHelper.getOperatedNumPrefix(mRefundBillType)+"："
                        + CommonUtils.formatNum(item.getRefundNum()) + item.getRefundUnit())
                .setVisible(R.id.asd_order_pick_num, mOnlyShow && (mRefundBillType == 3 || mRefundBillType == 4))
                // 提货数量
                .setText(R.id.asd_order_pick_num, "提货：" + CommonUtils.formatNum(item.getDeliveryNum()) + item.getInspectionUnit())
                // 修改价格权限
                .setGone(R.id.asd_change_price_group, mOnlyShow && mCanModify && item.getHomologous() == 1)
                // 未关联商品
                .setGone(R.id.asd_not_associated_group, mOnlyShow && mCanModify && item.getHomologous() == 0);
        if (mOnlyShow && mCanModify && item.getHomologous() == 0) {
            TextView textView = helper.getView(R.id.asd_not_associated_desc);
            textView.setTag(item);
            textView.setText(getClickText());
            textView.setMovementMethod(LinkMovementMethod.getInstance());
        }
    }

    private CharSequence getClickText() {
        String source = "该品项尚未关联二十二城商品，请点击关联商品操作";
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                if (mCallback != null) mCallback.toRelate((AfterSalesDetailsBean) widget.getTag());
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.parseColor(ColorStr.COLOR_5695D2));
            }
        }, source.indexOf("关联商品"), source.indexOf("关联商品") + 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }

    private SpannableString processAmount(String source) {
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(
                        Color.parseColor(ColorStr.COLOR_ED5655)),
                source.indexOf("¥"),
                source.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
