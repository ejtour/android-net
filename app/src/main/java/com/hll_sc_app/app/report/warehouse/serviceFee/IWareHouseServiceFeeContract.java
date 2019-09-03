package com.hll_sc_app.app.report.warehouse.serviceFee;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.warehouse.WareHouseDeliveryResp;
import com.hll_sc_app.bean.report.warehouse.WareHouseServiceFeeReq;
import com.hll_sc_app.bean.report.warehouse.WareHouseServiceFeeResp;

import java.util.List;

/**
 * 代仓发货统计
 * @author chukun
 * @since 2019/8/15
 */

public interface IWareHouseServiceFeeContract {
    interface IWareHouseServiceFeeView extends ILoadView {


        void setWareHouseDeliveryServiceFeeList(WareHouseServiceFeeResp wareHouseServiceFeeResp, boolean append);

        WareHouseServiceFeeReq getRequestParams();

        /**
         * 导出成功
         *
         * @param email 邮箱地址
         */
        void exportSuccess(String email);

        /**
         * 导出失败
         *
         * @param tip 失败提示
         */
        void exportFailure(String tip);

        /**
         * 绑定邮箱
         */
        void bindEmail();

        void export(String email);


        void showPurchaserWindow(List<PurchaserBean> purchaserBeans);

        /**
         * 获取货主ID
         *
         * @return 分类ID
         */
        String getShipperID();
    }

    interface IWareHouseServiceFeePresenter extends IPresenter<IWareHouseServiceFeeContract.IWareHouseServiceFeeView> {

        void loadWareHouseServiceFeeList();

        void loadMore();

        void getShipperList(String searchWord);

        /**
         * 导出
         *
         * @param email 邮箱地址
         */
        void exportWareHouseServiceFeeList(String email, String reqParams);
    }
}
