package com.hll_sc_app.app.select.shop.goodsassign;

import com.hll_sc_app.app.agreementprice.quotation.add.purchaser.shop.PurchaserShopListActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/27
 */
public class SelectShopPresenter implements ISelectShopContract.ISelectShopPresenter {
    private ISelectShopContract.ISelectShopView mView;

    public static SelectShopPresenter newInstance() {
        return new SelectShopPresenter();
    }

    private SelectShopPresenter() {
    }

    @Override
    public void register(ISelectShopContract.ISelectShopView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void start() {
        Common.queryCooperationShop(BaseMapReq.newBuilder()
                .put("purchaserID", mView.getPurchaserID())
                .put("searchParams", mView.getSearchWords())
                .put("actionType","targetedSelling")
                .put("groupID", UserConfig.getGroupID())
                .put("pageNo", "1")
                .put("pageSize", "1000")
                .create(), new SimpleObserver<CooperationShopListResp>(mView) {
            @Override
            public void onSuccess(CooperationShopListResp cooperationShopListResp) {
                mView.setData(cooperationShopListResp.getShopList());
            }
        });
    }
}
