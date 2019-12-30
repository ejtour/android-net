package com.hll_sc_app.app.contractmanage;

import com.hll_sc_app.api.ContractService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.contract.ContractListResp;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class ContractManagePresent implements IContractManageContract.IPresent {
    private IContractManageContract.IView mView;
    private final int PAGE_SIZE =20;
    private int pageNum = 1;
    private int pageTempNum = 1;

    public static ContractManagePresent newInstance() {
        return new ContractManagePresent();
    }

    @Override
    public void register(IContractManageContract.IView view) {
        mView = view;
    }


    @Override
    public void queryList(boolean isLoading) {
        UserBean userBean= GreenDaoUtils.getUser();
        if (userBean==null){
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("contractCode", mView.getContractCode())
                .put("contractName", mView.getContractName())
                .put("purchaserName", mView.getPurchaserName())
                .put("groupID",userBean.getGroupID())
                .put("isCloseToExpiration",mView.getDays())
                .put("signEndDate", mView.getSignTimeEnd())
                .put("signStartDate", mView.getSignTimeStart())
                .put("status", mView.getStatus())
                .put("pageNum", String.valueOf(pageTempNum))
                .put("pageSize", String.valueOf(PAGE_SIZE))

                .create();
        ContractService.INSTANCE.queryContractList(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doOnSubscribe(disposable -> mView.showLoading())
                .doFinally(() -> mView.hideLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<ContractListResp>() {
                    @Override
                    public void onSuccess(ContractListResp resp) {
                        mView.querySuccess(resp, pageTempNum > 1);
                        pageNum = pageTempNum;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        pageTempNum = pageNum;
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void queryMore() {
        pageTempNum++;
        queryList(false);
    }

    @Override
    public void refresh() {
        pageTempNum = 1;
        queryList(false);

    }

    @Override
    public int getPageSize() {
        return PAGE_SIZE;
    }
}
