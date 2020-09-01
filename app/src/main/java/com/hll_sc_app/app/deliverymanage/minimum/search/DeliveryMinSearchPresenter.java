package com.hll_sc_app.app.deliverymanage.minimum.search;

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
 * @since 2020/8/24
 */
public class DeliveryMinSearchPresenter implements IDeliveryMinSearchContract.IDeliveryMinSearchPresenter {
    private IDeliveryMinSearchContract.IDeliveryMinSearchView mView;

    private DeliveryMinSearchPresenter() {
    }

    public static DeliveryMinSearchPresenter newInstance() {
        return new DeliveryMinSearchPresenter();
    }

    @Override
    public void register(IDeliveryMinSearchContract.IDeliveryMinSearchView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void requestSearch(String searchWords) {
        if (TextUtils.isEmpty(searchWords)) {
            Observable.timer(0, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread())
                    .subscribe(aLong -> mView.refreshSearchData(new ArrayList<>()));
            return;
        }
        Common.orgSearch(searchWords, mView.getIndex(), "", new SimpleObserver<SingleListResp<ShopSearchEvent>>(mView, false) {
            @Override
            public void onSuccess(SingleListResp<ShopSearchEvent> shopSearchEventSingleListResp) {
                List<NameValue> list = new ArrayList<>();
                if (!CommonUtils.isEmpty(shopSearchEventSingleListResp.getRecords()))
                    for (ShopSearchEvent record : shopSearchEventSingleListResp.getRecords()) {
                        list.add(new NameValue(record.getName(), record.getShopMallId()));
                    }
                mView.refreshSearchData(list);
            }
        });
    }
}
