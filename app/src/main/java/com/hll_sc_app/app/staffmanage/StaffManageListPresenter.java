package com.hll_sc_app.app.staffmanage;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.api.StaffManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import io.reactivex.Observable;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 员工列表
 *
 * @author zhuyingsong
 * @date 2019/7/25
 */
public class StaffManageListPresenter implements StaffManageListContract.IStaffListPresenter {
    private StaffManageListContract.IStaffListView mView;
    private int mPageNum;
    private int mTempPageNum;

    static StaffManageListPresenter newInstance() {
        return new StaffManageListPresenter();
    }

    @Override
    public void start() {
        queryStaffNum();
        queryStaffList(true);
    }

    @Override
    public void register(StaffManageListContract.IStaffListView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryStaffNum() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("flag", "2")
                .put("groupID", userBean.getGroupID())
                .put("roleType", "1".equals(userBean.getCurRole()) ? "1" : "")
                .create();
        StaffManageService.INSTANCE
            .queryStaffNum(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<EmployeeBean>() {
                @Override
                public void onSuccess(EmployeeBean EmployeeBean) {
                    mView.showStaffNum(EmployeeBean.getEmployeeNum());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void queryStaffList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryStaffList(showLoading);
    }

    @Override
    public void queryMoreStaffList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryStaffList(false);
    }

    @Override
    public void delStaff(String employeeId, boolean confirm) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("employeeID", employeeId)
                .put("confirmFlag", confirm ? "1" : "")
                .create();
        StaffManageService.INSTANCE
                .delStaff(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object resp) {
                        mView.showToast("删除成功");
                        queryStaffList(true);
                        // 查询员工数量
                        queryStaffNum();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        if ("00120113117".equals(e.getCode())) {
                            mView.dropSea(employeeId, e.getMsg());
                        } else {
                            mView.showError(e);
                        }
                    }
                });
    }

    private void toQueryStaffList(boolean showLoading) {
        UserBean user = GreenDaoUtils.getUser();
        boolean crm = "1".equals(user.getCurRole());
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("pageNum", String.valueOf(mTempPageNum))
                .put("pageSize", "20")
                .put("groupID", user.getGroupID())
                .put("flag", "1")
                .put("roleType", crm ? "1" : "")
                .put(crm ? "keyword" : "searchParam", mView.getSearchParam())
                .create();
        Observable<BaseResp<List<EmployeeBean>>> observable;
        if (crm) observable = CooperationPurchaserService.INSTANCE.queryEmployeeList(req);
        else observable = StaffManageService.INSTANCE.queryStaffList(req);
        observable.compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (showLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<EmployeeBean>>() {
                    @Override
                    public void onSuccess(List<EmployeeBean> list) {
                        mPageNum = mTempPageNum;
                        mView.showStaffList(list, mPageNum != 1);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
