package com.hll_sc_app.app.setting.cooperation;

import com.hll_sc_app.api.UserService;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.bean.user.GroupParame;
import com.hll_sc_app.bean.user.GroupParameReq;

import java.util.List;

/**
 * @author baitianqi baitianqi@hualala.com
 * 
 * version 1.0.0
 * Copyright (C) 2017-2018 hualala 
 *           This program is protected by copyright laws. 
 *           Program Name:哗啦啦商城
 *
 * Description:  
 * 
 * CreateTime: 2019/7/17 11:00
 *
 * Change History:
 *
 *        Date             CR Number              Name              Description of change
 * 
 */
public class CooperationSettingActivityPresenter implements ICooperationSettingActivityContract.ICooperationSettingPresentr {
    ICooperationSettingActivityContract.ICooperationSettingView mView;

    static CooperationSettingActivityPresenter newInstance() {
        return new CooperationSettingActivityPresenter();
    }

    @Override
    public void getCooperationSetting(String types) {
        if (types == null || types.length() == 0) {
            return;
        }
        GroupParameReq groupParameReq = new GroupParameReq();
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean != null) {
            groupParameReq.setGroupID(userBean.getGroupID());
            groupParameReq.setParameTypes(types);
            groupParameReq.setFlag("1");
            BaseReq<GroupParameReq> baseReq = new BaseReq<>();
            baseReq.setData(groupParameReq);
            UserService.INSTANCE
                    .queryGroupParameterInSetting(baseReq)
                    .compose(ApiScheduler.getObservableScheduler())
                    .map(new Precondition<>())
                    .doOnSubscribe(disposable -> mView.showLoading())
                    .doFinally(() -> {
                        if (mView.isActive()) {
                            mView.hideLoading();
                        }
                    }).subscribe(new BaseCallback<List<GroupParame>>() {
                @Override
                public void onSuccess(List<GroupParame> groupParamsList) {
                    mView.showCooperationSetting(groupParamsList);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    if (mView.isActive()) {
                        mView.showToast(e.getMessage());
                    }
                }
            });
        }
    }

    @Override
    public void changeCooperationSetting(boolean isChecked, Integer type) {
        GroupParame groupParame = new GroupParame();
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean != null) {
            groupParame.setGroupID(Long.parseLong(userBean.getGroupID()));
            groupParame.setParameType(type);
            groupParame.setParameValue(isChecked ? 2 : 1);
            BaseReq<GroupParame> baseReq = new BaseReq<>();
            baseReq.setData(groupParame);
            UserService.INSTANCE
                    .changeGroupParameterInSetting(baseReq)
                    .compose(ApiScheduler.getObservableScheduler())
                    .map(new Precondition<>())
                    .doOnSubscribe(disposable -> mView.showLoading())
                    .doFinally(() -> {
                        if (mView.isActive()) {
                            mView.hideLoading();
                        }
                    }).subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object result) {
                    mView.showToast(isChecked ? "已打开" : "已关闭");
                }

                @Override
                public void onFailure(UseCaseException e) {
                    if (mView.isActive()) {
                        mView.showToast(e.getMessage());
                        mView.returnCheckStatus(!isChecked, type);
                    }
                }
            });
        }
    }

    @Override
    public void start() {

    }

    @Override
    public void register(ICooperationSettingActivityContract.ICooperationSettingView view) {
        this.mView = view;
    }
}
