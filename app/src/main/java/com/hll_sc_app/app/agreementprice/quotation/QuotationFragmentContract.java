package com.hll_sc_app.app.agreementprice.quotation;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationResp;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * 协议价管理-报价单
 *
 * @author 朱英松
 * @date 2019/7/8
 */
interface QuotationFragmentContract {

    interface IHomeView extends IExportView {
        /**
         * 展示列表
         *
         * @param resp   列表数据源
         * @param append true-追加
         */
        void showQuotationList(QuotationResp resp, boolean append);

        /**
         * 显示采购商筛选window
         *
         * @param list 采购商数据
         */
        void showPurchaserWindow(List<PurchaserBean> list);

        /**
         * 显示报价日期区间选择window
         */
        void showDateWindow();

        /**
         * 显示生效期限日期区间选择window
         */
        void showDateEffectWindow();

        /**
         * 获取筛选供应商ID
         *
         * @return 分类ID
         */
        String getPurchaserId();

        /**
         * 获取报价开始时间
         *
         * @return 报价开始时间
         */
        String getStartDate();

        /**
         * 获取报价结束时间
         *
         * @return 报价结束时间
         */
        String getEndDate();

        /**
         * 获取生效开始日期
         *
         * @return 生效开始日期
         */
        String getPriceStartDate();

        /**
         * 获取生效结束日期
         *
         * @return 生效结束日期
         */
        String getPriceEndDate();

        /**
         * 当前Fragment位置搜索页面中
         *
         * @return true-搜索页面
         */
        boolean isSearchActivity();

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();

        /**
         * 返回订单号
         *
         * @return 订单号
         */
        List<String> getBillNos();
    }

    interface IHomePresenter extends IPresenter<IHomeView> {
        /**
         * 加载报价单数据
         *
         * @param showLoading true-显示加载
         */
        void queryQuotationList(boolean showLoading);

        /**
         * 加载更多报价单数据
         */
        void queryMoreQuotationList();

        /**
         * 搜索合作采购商
         */
        void queryCooperationPurchaserList();

        /**
         * 导出报价单到邮箱
         *
         * @param email 邮箱
         */
        void exportQuotation(String email);
    }
}
