package com.hll_sc_app.app.crm.customer.seas;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/25
 */

public interface ICustomerSeasContract {
    interface ICustomerSeasView extends ILoadView {
        void setData(List<PurchaserShopBean> list, boolean append);

        String getSearchWords();
    }

    interface ICustomerSeasPresenter extends IPresenter<ICustomerSeasView> {
        void refresh();

        void loadMore();
    }
}
