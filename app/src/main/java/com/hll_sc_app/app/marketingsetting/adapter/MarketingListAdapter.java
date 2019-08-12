package com.hll_sc_app.app.marketingsetting.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.marketingsetting.MarketingListResp;
import com.hll_sc_app.citymall.util.CalendarUtils;

import java.util.List;

/**
 * 营销列表适配器
 */
public class MarketingListAdapter extends BaseQuickAdapter<MarketingListResp.MarketingBean, BaseViewHolder> {
    public MarketingListAdapter(@Nullable List<MarketingListResp.MarketingBean> data) {
        super(R.layout.list_item_marketing, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MarketingListResp.MarketingBean item) {
        helper.setText(R.id.txt_title, item.getDiscountName())
                .setText(R.id.txt_time, "促销时间: " + transformTime(item.getDiscountStartTime()) + "-" + transformTime(item.getDiscountEndTime()))
                .setText(R.id.txt_area, "活动区域: " + item.getAreaDesc())
                .setText(R.id.txt_rule, item.getRuleTypeName())
                .setText(R.id.txt_market_status, item.getDiscountStatusName());
        if (2 == item.getDiscountStatus()) {
            helper.setTextColor(R.id.txt_market_status, Color.parseColor("#95DE64"));
        }

    }

    /*格式化时间*/
    private String transformTime(String time) {
        return CalendarUtils.getDateFormatString(time, "yyyyMMddHHmm", "yyyy-MM-dd HH:mm");
    }
}
