package com.hll_sc_app.app.crm.daily.edit;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.daily.DailyBean;
import com.hll_sc_app.bean.daily.DailyEditReq;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Daily;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/14
 */

public class CrmDailyEditPresenter implements ICrmDailyEditContract.ICrmDailyEditPresenter {
    private ICrmDailyEditContract.ICrmDailyEditView mView;
    private DailyEditReq mReq;

    private CrmDailyEditPresenter(DailyEditReq req) {
        mReq = req;
    }

    public static CrmDailyEditPresenter newInstance(DailyEditReq req) {
        return new CrmDailyEditPresenter(req);
    }

    @Override
    public void submit() {
        Daily.editDaily(mReq, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.success();
            }
        });
    }

    @Override
    public void reqReceiverList() {
        UserBean user = GreenDaoUtils.getUser();
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", user.getGroupID())
                .put("pageNum", "1")
                .put("pageSize", "40")
                .put("roleCode", "SalesManager")
                .put("roleType", "1")
                .create();
        SimpleObserver<List<EmployeeBean>> observer = new SimpleObserver<List<EmployeeBean>>(mView) {
            @Override
            public void onSuccess(List<EmployeeBean> employeeBeans) {
                mView.cacheReceiverList(employeeBeans);
            }
        };
        CooperationPurchaserService.INSTANCE.queryEmployeeList(req)
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void start() {
        reqReceiverList();
        Daily.queryDaily(true, 1, 1,
                null, null, null, null,
                new SimpleObserver<SingleListResp<DailyBean>>(mView) {
                    @Override
                    public void onSuccess(SingleListResp<DailyBean> dailyBeanSingleListResp) {
                        mView.updateLastCommit(dailyBeanSingleListResp.getRecords());
                    }
                });
    }

    @Override
    public void register(ICrmDailyEditContract.ICrmDailyEditView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
