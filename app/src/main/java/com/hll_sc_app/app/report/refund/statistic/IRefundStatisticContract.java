package com.hll_sc_app.app.report.refund.statistic;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.refund.WaitRefundTotalResp;

/**
 * 退货合计
 * @author chukun
 * @date 2019/08/16
 */
public interface IRefundStatisticContract {

    interface IRefundStatisticView extends ILoadView {
        /**
         * 展示退货的合计信息
         * @param refundTotalResp
         */
        void showRefundedTotalInfo(WaitRefundTotalResp refundTotalResp);

    }

    interface IRefundStatisticPresenter extends IPresenter<IRefundStatisticView> {
        /**
         * 查询配送及时率饼图数据
         * @param showLoading true-显示对话框
         */
        void queryRefundedTotalInfo(boolean showLoading);
    }
}
