package com.hll_sc_app.app.report.refund.wait;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.refund.WaitRefundTotalResp;

/**
 * 退货合计
 * @author chukun
 * @date 2019/08/16
 */
public interface WaitRefundTotalInfoContract {

    interface IRefundTotalInfoView extends ILoadView {
        /**
         * 展示待退货的合计信息
         * @param refundTotalResp
         */
        void showWaitRefundTotalInfo(WaitRefundTotalResp refundTotalResp);

    }

    interface IRefundTotalInfoPresenter extends IPresenter<IRefundTotalInfoView> {
        /**
         * 查询配送及时率饼图数据
         * @param showLoading true-显示对话框
         */
        void queryWaitRefundTotalInfo(boolean showLoading);
    }
}
