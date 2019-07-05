package com.hll_sc_app.app.goods.relevance.goods.fragment;

import com.hll_sc_app.base.BaseLazyFragment;

/**
 * 第三方商品关联基类
 *
 * @author zhuyingsong
 * @date 2019-07-05
 */
public abstract class BaseGoodsRelevanceFragment extends BaseLazyFragment {
    /**
     * 刷新界面
     *
     * @param name 搜索词
     */
    public abstract void refreshFragment(String name);
}
