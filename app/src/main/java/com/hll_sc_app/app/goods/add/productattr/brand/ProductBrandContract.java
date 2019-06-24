package com.hll_sc_app.app.goods.add.productattr.brand;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

import java.util.List;

/**
 * 商品品牌
 *
 * @author zhuyingsong
 * @date 2019/6/24
 */
public interface ProductBrandContract {

    interface IProductAttrBrand extends ILoadView {
        /**
         * 展示商品品牌列表
         *
         * @param list list
         */
        void showProductBrandList(List<String> list);
    }

    interface IProductAttrBrandPresenter extends IPresenter<IProductAttrBrand> {
        /**
         * 查询审核通过品牌
         *
         * @param brandName 品牌名称
         */
        void queryProductBrandList(String brandName);
    }
}
