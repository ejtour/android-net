package com.hll_sc_app.app.marketingsetting.product;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.bean.event.MarketingEvent;

import org.greenrobot.eventbus.EventBus;

public class MarketingProductSearch implements ISearchContract.ISearchStrategy {
    @Override
    public void onSearch(String searchWords) {
        MarketingEvent event = new MarketingEvent();
        event.setSearchText(searchWords);
        EventBus.getDefault().post(event);
    }

    @Override
    public String getEditHint() {
        return "请输入优惠名称";
    }

    @Override
    public String getEmptyTip() {
        return "请输入优惠名称";
    }
}
