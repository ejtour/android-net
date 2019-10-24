package com.hll_sc_app.app.report.customreceivequery.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;

import java.util.List;

/***
 * 客户收货查询
 * */
public interface ICustomReceiveDetailContract {
    interface IView extends ILoadView {
        void querySuccess(List<CustomReceiveListResp.RecordsBean> customReceiveBeans, boolean isMore);

    }

    interface IPresent extends IPresenter<IView> {
        void queryList(boolean isLoading);

        void refresh(boolean isLoading);

        void getMore();

        int getPageSize();



    }
}
