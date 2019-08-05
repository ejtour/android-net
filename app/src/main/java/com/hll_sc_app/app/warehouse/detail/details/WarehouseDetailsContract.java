package com.hll_sc_app.app.warehouse.detail.details;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.warehouse.WarehouseDetailResp;

/**
 * 代仓管理-代仓详情
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
public interface WarehouseDetailsContract {

    interface IWarehouseDetailsView extends ILoadView {
        /**
         * 展示代仓详情
         *
         * @param resp 代仓详情数据
         */
        void showDetail(WarehouseDetailResp resp);
    }

    interface IWarehouseDetailsPresenter extends IPresenter<IWarehouseDetailsView> {
        /**
         * 查询代仓详情
         *
         * @param purchaserId 签约关系主键id
         */
        void queryCooperationWarehouseDetail(String purchaserId);
    }
}
