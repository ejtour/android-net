package com.hll_sc_app.app.cooperation.detail.details.basic;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.cooperation.DeliveryPeriodBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;

import java.util.List;


/**
 * 合作采购商详情-详细资料-基本信息
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
public interface CooperationDetailsBasicContract {

    interface IGoodsRelevanceListView extends ILoadView {

        /**
         * 显示到货时间选择框
         *
         * @param list 到货时间
         */
        void showDeliveryPeriodWindow(List<DeliveryPeriodBean> list);

        /**
         *  属性修改成功
         */
        void saveSuccess();

    }

    interface IGoodsRelevanceListPresenter extends IPresenter<IGoodsRelevanceListView> {
        /**
         * 配送时段列表查询
         */
        void queryDeliveryPeriod();

        /**
         * 修改配送方式
         *
         * @param req req
         */
        void editShopDeliveryPeriod(ShopSettlementReq req);

        /**
         * 编辑合作关系拜访维护级别/客户级别
         *
         * @param req 请求参数
         */
        void editCooperationPurchaserLevel(BaseMapReq req);
    }
}
