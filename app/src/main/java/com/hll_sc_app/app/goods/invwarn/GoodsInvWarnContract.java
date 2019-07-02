package com.hll_sc_app.app.goods.invwarn;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.goods.HouseBean;

import java.util.List;

/**
 * 代仓商品库存预警
 *
 * @author zhuyingsong
 * @date 2019/7/2
 */
public interface GoodsInvWarnContract {

    interface IGoodsInvWarnView extends ILoadView {
        /**
         * 保存成功
         */
        void saveSuccess();

        /**
         * 显示仓库选择 window
         *
         * @param list list
         */
        void showHouseWindow(List<HouseBean> list);

        /**
         * 显示选中的仓库
         *
         * @param houseBean houseBean
         */
        void showSelectHouse(HouseBean houseBean);

        /**
         * 获取选中仓库的ID
         *
         * @return 选中仓库的ID
         */
        String getHouseId();

        /**
         * 获取搜索词
         *
         * @return 搜索词
         */
        String getName();

        /**
         * 展示商品库存列表
         *
         * @param list   list
         * @param append true-追加
         * @param total  indexList
         */
        void showGoodsInvList(List<GoodsBean> list, boolean append, int total);
    }

    interface IGoodsInvWarnPresenter extends IPresenter<IGoodsInvWarnView> {
        /**
         * 查询仓库下拉列表
         *
         * @param show 显示 window
         */
        void queryHouseList(boolean show);

        /**
         * 查询代仓商品库存预警列表
         *
         * @param showLoading true-显示 loading
         */
        void queryGoodsInvList(boolean showLoading);

        /**
         * 查询下一页代仓商品库存预警列表
         */
        void queryMoreGoodsInvList();

        /**
         * 搜索-查询代仓商品库存预警列表
         *
         * @param showLoading true-显示 loading
         */
        void searchGoodsInvList(boolean showLoading);

        /**
         * 搜索-查询下一页代仓商品库存预警列表
         */
        void searchMoreGoodsInvList();

        /**
         * 库存预警值设置
         *
         * @param list list
         */
        void setGoodsInvWarnValue(List<GoodsBean> list);
    }
}
