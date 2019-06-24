package com.hll_sc_app.app.goods.add.productattr.brand.add;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.DepositProductBean;

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
         * 展示押金商品列表
         *
         * @param list   list
         * @param append true-追加
         * @param total  indexList
         */
        void showDepositProductsList(List<DepositProductBean> list, boolean append, int total);
    }

    interface ISaleUnitNameAddPresenter extends IPresenter<ISaleUnitNameAddView> {
        /**
         * 加载押金商品列表
         *
         * @param showLoading true-显示对话框
         */
        void queryDepositProducts(boolean showLoading);

        /**
         * 加载更多押金商品列表
         */
        void queryMoreDepositProducts();
    }
}
