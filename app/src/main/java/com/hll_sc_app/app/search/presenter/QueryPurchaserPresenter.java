package com.hll_sc_app.app.search.presenter;

import android.text.TextUtils;

import com.hll_sc_app.api.CommonService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseResp;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.PurchaserBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.event.ShopSearchEvent;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

public class QueryPurchaserPresenter extends BaseSearchPresenter {

    @Override
    public void requestSearch(String searchWords) {

        if (TextUtils.isEmpty(searchWords)){
            Observable.timer(0, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .subscribe(aLong -> mView.refreshSearchData(new ArrayList<>()));
            return;
        }
        CommonService.INSTANCE
                .queryPurchaserList(BaseMapReq.newBuilder()
                        .put("ignoreGroupActive", "0")
                        .put("groupID", UserConfig.getGroupID())
                        .put("searchParam", searchWords).create())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
//                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<PurchaserBean>>() {
                    @Override
                    public void onSuccess(List<PurchaserBean> purchaserBeans) {
                        List<NameValue> list = new ArrayList<>();
                        if (!CommonUtils.isEmpty(purchaserBeans)) {
                            for (PurchaserBean purchaserBean : purchaserBeans) {
                                list.add(new NameValue(purchaserBean.getPurchaserName(), purchaserBean.getPurchaserID()));
                            }
                        }
                        mView.refreshSearchData(list);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
