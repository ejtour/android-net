package com.hll_sc_app.app.report.dailysale;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;
import com.hll_sc_app.impl.IExportView;

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
         *
         * @param dateSaleAmountResp
         */
        void showDailyAggregationList(DateSaleAmountResp dateSaleAmountResp, boolean append);

        /**
         * 导出邮箱
         */
        void export(String email);

        BaseMapReq.Builder getReqBuilder();
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
