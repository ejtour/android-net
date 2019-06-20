package com.hll_sc_app.app.goods.add.specs;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;

/**
 * 新增商品规格
 *
 * @author zhuyingsong
 * @date 2019/6/19
 */
public interface GoodsSpecsAddContract {

    interface IGoodsAddView extends ILoadView {
        /**
         * sku 条码校验通过
         */
        void checkSuccess();
    }

    interface IGoodsAddPresenter extends IPresenter<IGoodsAddView> {
        /**
         * 商品sku条码校验
         *
         * @param skuCode sku 条码
         */
        void checkSkuCode(String skuCode);
    }
}
