package com.hll_sc_app.app.main;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SalesVolumeResp;
import com.hll_sc_app.bean.common.WeekSalesVolumeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Home;

import java.util.List;

/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
public class MainHomePresenter implements IMainHomeContract.IMainHomePresenter {
    private IMainHomeContract.IMainHomeView mView;

    private MainHomePresenter() {
    }

    public static MainHomePresenter newInstance() {
        return new MainHomePresenter();
    }

    @Override
    public void register(IMainHomeContract.IMainHomeView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void start() {
        load(true);
    }

    @Override
    public void load(boolean showLoading) {
        Home.querySalesVolume(mView.getDateType(), new SimpleObserver<SalesVolumeResp>(mView, showLoading) {
            @Override
            public void onSuccess(SalesVolumeResp resp) {
                mView.updateSalesVolume(resp);
            }
        });
        if (mView.getDateType() == IMainHomeContract.DateType.TYPE_DAY) {
            Home.queryWeekSalesVolume(new SimpleObserver<List<WeekSalesVolumeBean>>(mView, showLoading) {
                @Override
                public void onSuccess(List<WeekSalesVolumeBean> weekSalesVolumeBeans) {
                    mView.updateChartData(weekSalesVolumeBeans);
                }
            });
        }
    }
}
