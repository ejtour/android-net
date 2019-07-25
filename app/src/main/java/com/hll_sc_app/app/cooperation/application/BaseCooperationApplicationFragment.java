package com.hll_sc_app.app.cooperation.application;

import com.hll_sc_app.base.BaseLazyFragment;

/**
 * 合作采购商-我收到的申请
 *
 * @author zhuyingsong
 * @date 2019/7/24
 */
public abstract class BaseCooperationApplicationFragment extends BaseLazyFragment {

    /**
     * 触发搜索
     *
     * @param searchParam 搜索词
     */
    public abstract void toSearch(String searchParam);

    /**
     * 触发刷新
     */
    public abstract void refresh();
}
