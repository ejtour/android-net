package com.hll_sc_app.app.refundtime;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.refundtime.RefundTimeBean;
import com.hll_sc_app.bean.refundtime.RefundTimeResp;
import com.hll_sc_app.bean.refundtime.SetRefundTimeReq;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.ArrayList;
import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

public class RefundTimePresent implements IRefundTimeContract.IPresent {
    private IRefundTimeContract.IView mView;

    static RefundTimePresent newInstance() {
        return new RefundTimePresent();
    }

    @Override
    public void start() {
        listRefundTime();
    }

    @Override
    public void register(IRefundTimeContract.IView view) {
        this.mView = view;
    }

    @Override
    public void listRefundTime() {
        BaseMapReq req = BaseMapReq.newBuilder()
            .put("customerLevel", mView.getLevel().toString())
            .put("groupID", UserConfig.getGroupID())
            .create();
        UserService.INSTANCE.listRefundTime(req)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
            .subscribe(new BaseCallback<RefundTimeResp>() {
                @Override
                public void onSuccess(RefundTimeResp resp) {
                    mView.show(resp.getRecords());
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showError(e);
                }
            });
    }

    @Override
    public void setRefundTime() {
        RefundTimeAdapter mAdapter = mView.getMAdapter();
        List<RefundTimeBean> list = new ArrayList<>();
        for (int i = 0; ; i++) {
            RefundTimeBean refundTimeBean = mAdapter.getItem(i);
            if (refundTimeBean == null) {
                break;
            }
            list.add(refundTimeBean);
        }
        SetRefundTimeReq refundTimeReq = new SetRefundTimeReq();
        refundTimeReq.setCustomerLevel(mView.getLevel());
        refundTimeReq.setGroupID(Long.parseLong(UserConfig.getGroupID()));
        refundTimeReq.setRefunds(list);

        BaseReq<SetRefundTimeReq> req = new BaseReq<>();
        req.setData(refundTimeReq);
        UserService.INSTANCE.setRefundTime(req)
                .compose(ApiScheduler.getObservableScheduler())
                .map(new Precondition<>())
                .doFinally(() -> mView.hideLoading())
                .doOnSubscribe(disposable -> mView.showLoading())
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(mView.getOwner())))
                .subscribe(new BaseCallback<RefundTimeResp>() {
                    @Override
                    public void onSuccess(RefundTimeResp resp) {
                        mView.showToast("设置退货时效成功");
                    }

                    @Override
                    public void onFailure(UseCaseException e) {
                        mView.showError(e);
                    }
                });
    }
}
