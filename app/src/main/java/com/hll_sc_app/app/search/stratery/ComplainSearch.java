package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.bean.event.ComplainManageEvent;

import org.greenrobot.eventbus.EventBus;

import static com.hll_sc_app.bean.event.ComplainManageEvent.EVENT.REFRESH;
import static com.hll_sc_app.bean.event.ComplainManageEvent.TARGET.SELECT_PURCHASER_LIST;

public class ComplainSearch implements ISearchContract.ISearchStrategy {

    @Override
    public void onSearch(String searchWords) {
        ComplainManageEvent event = new ComplainManageEvent(SELECT_PURCHASER_LIST, REFRESH);
        event.setSearchContent(searchWords);
        EventBus.getDefault().post(event);
    }

    @Override
    public String getEditHint() {
        return "请输入名称进行查询";
    }
}
