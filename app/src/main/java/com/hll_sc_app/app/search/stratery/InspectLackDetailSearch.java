package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.bean.event.InspectLackSearchEvent;
import com.hll_sc_app.bean.event.SalesManSearchEvent;

import org.greenrobot.eventbus.EventBus;

public class InspectLackDetailSearch implements ISearchContract.ISearchStrategy {


    @Override
    public void onSearch(String searchWords) {
        EventBus.getDefault().post(new InspectLackSearchEvent(searchWords));
    }

    @Override
    public String getEditHint() {
        return "请输入搜索词";
    }
}
