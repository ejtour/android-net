package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.bean.event.CustomerLackSearchEvent;
import com.hll_sc_app.bean.event.SalesManSearchEvent;

import org.greenrobot.eventbus.EventBus;

public class CustomerLackSearch implements ISearchContract.ISearchStrategy {


    @Override
    public void onSearch(String searchWords) {
        EventBus.getDefault().post(new CustomerLackSearchEvent(searchWords));
    }

    @Override
    public String getEditHint() {
        return "请输入搜索词";
    }
}
