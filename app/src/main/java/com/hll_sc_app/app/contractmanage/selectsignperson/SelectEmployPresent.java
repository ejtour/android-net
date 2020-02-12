package com.hll_sc_app.app.contractmanage.selectsignperson;

import com.hll_sc_app.api.StaffManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SelectEmployPresent implements ISelectEmployContract.IPresent {

    private final int PAGE_SIZE = 20;
    private int pageNum = 1;
    private int pageTempNum = 1;

    private ISelectEmployContract.IView mView;

    static SelectEmployPresent newInstance() {
        return new SelectEmployPresent();
    }

    @Override
    public void register(ISelectEmployContract.IView view) {
        mView = view;
    }

    @Override
    public void queryList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        StaffManageService.INSTANCE
                .queryStaffList(BaseMapReq.newBuilder()
                        .put("groupID", userBean.getGroupID())
                        .put("pageNum", String.valueOf(pageTempNum))
                        .put("pageSize", String.valueOf(PAGE_SIZE))
                        .put("searchParams", mView.getSearchText())
                        .create())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<EmployeeBean>>() {
                    @Override
                    public void onSuccess(List<EmployeeBean> employeeBeans) {
                        pageNum = pageTempNum;
                        mView.querySuccess(employeeBeans, pageTempNum > 1);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        pageTempNum = pageNum;
                        mView.showError(e);
                    }
                });
    }


    @Override
    public void refresh() {
        pageTempNum = 1;
        queryList(false);
    }

    @Override
    public void quereMore() {
        pageTempNum++;
        queryList(false);
    }
}
