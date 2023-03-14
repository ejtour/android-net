package com.hll_sc_app.app.order.summary;

import static com.uber.autodispose.AutoDispose.autoDisposable;

import android.os.Handler;
import android.text.TextUtils;

import com.hll_sc_app.api.CommonService;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.export.ExportReq;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.order.summary.SummaryPurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.impl.IExportView;
import com.hll_sc_app.rest.Common;
import com.hll_sc_app.rest.Order;
import com.hll_sc_app.utils.Utils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/7
 */

public class OrderSummaryPresenter implements IOrderSummaryContract.IOrderSummaryPresenter {
    private IOrderSummaryContract.IOrderSummaryView mView;
    private int mPageNum;
    private SimpleObserver<ExportResp> observer;
    private ExportReq exportReq;

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
    public void export(String email, String source) {
        UserBean user = GreenDaoUtils.getUser();
        exportReq = new ExportReq();
        exportReq.setEmail(email);
        if(TextUtils.equals("shopmall-supplier-pc",source)){
            exportReq.setActionType("1");
        }
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
        order.setSubBillStatus(mView.getSubBillStatus());
        order.setRoleTypes(user.getAuthType());
        bean.setPendOrder(order);
        exportReq.setParams(bean);

        observer = getExportObserver(mView, source, 1);
        CommonService.INSTANCE
                .exportExcel(source, new BaseReq<>(exportReq))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    //单独处理 需要2次 第一次
    public SimpleObserver<ExportResp> getExportObserver(IExportView export, String source, int count) {
        final int[] loadCount = {count};
        return new SimpleObserver<ExportResp>(export) {

            @Override
            public void onSuccess(ExportResp resp) {
                ILoadView view = getView();
                if (!(view instanceof IExportView)) return;
                if (TextUtils.equals("shopmall-supplier-pc", source)) {
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
                } else {
                    if (!TextUtils.isEmpty(resp.getEmail()))
                        ((IExportView) view).exportSuccess(resp.getEmail());
                    else ((IExportView) view).exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
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
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Order.queryOrderSummary(mPageNum,
                mView.getSubBillStatus(),
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
