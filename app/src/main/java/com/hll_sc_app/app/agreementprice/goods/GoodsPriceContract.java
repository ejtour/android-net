package com.hll_sc_app.app.agreementprice.goods;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 协议价管理-商品
 *
 * @author 朱英松
 * @date 2019/7/11
 */
interface GoodsPriceContract {

    interface IGoodsPriceView extends ILoadView {
        /**
         * 展示列表
         *
         * @param list 列表数据源
         */
        void showGoodsPriceList(List<QuotationDetailBean> list);

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
         * 分类选择数据
         *
         * @return 分类数据
         */
        String getCategoryId();

        /**
         * 店铺ID，多个逗号隔开
         *
         * @return 店铺ID
         */
        String getShopIds();

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

    interface IGoodsPricePresenter extends IPresenter<IGoodsPriceView> {
        /**
         * 加载商品数据
         *
         * @param showLoading true-显示加载
         */
        void queryGoodsPriceList(boolean showLoading);

        /**
         * 搜索合作采购商
         */
        void queryCooperationPurchaserList();

        /**
         * 导出商品数据到邮箱
         *
         * @param email 邮箱
         */
        void exportQuotation(String email);
    }
}
