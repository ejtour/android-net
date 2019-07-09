package com.hll_sc_app.app.agreementprice.quotation.add.purchaser;

import com.hll_sc_app.app.agreementprice.quotation.QuotationFragmentPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 选择合作采购商
 *
 * @author zhuyingsong
 * @date 2019/7/9
 */
public class PurchaserListPresenter implements PurchaserListContract.IPurchaserListPresenter {
    private PurchaserListContract.IPurchaserListView mView;

    static PurchaserListPresenter newInstance() {
        return new PurchaserListPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(PurchaserListContract.IPurchaserListView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCooperationPurchaserList(String searchParam) {
        QuotationFragmentPresenter.getCooperationPurchaserObservable(searchParam)
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<PurchaserBean>>() {
                @Override
                public void onSuccess(List<PurchaserBean> result) {
                    mView.showPurchaserList(result);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }

}
