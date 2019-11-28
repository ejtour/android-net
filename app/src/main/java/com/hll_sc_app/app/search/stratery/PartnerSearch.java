package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.ISearchContract;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/28
 */

public class PartnerSearch implements ISearchContract.ISearchStrategy {

    @Override
    public String getEditHint() {
        return "请输入合作客户名称";
    }

    @Override
    public String getEmptyTip() {
        return "您可以根据合作客户名称搜索";
    }

    @Override
    public int getEmptyImage() {
        return R.drawable.ic_empty_group_view;
    }
}
