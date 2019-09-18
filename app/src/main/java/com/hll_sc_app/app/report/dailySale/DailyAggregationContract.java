package com.hll_sc_app.app.report.dailySale;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.pricemanage.PriceLogBean;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmount;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmountResp;

import java.util.List;

/**
 * 售日销售汇总
 *
 * @author chukun
 * @date 2019/7/16
 */
public interface DailyAggregationContract {

    interface IDailyAggregationView extends ILoadView {
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

        /**
         * 导出邮箱
         */
        void export(String email);
    }

    interface IDailyAggregationManagePresenter extends IPresenter<IDailyAggregationView> {
        /**
         * 加载日销售汇总列表
         *
         * @param showLoading true-显示对话框
         */
        void queryDailyAggregationList(boolean showLoading);

        /**
         * 加载更多日汇总列表
         */
        void queryMoreDailyAggregationList();

        /**
         * 导出日汇总报表
         *
         * @param email 邮箱地址
         */
        void exportDailyReport(String email);
    }
}
