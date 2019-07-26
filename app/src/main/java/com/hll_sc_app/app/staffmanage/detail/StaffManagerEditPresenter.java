package com.hll_sc_app.app.staffmanage.detail;

import com.hll_sc_app.api.StaffManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 员工列表-编辑员工
 *
 * @author zhuyingsong
 * @date 2018/7/25
 */
public class StaffManagerEditPresenter implements StaffManagerEditContract.IStaffManagerPresenter {
    private StaffManagerEditContract.IStaffManageEditView mView;

    static StaffManagerEditPresenter newInstance() {
        return new StaffManagerEditPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(StaffManagerEditContract.IStaffManageEditView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void editStaff(EmployeeBean resp) {
        if (resp == null) {
            return;
        }
        BaseReq<EmployeeBean> baseReq = new BaseReq<>(resp);
        StaffManageService.INSTANCE
            .editStaff(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object list) {
                    mView.editSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void addStaff(EmployeeBean resp) {

    }
}
