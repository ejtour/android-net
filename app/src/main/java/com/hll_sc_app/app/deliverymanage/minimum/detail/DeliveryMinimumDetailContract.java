package com.hll_sc_app.app.deliverymanage.minimum.detail;

import android.app.Activity;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.delivery.DeliveryMinimumBean;
import com.hll_sc_app.bean.delivery.DeliveryMinimumReq;
import com.hll_sc_app.bean.delivery.ProvinceListBean;

import java.util.List;

/**
 * 起送金额详情
 *
 * @author zhuyingsong
 * @date 2019/7/30
 */
public interface DeliveryMinimumDetailContract {

    interface IDeliveryMinimumDetailView extends ILoadView {
        /**
         * 展示地区列表
         *
         * @param list 列表数据
         */
        void showAreaList(List<ProvinceListBean> list);

        /**
         * 获取最小起购量分组Bean
         *
         * @return 起购量Bean
         */
        DeliveryMinimumBean getDeliveryMinimumBean();

        /**
         * 获取上下文对象
         *
         * @return 上下文对象
         */
        Activity getContext();

        /**
         * 编辑成功
         */
        void editSuccess();
    }

    interface IDeliveryMinimumDetailPresenter extends IPresenter<IDeliveryMinimumDetailView> {
        /**
         * 起送金额明细查询-地区
         */
        void queryDeliveryMinimumDetail();

        /**
         * 新增、编辑起送金额
         *
         * @param bean 起送金额分组
         */
        void editDeliveryMinimum(DeliveryMinimumReq bean);

        /**
         * 地区数据处理
         *
         * @param backList 选中地区数据
         */
        void processAreaData(List<ProvinceListBean> backList);
    }
}
