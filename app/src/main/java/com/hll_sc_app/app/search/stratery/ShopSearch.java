package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.ISearchContract;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/12
 */

public class ShopSearch implements ISearchContract.ISearchStrategy {

    @Override
    public String getEditHint() {
        return "请输入门店名称";
    }

    @Override
    public int getEmptyImage() {
        return R.drawable.ic_empty_group_view;
    }
}
