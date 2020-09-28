package com.hll_sc_app.app.report.voucherconfirm;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.report.voucherconfirm.VoucherGroupBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Report;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/9/21
 */
public class VoucherConfirmPresenter implements IVoucherConfirmContract.IVoucherConfirmPresenter {
    private IVoucherConfirmContract.IVoucherConfirmView mView;

    public static VoucherConfirmPresenter newInstance() {
        return new VoucherConfirmPresenter();
    }

    private VoucherConfirmPresenter() {
    }

    @Override
    public void start() {
        Report.queryVoucherGroups(mView.getReq().create(), new SimpleObserver<List<VoucherGroupBean>>(mView) {
            @Override
            public void onSuccess(List<VoucherGroupBean> voucherGroupBeans) {
                mView.setData(voucherGroupBeans);
            }
        });
    }

    @Override
    public void register(IVoucherConfirmContract.IVoucherConfirmView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
