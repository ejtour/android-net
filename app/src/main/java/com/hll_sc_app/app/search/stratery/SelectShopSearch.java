package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.bean.event.MarketingSelectShopEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 营销 优惠券分发选择客户
 */
public class SelectShopSearch implements ISearchContract.ISearchStrategy {
    @Override
    public void onSearch(String searchWords) {
        EventBus.getDefault().post(new MarketingSelectShopEvent(1, searchWords,null));
    }

    @Override
    public String getEditHint() {
        return "请输入门店名称进行查询";
    }
}
