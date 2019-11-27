package com.hll_sc_app.app.marketingsetting.coupon.selectshops;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.rest.Common;

public class SelectShopsPresent implements ISelectContract.ISearchShopsPresent {
    private ISelectContract.ISearchShopsView mView;

    public static SelectShopsPresent newInstance() {
        return new SelectShopsPresent();
    }

    @Override
    public void register(ISelectContract.ISearchShopsView view) {
        mView = view;
    }


    @Override
    public void getShops() {
        Common.queryCooperationShop(BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("pageNo", "1")
                .put("pageSize", "9999")
                .put("searchParams", mView.getSearchText())
                .put("purchaserID", mView.getPurchaserID())
                .put("actionType", "coupon").create(), new SimpleObserver<CooperationShopListResp>(mView) {
            @Override
            public void onSuccess(CooperationShopListResp cooperationShopListResp) {
                mView.showShops(cooperationShopListResp);
            }
        });
    }
}
