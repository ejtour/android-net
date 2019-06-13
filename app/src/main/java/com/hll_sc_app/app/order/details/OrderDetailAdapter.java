package com.hll_sc_app.app.order.details;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;

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

    private boolean mWareHouse;

    OrderDetailAdapter() {
        super(R.layout.item_order_detail);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        // 代仓是否显示
        holder.setGone(R.id.iod_ware_house, mWareHouse);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean item) {
        helper.itemView.setBackgroundResource(mData.indexOf(item) % 2 == 0 ? android.R.color.white : R.color.color_f7f8fa);
        ((GlideImageView) helper.getView(R.id.iod_image)).setImageURL(item.getImgUrl());
        // 押金商品
        List<OrderDepositBean> depositList = item.getDepositList();
        helper.setGone(R.id.iod_deposit_group, !CommonUtils.isEmpty(depositList));
        ((OrderDepositList) helper.getView(R.id.iod_deposit_list)).setData(depositList);
        StringBuilder builder = new StringBuilder("发货：");
        if (item.getSubBillStatus() == 2) builder.insert(0, "预");
        if (item.getSubBillStatus() >= 2 && item.getSubBillStatus() != 7)
            builder.append(CommonUtils.formatNum(item.getAdjustmentNum())).append(item.getAdjustmentUnit());
        else builder.append("- -");
        String deliveryText = builder.toString();

        builder.delete(0, deliveryText.length()).append("签收：");
        if (item.getSubBillStatus() == 4 || item.getSubBillStatus() == 6 || item.getSubBillStatus() == 8)
            builder.append(CommonUtils.formatNum(item.getInspectionNum())).append(item.getInspectionUnit());
        else builder.append("- -");
        String confirmText = builder.toString();

        helper.setText(R.id.iod_product_name, item.getProductName())
                .setText(R.id.iod_product_spec, item.getProductSpec()) // 规格
                .setText(R.id.iod_order_num, "订货： " + CommonUtils.formatNum(item.getProductNum()) + item.getSaleUnitName()) // 订货数量
                .setText(R.id.iod_delivery_num, processNum(deliveryText, item.getAdjustmentNum() != item.getProductNum())) // 预发货/发货数量
                .setText(R.id.iod_confirm_num, processNum(confirmText, item.getInspectionNum() != item.getProductNum())) // 签收数量
                .setText(R.id.iod_sale_unit_spec, "¥" + CommonUtils.formatMoney(item.getProductPrice()) + "/" + item.getSaleUnitName()) // 单价
                .setText(R.id.iod_amount, "小计：¥" + CommonUtils.formatMoney(item.getInspectionAmount())); // 小计
    }

    /**
     * 签收数与实际数量不一致时改变文本颜色
     */
    private SpannableString processNum(String source, boolean numDiff) {
        SpannableString delivery = new SpannableString(source);
        delivery.setSpan(new ForegroundColorSpan(Color.parseColor(!source.endsWith("-") && numDiff
                        ? ColorStr.COLOR_5695D2 : ColorStr.COLOR_666666)),
                source.indexOf("：") + 1, source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return delivery;
    }

    public void setNewData(List<OrderDetailBean> data, boolean wareHouse) {
        mWareHouse = wareHouse;
        setNewData(data);
    }
}
