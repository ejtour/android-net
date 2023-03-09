package com.hll_sc_app.app.export;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;

import com.hll_sc_app.R;
import com.hll_sc_app.api.CommonService;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.dialog.MakeSureDialog;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.export.ExportType;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IExportView;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

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

    private SimpleObserver<ExportResp> observer;
    private ExportReq exportReq = new ExportReq();

    @Override
    public void export(ExportType type, String email, String source) {
        if (type == ExportType.GOODS_TOTAL) {
            UserBean user = GreenDaoUtils.getUser();
            exportReq = new ExportReq();
            exportReq.setActionType("1");
            exportReq.setEmail(email);
            exportReq.setIsBindEmail(!TextUtils.isEmpty(email) ? "1" : null);
            exportReq.setTypeCode("pend_delivery");
            exportReq.setUserID(user.getEmployeeID());
            ExportReq.ParamsBean bean = new ExportReq.ParamsBean();
            bean.setPendDelivery(new ExportReq.ParamsBean.PendDelivery(null, 2, null, null));
            exportReq.setParams(bean);
            observer = getExportObserver(mView, source, 1);
            CommonService.INSTANCE
                    .exportExcel(source, new BaseReq<>(exportReq))
                    .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                    .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                    .subscribe(observer);

        } else if (type == ExportType.ASSEMBLY_ORDER) {
            Order.exportAssembly(null, 2, null, null, email, Utils.getExportObserver(mView, source), source);
        }
    }


    //单独处理 需要2次 第一次
    public SimpleObserver<ExportResp> getExportObserver(IExportView export, String source, int count) {
        final int[] loadCount = {count};
        return new SimpleObserver<ExportResp>(export) {

            @Override
            public void onSuccess(ExportResp resp) {
                ILoadView view = getView();
                if (!(view instanceof IExportView)) return;
                if (TextUtils.equals("1", exportReq.getActionType())) {
                    post(exportReq, source, ++loadCount[0]);
                } else if (!TextUtils.isEmpty(resp.getUrl())) {
                    ((IExportView) view).exportReportID(resp.getUrl(), mView);
                } else if (10 > loadCount[0]) {
                    new Handler().postDelayed(() -> {
                        post(exportReq, source, ++loadCount[0]);
                    }, 200);
                } else {
                    ((IExportView) view).exportFailure("下载失败,请重试或发送到邮箱！");
                }
            }

            @Override
            public void onFailure(UseCaseException e) {
                ILoadView view = getView();
                if (!(view instanceof IExportView)) return;
                if ("00120112037".equals(e.getCode())) ((IExportView) view).bindEmail();
                else
                    ((IExportView) view).exportFailure(TextUtils.isEmpty(e.getMsg()) ? "噢，服务器暂时开了小差\n攻城狮正在全力抢修" : e.getMsg());
            }
        };
    }

    private void post(ExportReq exportReq, String source, int count) {
        observer = getExportObserver(mView, source, count);
        exportReq.setActionType("3");
        CommonService.INSTANCE
                .exportExcel(source, new BaseReq<>(exportReq))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(IExportView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
