package com.hll_sc_app.impl;

import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.PurchaserShopBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/8
 */

public interface IPurchaserContract {
    interface IPurchaserView{
        void refreshPurchaserList(List<PurchaserBean> list);

        void refreshShopList(List<PurchaserShopBean> list);
    }

    interface IPurchaserPresenter{
        void getPurchaserList(String searchWords);

        void getShopList(String purchaseID, String searchWords);
    }
}
