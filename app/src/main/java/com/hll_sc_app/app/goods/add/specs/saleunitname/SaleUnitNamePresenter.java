package com.hll_sc_app.app.goods.add.specs.saleunitname;

import android.text.TextUtils;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.goods.SaleUnitNameBean;
import com.hll_sc_app.bean.goods.SaleUnitNameWrapper;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * 选择售卖单位
 *
 * @author zhuyingsong
 * @date 2019/6/19
 */
public class SaleUnitNamePresenter implements SaleUnitNameContract.ISaleUnitNameAddPresenter {
    private SaleUnitNameContract.ISaleUnitNameAddView mView;

    static SaleUnitNamePresenter newInstance() {
        return new SaleUnitNamePresenter();
    }

    @Override
    public void start() {
        querySaleUnitName();
    }

    @Override
    public void register(SaleUnitNameContract.ISaleUnitNameAddView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void querySaleUnitName() {
        BaseMapReq req = BaseMapReq.newBuilder().create();
        GoodsService.INSTANCE.querySaleUnitName(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<List<SaleUnitNameBean>>() {
                @Override
                public void onSuccess(List<SaleUnitNameBean> resp) {
                    mView.showSaleUnitNameList(processData(resp));
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    private List<SaleUnitNameWrapper> processData(List<SaleUnitNameBean> list) {
        List<SaleUnitNameWrapper> wrapperList = new ArrayList<>();
        String title = null;
        for (SaleUnitNameBean saleUnitNameBean : list) {
            if (!TextUtils.equals(title, saleUnitNameBean.getNameFirstLetter())) {
                title = saleUnitNameBean.getNameFirstLetter();
                wrapperList.add(new SaleUnitNameWrapper(true, saleUnitNameBean.getNameFirstLetter()));
            }
            wrapperList.add(new SaleUnitNameWrapper(saleUnitNameBean));
        }
        return wrapperList;
    }
}
