package com.hll_sc_app.app.report.customreceivequery.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveDetailBean;

import java.util.List;

/***
 * 客户收货查询
 * */
public interface ICustomReceiveDetailContract {
    interface IView extends ILoadView {
        void querySuccess(List<CustomReceiveDetailBean> customReceiveDetailBeans);

        String getOwnerId();

        String getVoucherId();
    }

    interface IPresent extends IPresenter<IView> {
        void refresh();
    }
}
