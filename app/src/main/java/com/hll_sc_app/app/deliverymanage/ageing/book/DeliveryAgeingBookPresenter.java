package com.hll_sc_app.app.deliverymanage.ageing.book;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.user.GroupParame;
import com.hll_sc_app.bean.user.GroupParameReq;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;


/**
 * 配送时效管理-预定设置
 *
 * @author 朱英松
 * @date 2019/7/29
 */
public class DeliveryAgeingBookPresenter implements DeliveryAgeingBookContract.IDeliveryAgeingBookPresenter {
    private DeliveryAgeingBookContract.IDeliveryAgeingBookView mView;

    public static DeliveryAgeingBookPresenter newInstance() {
        return new DeliveryAgeingBookPresenter();
    }

    @Override
    public void start() {
        queryGroupParam();
    }

    @Override
    public void register(DeliveryAgeingBookContract.IDeliveryAgeingBookView view) {
        this.mView = CommonUtils.checkNotNull(view);
    }

    @Override
    public void queryGroupParam() {
        GroupParameReq req = new GroupParameReq();
        req.setFlag("1");
        req.setParameTypes("13");
        req.setGroupID(UserConfig.getGroupID());
        BaseReq<GroupParameReq> baseReq = new BaseReq<>(req);
        UserService.INSTANCE
            .queryGroupParameterInSetting(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .subscribe(new BaseCallback<List<GroupParame>>() {
                @Override
                public void onSuccess(List<GroupParame> list) {
                    if (!CommonUtils.isEmpty(list)) {
                        GroupParame parameter = list.get(0);
                        if (parameter.getParameType() == 13) {
                            mView.showBookingDate(String.valueOf(parameter.getParameValue()));
                        }
                    }
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }

    @Override
    public void editGroupParam(String days) {
        GroupParame req = new GroupParame();
        req.setParameType(13);
        req.setParameValue(CommonUtils.getInt(days));
        req.setGroupID(CommonUtils.getLong(UserConfig.getGroupID()));
        BaseReq<GroupParame> baseReq = new BaseReq<>(req);
        UserService.INSTANCE
            .changeGroupParameterInSetting(baseReq)
            .compose(ApiScheduler.getObservableScheduler())
            .map(new Precondition<>())
            .doOnSubscribe(disposable -> mView.showLoading())
            .doFinally(() -> mView.hideLoading())
            .subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object o) {
                    mView.showToast("集团参数修改成功");
                }

                @Override
                public void onFailure(UseCaseException e) {
                    mView.showToast(e.getMessage());
                }
            });
    }
}
