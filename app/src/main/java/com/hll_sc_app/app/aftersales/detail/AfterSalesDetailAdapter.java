package com.hll_sc_app.app.aftersales.detail;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aftersales.common.AfterSalesHelper;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.aftersales.AfterSalesDetailsBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/4/25
 */

public class AfterSalesDetailAdapter extends BaseQuickAdapter<AfterSalesDetailsBean, BaseViewHolder> {

    /**
     * 售后订单类型
     */
    private int mRefundBillType;

    /**
     * 是否可以修改价格
     */
    private boolean mCanModify;

    public AfterSalesDetailAdapter(@Nullable List<AfterSalesDetailsBean> data) {
        super(R.layout.item_after_sales_detail, data);
    }

    public void setRefundBillType(int refundBillType) {
        mRefundBillType = refundBillType;
    }

    public void setCanModify(boolean canModify) {
        mCanModify = canModify;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        if (mCanModify) helper.addOnClickListener(R.id.asd_change_price);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, AfterSalesDetailsBean item) {
        ((GlideImageView) helper.getView(R.id.asd_img)).setImageURL(item.getImgUrl()); // 商品图片
        String refundText = "退款：¥" + CommonUtils.formatMoney(item.getRefundAmount()); // 售后金额
        helper.setText(R.id.asd_productName, item.getProductName()) // 商品名
                .setText(R.id.asd_spec_content, item.getProductSpec()) // 商品规格
                .setText(R.id.asd_spec_price, "¥" + CommonUtils.formatMoney(item.getProductPrice()) + "/" + item.getRefundUnit()) // 商品单价
                .setText(R.id.asd_order_num, "订货：" + CommonUtils.formatNum(item.getProductNum()) + item.getSaleUnitName()) // 订货数量
                .setText(R.id.asd_order_delivery_num, "发货：" + CommonUtils.formatNum(item.getAdjustmentNum()) + item.getAdjustmentUnit()) // 发货数量
                .setText(R.id.asd_order_confirmed_num, "签收：" + CommonUtils.formatNum(item.getInspectionNum()) + item.getInspectionUnit()) // 签收数量
                .setGone(R.id.asd_refund_amount, mRefundBillType != 5) // 非换货时，显示退款总金额
                .setText(R.id.asd_refund_amount, processAmount(refundText))// 退款总金额
                // 售后操作数
                .setText(R.id.asd_order_operation_num, AfterSalesHelper.getOperatedNumPrefix(mRefundBillType)
                        + CommonUtils.formatNum(item.getRefundNum()) + item.getInspectionUnit())
                // 提货数量
                .setText(R.id.asd_order_pick_num, "提货：" + CommonUtils.formatNum(item.getDeliveryNum()) + item.getInspectionUnit())
                // 修改价格权限
                .setGone(R.id.asd_change_price_group, mCanModify && item.getHomologous() == 1)
                // 未关联商品
                .setGone(R.id.asd_not_associated_group, item.getHomologous() == 0);
    }

    private SpannableString processAmount(String source) {
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new ForegroundColorSpan(
                        Color.parseColor(ColorStr.COLOR_F33030)),
                source.indexOf("¥"),
                source.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
