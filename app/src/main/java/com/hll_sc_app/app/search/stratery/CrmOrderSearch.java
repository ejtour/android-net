package com.hll_sc_app.app.search.stratery;

import com.hll_sc_app.R;
import com.hll_sc_app.app.search.ISearchContract;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/5
 */

public class CrmOrderSearch extends OrderSearch {

    @Override
    public ISearchContract.ISearchPresenter getSearchPresenter() {
        return null;
    }

    @Override
    public String getEditHint() {
        return "可输入门店名称进行搜索";
    }

    @Override
    public String getEmptyTip() {
        return "搜索数据";
    }

    @Override
    public int getEmptyImage() {
        return R.drawable.ic_empty_group_view;
    }
}
