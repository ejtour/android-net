package com.hll_sc_app.app.marketingsetting.product.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.marketingsetting.AreaListBean;
import com.hll_sc_app.bean.marketingsetting.MarketingProductAddResp;
import com.hll_sc_app.bean.marketingsetting.RuleListBean;

import java.util.List;

public interface IProductMarketingAddContract {

    interface IView extends ILoadView {

        /**
         * 新增成功
         */
        void addSuccess(MarketingProductAddResp marketingProductAddResp);

        /**
         * 编辑成功
         */
        void modifySuccess(MarketingProductAddResp marketingProductAddResp);
        /**
         * 获取主题
         *
         * @return
         */
        String getMarketingTheme();

        /**
         * 获取开始时间
         *
         * @return
         */
        String getStartTime();

        /**
         * 获取结束时间
         *
         * @return
         */
        String getEndTime();

        /**
         * 获取活动商品
         *
         * @return
         */
        List<SkuGoodsBean> getProducts();

        /**
         * 获取促销规则类型
         */
        int getRuleType();

        /**
         * 获取具体规则数据
         */
        List<RuleListBean> getRuleList();


        /**
         * 获取地区
         * 平铺省市级
         */
        List<AreaListBean> getAreaList();


        /**
         * 获取区域范围:1 全国 2 地区
         */
        int getAreaScope();

        /**
         * 阶段优惠
         */
        int getDiscountStage();

        /**
         * 客户范围
         */
        int getCustomerScope();

        /**
         * 返回修改的id
         * @return
         */
        String getId();

        /**
         *
         */
        int getDiscountType();
    }



    interface IPresenter extends com.hll_sc_app.base.IPresenter<IView> {

        /**
         * 新增商品营销
         */
        void addMarketingProduct();

        /**
         * 编辑商品促销
         */
        void modifyMarketingProduct();
    }
}
