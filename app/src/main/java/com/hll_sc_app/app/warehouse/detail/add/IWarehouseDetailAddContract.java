package com.hll_sc_app.app.warehouse.detail.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.warehouse.WarehouseShopBean;
import com.hll_sc_app.bean.warehouse.WarehouseShopEditReq;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/24
 */

interface IWarehouseDetailAddContract {
    interface IWarehouseDetailAddView extends ILoadView {
        void setData(List<WarehouseShopBean> list);

        void success();

        String getPurchaserID();
    }

    interface IWarehouseDetailAddPresenter extends IPresenter<IWarehouseDetailAddView> {
        void confirm(WarehouseShopEditReq req);
    }
}
