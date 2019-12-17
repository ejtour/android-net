package com.hll_sc_app.app.report.loss;

import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.loss.LossBean;
import com.hll_sc_app.impl.IExportView;

import java.util.List;

/**
 * 流失客户明细
 * @author chukun
 * @date 2019/08/16
 */
public interface ILossContract {

    interface ILossView extends IExportView {
        void setData(List<LossBean> list, boolean append);

        BaseMapReq.Builder getReq();
    }

    interface ILossPresenter extends IPresenter<ILossView> {
        void refresh();

        void loadMore();

        void export(String email);
    }
}
