package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.bean.event.RefundSearchEvent;
import com.hll_sc_app.bean.event.SalesManSearchEvent;

import org.greenrobot.eventbus.EventBus;

public class RefundSearch implements ISearchContract.ISearchStrategy {


    @Override
    public void onSearch(String searchWords) {
        EventBus.getDefault().post(new RefundSearchEvent(searchWords));
    }

    @Override
    public String getEditHint() {
        return "请输入商品名称";
    }
}
