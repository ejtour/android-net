package com.hll_sc_app.app.stockmanage.customersendmanage;

import com.hll_sc_app.api.GoodsService;
import com.hll_sc_app.api.StockManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.goods.HouseBean;
import com.hll_sc_app.bean.stockmanage.CustomerSendManageListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class CustomerSendManagePresent implements ICustomerSendManageContract.IPresent {

    private ICustomerSendManageContract.IView mView;

    private int pageSize = 20;
    private int pageNum = 1;
    private int pageNumTemp = 1;

    public static CustomerSendManagePresent newInstance() {
        return new CustomerSendManagePresent();
    }

    @Override
    public void start() {
        queryCustomerSendManageListResp(true);
        queryHouseList();
    }

    @Override
    public void register(ICustomerSendManageContract.IView view) {
        mView = view;
    }

    @Override
    public void queryHouseList() {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", userBean.getGroupID())
                .create();
        GoodsService.INSTANCE
                .queryHouseList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<HouseBean>>() {
                    @Override
                    public void onSuccess(List<HouseBean> houseBeans) {
                        mView.getHouseListSuccess(houseBeans);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }


    @Override
    public void queryCustomerSendManageListResp(boolean isLoading) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("pageNo", pageNumTemp + "")
                .put("pageSize", pageSize + "")
                .put("groupID", userBean.getGroupID())
                .put("houseID", mView.getHouseId())
                .put("searchParams", mView.getSearchContent())
                .create();
        StockManageService.INSTANCE
                .queryCustomerSendManageListResp(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<CustomerSendManageListResp>() {
                    @Override
                    public void onSuccess(CustomerSendManageListResp resp) {
                        mView.queryCustomerSendManageListSuccess(resp, pageNumTemp > 1);
                        pageNum = pageNumTemp;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                        pageNumTemp = pageNum;

                    }
                });

    }

    @Override
    public void getMore() {
        pageNumTemp++;
        queryCustomerSendManageListResp(false);
    }

    @Override
    public void refresh() {
        pageNumTemp = 1;
        queryCustomerSendManageListResp(false);
    }
}
