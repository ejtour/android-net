package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.ISearchContract;

/**
 * 员工部门选择
 */

public class StaffDepartSearch implements ISearchContract.ISearchStrategy {

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
