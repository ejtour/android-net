package com.hll_sc_app.app.agreementprice.goods.detail;

import com.hll_sc_app.api.AgreementPriceService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 协议价管理-商品详情
 *
 * @author zhuyingsong
 * @date 2019/7/11
 */
public class GoodsPriceDetailPresenter implements GoodsPriceDetailContract.IGoodsPriceDetailPresenter {
    private List<PurchaserBean> mListResp;
    private GoodsPriceDetailContract.IGoodsPriceDetailView mView;

    static GoodsPriceDetailPresenter newInstance() {
        return new GoodsPriceDetailPresenter();
    }

    @Override
    public void register(GoodsPriceDetailContract.IGoodsPriceDetailView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPriceUsePurchaser(String productSpecId) {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("groupID", UserConfig.getGroupID())
            .put("productSpecID", productSpecId)
            .create();
        AgreementPriceService.INSTANCE
            .queryPriceUsePurchaser(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<PurchaserBean>>() {
                @Override
                public void onSuccess(List<PurchaserBean> list) {
                    mListResp = list;
                    mView.showPurchaserList(mListResp);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public List<PurchaserBean> getPriceGoodsList() {
        return mListResp;
    }
}
