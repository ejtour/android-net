package com.hll_sc_app.app.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 设置界面
 *
 * @author zhuyingsong
 * @date 2019/7/11
 */
@Route(path = RouterConfig.SETTING)
public class SettingActivityActivity extends BaseLoadActivity implements SettingActivityContract.ISaleUnitNameAddView {
    private SettingActivityPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = SettingActivityPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {

    }

    @OnClick({R.id.img_close, R.id.txt_logout, R.id.txt_bill_setting, R.id.txt_cooperation_setting})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        } else if (view.getId() == R.id.txt_logout) {
            mPresenter.logout();
        } else if (view.getId() == R.id.txt_bill_setting) {
            RouterUtil.goToActivity(RouterConfig.BILL_SETTING);
        } else if (view.getId() == R.id.txt_cooperation_setting) {
            RouterUtil.goToActivity(RouterConfig.COOPERATION_SETTING);
        }
    }

    @Override
    public void logoutSuccess() {
        showToast("退出登录成功");
        UserConfig.reLogin();
    }
}
