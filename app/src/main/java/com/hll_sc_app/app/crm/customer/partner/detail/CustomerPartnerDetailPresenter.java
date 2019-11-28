package com.hll_sc_app.app.crm.customer.partner.detail;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/27
 */

public class CustomerPartnerDetailPresenter implements ICustomerPartnerDetailContract.ICustomerPartnerDetailPresenter {
    private ICustomerPartnerDetailContract.ICustomerPartnerDetailView mView;
    private int mPageNum;

    private CustomerPartnerDetailPresenter() {
    }

    public static CustomerPartnerDetailPresenter newInstance() {
        return new CustomerPartnerDetailPresenter();
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
        UserBean user = GreenDaoUtils.getUser();
        Common.queryCooperationShop(BaseMapReq.newBuilder()
                .put("actionType", "cooperation")
                .put("groupID", user.getGroupID())
                .put("pageNo", String.valueOf(mPageNum))
                .put("pageSize", "20")
                .put("purchaserID", mView.getPurchaserID())
                .put("searchParams", mView.getSearchWords())
                .put("salesmanID", mView.isAll() ? "" : user.getEmployeeID())
                .create(), new SimpleObserver<CooperationShopListResp>(mView, showLoading) {
            @Override
            public void onSuccess(CooperationShopListResp cooperationShopListResp) {
                mView.setData(cooperationShopListResp, mPageNum > 1);
                if (CommonUtils.isEmpty(cooperationShopListResp.getShopList())) return;
                mPageNum++;
            }
        });
    }

    @Override
    public void register(ICustomerPartnerDetailContract.ICustomerPartnerDetailView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
