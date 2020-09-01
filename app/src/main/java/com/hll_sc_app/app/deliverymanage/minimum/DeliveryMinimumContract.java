package com.hll_sc_app.app.deliverymanage.minimum;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.delivery.DeliveryMinimumBean;

import java.util.List;

/**
 * 起送金额列表
 *
 * @author zhuyingsong
 * @date 2019/7/30
 */
public interface DeliveryMinimumContract {

    interface IDeliveryMinimumView extends ILoadView {
        /**
         * 展示起送金额列表
         *
         * @param list 列表数据
         */
        void showDeliveryList(List<DeliveryMinimumBean> list);

        String getPurchaserID();

        String getPurchaserShopID();
    }

    interface IDeliveryMinimumPresenter extends IPresenter<IDeliveryMinimumView> {
        /**
         * 起送金额列表查询
         */
        void queryDeliveryMinimumList();

        /**
         * 删除起送金额分组
         *
         * @param bean 起送金额分组
         */
        void delDeliveryMinimum(DeliveryMinimumBean bean);
    }
}
