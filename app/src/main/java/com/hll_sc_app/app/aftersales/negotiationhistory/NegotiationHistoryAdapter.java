package com.hll_sc_app.app.aftersales.negotiationhistory;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.aftersales.NegotiationHistoryResp;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.List;

public class NegotiationHistoryAdapter extends BaseQuickAdapter<NegotiationHistoryResp.NegotiationHistoryBean, BaseViewHolder> {
    NegotiationHistoryAdapter(@Nullable List<NegotiationHistoryResp.NegotiationHistoryBean> data) {
        super(R.layout.item_after_sales_negotiation_history, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NegotiationHistoryResp.NegotiationHistoryBean item) {
        ((GlideImageView) helper.getView(R.id.inh_img)).setImageURL(item.getLogoUrl());
        helper.setText(R.id.inh_name, item.getGroupName()) // 供应商名称
                // 操作人和操作时间
                .setText(R.id.inh_handler, String.format("操作员%s %s", item.getHandleBy(), CalendarUtils.getFormatYyyyMmDdHhMm(String.valueOf(item.getHandleTime()))))
                // 操作日志
                .setText(R.id.inh_handle_log, item.getHandleLog())
                // 详细日志
                .setGone(R.id.inh_show_log, !TextUtils.isEmpty(item.getShowLog()))
                .setText(R.id.inh_show_log, TextUtils.isEmpty(item.getShowLog()) ? "" : item.getShowLog().replaceAll("\\$%\\$", "\n"));
    }
}
