package com.hll_sc_app.app.report.customerSale.saleDetail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.pricemanage.PriceLogBean;
import com.hll_sc_app.bean.report.req.CustomerSaleReq;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesRecords;
import com.hll_sc_app.bean.report.resp.bill.CustomerSalesResp;

import java.util.List;

/**
 * 售价设置-变更日志
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public interface CustomerSalesDetailContract {

    interface ICustomerSalesDetailView extends ILoadView {
        /**
         * 展示客户销售汇总，明细
         *
         * @param list   list
         * @param append true-追加
         * @param total  indexList
         */
        void showCustomerGatherDetailList(List<CustomerSalesRecords> list, boolean append, int total);

        /**
         * 显示汇总数据
         * @param customerSalesResp
         */
        void showCustomerSaleGatherDatas(CustomerSalesResp customerSalesResp);

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
    }

    interface ICustomerSaleDetailPresenter extends IPresenter<ICustomerSalesDetailView> {
        /**
         * 加载客户销售汇总，明细
         *
         * @param showLoading true-显示对话框
         */
        void queryCustomerGatherDetailList(boolean showLoading);

        /**
         * 加载更多客户销售汇总，明细
         */
        void queryMoreCustomerGatherDetailList();

        /**
         * 导出日志
         *
         * @param email 邮箱地址
         */
        void exportCustomerSaleDetail(String email, String reqParams);
    }
}
