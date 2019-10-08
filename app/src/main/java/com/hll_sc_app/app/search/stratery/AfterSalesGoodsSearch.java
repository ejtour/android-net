package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/8
 */

public class AfterSalesGoodsSearch extends CommonSearch {

    @Override
    public String getEditHint() {
        return "您可以输入商品关键字查找商品";
    }

    @Override
    public int getEmptyImage() {
        return R.drawable.ic_empty_group_view;
    }
}
