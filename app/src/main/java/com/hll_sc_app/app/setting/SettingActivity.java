package com.hll_sc_app.app.setting;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.menu.MenuActivity;
import com.hll_sc_app.app.menu.stratery.SettingMenu;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.menu.MenuBean;
import com.hll_sc_app.bean.update.UpdateInfo;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.FileManager;
import com.hll_sc_app.citymall.util.SystemUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.widget.WXFollowDialog;
import com.hualala.upgrade.UpgradeViewModel;
import com.hualala.upgrade.misapi.CheckVersionParams;
import com.hualala.upgrade.misapi.CheckVersionResp;

import java.io.File;

import kotlin.Unit;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/12
 */

@Route(path = RouterConfig.SETTING)
public class SettingActivity extends MenuActivity implements SettingContract.ISettingView {
    SettingContract.ISettingPresenter mPresenter;
    private MenuBean mCurBean;

    public static void start() {
        RouterUtil.goToActivity(RouterConfig.SETTING, SettingMenu.class.getSimpleName());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = SettingPresenter.newInstance();
        mPresenter.register(this);
        for (MenuBean bean : mAdapter.getData()) {
            if ("清除缓存".equals(bean.getLabel())) {
                updateExtra(bean, getCacheValue());
                break;
            }
        }
    }

    @Override
    protected BaseQuickAdapter.OnItemClickListener getItemClickListener() {
        return (adapter, view, position) -> {
            mCurBean = mAdapter.getItem(position);
            if (mCurBean == null) return;
            if ("清除缓存".equals(mCurBean.getLabel())) {
                mPresenter.cleanCache();
            } else if ("公众号".equals(mCurBean.getLabel())) {
                mPresenter.queryFollowQR();
            } else if ("版本信息".equals(mCurBean.getLabel())) {
                checkUpgrade(this, true);
            }
        };
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
    protected View getFooterView() {
        TextView textView = new TextView(this);
        ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(40));
        params.leftMargin = params.rightMargin = UIUtils.dip2px(40);
        params.topMargin = params.bottomMargin = UIUtils.dip2px(50);
        textView.setLayoutParams(params);
        textView.setText("退出登录");
        textView.setGravity(Gravity.CENTER);
        textView.setBackgroundResource(R.drawable.bg_white_solid);
        TextViewCompat.setTextAppearance(textView, R.style.TextAppearance_City22_Middle);
        textView.setOnClickListener(v -> {
            if (BuildConfig.isDebug) {
                logoutSuccess();
            } else {
                mPresenter.logout();
            }
        });
        if (BuildConfig.isDebug) {
            textView.setOnLongClickListener(v -> {
                mPresenter.logout();
                return true;
            });
        }
        return textView;
    }

    @Override
    public void logoutSuccess() {
        showToast("退出登录成功");
        UserConfig.reLogin();
    }

    @Override
    public void cleanSuccess() {
        updateExtra(mCurBean, getCacheValue());
    }

    private void updateExtra(MenuBean bean, String extra) {
        if (bean != null) {
            bean.setExtra(extra);
            mAdapter.notifyItemChanged(mAdapter.getData().indexOf(bean));
        }
    }

    @Override
    public void startClean() {
        updateExtra(mCurBean, "正在清除...");
    }

    /**
     * 触发时机
     * 1.设置中手动点击
     * 2.登录成功后进入首页
     * 3.app从后台回到前台
     *
     * @param activity
     */
    public static void checkUpgrade(AppCompatActivity activity, boolean showToast) {
        UserBean user = GreenDaoUtils.getUser();
        String versionNo = SystemUtils.getVersionName(App.INSTANCE) + "." + SystemUtils.getVersionCode(App.INSTANCE);
        UpgradeViewModel.newInstance(activity, BuildConfig.isDebug)
                .checkVersion(new CheckVersionParams("992", versionNo,
                                user.getGroupID(), ""),
                        checkVersionResp -> {
                            if (checkVersionResp.isNeedUpdate(versionNo)) {
                                CheckVersionResp.Properties properties = checkVersionResp.getProperties();
                                UpdateInfo updateInfo = new UpdateInfo();
                                updateInfo.setVersionName(properties.getVersionNo());
                                updateInfo.setVersionDesc(properties.getUpdateRemark());
                                updateInfo.setAppUrl(properties.getDownloadUrl());
                                updateInfo.setForce(CommonUtils.getInt(properties.isMustUpdate()));
                                showUpgrade(updateInfo);
                            } else {
                                if (showToast) {
                                    ToastUtils.showShort(App.INSTANCE, "当前版本已是最新");
                                }
                            }
                            return Unit.INSTANCE;
                        },
                        s -> {
                            return Unit.INSTANCE;
                        });
    }

    /**
     * 展示升级信息
     *
     * @param info 升级信息
     */
    private static void showUpgrade(UpdateInfo info) {
        if (info != null && !TextUtils.isEmpty(info.getAppUrl())) {
            // 显示更新信息弹窗
            UpgradeActivity.start(info);
        }
    }

    @Override
    public void showFollowDialog(String qrcodeUrl) {
        new WXFollowDialog(this).show(qrcodeUrl);
    }
}
