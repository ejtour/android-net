package com.hll_sc_app.app.report.salesman.sign;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesRecords;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;
import com.hll_sc_app.bean.report.salesman.SalesManAchievementReq;
import com.hll_sc_app.bean.report.salesman.SalesManSignAchievement;
import com.hll_sc_app.bean.report.salesman.SalesManSignResp;

import java.util.List;

/**
 * 业务员签约绩效
 * @author 初坤
 * @date 2019/7/16
 */
public interface SalesManSignAchievementContract {

    interface ISalesManSignAchievementView extends ILoadView {
        /**
         * 展示业务员签约绩效
         *
         * @param records   list
         * @param append true-追加
         * @param total  indexList
         */
        void showSalesManSignAchievementList(List<SalesManSignAchievement> records, boolean append, int total);

        void showSalesManSignTotalDatas(SalesManSignResp salesManSignResp);

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

    interface ISalesManSignAchievementPresenter extends IPresenter<ISalesManSignAchievementView> {
        /**
         * 加载业务员签约绩效
         *
         * @param showLoading true-显示对话框
         */
        void querySalesManSignAchievementList(boolean showLoading);

        /**
         * 加载更多业务员签约绩效
         */
        void queryMoreSalesManSignAchievementList();

        /**
         * 导出业务员签约绩效
         * @param email 邮箱地址
         */
        void exportSalesManSignAchievement(String email, String reqParams);
    }
}
