package com.hll_sc_app.app.warehouse.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.warehouse.WarehouseDetailResp;

/**
 * 代仓货主详情
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
public interface WarehouseDetailContract {

    interface ICooperationDetailView extends ILoadView {
        /**
         * 展示代仓详情
         *
         * @param resp resp
         */
        void showDetail(WarehouseDetailResp resp);
    }

    interface ICooperationDetailPresenter extends IPresenter<ICooperationDetailView> {
        /**
         * 查询代仓详情
         *
         * @param purchaserId 签约关系主键id
         */
        void queryCooperationWarehouseDetail(String purchaserId);
    }
}
