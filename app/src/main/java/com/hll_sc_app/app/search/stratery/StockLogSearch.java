package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.bean.event.StockManageEvent;

import org.greenrobot.eventbus.EventBus;

public class StockLogSearch implements ISearchContract.ISearchStrategy {
    @Override
    public void onSearch(String searchWords) {
        EventBus.getDefault().post(new StockManageEvent(StockManageEvent.TYPE_LOG, searchWords));
    }

    @Override
    public String getEditHint() {
        return "搜索";
    }


}
