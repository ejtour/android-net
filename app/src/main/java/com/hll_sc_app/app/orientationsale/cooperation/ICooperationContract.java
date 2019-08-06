package com.hll_sc_app.app.orientationsale.cooperation;

import com.hll_sc_app.app.agreementprice.quotation.add.purchaser.PurchaserListContract;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.orientation.OrientationListBean;

import java.util.List;

public interface ICooperationContract {


    interface ICooperationPurchaserView extends ILoadView {
        /**
         * 展示合作采购商列表
         *
         * @param list   list
         * @param append 追加
         * @param total  总个数
         */
        void showPurchaserList(List<OrientationListBean> list, boolean append, int total);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();
    }

    interface ICooperationPurchaserPresenter extends IPresenter<ICooperationContract.ICooperationPurchaserView> {
        /**
         * 查询合作采购商列表
         */
        void queryCooperationPurchaserList();
    }
}
