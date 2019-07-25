package com.hll_sc_app.app.agreementprice.quotation.add.ratiotemplate;

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
 * 选择协议价比例模板列表
 *
 * @author zhuyingsong
 * @date 2019/7/10
 */
public class QuotationRatioPresenter implements QuotationRatioContract.IQuotationRatioPresenter {
    private QuotationRatioContract.IQuotationRatioView mView;
    private int mPageNum;
    private int mTempPageNum;

    static QuotationRatioPresenter newInstance() {
        return new QuotationRatioPresenter();
    }

    @Override
    public void start() {
        queryRatioTemplateList(true);
    }

    @Override
    public void register(QuotationRatioContract.IQuotationRatioView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryRatioTemplateList(boolean showLoading) {
        mPageNum = 1;
        mTempPageNum = mPageNum;
        toQueryDepositProducts(showLoading);
    }

    @Override
    public void queryMoreRatioTemplateList() {
        mTempPageNum = mPageNum;
        mTempPageNum++;
        toQueryDepositProducts(false);
    }

    private void toQueryDepositProducts(boolean showLoading) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("pageNum", String.valueOf(mTempPageNum))
            .put("pageSize", "20")
            .create();
        PriceRatioTemplateService.INSTANCE.queryRatioTemplateList(req)
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
                    mView.showRatioTemplateList(resp.getRecords(), mPageNum != 1, total);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }
}
