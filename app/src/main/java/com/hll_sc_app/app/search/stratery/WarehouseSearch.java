package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.event.GoodsRelevanceSearchEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 代仓客户搜索
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */

public class WarehouseSearch implements ISearchContract.ISearchStrategy {
    @Override
    public void onSearch(String searchWords) {
        EventBus.getDefault().post(new GoodsRelevanceSearchEvent(searchWords));
    }

    @Override
    public String getEditHint() {
        return UserConfig.isSelfOperated() ? "请输入客户名称" : "请输入代仓公司名称";
    }

    @Override
    public String getEmptyTip() {
        return UserConfig.isSelfOperated() ? "您可以输入客户名称查找客户" : "请输入代仓公司名称查询";
    }

    @Override
    public int getEmptyImage() {
        return R.drawable.ic_search_empty_purchaser;
    }
}
