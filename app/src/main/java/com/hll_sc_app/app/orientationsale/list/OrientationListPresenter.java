package com.hll_sc_app.app.orientationsale.list;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.orientation.OrientationListRes;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class OrientationListPresenter implements IOrientationListContract.IOrientationListPresenter {

    private IOrientationListContract.IOrientationListView mView;
    private int mPageNum = 1;

    public void load(boolean showLoading) {
        SimpleObserver<OrientationListRes> observer = new SimpleObserver<OrientationListRes>(mView, showLoading) {
            @Override
            public void onSuccess(OrientationListRes orientationListRes) {
                mView.setView(orientationListRes.getRecords(), mPageNum > 1);
                if (CommonUtils.isEmpty(orientationListRes.getRecords())) return;
                mPageNum++;
            }
        };
        GoodsService.INSTANCE.getOrientationList(BaseMapReq.newBuilder()
                .put("pageNum", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .put("supplierID", UserConfig.getGroupID())
                .put("type", "1")
                .create())
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .subscribe(observer);
    }

    @Override
    public void register(IOrientationListContract.IOrientationListView view) {
        this.mView = view;
    }

    static OrientationListPresenter newInstance() {
        return new OrientationListPresenter();
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
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
