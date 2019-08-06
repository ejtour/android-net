package com.hll_sc_app.app.orientationsale.cooperation;

import com.hll_sc_app.app.agreementprice.quotation.QuotationFragmentPresenter;
import com.hll_sc_app.app.agreementprice.quotation.add.purchaser.PurchaserListPresenter;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.bean.agreementprice.quotation.QuotationBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.orientation.OrientationListBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class CooperationPresenter implements ICooperationContract.ICooperationPurchaserPresenter {

    private ICooperationContract.ICooperationPurchaserView mView;

    static CooperationPresenter newInstance() {
        return new CooperationPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(ICooperationContract.ICooperationPurchaserView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryCooperationPurchaserList() {
        QuotationFragmentPresenter.getCooperationPurchaserObservable(mView.getSearchParam())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<PurchaserBean>>() {
                    @Override
                    public void onSuccess(List<PurchaserBean> result) {
                        mView.showPurchaserList(transformPurchaserBean(result), false, 0);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showToast(e.getMessage());
                    }
                });
    }

    private List<OrientationListBean> transformPurchaserBean(List<PurchaserBean> list) {
        List<OrientationListBean> orientationListBeans = new ArrayList<>();
        if (!CommonUtils.isEmpty(list)) {
            for (PurchaserBean bean : list) {
                OrientationListBean orientationListBean = new OrientationListBean();
                orientationListBean.setPurchaserID(bean.getPurchaserID());
                orientationListBean.setPurchaserName(bean.getPurchaserName());
                orientationListBeans.add(orientationListBean);
            }
        }
        return orientationListBeans;
    }
}
