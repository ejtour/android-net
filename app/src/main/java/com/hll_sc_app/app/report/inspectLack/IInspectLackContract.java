package com.hll_sc_app.app.report.inspectLack;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.report.inspectLack.InspectLackResp;
import com.hll_sc_app.bean.report.req.BaseReportReqParam;

/**
 * @author chukun
 * @since 2019/8/15
 */

public interface IInspectLackContract {
    interface IInspectLackView extends ILoadView {
        void setInspectList(InspectLackResp inspectLackResp, boolean append);

        BaseReportReqParam getRequestParams();

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

    interface IInspectLackPresenter extends IPresenter<IInspectLackContract.IInspectLackView> {

        void loadInspectLackList();

        void loadMore();

        /**
         * 导出
         *
         * @param email 邮箱地址
         */
        void exportInspectLack(String email, String reqParams);
    }
}
