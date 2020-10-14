package com.hll_sc_app.app.order.transfer.details;

import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.detail.TransferDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/3
 */

public class TransferDetailAdapter extends BaseQuickAdapter<TransferDetailBean, BaseViewHolder> {

    private boolean mWareHouse;

    TransferDetailAdapter() {
        super(R.layout.item_transfer_detail, null);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        // 代仓是否显示
        holder.setGone(R.id.itd_ware_house, mWareHouse);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, TransferDetailBean item) {
        StringBuilder builder = new StringBuilder("单价：¥");
        builder.append(CommonUtils.formatMoney(item.getGoodsPrice()));
        if (!TextUtils.isEmpty(item.getOrderUnit()))
            builder.append("/").append(item.getOrderUnit());
        String unitPrice = builder.toString();
        ((GlideImageView) helper.getView(R.id.itd_image)).setImageURL(item.getImgUrl());
        helper.setText(R.id.itd_product_name, TextUtils.isEmpty(item.getProductName()) ? item.getGoodsName() : item.getProductName())
                .setText(R.id.itd_sale_unit_spec, processPrice(unitPrice)) // 单价
                .setText(R.id.itd_amount, processPrice("小计： ¥" + CommonUtils.formatMoney(item.getTotalAmount()))) // 小计
                .setGone(R.id.itd_remark, !TextUtils.isEmpty(item.getDetailRemark()))
                .setText(R.id.itd_remark, "备注：" + item.getDetailRemark()) // 商品备注
                .setGone(R.id.itd_tag, item.getIsRelated() == 1 && (item.getHomologous() == 0 || !TextUtils.isEmpty(item.getFailReason())))
                .setText(R.id.itd_tag, !TextUtils.isEmpty(item.getFailReason()) ? item.getFailReason() : "未关联");

        // 订货数量
        SpannableString num = new SpannableString(CommonUtils.formatNum(item.getGoodsNum()));
        num.setSpan(new StyleSpan(Typeface.BOLD), 0, num.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        TextView orderNum = helper.getView(R.id.itd_order_num);
        orderNum.setText(num);
        orderNum.append(item.getOrderUnit());
    }

    private SpannableString processPrice(String source) {
        SpannableString price = new SpannableString(source);
        price.setSpan(new ForegroundColorSpan(Color.parseColor(ColorStr.COLOR_ED5655)),
                source.indexOf("¥"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return price;
    }

    public void setNewData(List<TransferDetailBean> data, boolean wareHouse) {
        mWareHouse = wareHouse;
        setNewData(data);
    }
}
