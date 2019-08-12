package com.hll_sc_app.app.marketingsetting.product;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.bean.marketingsetting.MarketingListResp;
import com.hll_sc_app.bean.marketingsetting.MarketingStatusBean;

import java.util.List;

public interface IProductMarketingContract {

    interface IView extends ILoadView {

        /**
         * 获取促销状态成功
         */
        void showMarketingStatus(List<MarketingStatusBean> marketingStatusBeans);

        /**
         * 获取优惠名称
         */
        String getDiscountName();

        /**
         * 获取促销状态
         */
        String getDiscountStatus();

        /**
         * 展示列表
         */
        void showList(List<MarketingListResp.MarketingBean> marketingBeans);

        /**
         * 获取起始时间
         */
        String getStartTime();

        /**
         * 获取结束时间
         */
        String getEndTime();
    }


    interface IPresenter extends com.hll_sc_app.base.IPresenter<IView> {
        /**
         * 获取促销状态列表
         */
        void getMarketingStatus();

        /**
         * 获取商品促销列表
         */
        void getMarketingProductList(boolean isLoading);

        /**
         * 加载更多
         */
        void getMoreList();

        /**
         * 刷新
         */
        void refreshList();

        int getPageNum();
    }
}
