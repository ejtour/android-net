package com.hll_sc_app.app.report.voucherconfirm.detail;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveListResp;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/21
 */
public interface IVoucherConfirmDetailContract {
    interface IVoucherConfirmDetailView extends ILoadView {
        void setData(List<CustomReceiveListResp.RecordsBean> list, boolean append);

        void success();

        BaseMapReq.Builder getReq();
    }

    interface IVoucherConfirmDetailPresenter extends IPresenter<IVoucherConfirmDetailView> {
        void refresh();

        void loadMore();

        void confirm(String extGroupID, List<String> ids);
    }
}
