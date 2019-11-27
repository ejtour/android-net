package com.hll_sc_app.app.crm.customer.partner.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/27
 */

public interface ICustomerPartnerDetailContract {
    interface ICustomerPartnerDetailView extends ILoadView{
        void setData(CooperationShopListResp resp,boolean append);

        String getSearchWords();

        String getPurchaserID();

        boolean isAll();
    }

    interface ICustomerPartnerDetailPresenter extends IPresenter<ICustomerPartnerDetailView>{
        void refresh();
        void loadMore();
    }
}
