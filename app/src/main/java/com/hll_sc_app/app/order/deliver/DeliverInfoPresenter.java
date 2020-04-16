package com.hll_sc_app.app.order.deliver;

import android.text.TextUtils;

import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.order.deliver.DeliverInfoResp;
import com.hll_sc_app.bean.order.deliver.DeliverShopResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.utils.Utils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */

public class DeliverInfoPresenter implements IDeliverInfoContract.IDeliverInfoPresenter {
    private IDeliverInfoContract.IDeliverInfoView mView;

    private DeliverInfoPresenter() {
    }

    public static DeliverInfoPresenter newInstance() {
        return new DeliverInfoPresenter();
    }

    @Override
    public void requestShopList(String specID) {
        Order.getDeliverShop(mView.getSubBillStatus(), specID, mView.getStartDate(), mView.getEndDate(), new SimpleObserver<List<DeliverShopResp>>(mView) {
            @Override
            public void onSuccess(List<DeliverShopResp> list) {
                mView.updateShopList(list);
            }
        });
    }

    @Override
    public void export(String email) {
        UserBean user = GreenDaoUtils.getUser();
        ExportReq exportReq = new ExportReq();
        exportReq.setEmail(email);
        exportReq.setIsBindEmail(!TextUtils.isEmpty(email) ? "1" : null);
        exportReq.setTypeCode("pend_delivery");
        exportReq.setUserID(user.getEmployeeID());
        ExportReq.ParamsBean bean = new ExportReq.ParamsBean();
        bean.setPendDelivery(new ExportReq.ParamsBean.PendDelivery(mView.getSearchWords(), mView.getSubBillStatus(),
                mView.getStartDate(), mView.getEndDate()));
        exportReq.setParams(bean);
        Common.exportExcel(exportReq, Utils.getExportObserver(mView));
    }

    @Override
    public void start() {
        Order.getDeliverInfo(mView.getSubBillStatus(), mView.getStartDate(), mView.getEndDate(), new SimpleObserver<List<DeliverInfoResp>>(mView) {
            @Override
            public void onSuccess(List<DeliverInfoResp> list) {
                mView.updateInfoList(list);
            }
        });
    }

    @Override
    public void register(IDeliverInfoContract.IDeliverInfoView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
