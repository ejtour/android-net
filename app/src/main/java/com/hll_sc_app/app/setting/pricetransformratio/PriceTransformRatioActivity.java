package com.hll_sc_app.app.setting.pricetransformratio;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.api.UserService;
import com.hll_sc_app.app.setting.remind.IRemindSettingContract;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseReq;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.ApiScheduler;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.http.Precondition;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.user.GroupParame;
import com.hll_sc_app.bean.user.GroupParameReq;
import com.kyleduo.switchbutton.SwitchButton;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;

/**
 * 价格根据转换率变价设置
 */
@Route(path = RouterConfig.SETTING_PRICE_TRANSFORM_RADIO)
public class PriceTransformRatioActivity extends BaseLoadActivity {
    @BindView(R.id.ars_switch)
    SwitchButton mSwitch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_setting_price_transform_radio);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        GroupParameReq groupParameReq = new GroupParameReq();
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean != null) {
            groupParameReq.setGroupID(userBean.getGroupID());
            groupParameReq.setParameTypes("26");
            groupParameReq.setFlag("1");
            BaseReq<GroupParameReq> baseReq = new BaseReq<>();
            baseReq.setData(groupParameReq);
            UserService.INSTANCE
                    .queryGroupParameterInSetting(baseReq)
                    .compose(ApiScheduler.getObservableScheduler())
                    .map(new Precondition<>())
                    .doOnSubscribe(disposable -> showLoading())
                    .doFinally(() -> {
                        if (isActive()) {
                            hideLoading();
                        }
                    }).subscribe(new BaseCallback<List<GroupParame>>() {
                @Override
                public void onSuccess(List<GroupParame> groupParamsList) {
                    GroupParame groupParame = groupParamsList.get(0);
                    mSwitch.setCheckedNoEvent(groupParame.getParameValue() == 2);
                }

                @Override
                public void onFailure(UseCaseException e) {
                    if (isActive()) {
                        showToast(e.getMessage());
                    }
                }
            });
        }
    }

    @OnCheckedChanged(R.id.ars_switch)
    public void onCheckedChanged(boolean isChecked) {
        GroupParame groupParame = new GroupParame();
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean != null) {
            groupParame.setGroupID(Long.parseLong(userBean.getGroupID()));
            groupParame.setParameType(26);
            groupParame.setParameValue(isChecked ? 2 : 1);
            BaseReq<GroupParame> baseReq = new BaseReq<>();
            baseReq.setData(groupParame);
            UserService.INSTANCE
                    .changeGroupParameterInSetting(baseReq)
                    .compose(ApiScheduler.getObservableScheduler())
                    .map(new Precondition<>())
                    .doOnSubscribe(disposable -> showLoading())
                    .doFinally(() -> {
                        if (isActive()) {
                            hideLoading();
                        }
                    }).subscribe(new BaseCallback<Object>() {
                @Override
                public void onSuccess(Object result) {
                    showToast(isChecked ? "已打开" : "已关闭");
                }

                @Override
                public void onFailure(UseCaseException e) {
                    if (isActive()) {
                        mSwitch.setCheckedNoEvent(!isChecked);
                        showToast(e.getMessage());
                    }
                }
            });
        }
    }


}
