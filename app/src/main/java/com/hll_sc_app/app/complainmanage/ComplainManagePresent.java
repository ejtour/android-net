package com.hll_sc_app.app.complainmanage;

import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.complain.ComplainListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class ComplainManagePresent implements IComplainManageContract.IPresent {
    private IComplainManageContract.IView mView;

    private int pageSize = 20;
    private int pageNum = 1;
    private int pageNumTemp = 1;

    public static ComplainManagePresent newInstance() {
        return new ComplainManagePresent();
    }

    @Override
    public void register(IComplainManageContract.IView view) {
        mView = view;
    }


    @Override
    public void queryComplainList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq baseMapReq = BaseMapReq.newBuilder()
                .put("pageNum", pageNumTemp + "")
                .put("pageSize", pageSize + "")
                .put("status", mView.getComplaintStatus() + "")
                .put("supplyID", userBean.getGroupID())
                .put("target", "2")
                .create();

        ComplainManageService.INSTANCE
                .queryComplainList(baseMapReq)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<ComplainListResp>() {
                    @Override
                    public void onSuccess(ComplainListResp resp) {
                        mView.queryListSuccess(resp, pageNumTemp > 1);
                        pageNum = pageNumTemp;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                        pageNumTemp = pageNum;
                    }
                });

    }

    @Override
    public void getMore() {
        pageNumTemp++;
        queryComplainList(false);
    }

    @Override
    public void refresh() {
        pageNumTemp = 1;
        queryComplainList(false);
    }

    @Override
    public void export(String email) {

    }

    @Override
    public int getPageSize() {
        return pageSize;
    }
}
