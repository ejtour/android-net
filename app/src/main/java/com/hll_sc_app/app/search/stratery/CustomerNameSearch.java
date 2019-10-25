package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.app.search.ISearchContract;

/**
 * 营销 优惠券分发选择客户
 */
public class CustomerNameSearch implements ISearchContract.ISearchStrategy {

    @Override
    public String getEditHint() {
        return "请输入客户名称进行查询";
    }
}
