package com.hll_sc_app.app.crm.home;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.IPresenter;
import com.hll_sc_app.bean.home.ManagementShopResp;
import com.hll_sc_app.bean.home.StatisticResp;
import com.hll_sc_app.bean.home.TrendBean;
import com.hll_sc_app.bean.home.VisitResp;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/20
 */

public interface ICrmHomeContract {
    interface ICrmHomeView extends ILoadView {
        void updateHomeStatistic(StatisticResp resp);

        void updateTrend(List<TrendBean> list);

        void updateVisitPlan(VisitResp resp);

        void updateManagementShop(ManagementShopResp resp);
    }

    interface ICrmHomePresenter extends IPresenter<ICrmHomeView> {
        void refresh();
    }
}
