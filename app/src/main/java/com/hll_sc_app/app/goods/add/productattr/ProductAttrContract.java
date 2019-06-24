package com.hll_sc_app.app.goods.add.productattr;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.ProductAttrBean;

import java.util.List;

/**
 * 选择商品属性
 *
 * @author zhuyingsong
 * @date 2019/6/24
 */
public interface ProductAttrContract {

    interface IProductAttr extends ILoadView {
        /**
         * 展示商品属性列表
         *
         * @param list list
         */
        void showProductAttrList(List<ProductAttrBean> list);
    }

    interface IProductAttrPresenter extends IPresenter<IProductAttr> {
        /**
         * 商品属性公共列表
         */
        void queryProductAttrsList();
    }
}
