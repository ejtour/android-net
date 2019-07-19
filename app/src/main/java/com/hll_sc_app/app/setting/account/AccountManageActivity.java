package com.hll_sc_app.app.setting.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
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
@Route(path = RouterConfig.SETTING_ACCOUNT)
public class AccountManageActivity extends BaseLoadActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_account);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
    }

    @OnClick({R.id.img_close, R.id.txt_change_password})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        } else if (view.getId() == R.id.txt_change_password) {
            RouterUtil.goToActivity(RouterConfig.USER_CHANGE);
        }
    }
}
