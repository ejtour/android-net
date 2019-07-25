package com.hll_sc_app.app.cooperation.application.thirdpart;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.ThirdPartyPurchaserResp;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商-我收到的申请-第三方申请
 *
 * @author zhuyingsong
 * @date 2019/7/24
 */
public class CooperationThirdPartPresenter implements CooperationThirdPartContract.ICooperationThirdPartPresenter {
    static final int PAGE_SIZE = 20;
    private int mPageNum;
    private int mTempNum;
    private List<PurchaserBean> mListPurchaser;
    private CooperationThirdPartContract.ICooperationThirdPartView mView;

    public static CooperationThirdPartPresenter newInstance() {
        return new CooperationThirdPartPresenter();
    }

    @Override
    public void start() {
        queryCooperationThirdPartList(true);
    }

    @Override
    public void register(CooperationThirdPartContract.ICooperationThirdPartView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCooperationThirdPartList(boolean showLoading) {
        mPageNum = 1;
        mTempNum = mPageNum;
        toQueryThirdPartList(showLoading);
    }

    @Override
    public void queryMoreThirdPartList() {
        mTempNum = mPageNum;
        mTempNum++;
        toQueryThirdPartList(false);
    }


    private void toQueryThirdPartList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("pageNum", String.valueOf(mTempNum))
            .put("pageSize", "20")
            .put("groupName", mView.getSearchParam())
            .put("plateSupplierID", UserConfig.getGroupID())
            .create();
        CooperationPurchaserService.INSTANCE
            .queryThirdPartPurchaserList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<ThirdPartyPurchaserResp>() {
                @Override
                public void onSuccess(ThirdPartyPurchaserResp resp) {
                    mPageNum = mTempNum;
                    mView.showCooperationThirdPartList(resp.getRecords(), mPageNum != 1, resp.getTotal());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
