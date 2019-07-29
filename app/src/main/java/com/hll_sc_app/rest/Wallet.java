package com.hll_sc_app.rest;

import com.hll_sc_app.api.WalletService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.bean.wallet.details.DetailsExportReq;
import com.hll_sc_app.bean.wallet.details.DetailsListResp;
import com.hll_sc_app.bean.wallet.WalletStatusResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/27
 */

public class Wallet {
    /**
     * 查询钱包状态
     */
    public static void queryWalletStatus(SimpleObserver<WalletStatusResp> observer) {
        WalletService.INSTANCE.queryWalletStatus(BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("groupType", "1").create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 获取钱包明细列表
     *
     * @param pageNum      页码
     * @param beginTime    开始时间 yyyyMMdd
     * @param endTime      结束时间 yyyyMMdd
     * @param settleUnitID 结算主体id
     */
    public static void getWalletDetailsList(int pageNum, String beginTime, String endTime, String settleUnitID, SimpleObserver<DetailsListResp> observer) {
        WalletService.INSTANCE
                .getWalletDetailsList(BaseMapReq.newBuilder()
                        .put("pageNo", String.valueOf(pageNum))
                        .put("pageSize", "20")
                        .put("beginTime", beginTime)
                        .put("endTime", endTime)
                        .put("settleUnitID", settleUnitID).create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    /**
     * 导出钱包明细列表
     *
     * @param req 明细请求
     */
    public static void exportWalletDetailsList(DetailsExportReq req, SimpleObserver<ExportResp> observer) {
        WalletService.INSTANCE
                .exportWalletDetailsList(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
