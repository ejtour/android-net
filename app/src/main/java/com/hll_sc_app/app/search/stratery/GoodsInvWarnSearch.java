package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.bean.event.GoodsInvWarnSearchEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

public class GoodsInvWarnSearch extends GoodsSearch {
    @Override
    public void onSearch(String searchWords) {
        EventBus.getDefault().post(new GoodsInvWarnSearchEvent(searchWords));
    }
}
