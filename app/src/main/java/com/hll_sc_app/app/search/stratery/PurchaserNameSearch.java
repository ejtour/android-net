package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.ISearchContract;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

public class PurchaserNameSearch implements ISearchContract.ISearchStrategy {

    @Override
    public String getEditHint() {
        return "请输入客户集团名称进行查询";
    }

    @Override
    public int getEmptyImage() {
        return R.drawable.ic_search_empty_purchaser;
    }
}
