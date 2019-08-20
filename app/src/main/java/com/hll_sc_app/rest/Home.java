package com.hll_sc_app.rest;

import com.hll_sc_app.api.HomeService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.SalesVolumeResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/20
 */

public class Home {
    /**
     * 首页查询销售额
     *
     * @param date 标识：0-日,1-周,2-月
     */
    public static void querySalesVolume(int date, SimpleObserver<SalesVolumeResp> observer) {
        HomeService.INSTANCE
                .querySalesVolume(BaseMapReq.newBuilder()
                        .put("groupID", UserConfig.getGroupID())
                        .put("date", String.valueOf(date))
                        .put("version", "1").create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }
}
