package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

public class EmployeeSearch implements ISearchContract.ISearchStrategy {

    @Override
    public String getEditHint() {
        return "您可以根据员工的名称、编号、电话来搜索";
    }
}
