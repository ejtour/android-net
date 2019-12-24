package com.hll_sc_app.app.agreementprice.goods;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.user.CategoryResp;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * 协议价管理-商品
 *
 * @author 朱英松
 * @date 2019/7/11
 */
interface GoodsPriceContract {

    interface IGoodsPriceView extends IExportView {
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
         * 显示分类选择window
         *
         * @param resp 分类数据
         */
        void showCategoryWindow(CategoryResp resp);

        /**
         * 显示生效期限日期区间选择window
         */
        void showDateEffectWindow();

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
         * 展示门店数据
         *
         * @param list 门店列表
         */
        void showPurchaserShopList(List<PurchaserShopBean> list);
    }

    interface IGoodsPricePresenter extends IPresenter<IGoodsPriceView> {
        /**
         * 加载商品数据
         */
        void queryGoodsPriceList();

        /**
         * 搜索合作采购商
         */
        void queryCooperationPurchaserList();

        /**
         * 获取分类列表
         */
        void queryCategory();

        /**
         * 导出商品数据到邮箱
         *
         * @param email 邮箱
         */
        void exportQuotation(String email);

        /**
         * 查询采购商门店列表
         *
         * @param purchaserId 采购商 Id
         */
        void queryPurchaserShopList(String purchaserId);
    }
}
