package com.hll_sc_app.app.report.salesman.sales;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.salesman.SalesManAchievementReq;
import com.hll_sc_app.bean.report.salesman.SalesManSalesAchievement;
import com.hll_sc_app.bean.report.salesman.SalesManSalesResp;
import com.hll_sc_app.bean.report.salesman.SalesManSignAchievement;
import com.hll_sc_app.bean.report.salesman.SalesManSignResp;

import java.util.List;

/**
 * 业务员销售额绩效
 * @author 初坤
 * @date 2019/7/16
 */
public interface SalesManSalesAchievementContract {

    interface ISalesManSalesAchievementView extends ILoadView {
        /**
         * 展示业务员销售额绩效
         *
         * @param records   list
         * @param append true-追加
         * @param total  indexList
         */
        void showSalesManSalesAchievementList(List<SalesManSalesAchievement> records, boolean append, int total);

        void showSalesManSalesTotalDatas(SalesManSalesResp salesManSalesResp);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();

        /**
         * 获取参数
         *
         * @return 参数
         */
        SalesManAchievementReq getParams();

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

    interface ISalesManSalesAchievementPresenter extends IPresenter<ISalesManSalesAchievementView> {
        /**
         * 加载业务员销售额绩效
         *
         * @param showLoading true-显示对话框
         */
        void querySalesManSalesAchievementList(boolean showLoading);

        /**
         * 加载更多业务员销售额绩效
         */
        void queryMoreSalesManSalesAchievementList();

        /**
         * 导出业务员销售额绩效
         * @param email 邮箱地址
         */
        void exportSalesManSalesAchievement(String email, String reqParams);
    }
}
