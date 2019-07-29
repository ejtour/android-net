package com.hll_sc_app.app.deliverymanage.ageing.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;

/**
 * 配送时效编辑、新增界面
 *
 * @author zhuyingsong
 * @date 2019/7/29
 */
public interface DeliveryAgeingDetailContract {

    interface IDeliveryAgeingDetailView extends ILoadView {
        /**
         * 修改成功
         */
        void editSuccess();
    }

    interface IDeliveryAgeingDetailPresenter extends IPresenter<IDeliveryAgeingDetailView> {
        /**
         * 查询配送方式
         */
        void queryDeliveryList();

        /**
         * 配送时效操作
         *
         * @param req 请求参数
         */
        void editDeliveryAgeing(BaseMapReq req);
    }
}
