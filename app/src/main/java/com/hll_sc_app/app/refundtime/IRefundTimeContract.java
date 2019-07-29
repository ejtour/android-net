package com.hll_sc_app.app.refundtime;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.refundtime.RefundTimeBean;

import java.util.List;

public interface IRefundTimeContract {
    interface IView extends ILoadView {
        /**
         * 获取当前级别
         */
        Integer getLevel();

        /**
         * 获取adapter
         */
        RefundTimeAdapter getMAdapter();


        /**
         * 展示数据
         */
        void show(List<RefundTimeBean> list);
    }

    interface IPresent extends IPresenter<IView> {
        /**
         * 退货时效列表查询
         */
        void listRefundTime();

        /**
         * 退货时效保存
         */
        void setRefundTime();
    }
}
