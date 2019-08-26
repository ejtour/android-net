package com.hll_sc_app.app.crm.home;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.home.ManagementShopResp;
import com.hll_sc_app.bean.home.StatisticResp;
import com.hll_sc_app.bean.home.VisitResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Home;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/20
 */

public class CrmHomePresenter implements ICrmHomeContract.ICrmHomePresenter {
    private ICrmHomeContract.ICrmHomeView mView;

    private CrmHomePresenter() {
    }

    public static CrmHomePresenter newInstance() {
        return new CrmHomePresenter();
    }

    @Override
    public void start() {
        load(true);
    }

    private void load(boolean showLoading) {
        Home.queryCrmHomeStatistic(0, new SimpleObserver<StatisticResp>(mView, showLoading) {
            @Override
            public void onSuccess(StatisticResp resp) {
                mView.updateHomeStatistic(resp);
            }
        });

        Home.queryVisitPlan(new SimpleObserver<VisitResp>(mView, showLoading) {
            @Override
            public void onSuccess(VisitResp resp) {
                mView.updateVisitPlan(resp);
            }
        });

        Home.queryCrmHomeStatistic(1, new SimpleObserver<StatisticResp>(mView, showLoading) {
            @Override
            public void onSuccess(StatisticResp resp) {
                mView.updateTrend(resp.getOrders());
            }
        });

        Home.queryManagementShopInfo(new SimpleObserver<ManagementShopResp>(mView, showLoading) {
            @Override
            public void onSuccess(ManagementShopResp resp) {
                mView.updateManagementShop(resp);
            }
        });
    }

    @Override
    public void refresh() {
        load(false);
    }

    @Override
    public void register(ICrmHomeContract.ICrmHomeView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
