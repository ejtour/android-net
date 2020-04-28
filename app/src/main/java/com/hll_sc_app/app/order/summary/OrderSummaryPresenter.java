package com.hll_sc_app.app.order.summary;

import android.text.TextUtils;

import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.order.summary.SummaryPurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/7
 */

public class OrderSummaryPresenter implements IOrderSummaryContract.IOrderSummaryPresenter {
    private IOrderSummaryContract.IOrderSummaryView mView;
    private int mPageNum;

    public static OrderSummaryPresenter newInstance() {
        return new OrderSummaryPresenter();
    }
    private OrderSummaryPresenter() {
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void export(String email) {
        UserBean user = GreenDaoUtils.getUser();
        ExportReq exportReq = new ExportReq();
        exportReq.setEmail(email);
        exportReq.setIsBindEmail(!TextUtils.isEmpty(email) ? "1" : null);
        exportReq.setTypeCode("pend_order");
        exportReq.setUserID(user.getEmployeeID());
        ExportReq.ParamsBean bean = new ExportReq.ParamsBean();
        ExportReq.ParamsBean.PendOrder order = new ExportReq.ParamsBean.PendOrder();
        if (mView.getSearchType() == 1) {
            order.setShipperID(mView.getSearchId());
            order.setShipperName(mView.getSearchWords());
        } else {
            order.setPurchaserID(mView.getSearchId());
            order.setSearchWords(mView.getSearchWords());
        }
        bean.setPendOrder(order);
        exportReq.setParams(bean);
        Common.exportExcel(exportReq, Utils.getExportObserver(mView));
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Order.queryOrderSummary(mPageNum,
                mView.getSearchWords(),
                mView.getSearchId(),
                mView.getSearchType(),
                new SimpleObserver<SingleListResp<SummaryPurchaserBean>>(mView, showLoading) {
                    @Override
                    public void onSuccess(SingleListResp<SummaryPurchaserBean> summaryPurchaserBeanSingleListResp) {
                        mView.setData(summaryPurchaserBeanSingleListResp.getRecords(), mPageNum > 1);
                        if (CommonUtils.isEmpty(summaryPurchaserBeanSingleListResp.getRecords()))
                            return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void register(IOrderSummaryContract.IOrderSummaryView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
