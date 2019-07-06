package com.hll_sc_app.app.goods.relevance.goods.fragment;

import com.hll_sc_app.base.BaseLazyFragment;
import com.hll_sc_app.bean.order.detail.TransferDetailBean;

import java.util.List;

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

    /**
     * 使用已有数据刷新列表
     * @param beans 已有的转单明细数据
     */
    public abstract void refreshList(List<TransferDetailBean> beans);
}
