package com.hll_sc_app.app.deliverymanage.range;

import android.content.Context;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.delivery.DeliveryMinimumReq;
import com.hll_sc_app.bean.delivery.ProvinceListBean;
import com.hll_sc_app.bean.stockmanage.DepotRangeReq;

import java.util.List;

/**
 * 配送管理-配送范围
 *
 * @author zhuyingsong
 * @date 2019/08/01
 */
public interface DeliveryRangeContract {

    interface IDeliveryRangeView extends ILoadView {
        /**
         * 展示地区列表
         *
         * @param list 列表数据
         */
        void showAreaList(List<ProvinceListBean> list);

        /**
         * 展示已选的列表
         *
         * @param list 地区列表数据
         */
        void showSelectAreaList(List<ProvinceListBean> list);

        /**
         * 获取上下文对象
         *
         * @return context
         */
        Context getContext();

        void success();
    }

    interface IDeliveryRangePresenter extends IPresenter<IDeliveryRangeView> {
        /**
         * 配送范围查询
         */
        void queryDeliveryRange();

        /**
         * 编辑配送范围
         *
         * @param bean 配送范围
         */
        void editDeliveryMinimum(DeliveryMinimumReq bean);

        /**
         * 处理地区数据
         *
         * @param backList 选中的地区数据
         */
        void processAreaData(List<ProvinceListBean> backList);


        void setDepotRange(DepotRangeReq req);
    }
}
