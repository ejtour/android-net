package com.hll_sc_app.app.cooperation.detail;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserDetail;
import com.hll_sc_app.bean.cooperation.CooperationShopReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商详情
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public class CooperationDetailPresenter implements CooperationDetailContract.ICooperationDetailPresenter {
    private int mPageNum;
    private int mTempPageNum;
    private CooperationDetailContract.ICooperationDetailView mView;

    static CooperationDetailPresenter newInstance() {
        return new CooperationDetailPresenter();
    }

    @Override
    public void start() {
        queryPurchaserDetail(false);
    }

    @Override
    public void register(CooperationDetailContract.ICooperationDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserDetail(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryPurchaserDetail(showLoading);
    }

    @Override
    public void queryMorePurchaserDetail() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryPurchaserDetail(false);
    }

    @Override
    public void editCooperationPurchaserShop(CooperationShopReq req) {
        if (req == null) {
            return;
        }
        BaseReq<CooperationShopReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        CooperationPurchaserService.INSTANCE.addCooperationShop(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    queryPurchaserDetail(true);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    private void toQueryPurchaserDetail(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("pageNo", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("originator", "1")
            .put("groupID", UserConfig.getGroupID())
            .put("purchaserID", mView.getPurchaserId())
            .create();
        CooperationPurchaserService.INSTANCE.queryCooperationPurchaserDetail(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<CooperationPurchaserDetail>() {
                @Override
                public void onSuccess(CooperationPurchaserDetail resp) {
                    mPageNum = mTempPageNum;
                    mView.showPurchaserDetail(resp, mPageNum != 1);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
