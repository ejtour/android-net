package com.hll_sc_app.app.pricemanage;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.agreementprice.quotation.RatioTemplateBean;
import com.hll_sc_app.bean.goods.SkuGoodsBean;

import java.util.List;

/**
 * 售价设置
 *
 * @author zhuyingsong
 * @date 2019/7/12
 */
public interface PriceManageContract {

    interface IPriceManageView extends ILoadView {
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
    }
}
