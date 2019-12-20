package com.hll_sc_app.app.stockmanage.purchaserorder;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderBean;

import java.util.List;

/**
 * 采购单查询
 *
 * @author chukun
 * @date 2019/9/10
 */
public interface IPurchaserOrderContract {

    interface IPurchaserOrderView extends ILoadView {
        void setData(List<PurchaserOrderBean> list, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface IPurchaserOrderPresenter extends IPresenter<IPurchaserOrderView> {
        void refresh();

        void loadMore();
    }
}
