package com.hll_sc_app.app.complainmanage.add;

import com.hll_sc_app.api.ComplainManageService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.complain.DropMenuBean;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class ComplainMangeAddPresent implements IComplainMangeAddContract.IPresent {
    private IComplainMangeAddContract.IView mView;

    public static ComplainMangeAddPresent newInstance() {
        return new ComplainMangeAddPresent();
    }

    @Override
    public void register(IComplainMangeAddContract.IView view) {
        mView = view;
    }

    @Override
    public void queryDropMenus() {
        BaseMapReq req = BaseMapReq.newBuilder()
                .put("name", "ComplaintTypeEnum")
                .create();
        ComplainManageService.INSTANCE
                .queryDropMenus(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<List<DropMenuBean>>() {
                    @Override
                    public void onSuccess(List<DropMenuBean> dropMenuBeans) {
                        mView.queryMenuSuccess(dropMenuBeans);
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });

    }
}
