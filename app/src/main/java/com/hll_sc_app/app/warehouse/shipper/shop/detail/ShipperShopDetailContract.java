package com.hll_sc_app.app.warehouse.shipper.shop.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.warehouse.ShipperShopResp;

import java.util.List;

/**
 * 代仓门店管理详情
 *
 * @author zhuyingsong
 * @date 2019/8/7
 */
public interface ShipperShopDetailContract {

    interface IShipperShopDetailView extends ILoadView {
        /**
         * 代仓客户集团名称检索字段
         *
         * @return 搜索词
         */
        String getSearchParam();

        /**
         * 获取代仓集团Id
         *
         * @return 集团Id
         */
        String getWarehouseId();

        /**
         * 展示代仓客户集团列表
         *
         * @param list     list
         * @param append   true-追加
         * @param totalNum 总数
         */
        void showWarehouseList(List<ShipperShopResp.ShopBean> list, boolean append, int totalNum);
    }

    interface IShipperShopDetailPresenter extends IPresenter<IShipperShopDetailView> {
        /**
         * 查询签约关系列表
         *
         * @param showLoading true-显示 loading
         */
        void queryWarehouseList(boolean showLoading);

        /**
         * 查询下一页签约关系列表
         */
        void queryMoreWarehouseList();

        /**
         * 代仓编辑合作采购商
         *
         * @param bean       采购商
         * @param actionType 操作类型
         */
        void editWarehousePurchaser(ShipperShopResp.ShopBean bean, String actionType);
    }
}
