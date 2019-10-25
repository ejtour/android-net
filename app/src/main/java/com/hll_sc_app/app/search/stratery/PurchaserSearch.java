package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.ISearchContract;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/21
 */

public class PurchaserSearch implements ISearchContract.ISearchStrategy {

    @Override
    public String getEditHint() {
        return "你想搜哪个合作采购商";
    }

    @Override
    public String getEmptyTip() {
        return "您可以根据采购商名称搜索";
    }

    @Override
    public int getEmptyImage() {
        return R.drawable.ic_empty_group_view;
    }
}
