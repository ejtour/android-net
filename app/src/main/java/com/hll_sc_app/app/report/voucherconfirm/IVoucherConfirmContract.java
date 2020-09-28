package com.hll_sc_app.app.report.voucherconfirm;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.bean.report.voucherconfirm.VoucherGroupBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/21
 */
public interface IVoucherConfirmContract {
    interface IVoucherConfirmView extends ILoadView{
        BaseMapReq.Builder getReq();

        void setData(List<VoucherGroupBean> list);
    }

    interface IVoucherConfirmPresenter extends IPresenter<IVoucherConfirmView>{

    }
}
