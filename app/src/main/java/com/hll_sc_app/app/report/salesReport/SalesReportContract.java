package com.hll_sc_app.app.report.salesReport;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.bean.report.salesReport.SalesReportReq;
import com.hll_sc_app.bean.report.salesReport.SalesReportResp;

/**
 * 销售日报
 *
 * @author chukun
 * @date 2019.09.09
 */
public interface SalesReportContract {

    interface ISalesReportView extends ILoadView {
        /**
         * 展示销售日报的数据
         * @param salesReportResp
         */
        void showSalesReportList(SalesReportResp salesReportResp, boolean append, int total);


        /**
         * 获取参数列表
         * @return 开始时间
         */
        SalesReportReq getRequestParams ();

        /**
         * 绑定邮箱
         */
        void bindEmail();

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
         * 导出邮箱
         */
        void export(String email);
    }

    interface ISalesReportPresenter extends IPresenter<ISalesReportView> {
        /**
         * 加载销售日报的数据
         *
         * @param showLoading true-显示对话框
         */
        void querySalesReportList(boolean showLoading);

        /**
         * 加载更多销售日报的数据
         */
        void queryMoreSalesReportList();

        /**
         * 导出日汇总报表
         *
         * @param email 邮箱地址
         */
        void exportSalesReport(String email);
    }
}
