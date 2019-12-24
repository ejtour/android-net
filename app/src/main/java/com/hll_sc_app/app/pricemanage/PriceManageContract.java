package com.hll_sc_app.app.pricemanage;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.common.WareHouseShipperBean;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.priceratio.RatioTemplateBean;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * 售价设置
 *
 * @author zhuyingsong
 * @date 2019/7/12
 */
public interface PriceManageContract {

    interface IPriceManageView extends IExportView {
        /**
         * 展示售价设置列表
         *
         * @param list   list
         * @param append true-追加
         * @param total  indexList
         */
        void showPriceManageList(List<SkuGoodsBean> list, boolean append, int total);

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getSearchParam();

        /**
         * 显示比例模板
         *
         * @param values 数据
         */
        void showRatioTemplateWindow(List<RatioTemplateBean> values);

        /**
         * 显示筛选弹窗
         *
         * @param resp resp
         */
        void showCustomCategoryWindow(CustomCategoryResp resp);

        /**
         * 获取过滤商品状态
         *
         * @return 上下架
         */
        String getProductStatus();

        /**
         * 获取过滤商品分类
         *
         * @return 商品分类
         */
        String getProductCategoryIds();

        /**
         * 获取过滤商品属性：自营代仓
         *
         * @return
         */
        int getIsWareHourse();

        String getOwnerID();

        void queryOwnersSuccess(List<WareHouseShipperBean> wareHouseShipperBeans);
    }

    interface IPriceManagePresenter extends IPresenter<IPriceManageView> {
        /**
         * 加载售价设置列表
         *
         * @param showLoading true-显示对话框
         */
        void queryPriceManageList(boolean showLoading);

        /**
         * 加载更多售价设置列表
         */
        void queryMorePriceManageList();

        /**
         * 商品价格修改服务
         *
         * @param bean         商品
         * @param productPrice 修改后的价格
         */
        void updateProductPrice(SkuGoodsBean bean, String productPrice);

        /**
         * 商品成本价修改
         *
         * @param bean      商品
         * @param costPrice 修改后的价格
         */
        void updateCostPrice(SkuGoodsBean bean, String costPrice);

        /**
         * 加载比例模板列表
         */
        void queryRatioTemplateList();

        /**
         * 查询自定义分类
         */
        void queryCustomCategory();

        /**
         * 导出邮箱
         *
         * @param email
         */
        void export(String email);

        void queryOwners();
    }
}
