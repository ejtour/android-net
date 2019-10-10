package com.hll_sc_app.app.cooperation.application.platform;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.CooperationPurchaserResp;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 合作采购商-我收到的申请-平台申请
 *
 * @author zhuyingsong
 * @date 2019/7/24
 */
public class CooperationPlatformPresenter implements CooperationPlatformContract.ICooperationPlatformPresenter {
    static final int PAGE_SIZE = 20;
    private int mPageNum;
    private int mTempNum;
    private List<PurchaserBean> mListPurchaser;
    private CooperationPlatformContract.ICooperationPlatformView mView;

    public static CooperationPlatformPresenter newInstance() {
        return new CooperationPlatformPresenter();
    }

    @Override
    public void start() {
        queryCooperationPlatformList(true);
    }

    @Override
    public void register(CooperationPlatformContract.ICooperationPlatformView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCooperationPlatformList(boolean showLoading) {
        mPageNum = 1;
        mTempNum = mPageNum;
        toQueryPlatformList(showLoading);
    }

    @Override
    public void queryMoreCooperationPlatformList() {
        mTempNum = mPageNum;
        mTempNum++;
        toQueryPlatformList(false);
    }


    private void toQueryPlatformList(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("actionType", "cooperationApplication")
            .put("groupID", UserConfig.getGroupID())
            .put("name", mView.getSearchParam())
            .put("pageNo", String.valueOf(mTempNum))
            .put("pageSize", "20")
            .put("originator", "1")
            .put("cooperationActive", "2")
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
                    mPageNum = mTempNum;
                    mView.showCooperationPlatformList(resp.getRecords(), mPageNum != 1);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
