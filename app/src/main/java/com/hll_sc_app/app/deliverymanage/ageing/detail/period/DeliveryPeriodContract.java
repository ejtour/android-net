package com.hll_sc_app.app.deliverymanage.ageing.detail.period;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.delivery.DeliveryPeriodBean;

import java.util.List;

/**
 * 选择配送时段
 *
 * @author zhuyingsong
 * @date 2019/7/29
 */
public interface DeliveryPeriodContract {

    interface IDeliveryPeriodView extends ILoadView {
        /**
         * 展示配送时段列表
         *
         * @param list 配送时段数据源
         */
        void showList(List<DeliveryPeriodBean> list);

        /**
         * 获取截止时间
         *
         * @return 截止时间
         */
        String getBillUpDateTime();

        /**
         * 获取日期参数
         *
         * @return 1-当日，2-其它日
         */
        String getFlag();
    }

    interface IDeliveryPeriodPresenter extends IPresenter<IDeliveryPeriodView> {
        /**
         * 查询配送方式
         */
        void queryDeliveryPeriodList();

        /**
         * 配送时段操作
         *
         * @param bean           请求参数
         * @param operationType 操作类型，0-新增，1-更新，2-删除
         */
        void editDeliveryPeriod(DeliveryPeriodBean bean, String operationType);
    }
}
