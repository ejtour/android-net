package com.hll_sc_app.app.cooperation.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;

/**
 * 合作采购商详情
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public interface CooperationDetailContract {

    interface IGoodsRelevancePurchaserView extends ILoadView {
        /**
         * 展示采购商集团详情
         *
         * @param resp resp
         */
        void showPurchaserDetail(CooperationPurchaserDetail resp);

        /**
         * 获取采购商ID
         *
         * @return 合作商 ID
         */
        String getPurchaserId();
    }

    interface IGoodsRelevancePurchaserPresenter extends IPresenter<IGoodsRelevancePurchaserView> {
        /**
         * 查询商品关联的采购商列表
         */
        void queryPurchaserDetail();

        /**
         * 删除合作餐企
         *
         * @param purchaserId 采购商集团ID
         */
        void delCooperationPurchaser(String purchaserId);
    }
}
