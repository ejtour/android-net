package com.hll_sc_app.app.agreementprice.quotation.add;

import com.hll_sc_app.api.AgreementPriceService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 协议价管理-添加报价单
 *
 * @author zhuyingsong
 * @date 2019/7/9
 */
public class QuotationAddPresenter implements QuotationAddContract.IPurchasePresenter {
    private QuotationAddContract.IPurchaseView mView;

    static QuotationAddPresenter newInstance() {
        return new QuotationAddPresenter();
    }

    @Override
    public void register(QuotationAddContract.IPurchaseView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void addQuotation(QuotationReq req) {
        if (req == null) {
            return;
        }
        BaseReq<QuotationReq> baseReq = new BaseReq<>();
        baseReq.setData(req);
        AgreementPriceService.INSTANCE.addQuotation(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object result) {
                    mView.addSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    if (mView.isActive()) {
                        mView.showToast(e.getMessage());
                    }
                }
            });
    }
}
