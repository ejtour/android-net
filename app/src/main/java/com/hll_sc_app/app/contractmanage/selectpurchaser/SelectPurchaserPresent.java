package com.hll_sc_app.app.contractmanage.selectpurchaser;

import com.hll_sc_app.api.CooperationPurchaserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.common.SingleListResp;
import com.hll_sc_app.bean.cooperation.CooperationShopListResp;
import com.hll_sc_app.bean.cooperation.QueryGroupListResp;
import com.hll_sc_app.bean.customer.CustomerBean;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Common;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class SelectPurchaserPresent implements ISelectPurchaserContract.IPresent {

    private final int PAGE_SIZE = 20;
    private int pageNum = 1;
    private int pageTempNum = 1;

    private ISelectPurchaserContract.IView mView;

    static SelectPurchaserPresent newInstance() {
        return new SelectPurchaserPresent();
    }

    @Override
    public void register(ISelectPurchaserContract.IView view) {
        mView = view;
    }

    @Override
    public void queryList(boolean isLoading) {
        if (mView.getContractBean().isCooperation()) {//合作关系
            if (mView.isGroup()) {//合作集团
                queryCooperationGroupList(isLoading);
            } else {//合作门店
                queryShopList(isLoading);
            }
        } else {//意向客户
            searchIntentionCustomer(isLoading);
        }
    }


    @Override
    public void refresh() {
        pageTempNum = 1;
        queryList(false);
    }

    @Override
    public void quereMore() {
        pageTempNum++;
        queryList(false);
    }

    public void queryCooperationGroupList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        CooperationPurchaserService.INSTANCE
                .queryGroupList(BaseMapReq.newBuilder()
                        .put("actionType", "cooperation")
                        .put("requestOriginator", "1")
                        .put("resourceType", "1")
                        .put("pageSize", String.valueOf(PAGE_SIZE))
                        .put("pageNum", String.valueOf(pageTempNum))
                        .put("searchParams", mView.getSearchText())
                        .put("groupID", userBean.getGroupID())
                        .create())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<QueryGroupListResp>() {
                    @Override
                    public void onSuccess(QueryGroupListResp queryGroupListResp) {
                        pageNum = pageTempNum;
                        List<NameValue> list = new ArrayList<>();
                        if (!CommonUtils.isEmpty(queryGroupListResp.getGroupList())) {
                            for (PurchaserBean purchaserBean : queryGroupListResp.getGroupList()) {
                                NameValue bean = new NameValue(purchaserBean.getGroupName(), purchaserBean.getGroupID());
                                list.add(bean);
                            }
                        }
                        mView.querySuccess(list, pageTempNum > 1);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        pageTempNum = pageNum;
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void searchIntentionCustomer(boolean isLoading) {
        Common.searchIntentionCustomer(pageTempNum, mView.getSearchText(),
                new SimpleObserver<SingleListResp<CustomerBean>>(mView, isLoading) {
                    @Override
                    public void onSuccess(SingleListResp<CustomerBean> customerBeanSingleListResp) {
                        List<NameValue> list = new ArrayList<>();
                        if (!CommonUtils.isEmpty(customerBeanSingleListResp.getRecords())) {
                            for (CustomerBean record : customerBeanSingleListResp.getRecords()) {
                                NameValue bean = new NameValue(record.getCustomerName(), record.getId());
                                list.add(bean);
                            }
                        }
                        pageNum = pageTempNum;
                        mView.querySuccess(list, pageTempNum > 1);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        pageTempNum = pageNum;
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void queryShopList(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        CooperationPurchaserService.INSTANCE
                .queryShopList(BaseMapReq.newBuilder()
                        .put("actionType", "cooperation")
                        .put("groupID", userBean.getGroupID())
                        .put("cooperationActive", "0")
                        .put("isCooperation", "1")
                        .put("pageNo", String.valueOf(pageTempNum))
                        .put("pageSize", String.valueOf(PAGE_SIZE))
                        .put("purchaserID", mView.getContractBean().getPurchaserID())
                        .put("status", "2")
                        .put("shopName", mView.getSearchText())
                        .create())
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CooperationShopListResp>() {
                    @Override
                    public void onSuccess(CooperationShopListResp cooperationShopListResp) {
                        pageNum = pageTempNum;
                        List<NameValue> list = new ArrayList<>();
                        if (!CommonUtils.isEmpty(cooperationShopListResp.getShopList())) {
                            for (PurchaserShopBean shopBean : cooperationShopListResp.getShopList()) {
                                NameValue bean = new NameValue(shopBean.getShopName(), shopBean.getShopID());
                                list.add(bean);
                            }
                        }
                        mView.querySuccess(list, pageTempNum > 1);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        pageTempNum = pageNum;
                        mView.showError(e);
                    }
                });
    }
}
