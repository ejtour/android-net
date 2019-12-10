package com.hll_sc_app.app.report.dailySale;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.pricemanage.PriceLogBean;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmount;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * 售日销售汇总
 *
 * @author chukun
 * @date 2019/7/16
 */
public interface DailyAggregationContract {

    interface IDailyAggregationView extends IExportView {
        /**
         * 展示客户销售汇总的数据
         * @param dateSaleAmountResp
         */
        void showDailyAggregationList(DateSaleAmountResp dateSaleAmountResp,boolean append, int total);


        /**
         * 获取开始时间
         *
         * @return 开始时间
         */
        String getStartDate();

        /**
         * 获取结束时间
         *
         * @return 结束时间
         */
        String getEndDate();

        /**
         * 导出邮箱
         */
        void export(String email);
    }

    interface IDailyAggregationManagePresenter extends IPresenter<IDailyAggregationView> {
        void refresh();
        void loadMore();

        /**
         * 导出日汇总报表
         *
         * @param email 邮箱地址
         */
        void exportDailyReport(String email);
    }
}
