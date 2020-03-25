package com.hll_sc_app.app.deliverymanage.ageing.book;

import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.user.GroupParamBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.rest.User;

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
        User.queryGroupParam("13", new SimpleObserver<List<GroupParamBean>>(mView) {
            @Override
            public void onSuccess(List<GroupParamBean> groupParamBeans) {
                if (!CommonUtils.isEmpty(groupParamBeans)) {
                    GroupParamBean parameter = groupParamBeans.get(0);
                    if (parameter.getParameType() == 13) {
                        mView.showBookingDate(String.valueOf(parameter.getParameValue()));
                    }
                }
            }
        });
    }

    @Override
    public void editGroupParam(String days) {
        User.changeGroupParam(13, CommonUtils.getInt(days), new SimpleObserver<Object>(mView) {
            @Override
            public void onSuccess(Object o) {
                mView.showToast("集团参数修改成功");
            }
        });
    }
}
