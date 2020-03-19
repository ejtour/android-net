package com.hll_sc_app.app.marketingsetting.check.groups;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;

import java.util.ArrayList;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/4
 */

public class CheckGroupsPresenter implements ICheckGroupsContract.ICheckGroupsPresenter {
    private ICheckGroupsContract.ICheckGroupsView mView;

    @Override
    public void reqShopList(String id) {
        Common.queryCooperationShop(BaseMapReq.newBuilder()
                .put("groupID", UserConfig.getGroupID())
                .put("pageNo", "1")
                .put("pageSize", "9999")
                .put("purchaserID", id)
                .create(), new SimpleObserver<CooperationShopListResp>(mView) {
            @Override
            public void onSuccess(CooperationShopListResp cooperationShopListResp) {
                ArrayList<String> list = new ArrayList<>();
                if (!CommonUtils.isEmpty(cooperationShopListResp.getShopList())) {
                    for (PurchaserShopBean purchaserShopBean : cooperationShopListResp.getShopList()) {
                        list.add(purchaserShopBean.getShopName());
                    }
                }
                mView.handleData(list);
            }
        });
    }

    @Override
    public void register(ICheckGroupsContract.ICheckGroupsView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
