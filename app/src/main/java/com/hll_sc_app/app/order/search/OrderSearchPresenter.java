package com.hll_sc_app.app.order.search;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.common.WareHouseShipperBean;
import com.hll_sc_app.bean.event.ShopSearchEvent;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/25
 */

public class OrderSearchPresenter implements IOrderSearchContract.IOrderSearchPresenter {
    private IOrderSearchContract.IOrderSearchView mView;

    @Override
    public void requestSearch(String searchWords) {
        if (mView.getIndex() == 0)
            Common.searchShopList(searchWords, new SimpleObserver<SingleListResp<ShopSearchEvent>>(mView, false) {
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
        else if (mView.getIndex() == 1)
            Common.searchShipperList(1, searchWords, new SimpleObserver<List<WareHouseShipperBean>>(mView, false) {
                @Override
                public void onSuccess(List<WareHouseShipperBean> wareHouseShipperBeans) {
                    List<NameValue> list = new ArrayList<>();
                    if (!CommonUtils.isEmpty(wareHouseShipperBeans)) {
                        for (WareHouseShipperBean bean : wareHouseShipperBeans) {
                            list.add(new NameValue(bean.getGroupName(), bean.getPurchaserID()));
                        }
                    }
                    mView.refreshSearchData(list);
                }
            });
    }

    @Override
    public void register(IOrderSearchContract.IOrderSearchView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
