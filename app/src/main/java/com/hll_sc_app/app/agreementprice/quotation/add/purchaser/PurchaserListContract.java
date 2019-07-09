package com.hll_sc_app.app.agreementprice.quotation.add.purchaser;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 选择合作采购商
 *
 * @author zhuyingsong
 * @date 2019/7/9
 */
public interface PurchaserListContract {

    interface IPurchaserListView extends ILoadView {
        /**
         * 展示合作采购商列表
         *
         * @param list list
         */
        void showPurchaserList(List<PurchaserBean> list);
    }

    interface IPurchaserListPresenter extends IPresenter<IPurchaserListView> {
        /**
         * 查询合作采购商列表
         *
         * @param searchParam 搜索词
         */
        void queryCooperationPurchaserList(String searchParam);
    }
}
