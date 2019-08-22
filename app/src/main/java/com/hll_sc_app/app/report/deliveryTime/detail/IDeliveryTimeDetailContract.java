package com.hll_sc_app.app.report.deliveryTime.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeItem;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeReq;
import com.hll_sc_app.bean.report.deliveryTime.DeliveryTimeResp;
import com.hll_sc_app.bean.report.inspectLack.detail.InspectLackDetailReq;
import com.hll_sc_app.bean.report.inspectLack.detail.InspectLackDetailResp;

/**
 * @author chukun
 * @since 2019/8/15
 */

public interface IDeliveryTimeDetailContract {
    interface IDeliveryTimeDetailView extends ILoadView {
        void  setDeliveryTimeDetailList(DeliveryTimeResp deliveryTimeResp, boolean append);

        DeliveryTimeReq getRequestParams();

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
    }

    interface IDeliveryTimeDetailPresenter extends IPresenter<IDeliveryTimeDetailContract.IDeliveryTimeDetailView> {

        void loadDeliveryTimeDetailList();

        void loadMore();

        /**
         * 导出
         *
         * @param email 邮箱地址
         */
        void exportDeliveryTimeDetail(String email, String reqParams);
    }
}
