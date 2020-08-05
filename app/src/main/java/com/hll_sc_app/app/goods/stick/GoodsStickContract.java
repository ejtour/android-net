package com.hll_sc_app.app.goods.stick;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.CustomCategoryResp;
import com.hll_sc_app.bean.goods.GoodsBean;

import java.util.List;
import java.util.Map;

/**
 * 商品置顶管理
 *
 * @author zhuyingsong
 * @date 2019/7/1
 */
public interface GoodsStickContract {

    interface IGoodsStickView extends ILoadView {
        /**
         * 显示自定义分类
         *
         * @param resp 自定义分类
         */
        void showCustomCategoryList(CustomCategoryResp resp);

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
        String getShopProductCategorySubId();

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getName();

        /**
         * 保存成功
         */
        void saveSuccess();
    }

    interface IGoodsStickPresenter extends IPresenter<IGoodsStickView> {
        /**
         * 查询自定义分类
         */
        void queryCustomCategory();

        /**
         * 查询商品列表
         *
         * @param showLoading true显示加载对话框
         */
        void queryGoodsList(boolean showLoading);

        /**
         * 加载更多商品列表
         */
        void queryMoreGoodsList();

        /**
         * 商品置顶
         *
         * @param map 置顶数据
         */
        void goods2Top(Map<String, List<GoodsBean>> map, List<String> deleteIds);
    }
}
