package com.hll_sc_app.app.blacklist.list;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.orientation.OrientationListRes;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class BlackListPresenter implements IBlackListContract.IBlackListPresenter {

    private IBlackListContract.IBlackListView mView;

    private Integer pageNum = 1;

    @Override
    public void getOrientation(Integer pageNum) {
        if(pageNum != null && pageNum != 0) {
            this.pageNum = pageNum;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("pageNum", pageNum.toString())
                .put("pageSize", "20")
                .put("type", "2")
                .put("supplierID", UserConfig.getGroupID())
                .create();
        GoodsService.INSTANCE.getOrientationList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<OrientationListRes>() {
                    @Override
                    public void onSuccess(OrientationListRes resp) {
                        mView.setView(resp.getRecords(), pageNum);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void register(IBlackListContract.IBlackListView view) {
        this.mView = view;
    }

    static BlackListPresenter newInstance() {
        return new BlackListPresenter();
    }

    @Override
    public void delOrientation(String mainID) {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("mainID", mainID)
                .create();
        GoodsService.INSTANCE.delOrientation(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<Object>() {
                    @Override
                    public void onSuccess(Object resp) {
                        mView.delSuccess();
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
