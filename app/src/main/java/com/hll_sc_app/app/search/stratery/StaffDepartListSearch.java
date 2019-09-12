package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.ISearchContract;
import com.hll_sc_app.bean.event.StaffDepartListEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 员工部门选择
 */

public class StaffDepartListSearch implements ISearchContract.ISearchStrategy {
    @Override
    public void onSearch(String searchWords) {
        EventBus.getDefault().post(new StaffDepartListEvent(searchWords));
    }

    @Override
    public String getEditHint() {
        return "请输入员工部门名称";
    }

    @Override
    public String getEmptyTip() {
        return "您可以输入员工部门名称查找";
    }

    @Override
    public int getEmptyImage() {
        return R.drawable.ic_search_empty_purchaser;
    }
}
