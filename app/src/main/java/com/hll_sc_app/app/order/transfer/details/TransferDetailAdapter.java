package com.hll_sc_app.app.order.transfer.details;

import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.ViewGroup;

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
        helper.itemView.setBackgroundResource(mData.indexOf(item) % 2 == 0 ? android.R.color.white : R.color.color_f7f8fa);
        ((GlideImageView) helper.getView(R.id.itd_image)).setImageURL(item.getImgUrl());
        helper.setText(R.id.itd_product_name, item.getProductName())
                .setText(R.id.itd_order_num, "订货： " + CommonUtils.formatNum(item.getGoodsNum()) + item.getSaleUnitName()) // 订货数量
                .setText(R.id.itd_sale_unit_spec, processPrice("单价： ¥" + CommonUtils.formatMoney(item.getGoodsPrice()) + "/" + item.getSaleUnitName())) // 单价
                .setText(R.id.itd_amount, processPrice("小计： ¥" + CommonUtils.formatMoney(item.getTotalAmount()))) // 小计
                .setGone(R.id.itd_tag, item.getIsRelated() == 1 && (item.getHomologous() == 0 || !TextUtils.isEmpty(item.getFailReason())))
                .setText(R.id.itd_tag, !TextUtils.isEmpty(item.getFailReason()) ? item.getFailReason() : "未关联");
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
