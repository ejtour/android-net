package com.hll_sc_app.app.crm.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.format.Formatter;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.setting.SettingContract;
import com.hll_sc_app.app.setting.SettingPresenter;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.citymall.util.FileManager;
import com.hll_sc_app.citymall.util.SystemUtils;
import com.tencent.bugly.beta.Beta;

import java.io.File;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/27
 */
@Route(path = RouterConfig.CRM_SETTING)
public class CrmSettingActivity extends BaseLoadActivity implements SettingContract.ISettingView {
    @BindView(R.id.acs_clean_cache)
    TextView mCleanCache;
    @BindView(R.id.acs_version_info)
    TextView mVersionInfo;
    private SettingContract.ISettingPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_crm_setting);
        ButterKnife.bind(this);
        mCleanCache.setText(getCacheValue());
        mVersionInfo.setText(String.format(Locale.getDefault(), "%s.%d", SystemUtils.getVersionName(this),
                SystemUtils.getVersionCode(this)));
        mPresenter = new SettingPresenter();
        mPresenter.register(this);
    }

    private String getCacheValue() {
        try {
            return Formatter.formatFileSize(this, FileManager.getFolderSize(new File(Constant.GLIDE_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    @OnClick({R.id.acs_change_password, R.id.acs_clean_cache, R.id.acs_version_info, R.id.acs_logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.acs_change_password:
                RouterUtil.goToActivity(RouterConfig.USER_CHANGE);
                break;
            case R.id.acs_clean_cache:
                mPresenter.cleanCache();
                break;
            case R.id.acs_version_info:
                Beta.checkUpgrade(true, false);
                break;
            case R.id.acs_logout:
                if (BuildConfig.isDebug) logoutSuccess();
                else mPresenter.logout();
                break;
        }
    }

    @Override
    public void logoutSuccess() {
        showToast("退出登录成功");
        UserConfig.reLogin();
    }

    @Override
    public void cleanSuccess() {
        mCleanCache.setText(getCacheValue());
    }

    @Override
    public void startClean() {
        mCleanCache.setText("正在清除...");
    }
}
