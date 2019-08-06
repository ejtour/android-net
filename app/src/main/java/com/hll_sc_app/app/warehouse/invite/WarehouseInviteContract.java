package com.hll_sc_app.app.warehouse.invite;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 代仓公司-我的申请
 *
 * @author zhuyingsong
 * @date 2019/8/5
 */
public interface WarehouseInviteContract {

    interface IWarehouseInviteView extends ILoadView {
        /**
         * 采购商集团名称检索字段
         *
         * @return 搜索词
         */
        String getSearchParam();

        /**
         * 展示我的申请列表
         *
         * @param list     list
         * @param append   true-追加
         * @param totalNum 总数
         */
        void showWarehouseList(List<PurchaserBean> list, boolean append, int totalNum);
    }

    interface IWarehouseInvitePresenter extends IPresenter<IWarehouseInviteView> {
        /**
         * 查询我的申请数据列表
         *
         * @param showLoading true-显示 loading
         */
        void queryWarehouseList(boolean showLoading);

        /**
         * 查询下一页我的申请数据列表
         */
        void queryMoreWarehouseList();
    }
}
