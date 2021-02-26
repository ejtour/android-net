package com.hll_sc_app.app.setting;

import android.os.Bundle;
import android.text.format.Formatter;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.widget.TextViewCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.R;
import com.hll_sc_app.app.menu.MenuActivity;
import com.hll_sc_app.app.menu.stratery.SettingMenu;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.menu.MenuBean;
import com.hll_sc_app.citymall.util.FileManager;
import com.hll_sc_app.widget.WXFollowDialog;

import java.io.File;

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

    @Override
    public void showFollowDialog(String qrcodeUrl) {
        new WXFollowDialog(this).show(qrcodeUrl);
    }
}
