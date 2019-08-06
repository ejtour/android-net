package com.hll_sc_app.app.warehouse.detail.details;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
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

        /**
         * 操作成功
         */
        void editSuccess();
    }

    interface IWarehouseDetailsPresenter extends IPresenter<IWarehouseDetailsView> {
        /**
         * 查询代仓详情
         *
         * @param purchaserId 签约关系主键id
         */
        void queryCooperationWarehouseDetail(String purchaserId);

        /**
         * 申请代仓
         *
         * @param req 请求参数
         */
        void addWarehouse(BaseMapReq req);

        /**
         * 解除签约关系或者放弃代仓
         *
         * @param req  req
         * @param type 1 解除，2 放弃
         */
        void delWarehouse(BaseMapReq req, String type);

        /**
         * 同意或者拒接签约
         *
         * @param req  req
         * @param type agree-同意合作 refuse-拒绝合作
         */
        void agreeOrRefuseWarehouse(BaseMapReq req, String type);
    }
}
