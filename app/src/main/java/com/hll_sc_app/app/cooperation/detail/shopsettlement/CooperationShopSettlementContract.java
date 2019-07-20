package com.hll_sc_app.app.cooperation.detail.shopsettlement;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.SettlementBean;
import com.hll_sc_app.bean.cooperation.ShopSettlementReq;

/**
 * 合作采购商详情-选择结算方式
 *
 * @author zhuyingsong
 * @date 2019/7/18
 */
public interface CooperationShopSettlementContract {

    interface ICooperationShopSettlementView extends ILoadView {
        /**
         * 展示支持的结算方式
         *
         * @param bean 结算方式
         */
        void showSettlementList(SettlementBean bean);

        /**
         * 修改成功
         */
        void editSuccess();

        /**
         * 要求输入验证信息
         */
        void showEditText();

        /**
         * 获取验证信息
         *
         * @return 验证信息
         */
        String getVerification();
    }

    interface ICooperationShopSettlementPresenter extends IPresenter<ICooperationShopSettlementView> {
        /**
         * 查询支付方式
         */
        void querySettlementList();

        /**
         * 批量修改结算方式
         *
         * @param req req
         */
        void editShopSettlement(ShopSettlementReq req);

        /**
         * 合作商信息编辑
         *
         * @param req req
         */
        void editCooperationPurchaser(ShopSettlementReq req);

        /**
         * 添加合作餐企
         *
         * @param req req
         */
        void addCooperationPurchaser(ShopSettlementReq req);

        /**
         * 查询集团参数:5-添加为合作采购商时需要验证开关
         *
         * @param purchaserId purchaserId
         */
        void queryGroupParameterInSetting(String purchaserId);
    }
}
