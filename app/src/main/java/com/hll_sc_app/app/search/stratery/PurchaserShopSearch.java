package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.ISearchContract;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/8
 */

public class PurchaserShopSearch implements ISearchContract.ISearchStrategy {
    @Override
    public String getEditHint() {
        return "你想要搜哪个合作客户门店";
    }

    @Override
    public String getEmptyTip() {
        return "您可以根据客户门店名称搜索";
    }

    @Override
    public int getEmptyImage() {
        return R.drawable.ic_empty_shop_view;
    }
}
