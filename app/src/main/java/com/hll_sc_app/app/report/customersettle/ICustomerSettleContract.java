package com.hll_sc_app.app.report.customersettle;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.customersettle.CustomerSettleBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/24
 */

interface ICustomerSettleContract {
    interface ICustomerSettleView extends ILoadView {
        void setData(List<CustomerSettleBean> list);

        void setPurchaserData(List<PurchaserBean> list, boolean apppend);

        BaseMapReq.Builder getReq();
    }

    interface ICustomerSettlePresenter extends IPresenter<ICustomerSettleView> {
        void loadInfo();

        void windowLoadMore();
    }
}
