package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.ISearchContract;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

public class GoodsSearch implements ISearchContract.ISearchStrategy {

    @Override
    public int getEmptyImage() {
        return R.drawable.ic_search_goods;
    }

    @Override
    public String getEditHint() {
        return "请输入商品名称或者别名进行查询";
    }
}
