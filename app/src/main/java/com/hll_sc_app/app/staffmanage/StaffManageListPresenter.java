package com.hll_sc_app.app.staffmanage;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.api.StaffManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.staff.StaffBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

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
        queryStaffList(true);
    }

    @Override
    public void register(StaffManageListContract.IStaffListView view) {
        this.mView = CommonUtils.checkNotNull(view);
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
    public void delStaff(String purchaserId) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("originator", "1")
            .put("purchaserID", purchaserId)
            .create();
        CooperationPurchaserService.INSTANCE.delCooperationPurchaser(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    queryStaffList(true);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    private void toQueryStaffList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("groupID", UserConfig.getGroupID())
            .put("searchParam", mView.getSearchParam())
            .create();
        StaffManageService.INSTANCE
            .queryStaffList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<StaffBean>>() {
                @Override
                public void onSuccess(List<StaffBean> list) {
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
