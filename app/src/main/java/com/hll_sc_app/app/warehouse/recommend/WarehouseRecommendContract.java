package com.hll_sc_app.app.warehouse.recommend;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.goods.PurchaserBean;

import java.util.List;

/**
 * 代仓管理-我是货主默认介绍页面-查看推荐
 *
 * @author zhuyingsong
 * @date 2019/8/2
 */
public interface WarehouseRecommendContract {

    interface IWarehouseRecommendView extends ILoadView {
        /**
         * 显示列表
         *
         * @param list 集合数据
         */
        void showList(List<PurchaserBean> list);
    }

    interface IWarehouseRecommendPresenter extends IPresenter<IWarehouseRecommendView> {
        /**
         * 推荐代仓公司
         */
        void getRecommendWarehouse();
    }
}
