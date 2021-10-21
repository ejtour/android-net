package com.hll_sc_app.app.export;

import android.text.TextUtils;

import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportType;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IExportView;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.utils.Utils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/24
 */

class ExportPresenter implements IExportContract.IExportPresenter {
    private IExportView mView;

    public static ExportPresenter newInstance() {
        return new ExportPresenter();
    }

    private ExportPresenter() {
    }

    @Override
    public void export(ExportType type, String email) {
        if (type == ExportType.GOODS_TOTAL) {
            UserBean user = GreenDaoUtils.getUser();
            ExportReq exportReq = new ExportReq();
            exportReq.setEmail(email);
            exportReq.setIsBindEmail(!TextUtils.isEmpty(email) ? "1" : null);
            exportReq.setTypeCode("pend_delivery");
            exportReq.setUserID(user.getEmployeeID());
            ExportReq.ParamsBean bean = new ExportReq.ParamsBean();
            bean.setPendDelivery(new ExportReq.ParamsBean.PendDelivery(null, 2, null, null));
            exportReq.setParams(bean);
            Common.exportExcel(exportReq, Utils.getExportObserver(mView));
        } else if (type == ExportType.ASSEMBLY_ORDER) {
            Order.exportAssembly(null, 2,null, null, email, Utils.getExportObserver(mView));
        }
    }

    @Override
    public void register(IExportView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
