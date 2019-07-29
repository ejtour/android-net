package com.hll_sc_app.app.deliverymanage.ageing.detail.period;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
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
    }

    interface IDeliveryPeriodPresenter extends IPresenter<IDeliveryPeriodView> {
        /**
         * 查询配送方式
         *
         * @param billUpDateTime 截单时间
         * @param flg            1-当日，2-其它日
         */
        void queryDeliveryPeriodList(String billUpDateTime, String flg);

        /**
         * 配送时效操作
         *
         * @param req 请求参数
         */
        void editDeliveryAgeing(BaseMapReq req);
    }
}
