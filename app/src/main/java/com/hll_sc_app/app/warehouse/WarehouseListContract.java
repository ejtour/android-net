package com.hll_sc_app.app.warehouse;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 代仓公司列表
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
public interface WarehouseListContract {

    interface IWarehouseListView extends ILoadView {
        /**
         * 代仓集团名称检索字段
         *
         * @return 搜索词
         */
        String getSearchParam();

        /**
         * 展示代仓集团列表
         *
         * @param list     list
         * @param append   true-追加
         * @param totalNum 总数
         */
        void showWarehouseList(List<PurchaserBean> list, boolean append, int totalNum);
    }

    interface IWarehouseListPresenter extends IPresenter<IWarehouseListView> {
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
         * 解除签约关系
         *
         * @param groupId 货主集团ID
         */
        void delWarehouseList(String groupId);
    }
}
