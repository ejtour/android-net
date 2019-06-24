package com.hll_sc_app.app.goods.add.productattr.brand.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.ProductBrandBean;

import java.util.List;

/**
 * 商品品牌-新增
 *
 * @author zhuyingsong
 * @date 2019/6/24
 */
public interface ProductBrandAddContract {

    interface ISaleUnitNameAddView extends ILoadView {
        /**
         * 展示品牌列表
         *
         * @param list   list
         * @param append true-追加
         * @param total  indexList
         */
        void showProductBrandList(List<ProductBrandBean> list, boolean append, int total);
    }

    interface ISaleUnitNameAddPresenter extends IPresenter<ISaleUnitNameAddView> {
        /**
         * 查询品牌列表
         *
         * @param showLoading true-显示对话框
         */
        void queryProductBrandList(boolean showLoading);

        /**
         * 查询更多品牌列表
         */
        void queryMoreProductBrandList();
    }
}
