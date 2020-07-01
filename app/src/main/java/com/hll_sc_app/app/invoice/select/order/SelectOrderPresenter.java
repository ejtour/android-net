package com.hll_sc_app.app.invoice.select.order;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.filter.DateParam;
import com.hll_sc_app.bean.invoice.InvoiceOrderResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Invoice;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/12
 */

public class SelectOrderPresenter implements ISelectOrderContract.ISelectOrderPresenter {
    private List<String> mShopIDList;
    private ISelectOrderContract.ISelectOrderView mView;
    private DateParam mParam;

    public static SelectOrderPresenter newInstance(DateParam param, List<String> shopIDList) {
        SelectOrderPresenter presenter = new SelectOrderPresenter();
        presenter.mParam = param;
        presenter.mShopIDList = shopIDList;
        return presenter;
    }

    private SelectOrderPresenter() {
    }

    @Override
    public void start() {
        Invoice.getRelevanceOrderList(
                mParam.getFormatStartDate(),
                mParam.getFormatEndDate(),
                mShopIDList,
                new SimpleObserver<InvoiceOrderResp>(mView) {
                    @Override
                    public void onSuccess(InvoiceOrderResp invoiceOrderResp) {
                        mView.updateOrderData(invoiceOrderResp);
                    }
                });
    }

    @Override
    public void register(ISelectOrderContract.ISelectOrderView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
