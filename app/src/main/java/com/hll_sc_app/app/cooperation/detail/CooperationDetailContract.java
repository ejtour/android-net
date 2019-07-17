package com.hll_sc_app.app.cooperation.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.cooperation.CooperationShopReq;

/**
 * 合作采购商详情
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public interface CooperationDetailContract {

    interface ICooperationDetailView extends ILoadView {
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

    interface ICooperationDetailPresenter extends IPresenter<ICooperationDetailView> {

        /**
         * 查询采购商集团详情
         */
        void queryPurchaserDetail();

        /**
         * 新增或删除合作门店
         *
         * @param req req
         */
        void editCooperationPurchaserShop(CooperationShopReq req);
    }
}
