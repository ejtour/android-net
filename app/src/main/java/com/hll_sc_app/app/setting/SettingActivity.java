package com.hll_sc_app.app.setting;

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
import com.hll_sc_app.app.setting.group.GroupSettingActivity;
import com.hll_sc_app.app.web.WebActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
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
 * 设置界面
 *
 * @author zhuyingsong
 * @date 2019/7/11
 */
@Route(path = RouterConfig.SETTING)
public class SettingActivity extends BaseLoadActivity implements SettingContract.ISettingView {
    @BindView(R.id.txt_version)
    TextView mTxtVersion;
    @BindView(R.id.txt_categoryName)
    TextView mCache;
    private SettingPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        mPresenter = SettingPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTxtVersion.setText(String.format(Locale.getDefault(), "%s.%d", SystemUtils.getVersionName(this),
            SystemUtils.getVersionCode(this)));
        mCache.setText(getCacheValue());
    }


    @OnClick({R.id.img_close, R.id.txt_price_ratio, R.id.txt_logout, R.id.txt_account_manage, R.id.txt_bill_setting, R.id.rl_privacy,
            R.id.txt_cooperation_setting, R.id.rl_custom_phone, R.id.txt_categoryName, R.id.txt_tax, R.id.txt_remind, R.id.txt_version})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_logout:
                if (BuildConfig.isDebug) logoutSuccess();
                else mPresenter.logout();
                break;
            case R.id.txt_price_ratio:
                RouterUtil.goToActivity(RouterConfig.SETTING_PRICE_RATIO);
                break;
            case R.id.txt_bill_setting:
                GroupSettingActivity.start("订单设置", getString(R.string.right_billSetting), GroupSettingActivity.ORDER_SETTING);
                break;
            case R.id.txt_cooperation_setting:
                GroupSettingActivity.start("合作采购商设置", getString(R.string.right_workingMealSetting), GroupSettingActivity.CO_SETTING);
                break;
            case R.id.txt_categoryName:
                mPresenter.cleanCache();
                break;
            case R.id.txt_account_manage:
                RouterUtil.goToActivity(RouterConfig.SETTING_ACCOUNT);
                break;
            case R.id.rl_custom_phone:
                UIUtils.callPhone(getString(R.string.contact_phone));
                break;
            case R.id.txt_tax:
                RouterUtil.goToActivity(RouterConfig.SETTING_TAX);
                break;
            case R.id.txt_remind:
                RouterUtil.goToActivity(RouterConfig.SETTING_REMIND);
                break;
            case R.id.txt_version:
                Beta.checkUpgrade(true, false);
                break;
            case R.id.rl_privacy:
                WebActivity.start("隐私政策和用户协议", "file:////android_asset/registerLegal.html");
                break;
        }
    }

    @Override
    public void logoutSuccess() {
        showToast("退出登录成功");
        UserConfig.reLogin();
    }

    private String getCacheValue() {
        try {
            return Formatter.formatFileSize(this, FileManager.getFolderSize(new File(Constant.GLIDE_CACHE_DIR)));
        } catch (Exception e) {
            e.printStackTrace();
            return "获取失败";
        }
    }

    @Override
    public void cleanSuccess() {
        mCache.setText(getCacheValue());
    }

    @Override
    public void startClean() {
        mCache.setText("正在清除...");
    }
}
