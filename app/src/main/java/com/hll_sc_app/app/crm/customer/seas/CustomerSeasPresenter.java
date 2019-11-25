package com.hll_sc_app.app.crm.customer.seas;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/25
 */

public class CustomerSeasPresenter implements ICustomerSeasContract.ICustomerSeasPresenter {
    private ICustomerSeasContract.ICustomerSeasView mView;
    private int mPageNum;

    private CustomerSeasPresenter() {
    }

    public static CustomerSeasPresenter newInstance() {
        return new CustomerSeasPresenter();
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        load(false);
    }

    @Override
    public void loadMore() {
        load(false);
    }

    @Override
    public void start() {
        mPageNum = 1;
        load(true);
    }

    private void load(boolean showLoading) {
        Common.queryCooperationShop(BaseMapReq.newBuilder()
                .put("pageNo", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .put("groupID", UserConfig.getGroupID())
                .put("searchParams", mView.getSearchWords())
                .put("actionType", "CustomerHighSeas")
                .create(), new SimpleObserver<CooperationShopListResp>(mView, showLoading) {
            @Override
            public void onSuccess(CooperationShopListResp cooperationShopListResp) {
                mView.setData(cooperationShopListResp.getShopList(), mPageNum > 1);
                if (CommonUtils.isEmpty(cooperationShopListResp.getShopList())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(ICustomerSeasContract.ICustomerSeasView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
