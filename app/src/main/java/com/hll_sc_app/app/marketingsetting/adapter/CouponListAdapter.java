package com.hll_sc_app.app.marketingsetting.adapter;

import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.marketingsetting.CouponListResp;

import java.util.List;

/**
 * 优惠券bean
 * 0全部1未开始2,促销中3,已失效4,已作废
 * option 1修改 2作废 3 启用 4 暂停 5 发放
 */
public class CouponListAdapter extends BaseQuickAdapter<CouponListResp.CouponBean, BaseViewHolder> {
    public CouponListAdapter(@Nullable List<CouponListResp.CouponBean> data) {
        super(R.layout.list_item_coupon, data);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        holder.addOnClickListener(R.id.txt_invalid);
        holder.addOnClickListener(R.id.txt_pause);
        holder.addOnClickListener(R.id.txt_send);
        holder.addOnClickListener(R.id.txt_start);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, CouponListResp.CouponBean item) {
        boolean isGreen = !(item.getDiscountStatus() == 3 || item.getDiscountStatus() == 4);
        helper.setImageResource(R.id.img_coupon, isGreen ? R.drawable.ic_coupon_green : R.drawable.ic_coupon_grey);
        helper.setTextColor(R.id.txt_coupon_name, Color.parseColor(isGreen ? "#ffffff" : "#999999"))
                .setTextColor(R.id.txt_coupon_condition, Color.parseColor(isGreen ? "#ffffff" : "#999999"))
                .setTextColor(R.id.txt_coupon_price, Color.parseColor(isGreen ? "#71BEAF" : "#999999"))
                .setTextColor(R.id.txt_coupon_status, Color.parseColor(isGreen ? "#71BEAF" : "#999999"))
                .setText(R.id.txt_coupon_price, item.getCouponValue())
                .setText(R.id.txt_coupon_name, item.getDiscountName())
                .setText(R.id.txt_coupon_status, item.getDiscountStatusName())
                .setText(R.id.txt_coupon_condition, item.getCouponCondition() == 0 ? "无使用限制" : String.format("满%s可以使用", item.getCouponConditionValue()))
                .setText(R.id.txt_money_has, "已领取：" + item.getSendCount())
                .setText(R.id.txt_money_used, "已使用：" + item.getUseCount())
                .setText(R.id.txt_pause, item.getOpList().contains(CouponListResp.CouponBean.OPTION_PAUSE) ? "暂停" : "")
                .setText(R.id.txt_start, item.getOpList().contains(CouponListResp.CouponBean.OPTION_ENABLE) ? "启用" : "");


        helper.getView(R.id.txt_invalid).setVisibility(item.getOpList().contains(CouponListResp.CouponBean.OPTION_INVALID) ? View.VISIBLE : View.GONE);
        helper.getView(R.id.txt_pause).setVisibility(item.getOpList().contains(CouponListResp.CouponBean.OPTION_PAUSE) ? View.VISIBLE : View.GONE);
        helper.getView(R.id.txt_start).setVisibility(item.getOpList().contains(CouponListResp.CouponBean.OPTION_ENABLE) ? View.VISIBLE : View.GONE);
        helper.getView(R.id.txt_send).setVisibility(item.getOpList().contains(CouponListResp.CouponBean.OPTION_RELEASE) ? View.VISIBLE : View.GONE);


    }
}
