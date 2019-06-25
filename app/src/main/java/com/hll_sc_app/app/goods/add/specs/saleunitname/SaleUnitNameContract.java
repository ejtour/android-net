package com.hll_sc_app.app.goods.add.specs.saleunitname;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.SaleUnitNameWrapper;

import java.util.List;

/**
 * 选择售卖单位
 *
 * @author zhuyingsong
 * @date 2019/6/19
 */
public interface SaleUnitNameContract {

    interface ISaleUnitNameAddView extends ILoadView {
        /**
         * 展示商品单位列表
         *
         * @param list      list
         */
        void showSaleUnitNameList(List<SaleUnitNameWrapper> list);
    }

    interface ISaleUnitNameAddPresenter extends IPresenter<ISaleUnitNameAddView> {
        /**
         * 查询商品单位列表
         */
        void querySaleUnitName();
    }
}
