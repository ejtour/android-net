package com.hll_sc_app.app.setting.priceratio;

import com.hll_sc_app.api.PriceRatioTemplateService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.priceratio.RatioTemplateResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 设置界面-价格比例设置-列表
 *
 * @author zhuyingsong
 * @date 2019/7/22
 */
public class PriceRatioTemplateListPresenter implements PriceRatioTemplateListContract.IPriceRatioPresenter {
    private PriceRatioTemplateListContract.IPriceRatioView mView;
    private int mPageNum;
    private int mTempPageNum;

    static PriceRatioTemplateListPresenter newInstance() {
        return new PriceRatioTemplateListPresenter();
    }

    @Override
    public void start() {
        queryPriceRatioTemplate(true);
    }

    @Override
    public void register(PriceRatioTemplateListContract.IPriceRatioView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPriceRatioTemplate(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryPriceRatio(showLoading);
    }

    @Override
    public void queryMorePriceRatioTemplate() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryPriceRatio(false);
    }

    @Override
    public void delPriceRatioTemplate(String templateId) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("templateID", templateId)
            .create();
        PriceRatioTemplateService.INSTANCE
            .delPriceRatioTemplate(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object resp) {
                    mView.showToast("比例模板删除成功");
                    queryPriceRatioTemplate(true);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    private void toQueryPriceRatio(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .put("templateType", mView.getTemplateType())
            .create();
        PriceRatioTemplateService.INSTANCE
            .queryRatioTemplateList(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> {
                if (showLoading) {
                    mView.showLoading();
                }
            })
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<RatioTemplateResp>() {
                @Override
                public void onSuccess(RatioTemplateResp resp) {
                    mPageNum = mTempPageNum;
                    int total = resp.getPageInfo() != null ? resp.getPageInfo().getTotal() : 0;
                    mView.showPriceRatioTemplate(resp.getRecords(), mPageNum != 1, total);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
