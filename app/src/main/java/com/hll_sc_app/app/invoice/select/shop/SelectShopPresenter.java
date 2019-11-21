package com.hll_sc_app.app.invoice.select.shop;

import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/12
 */

public class SelectShopPresenter implements ISelectShopContract.ISelectShopPresenter {
    private int mPageNum;
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

    private void search(boolean showLoading) {
        UserBean user = GreenDaoUtils.getUser();
        Common.queryCooperationShop(
                BaseMapReq.newBuilder()
                        .put("actionType", "invoice")
                        .put("groupID", user.getGroupID())
                        .put("pageNo", String.valueOf(mPageNum))
                        .put("pageSize", "20")
                        .put("salesmanID", user.getEmployeeID())
                        .put(mView.isShop() ? "searchParams" : "purchaserName", mView.getSearchWords())
                        .create(),
                new SimpleObserver<CooperationShopListResp>(mView, showLoading) {
                    @Override
                    public void onSuccess(CooperationShopListResp cooperationShopListResp) {
                        mView.setListData(cooperationShopListResp.getShopList(), mPageNum > 1);
                        if (CommonUtils.isEmpty(cooperationShopListResp.getShopList())) return;
                        mPageNum++;
                    }
                });
    }

    @Override
    public void start() {
        mPageNum = 1;
        search(true);
    }

    @Override
    public void refresh() {
        mPageNum = 1;
        search(false);
    }

    @Override
    public void loadMore() {
        search(false);
    }
}
