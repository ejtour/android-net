package com.hll_sc_app.app.report.warehouse.fee;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.warehouse.WareHouseFeeBean;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * 代仓发货统计
 *
 * @author chukun
 * @since 2019/8/15
 */

public interface IWareHouseFeeContract {
    interface IWareHouseFeeView extends IExportView {
        void setData(List<WareHouseFeeBean> list, boolean append);

        BaseMapReq.Builder getReq();

        void cacheShipperList(List<PurchaserBean> purchaserBeans);
    }

    interface IWareHouseFeePresenter extends IPresenter<IWareHouseFeeView> {

        void loadList();

        void refresh();

        void loadMore();

        void getShipperList();

        void export(String email);
    }
}
