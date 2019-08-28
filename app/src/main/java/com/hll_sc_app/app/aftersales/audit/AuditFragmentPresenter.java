package com.hll_sc_app.app.aftersales.audit;

import com.hll_sc_app.base.bean.MsgWrapper;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.aftersales.AfterSalesBean;
import com.hll_sc_app.bean.filter.AuditParam;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.AfterSales;
import com.hll_sc_app.utils.Utils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public class AuditFragmentPresenter implements IAuditFragmentContract.IAuditFragmentPresenter {
    private IAuditFragmentContract.IAuditFragmentView mView;
    private int mPageNum;

    static AuditFragmentPresenter newInstance() {
        return new AuditFragmentPresenter();
    }

    @Override
    public void start() {
        mPageNum = 1;
        requestList(true);
    }

    @Override
    public void register(IAuditFragmentContract.IAuditFragmentView view) {
        mView = CommonUtils.checkNotNull(view);
    }

    private void requestList(boolean showLoading) {
        AuditParam param = mView.getAuditParam();
        AfterSales.requestAfterSalesList(param.getFormatStartDate(),
                param.getFormatEndDate(),
                mView.getBillStatus(),
                param.getPurchaserShopID(),
                param.getPurchaserID(),
                null,
                param.getSourceType(),
                mPageNum,
                new SimpleObserver<List<AfterSalesBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(List<AfterSalesBean> recordsBeans) {
                        mView.showList(recordsBeans, mPageNum > 1);
                        if (!CommonUtils.isEmpty(recordsBeans)) {
                            mPageNum++;
                        }
                    }
                });
    }

    public void requestDetails(String refundBillID) {
        AfterSales.requestAfterSalesDetail(
                refundBillID,
                new SimpleObserver<List<AfterSalesBean>>(mView) {
                    @Override
                    public void onSuccess(List<AfterSalesBean> recordsBeans) {
                        if (!CommonUtils.isEmpty(recordsBeans)) {
                            mView.updateItem(recordsBeans.get(0));
                        } else {
                            mView.showToast("获取数据失败");
                        }
                    }
                });
    }

    @Override
    public void exportOrder(String email) {
        AuditParam param = mView.getAuditParam();
        AfterSales.exportAfterSalesOrder(param.getFormatStartDate(),
                param.getFormatEndDate(),
                mView.getBillStatus(),
                param.getPurchaserShopID(),
                param.getPurchaserID(),
                param.getSourceType(),
                email,
                Utils.getExportObserver(mView));
    }


    @Override
    public void refresh() {
        mPageNum = 1;
        requestList(false);
    }

    @Override
    public void loadMore() {
        requestList(false);
    }

    @Override
    public void doAction(int actionType, String billID, int status, int type, String payType, String reason) {
        AfterSales.afterSalesAction(actionType, billID, status, type, payType, reason, null,
                new SimpleObserver<MsgWrapper<Object>>(true, mView) {
                    @Override
                    public void onSuccess(MsgWrapper<Object> objectMsgWrapper) {
                        mView.actionSuccess();
                    }
                });
    }
}
