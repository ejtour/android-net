package com.hll_sc_app.app.agreementprice.quotation.detail;

import android.text.TextUtils;

import com.hll_sc_app.api.AgreementPriceService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationDetailResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 协议价管理-报价单详情
 *
 * @author zhuyingsong
 * @date 2019/2/18
 */
public class QuotationDetailPresenter implements QuotationDetailContract.IPurchasePresenter {
    private QuotationDetailContract.IPurchaseView mView;
    private String mBillId;
    private String mBillNo;

    private QuotationDetailPresenter(String billId, String billNo) {
        this.mBillId = billId;
        this.mBillNo = billNo;
    }

    static QuotationDetailPresenter newInstance(String billId, String billNo) {
        return new QuotationDetailPresenter(billId, billNo);
    }

    @Override
    public void start() {
        getQuotationDetail();
    }

    @Override
    public void register(QuotationDetailContract.IPurchaseView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void getQuotationDetail() {
        if (TextUtils.isEmpty(mBillId)) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("billIDs", mBillId)
            .put("groupID", UserConfig.getGroupID())
            .create();
        AgreementPriceService.INSTANCE
            .queryQuotationDetail(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<QuotationDetailResp>() {
                @Override
                public void onSuccess(QuotationDetailResp result) {
                    mView.showGoodsDetail(result);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }

    @Override
    public void disableQuotation() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("billID", mBillId)
            .put("billNo", mBillNo)
            .put("groupID", UserConfig.getGroupID())
            .put("flag", "1")
            .create();
        AgreementPriceService.INSTANCE.disableQuotation(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object result) {
                    mView.disableQuotationSuccess();
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }
}
