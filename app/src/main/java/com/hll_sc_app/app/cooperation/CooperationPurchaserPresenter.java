package com.hll_sc_app.app.cooperation;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商列表
 *
 * @author zhuyingsong
 * @date 2019/7/16
 */
public class CooperationPurchaserPresenter implements CooperationPurchaserContract.IGoodsRelevancePurchaserPresenter {
    private CooperationPurchaserContract.IGoodsRelevancePurchaserView mView;
    private List<HouseBean> mListHouseBean;
    private int mPageNum;
    private int mTempPageNum;

    static CooperationPurchaserPresenter newInstance() {
        return new CooperationPurchaserPresenter();
    }

    @Override
    public void start() {
        queryPurchaserList(true);
    }

    @Override
    public void register(CooperationPurchaserContract.IGoodsRelevancePurchaserView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryPurchaserList(showLoading);
    }

    @Override
    public void queryMorePurchaserList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryPurchaserList(false);
    }

    @Override
    public void delCooperationPurchaser(String purchaserId) {
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
                    queryPurchaserList(true);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    private void toQueryPurchaserList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", "formalCooperation")
            .put("groupID", UserConfig.getGroupID())
            .put("name", mView.getSearchParam())
            .put("pageNo", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("originator", "1")
            .create();
        CooperationPurchaserService.INSTANCE.queryCooperationPurchaserList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<CooperationPurchaserResp>() {
                @Override
                public void onSuccess(CooperationPurchaserResp resp) {
                    mPageNum = mTempPageNum;
                    mView.showPurchaserList(resp, mPageNum != 1);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
