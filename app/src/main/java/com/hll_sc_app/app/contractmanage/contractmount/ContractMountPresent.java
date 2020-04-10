package com.hll_sc_app.app.contractmanage.contractmount;

import com.hll_sc_app.api.ContractService;
import com.hll_sc_app.api.OrderService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.contract.ContractListResp;
import com.hll_sc_app.bean.contract.ContractMountBean;
import com.hll_sc_app.bean.order.OrderResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class ContractMountPresent implements IContractMountContract.IPresent {
    private IContractMountContract.IView mView;

    private int pageNum = 1;
    private int pageNumTemp = 1;
    private final int pageSize = 20;

    @Override
    public void register(IContractMountContract.IView view) {
        mView = view;
    }

    public static ContractMountPresent newInstance() {
        return new ContractMountPresent();
    }

    @Override
    public void getContractMount(String contractID) {
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", userBean.getGroupID())
                .put("contractID", contractID)
                .create();
        ContractService.INSTANCE.getContractMount(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<ContractMountBean>() {
                    @Override
                    public void onSuccess(ContractMountBean mountBean) {
                        mView.getContractMountSuccess(mountBean);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void getOrderList(boolean isLoading) {
        ContractListResp.ContractBean contractBean = mView.getContractBean();
        if (mView.getStartDate().compareTo(contractBean.getStartDate()) < 0) {
            mView.showToast("起始时间请大于" + CalendarUtils.getDateFormatString(contractBean.getStartDate(), "yyyyMMdd", "yyyy年MM月dd日"));
            return;
        } else if (mView.getEndDate().compareTo(contractBean.getEndDate()) > 0) {
            mView.showToast("结束时间请小于" + CalendarUtils.getDateFormatString(contractBean.getEndDate(), "yyyyMMdd", "yyyy年MM月dd日"));
            return;
        }

        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean == null) {
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("groupID", userBean.getGroupID())
                .put("purchaserIDs", contractBean.getPurchaserID())
                .put("shopID", contractBean.getShopID())
                .put("subBillNo", mView.getOrderNo())
                .put("subBillDateStart", mView.getStartDate())
                .put("subBillDateEnd", mView.getEndDate())
                .put("pageNum", String.valueOf(pageNumTemp))
                .put("pageSize", String.valueOf(pageSize))
                .create();

        OrderService.INSTANCE.getOrderList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> {
                    if (isLoading) {
                        mView.showLoading();
                    }
                })
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<OrderResp>>() {
                    @Override
                    public void onSuccess(List<OrderResp> orderResps) {
                        pageNum = pageNumTemp;
                        mView.getOrderListSuccess(orderResps, pageNumTemp > 1);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                        pageNumTemp = pageNum;
                    }
                });
    }

    @Override
    public void getMoreOrder() {
        pageNumTemp++;
        getOrderList(false);
    }

    @Override
    public void refreshOrder() {
        pageNumTemp = 1;
        getOrderList(false);
    }

    @Override
    public int getOrderPageSize() {
         return pageSize;
    }
}
