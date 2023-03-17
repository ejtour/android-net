package com.hll_sc_app.app.report.customersettle.detail;

import android.text.TextUtils;

import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.report.customersettle.CustomerSettleDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Report;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/26
 */

class CustomerSettleDetailPresenter implements ICustomerSettleDetailContract.ICustomerSettleDetailPresenter {
    private ICustomerSettleDetailContract.ICustomerSettleDetailView mView;

    public static CustomerSettleDetailPresenter newInstance() {
        return new CustomerSettleDetailPresenter();
    }

    private CustomerSettleDetailPresenter() {
    }

    @Override
    public void start() {
        Report.queryCustomerSettleDetail(mView.getReq().create(), new SimpleObserver<CustomerSettleDetailResp>(mView) {
            @Override
            public void onSuccess(CustomerSettleDetailResp customerSettleDetailResp) {
                mView.setData(customerSettleDetailResp);
            }
        });
    }

    @Override
    public void register(ICustomerSettleDetailContract.ICustomerSettleDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void export(String email) {
        ExportReq req = new ExportReq();
        req.setEmail(email);
        req.setIsBindEmail(!TextUtils.isEmpty(email) ? "1" : null);
        req.setTypeCode("voucher_list");
        req.setUserID(GreenDaoUtils.getUser().getEmployeeID());
        ExportReq.ParamsBean bean = new ExportReq.ParamsBean();
        bean.setVoucherList(mView.getReq().create().getData());
        req.setParams(bean);
        Common.exportExcel(req, Utils.getExportObserver(mView, "shopmall-supplier"),"shopmall-supplier");
    }
}
