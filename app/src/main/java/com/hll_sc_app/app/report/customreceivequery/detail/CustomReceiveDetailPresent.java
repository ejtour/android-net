package com.hll_sc_app.app.report.customreceivequery.detail;

import android.text.TextUtils;

import com.hll_sc_app.api.ReportService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.report.customreceivequery.CustomReceiveDetailBean;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.utils.Utils;

import java.util.List;

/***
 * 客户收货查询
 * */
public class CustomReceiveDetailPresent implements ICustomReceiveDetailContract.IPresent {
    private ICustomReceiveDetailContract.IView mView;

    public static CustomReceiveDetailPresent newInstance() {
        return new CustomReceiveDetailPresent();
    }

    @Override
    public void start() {
        load(true);
    }

    @Override
    public void register(ICustomReceiveDetailContract.IView view) {
        mView = view;
    }

    @Override
    public void refresh() {
        load(false);
    }

    @Override
    public void export(String email) {
        ExportReq req = new ExportReq();
        req.setEmail(email);
        req.setIsBindEmail(!TextUtils.isEmpty(email) ? "1" : null);
        req.setTypeCode("voucher_detail");
        req.setUserID(GreenDaoUtils.getUser().getEmployeeID());
        ExportReq.ParamsBean bean = new ExportReq.ParamsBean();
        ExportReq.ParamsBean.VoucherDetail detail = new ExportReq.ParamsBean.VoucherDetail();
        detail.setGroupID(mView.getOwnerId());
        detail.setVoucherID(mView.getVoucherId());
        bean.setVoucherDetail(detail);
        req.setParams(bean);
        Common.exportExcel(req, Utils.getExportObserver(mView));
    }

    private void load(boolean showLoading) {
        SimpleObserver<List<CustomReceiveDetailBean>> observer = new SimpleObserver<List<CustomReceiveDetailBean>>(mView, showLoading) {
            @Override
            public void onSuccess(List<CustomReceiveDetailBean> customReceiveDetailBeans) {
                mView.querySuccess(customReceiveDetailBeans);
            }
        };
        ReportService.INSTANCE
                .queryCustomReceiveDetail(BaseMapReq.newBuilder()
                        .put("groupID", mView.getOwnerId())
                        .put("voucherID", mView.getVoucherId())
                        .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);
    }
}
