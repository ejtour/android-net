package com.hll_sc_app.app.search.presenter;

import android.text.TextUtils;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.event.ShopSearchEvent;
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
 * @since 2019/7/25
 */

public class ShopSearchPresenter extends BaseSearchPresenter {

    @Override
    public void requestSearch(String searchWords) {
        if (TextUtils.isEmpty(searchWords)){
            Observable.timer(0, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .subscribe(aLong -> mView.refreshSearchData(new ArrayList<>()));
            return;
        }
        Common.searchShopList(searchWords, new SimpleObserver<SingleListResp<ShopSearchEvent>>(mView, false) {
            @Override
            public void onSuccess(SingleListResp<ShopSearchEvent> resp) {
                List<NameValue> list = new ArrayList<>();
                if (!CommonUtils.isEmpty(resp.getRecords())) {
                    for (ShopSearchEvent bean : resp.getRecords()) {
                        list.add(new NameValue(bean.getName(), bean.getShopMallId()));
                    }
                }
                mView.refreshSearchData(list);
            }
        });
    }
}
