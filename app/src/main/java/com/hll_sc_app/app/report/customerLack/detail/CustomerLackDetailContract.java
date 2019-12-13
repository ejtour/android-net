package com.hll_sc_app.app.report.customerLack.detail;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.customerLack.CustomerLackItem;
import com.hll_sc_app.bean.report.customerLack.CustomerLackReq;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * @author chukun
 * @since 2019/7/23
 */

public interface CustomerLackDetailContract {
    interface ICustomerLackDetailView extends IExportView {
        void setList(List<CustomerLackItem> beans, boolean append);

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

    interface ICustomerLackDetailPresenter extends IPresenter<CustomerLackDetailContract.ICustomerLackDetailView> {
        void refresh();

        void loadMore();

        void export(String params,String email);
    }
}
