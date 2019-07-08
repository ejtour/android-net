package com.hll_sc_app.app.agreementprice.quotation;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationResp;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 协议价管理-报价单
 *
 * @author 朱英松
 * @date 2019/7/8
 */
interface QuotationFragmentContract {

    interface IHomeView extends ILoadView {
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
        void showSupplierWindow(List<PurchaserBean> list);

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
        String getSupplierID();

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
         * 获取供应商列表
         *
         * @return 供应商列表
         */
        List<PurchaserBean> getSupplierList();

        /**
         * 导出成功
         *
         * @param email 成功的邮箱地址
         */
        void exportSuccess(String email);

        /**
         * 失败
         *
         * @param message 失败
         */
        void exportFailure(String message);

        /**
         * 导出邮箱的时候未绑定邮箱提示
         */
        void unbindEmail();

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
         * 配送报价供应商查询
         *
         * @param show true-显示
         */
        void getSuppliers(boolean show);

        /**
         * 导出报价单到邮箱
         *
         * @param email 邮箱
         */
        void exportQuotation(String email);
    }
}
