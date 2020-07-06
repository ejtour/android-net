package com.hll_sc_app.app.report.customersettle.search;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.PurchaserShopBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/26
 */

class RDCSearchPresenter implements IRDCSearchContract.IRDCSearchPresenter {
    private IRDCSearchContract.IRDCSearchView mView;

    @Override
    public void requestSearch(String searchWords) {
        if (TextUtils.isEmpty(searchWords)) {
            Observable.timer(0, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .subscribe(aLong -> mView.refreshSearchData(new ArrayList<>()));
            return;
        }
        Common.queryPurchaserShopList(mView.getExtGroupID(), "SHOP_AND_DISTRIBUTION", searchWords,
                new SimpleObserver<List<PurchaserShopBean>>(mView, false) {
                    @Override
                    public void onSuccess(List<PurchaserShopBean> purchaserShopBeans) {
                        List<NameValue> list = new ArrayList<>();
                        if (!CommonUtils.isEmpty(purchaserShopBeans)) {
                            for (PurchaserShopBean bean : purchaserShopBeans) {
                                list.add(new NameValue(bean.getShopName(), bean.getExtShopID()));
                            }
                        }
                        mView.refreshSearchData(list);
                    }
                });
    }

    @Override
    public void register(IRDCSearchContract.IRDCSearchView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
