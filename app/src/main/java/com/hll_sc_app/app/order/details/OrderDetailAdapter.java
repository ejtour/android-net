package com.hll_sc_app.app.order.details;

import android.graphics.Color;
import android.graphics.Paint;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.detail.OrderDepositBean;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.widget.order.OrderDepositList;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/12
 */

public class OrderDetailAdapter extends BaseQuickAdapter<OrderDetailBean, BaseViewHolder> {
    private static final String DETAIL_TEXT = "小计";
    public static final String REJECT_TEXT = "收款";

    private int mWarehouseStatus;
    private String mLabel;

    OrderDetailAdapter() {
        this(null, DETAIL_TEXT);
    }

    public OrderDetailAdapter(List<OrderDetailBean> data, String label) {
        super(R.layout.item_order_detail, data);
        mLabel = label;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        // 代仓是否显示
        holder.setGone(R.id.iod_ware_house, mWarehouseStatus > 0);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean item) {
        helper.itemView.setBackgroundResource(!isDetailList() || mData.indexOf(item) % 2 == 0
                ? android.R.color.white : R.color.color_f7f8fa);
        ((GlideImageView) helper.getView(R.id.iod_image)).setImageURL(item.getImgUrl());
        // 押金商品
        List<OrderDepositBean> depositList = item.getDepositList();
        helper.setGone(R.id.iod_deposit_group, !CommonUtils.isEmpty(depositList));
        ((OrderDepositList) helper.getView(R.id.iod_deposit_list)).setData(depositList);
        StringBuilder builder = new StringBuilder("发货：");
        if (item.getSubBillStatus() == 2) builder.insert(0, "预");
        if (item.getSubBillStatus() >= 2 && item.getSubBillStatus() != 7)
            builder.append(CommonUtils.formatNum(item.getAdjustmentNum())).append(item.getAdjustmentUnit());
        else builder.append("— —");
        String deliveryText = builder.toString();

        builder.delete(0, deliveryText.length()).append("签收：");
        if (!isDetailList() || item.getSubBillStatus() == 4 || item.getSubBillStatus() == 6 || item.getSubBillStatus() == 8)
            builder.append(CommonUtils.formatNum(item.getInspectionNum())).append(item.getInspectionUnit());
        else builder.append("— —");
        String confirmText = builder.toString();

        builder.delete(0, confirmText.length()).append("单价：¥").append(CommonUtils.formatMoney(item.getInspectionPrice()));
        if (!TextUtils.isEmpty(item.getInspectionUnit()))
            builder.append("/").append(item.getInspectionUnit());
        String unitPrice = builder.toString();

        helper.setText(R.id.iod_product_name, item.getProductName())
                .setText(R.id.iod_ware_house, mWarehouseStatus == 1 ? "代仓" : mWarehouseStatus == 2 ? "代配" : "")
                .setText(R.id.iod_product_spec, item.getProductSpec()) // 规格
                .setText(R.id.iod_order_num, processNum("订货： " + CommonUtils.formatNum(item.getProductNum()) + item.getSaleUnitName(), false)) // 订货数量
                .setText(R.id.iod_delivery_num, processNum(deliveryText, item.getAdjustmentNum() != item.getProductNum())) // 预发货/发货数量
                .setText(R.id.iod_confirm_num, processNum(confirmText, item.getInspectionNum() != item.getProductNum())) // 签收数量
                .setText(R.id.iod_sale_unit_spec, processPrice(unitPrice)) // 单价
                .setGone(R.id.iod_remark, !TextUtils.isEmpty(item.getDetailRemark()))
                .setText(R.id.iod_remark, "备注：" + item.getDetailRemark()) // 商品备注
                .setText(R.id.iod_amount, processPrice(mLabel + "：¥" + CommonUtils.formatMoney(isDetailList()
                        ? item.getInspectionAmount() : 0))); // 小计，拒收金额显示 0

        //显示单价的旧价格
        helper.setVisible(R.id.iod_sale_unit_spec_old, item.getShowOldPrice() == 1)
                .setText(R.id.iod_sale_unit_spec_old, "¥" + CommonUtils.formatMoney(item.getOldProductPrice()) + "/" + item.getSaleUnitName());
        TextView textView = helper.getView(R.id.iod_sale_unit_spec_old);
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//中划线
    }

    private SpannableString processPrice(String source) {
        SpannableString price = new SpannableString(source);
        price.setSpan(new ForegroundColorSpan(Color.parseColor(ColorStr.COLOR_999999)),
                0, source.indexOf("¥"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return price;
    }

    /**
     * 数目与订货数不一致时强调
     */
    private CharSequence processNum(String source, boolean numDiff) {
        if (source.endsWith("—") || !isDetailList()) {
            return source;
        }
        SpannableString delivery = new SpannableString(source);
        delivery.setSpan(new ForegroundColorSpan(Color.parseColor(numDiff
                        ? ColorStr.COLOR_5695D2 : ColorStr.COLOR_666666)),
                source.indexOf("：") + 1, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return delivery;
    }

    private boolean isDetailList() {
        return DETAIL_TEXT.equals(mLabel);
    }

    public void setNewData(List<OrderDetailBean> data, int warehouseStatus) {
        mWarehouseStatus = warehouseStatus;
        setNewData(data);
    }
}
