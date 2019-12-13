package com.hll_sc_app.app.report.customerLack;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.customerLack.CustomerLackReq;
import com.hll_sc_app.bean.report.customerLack.CustomerLackSummary;
import com.hll_sc_app.impl.IExportView;
import com.hll_sc_app.impl.IPurchaserContract;

import java.util.List;

/**
 * @author chukun
 * @since 2019/08/14
 */

public interface ICustomerLackSummaryContract {
    interface ICustomerLackView extends IExportView, IPurchaserContract.IPurchaserView {
        void showSummaryList(List<CustomerLackSummary> list, boolean append);

        CustomerLackReq getRequestParams();

        /**
         * 导出成功
         *
         * @param email 邮箱地址
         */
        void exportSuccess(String email);

        /**
         * 导出失败
         *
         * @param tip 失败提示
         */
        void exportFailure(String tip);

        /**
         * 绑定邮箱
         */
        void bindEmail();

        void export(String email);
    }

    interface ICustomerLackPresenter extends IPresenter<ICustomerLackView>, IPurchaserContract.IPurchaserPresenter {
        void querySummaryList(boolean showLoading);

        void reload();

        void loadMore();

        void refresh();

        void export(String params,String email);
    }
}
