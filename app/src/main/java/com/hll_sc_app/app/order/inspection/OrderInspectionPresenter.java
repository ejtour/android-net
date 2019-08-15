package com.hll_sc_app.app.order.inspection;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.order.inspection.OrderInspectionReq;
import com.hll_sc_app.bean.order.inspection.OrderInspectionResp;
import com.hll_sc_app.bean.user.GroupParame;
import com.hll_sc_app.bean.user.GroupParameReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.Order;
import com.uber.autodispose.android.lifecycle.AndroidLifecycleScopeProvider;

import java.util.List;

import static com.uber.autodispose.AutoDispose.autoDisposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/17
 */

public class OrderInspectionPresenter implements IOrderInspectionContract.IOrderInspectionPresenter {
    private IOrderInspectionContract.IOrderInspectionView mView;

    public static OrderInspectionPresenter newInstance() {
        return new OrderInspectionPresenter();
    }

    @Override
    public void start() {
        GroupParameReq req = new GroupParameReq();
        req.setGroupID(UserConfig.getGroupID());
        req.setParameTypes("15");
        SimpleObserver<List<GroupParame>> observer = new SimpleObserver<List<GroupParame>>(mView, false) {
            @Override
            public void onSuccess(List<GroupParame> groupParames) {
                Integer result = null;
                if (!CommonUtils.isEmpty(groupParames))
                    result = groupParames.get(0).getParameValue();
                mView.gotInspectionMode(result);
            }
        };
        UserService.INSTANCE.queryGroupParameterInSetting(new BaseReq<>(req))
                .compose(ApiScheduler.getDefaultObservableWithLoadingScheduler(observer))
                .as(autoDisposable(AndroidLifecycleScopeProvider.from(observer.getOwner())))
                .subscribe(observer);
    }

    @Override
    public void register(IOrderInspectionContract.IOrderInspectionView view) {
        mView = CommonUtils.requireNonNull(view);
    }

    @Override
    public void confirmOrder(OrderInspectionReq req) {
        Order.inspectionOrder(req, new SimpleObserver<OrderInspectionResp>(mView) {
            @Override
            public void onSuccess(OrderInspectionResp orderInspectionResp) {
                mView.confirmSuccess(orderInspectionResp);
            }
        });
    }
}
