package com.hll_sc_app.app.goods.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.SpecsBean;

import java.util.List;

/**
 * 商品详情
 *
 * @author zhuyingsong
 * @date 2019/6/13
 */
public interface GoodsDetailContract {

    interface IGoodsDetailView extends ILoadView {
        /**
         * 显示商品详情
         *
         * @param bean GoodsBean
         */
        void showDetail(GoodsBean bean);

        /**
         * 获取商品 ID
         *
         * @return 商品 ID
         */
        String getProductId();
    }

    interface IGoodsDetailPresenter extends IPresenter<IGoodsDetailView> {
        /**
         * 查询商品详情
         */
        void queryGoodsDetail();

        /**
         * 商品规格状态修改
         *
         * @param list 规格
         */
        void updateSpecStatus(List<SpecsBean> list);
    }
}
