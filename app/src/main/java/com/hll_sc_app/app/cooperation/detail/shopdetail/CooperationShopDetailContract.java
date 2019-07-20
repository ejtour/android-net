package com.hll_sc_app.app.cooperation.detail.shopdetail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.DeliveryPeriodBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;

import java.util.List;

/**
 * 合作采购商详情-门店详情
 *
 * @author zhuyingsong
 * @date 2019/7/19
 */
public interface CooperationShopDetailContract {

    interface ICooperationShopDetailView extends ILoadView {

        /**
         * 修改成功
         */
        void editSuccess();

        /**
         * 获取 shopBean
         *
         * @return shopBean
         */
        PurchaserShopBean getShopBean();

        /**
         * 显示到货时间选择框
         *
         * @param list 到货时间
         */
        void showDeliveryPeriodWindow(List<DeliveryPeriodBean> list);
    }

    interface ICooperationShopDetailPresenter extends IPresenter<ICooperationShopDetailView> {
        /**
         * 编辑合作门店
         *
         * @param actionType 操作类型（同意申请-agree，拒绝申请-refuse，重新申请-revalidation）
         */
        void editCooperationShop(String actionType);

        /**
         * 解除合作
         */
        void delCooperationShop();

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
    }
}
