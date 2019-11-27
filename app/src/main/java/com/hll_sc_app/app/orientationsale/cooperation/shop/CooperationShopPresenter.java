package com.hll_sc_app.app.orientationsale.cooperation.shop;

import com.hll_sc_app.app.agreementprice.quotation.add.purchaser.shop.PurchaserShopListActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;

import java.util.List;

public class CooperationShopPresenter implements ICooperationShopContract.ICooperationShopPresenter {

    private ICooperationShopContract.ICooperationShopView mView;

    static CooperationShopPresenter newInstance() {
        return new CooperationShopPresenter();
    }

    @Override
    public void start() {
        // no-op
    }

    @Override
    public void register(ICooperationShopContract.ICooperationShopView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryPurchaserShopList(String purchaserId) {
        Common.queryCooperationShop(BaseMapReq.newBuilder()
                .put("purchaserID", purchaserId)
                .put("searchParams", mView.getSearchParam())
                .put("actionType","targetedSelling")
                .put("groupID", UserConfig.getGroupID())
                .put("pageNo", "1")
                .put("pageSize", "1000")
                .create(), new SimpleObserver<CooperationShopListResp>(mView) {
            @Override
            public void onSuccess(CooperationShopListResp cooperationShopListResp) {
                List<PurchaserShopBean> list = cooperationShopListResp.getShopList();
                if (!CommonUtils.isEmpty(list)) {
                    PurchaserShopBean shopBean = new PurchaserShopBean();
                    shopBean.setShopName(PurchaserShopListActivity.STRING_ALL);
                    list.add(0, shopBean);
                }
                mView.showPurchaserShopList(list);
            }
        });
    }
}
