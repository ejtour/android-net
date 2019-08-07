package com.hll_sc_app.app.report.deliveryLack;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.deliveryLack.DeliveryLackGather;
import com.hll_sc_app.bean.report.deliveryLack.DeliveryLackGatherResp;
import com.hll_sc_app.bean.report.req.BaseReportReqParam;

import java.util.List;

/**
 * 业务员签约绩效
 *
 * @author 初坤
 * @date 2019/7/16
 */
public interface DeliveryLackGatherContract {

    interface IDeliveryLackGatherView extends ILoadView {
        /**
         * 展示缺货汇总表
         *
         * @param records list
         * @param append  true-追加
         * @param total   indexList
         */
        void showDeliveryLackGatherList(List<DeliveryLackGather> records, boolean append, int total);

        /**
         * 显示汇总数据
         *
         * @param deliveryLackGatherResp
         */
        void showTotalDeliveryGatherDatas(DeliveryLackGatherResp deliveryLackGatherResp);

        /**
         * 获取开始时间
         *
         * @return 开始时间
         */
        String getStartDate();

        /**
         * 获取结束时间
         *
         * @return 结束时间
         */
        String getEndDate();

        BaseReportReqParam getReqParams();

        /**
         * 导出成功
         *
         * @param email 邮箱地址
         */
        void exportSuccess(String email);

        /**
         * 导出失败
         *
         * @param tip 失败提示
         */
        void exportFailure(String tip);

        /**
         * 绑定邮箱
         */
        void bindEmail();

        void export(String email);
    }

    interface IDeliveryLackGatherPresenter extends IPresenter<IDeliveryLackGatherView> {
        /**
         * 加载业务员签约绩效
         *
         * @param showLoading true-显示对话框
         */
        void queryDeliveryLackGatherList(boolean showLoading);

        /**
         * 加载更多业务员签约绩效
         */
        void queryMoreDeliveryLackGatherList();

        /**
         * 导出业务员签约绩效
         *
         * @param email 邮箱地址
         */
        void exportDeliveryLackGather(String email, String reqParams);
    }
}
