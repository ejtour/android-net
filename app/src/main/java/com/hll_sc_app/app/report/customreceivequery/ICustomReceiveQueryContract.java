package com.hll_sc_app.app.report.customreceivequery;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;

import java.util.List;

/***
 * 客户收货查询
 * */
public interface ICustomReceiveQueryContract {
    interface IView extends ILoadView {
        void querySuccess(List<CustomReceiveListResp.CustomReceiveBean> customReceiveBeans, boolean isMore);

        String getOwnerId();

        String getStartDate();

        String getEndDate();

        String getType();

        String getStatus();

        void queryCustomerListSuccess(List<PurchaserBean> purchaserBeans,boolean isMore);

        void queryListFail();
    }

    interface IPresent extends IPresenter<IView> {
        void queryList(boolean isLoading);

        void refresh(boolean isLoading);

        void getMore();

        int getPageSize();

        void queryCustomer(boolean isLoading);

        void refreshCustomer();

        void getMoreCustomer();

        int getPageSizeCustom();


    }
}
