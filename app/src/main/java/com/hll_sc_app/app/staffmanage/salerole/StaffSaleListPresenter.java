package com.hll_sc_app.app.staffmanage.salerole;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.staff.EmployeeBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 员工列表——关联门店_选择接收人
 */
public class StaffSaleListPresenter implements StaffSaleListContract.IPresent {
    private final int pageSize = 20;
    private StaffSaleListContract.IView mView;
    private int mPageNum = 1;
    private int mTempPageNum = 1;

    static StaffSaleListPresenter newInstance() {
        return new StaffSaleListPresenter();
    }

    @Override
    public void start() {

    }

    @Override
    public void register(StaffSaleListContract.IView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryEmployeeList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("pageNum", String.valueOf(mTempPageNum))
                .put("pageSize", String.valueOf(pageSize))
                .put("roleType", String.valueOf(1))
                .put("keyword", mView.getSearchContent())
                .create();
        CooperationPurchaserService.INSTANCE
                .queryEmployeeList(req)
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
                        mPageNum = mTempPageNum;
                        mView.showEmployeeList(employeeBeans, mTempPageNum > 1);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mTempPageNum = mPageNum;
                        mView.showError(e);

                    }
                });
    }


    @Override
    public void refresh() {
        mTempPageNum = 1;
        queryEmployeeList(false);
    }

    @Override
    public void getMore() {
        mTempPageNum++;
        queryEmployeeList(false);
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
}
