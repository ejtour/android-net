package com.hll_sc_app.app.select.shop.goodsassign;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/27
 */
public interface ISelectShopContract {
    interface ISelectShopView extends ILoadView{
        void setData(List<PurchaserShopBean> list);

        String getSearchWords();

        String getPurchaserID();
    }

    interface ISelectShopPresenter extends IPresenter<ISelectShopView>{

    }
}
