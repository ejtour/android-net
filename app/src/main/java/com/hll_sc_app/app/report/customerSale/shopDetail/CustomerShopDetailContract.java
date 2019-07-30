package com.hll_sc_app.app.report.customerSale.shopDetail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesRecords;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;

import java.util.HashMap;
import java.util.List;

/**
 *
 * 报表 客户销售门店
 * @author zhuyingsong
 * @date 2019/7/16
 */
public interface CustomerShopDetailContract {

    interface ICustomerShopDetailView extends ILoadView {
        /**
         * 展示客户销售汇总，门店明细
         *
         * @param list   list
         * @param append true-追加
         * @param total  indexList
         */
        void showCustomerShopDetailList(List<CustomerSalesRecords> list, boolean append, int total);

        /**
         * 显示汇总数据
         * @param customerSalesResp
         */
        void showCustomerShopGatherDatas(CustomerSalesResp customerSalesResp);
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
        CustomerSaleReq getParams();

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

    interface ICustomerShopDetailPresenter extends IPresenter<ICustomerShopDetailView> {
        /**
         * 加载客户销售汇总，门店明细
         *
         * @param showLoading true-显示对话框
         */
        void queryCustomerShopDetailList(boolean showLoading);

        /**
         * 加载更多客户销售汇总，门店明细
         */
        void queryMoreCustomerShopDetailList();

        /**
         * 导出日志
         *
         * @param email 邮箱地址
         */
        void exportCustomerShopDetail(String email, String reqParams);
    }
}
