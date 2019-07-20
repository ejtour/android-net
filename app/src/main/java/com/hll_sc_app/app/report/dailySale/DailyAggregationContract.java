package com.hll_sc_app.app.report.dailySale;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.pricemanage.PriceLogBean;
import com.hll_sc_app.bean.report.resp.bill.DateSaleAmount;

import java.util.List;

/**
 * 售价设置-变更日志
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public interface DailyAggregationContract {

    interface IDailyAggregationView extends ILoadView {
        /**
         * 展示日汇总报表
         *
         * @param list   list
         * @param append true-追加
         * @param total  indexList
         */
        void showDailyAggregationList(List<DateSaleAmount> list, boolean append, int total);

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
