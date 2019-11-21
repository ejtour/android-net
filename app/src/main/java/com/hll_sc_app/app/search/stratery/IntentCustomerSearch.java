package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.ISearchContract;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/20
 */

public class IntentCustomerSearch implements ISearchContract.ISearchStrategy {
    @Override
    public String getEditHint() {
        return "请输入意向客户名称";
    }

    @Override
    public String getEmptyTip() {
        return "您可以输入意向客户名称进行搜索";
    }

    @Override
    public int getEmptyImage() {
        return R.drawable.ic_empty_group_view;
    }
}
