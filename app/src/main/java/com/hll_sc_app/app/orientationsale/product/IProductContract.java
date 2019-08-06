package com.hll_sc_app.app.orientationsale.product;

import com.hll_sc_app.app.agreementprice.quotation.add.goods.GoodsQuotationSelectContract;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SkuGoodsBean;
import com.hll_sc_app.bean.user.CategoryResp;

import java.util.List;

public interface IProductContract {

    interface IProductView extends ILoadView {

        /**
         * 显示商城二级分类
         *
         * @param resp 自定义分类
         */
        void showCategoryList(CategoryResp resp);

        /**
         * 展示商品列表
         *
         * @param list   列表
         * @param append 追加
         */
        void showList(List<GoodsBean> list, boolean append);

        /**
         * 获取分类 ID
         *
         * @return ID
         */
        String getCategorySubId();

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getName();
    }

    interface IProductPresenter extends IPresenter<IProductContract.IProductView> {
        /**
         * 查询自定义分类
         */
        void queryCategory();

        /**
         * 查询商品列表
         */
        public void queryGoodsList(Integer pageNo, boolean showLoadIng);
    }
}
