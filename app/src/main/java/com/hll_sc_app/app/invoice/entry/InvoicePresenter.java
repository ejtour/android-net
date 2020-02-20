package com.hll_sc_app.app.invoice.entry;

import android.text.TextUtils;

import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.filter.DateParam;
import com.hll_sc_app.bean.invoice.InvoiceListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Invoice;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/10
 */

public class InvoicePresenter implements IInvoiceContract.IInvoicePresenter {
    private int mStatus;
    private int mPageNum;
    private DateParam mParam;
    private IInvoiceContract.IInvoiceView mView;

    public static InvoicePresenter newInstance(int status, DateParam param) {
        InvoicePresenter presenter = new InvoicePresenter();
        presenter.mStatus = status;
        presenter.mParam = param;
        return presenter;
    }

    @Override
    public void loadMore() {
        requestInvoiceList(false);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        requestInvoiceList(false);
    }

    @Override
    public void export(String email) {
        UserBean user = GreenDaoUtils.getUser();
        ExportReq exportReq = new ExportReq();
        exportReq.setEmail(email);
        exportReq.setIsBindEmail(!TextUtils.isEmpty(email) ? "1" : null);
        exportReq.setTypeCode("invoice");
        exportReq.setUserID(user.getEmployeeID());
        ExportReq.ParamsBean bean = new ExportReq.ParamsBean();
        ExportReq.ParamsBean.InvoiceParams invoiceParams = new ExportReq.ParamsBean.InvoiceParams();
        if (!UserConfig.crm()){
            if (mStatus > 1) {
                invoiceParams.setEndTime(mParam.getFormatEndDate());
                invoiceParams.setStartTime(mParam.getFormatStartDate());
            }
        }else {
            invoiceParams.setUserID(user.getEmployeeID());
        }
        invoiceParams.setGroupID(user.getGroupID());
        invoiceParams.setInvoiceStatus(mStatus);
        bean.setInvoice(invoiceParams);
        exportReq.setParams(bean);
        Common.exportExcel(exportReq, Utils.getExportObserver(mView));
    }

    private void requestInvoiceList(boolean showLoading) {
        Invoice.getInvoiceList(mStatus, mPageNum,
                mParam.getFormatStartDate(), mParam.getFormatEndDate(),
                new SimpleObserver<InvoiceListResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(InvoiceListResp invoiceListResp) {
                        mView.setListData(invoiceListResp.getRecords(), mPageNum > 1);
                        if (!CommonUtils.isEmpty(invoiceListResp.getRecords())) mPageNum++;
                    }
                });
    }

    @Override
    public void start() {
        mPageNum = 1;
        requestInvoiceList(true);
    }

    @Override
    public void register(IInvoiceContract.IInvoiceView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
