package com.hll_sc_app.app.order.deliver.modify;

import android.support.annotation.NonNull;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.bean.user.GroupParame;
import com.hll_sc_app.bean.user.GroupParameReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/13
 */

public class ModifyDeliverInfoPresenter implements IModifyDeliverInfoContract.IModifyDeliverInfoPresenter {
    private List<OrderDetailBean> mList;
    private String mSubBillID;
    private IModifyDeliverInfoContract.IModifyDeliverInfoView mView;

    private ModifyDeliverInfoPresenter(List<OrderDetailBean> list, String subBillID) {
        mList = list;
        mSubBillID = subBillID;
    }

    public static ModifyDeliverInfoPresenter newInstance(@NonNull List<OrderDetailBean> list, @NonNull String subBillID) {
        return new ModifyDeliverInfoPresenter(list, subBillID);
    }

    @Override
    public void modifyDeliverInfo() {
        Order.modifyDeliverInfo(mSubBillID, mList, new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.modifySuccess();
            }
        });
    }

    @Override
    public void start() {
        GroupParameReq req = new GroupParameReq();
        req.setGroupID(UserConfig.getGroupID());
        req.setParameTypes("7,10");
        SimpleObserver<List<GroupParame>> observer = new SimpleObserver<List<GroupParame>>(mView, false) {
            @Override
            public void onSuccess(List<GroupParame> groupParames) {
                int onNum = 0;
                if (!CommonUtils.isEmpty(groupParames)) {
                    for (GroupParame parame : groupParames) {
                        if (parame.getParameValue() == 2) {
                            onNum++;
                        }
                    }
                }
                if (onNum == 2) {
                    mView.modifyPrice();
                }
            }
        };
        UserService.INSTANCE.queryGroupParameterInSetting(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(IModifyDeliverInfoContract.IModifyDeliverInfoView view) {
        mView = CommonUtils.requireNonNull(view);
    }
}
