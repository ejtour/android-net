package com.hll_sc_app.app.goods.add.selectproductowner;

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

public class SelectContractListPresent implements ISelectContractListContract.IPresent{

    private int page_num=1;
    private int page_num_temp=1;
    private static final int page_size= 20;

    private ISelectContractListContract.IView mView;

    @Override
    public void start() {
        //no-op
    }

    public static SelectContractListPresent newInstance(){
        return new SelectContractListPresent();
    }

    @Override
    public void queryList(boolean isLoading) {
        UserBean userBean= GreenDaoUtils.getUser();
        if (userBean==null){
            return;
        }
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("contractName", mView.getContractName())
                .put("groupID",userBean.getGroupID())
                .put("status", "2")
                .put("isSon", "1")
                .put("pageNum", String.valueOf(page_num_temp))
                .put("pageSize", String.valueOf(page_size))
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
                        mView.showList(resp.getList(), page_num_temp > 1);
                        page_num = page_num_temp;
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        page_num_temp = page_num;
                        mView.showError(e);
                    }
                });
    }

    @Override
    public void refresh() {
        page_num_temp  =1;
        queryList(false);
    }

    @Override
    public void quereMore() {
        page_num_temp++;
        queryList(false);
    }

    @Override
    public void register(ISelectContractListContract.IView view) {
        mView = view;
    }
}
