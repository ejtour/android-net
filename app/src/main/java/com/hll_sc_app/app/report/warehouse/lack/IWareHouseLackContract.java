package com.hll_sc_app.app.report.warehouse.lack;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.lack.LackDetailsResp;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * @author chukun
 * @since 2019/8/15
 */

public interface IWareHouseLackContract {
    interface IWareHouseLackView extends IExportView {

        void setData(LackDetailsResp resp, boolean append);

        BaseMapReq.Builder getReq();

        void cacheShipperList(List<PurchaserBean> purchaserBeans);
    }

    interface IWareHouseLackPresenter extends IPresenter<IWareHouseLackView> {

        void loadList();

        void refresh();

        void loadMore();

        void getShipperList();

        void export(String email);
    }
}
