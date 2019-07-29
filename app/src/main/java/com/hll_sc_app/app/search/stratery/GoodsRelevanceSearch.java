package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.bean.event.GoodsRelevanceSearchEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

public class GoodsRelevanceSearch implements ISearchContract.ISearchStrategy {
    @Override
    public void onSearch(String searchWords) {
        EventBus.getDefault().post(new GoodsRelevanceSearchEvent(searchWords));
    }

    @Override
    public String getEditHint() {
        return "请输入采购商集团名称进行查询";
    }

    @Override
    public int getEmptyImage() {
        return R.drawable.ic_search_empty_purchaser;
    }
}
