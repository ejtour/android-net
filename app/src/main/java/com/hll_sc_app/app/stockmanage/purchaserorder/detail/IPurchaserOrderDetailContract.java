package com.hll_sc_app.app.stockmanage.purchaserorder.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.stockmanage.purchaserorder.PurchaserOrderDetailResp;

/**
 * 采购单详情查询
 * @author chukun
 * @date 2019/9/10
 */
public interface IPurchaserOrderDetailContract {

    interface IPurchaserOrderDetailView extends ILoadView {
        void setData(PurchaserOrderDetailResp resp);

        String getPurchaserBillID();
    }

    interface IPurchaserOrderDetailPresenter extends IPresenter<IPurchaserOrderDetailView> {
    }
}
