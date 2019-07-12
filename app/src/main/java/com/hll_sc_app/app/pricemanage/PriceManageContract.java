package com.hll_sc_app.app.pricemanage;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
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
    }
}
