package com.hll_sc_app.app.setting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.update.UpdateInfo;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;
import com.hll_sc_app.widget.AccountDialog;
import com.hll_sc_app.widget.PrivacyDialog;
import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注销登录
 */
public class LogoffActivity extends BaseLoadActivity implements LogoffContract.ILogoffView {

    @BindView(R.id.cbox_make_sure)
    CheckBox mCboxMakeSure;
    @BindView(R.id.txt_cancel_logoff)
    TextView mTxtCancel;
    @BindView(R.id.txt_logoff)
    TextView mTxtLogoff;

    private LogoffContract.ILogoffPresenter mPresenter;

    public static void start() {
        Context context = App.INSTANCE;
        Intent starter = new Intent(context, LogoffActivity.class);
        starter.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.dialog_logoff);
        ButterKnife.bind(this);
        mPresenter = ILogoffPresenter.newInstance();
        mPresenter.register(this);
        initView();
    }

    private void initView() {

        Window window = getWindow();
        window.setGravity(Gravity.BOTTOM);
        window.getDecorView().setPadding(0, 0, 0, 0);

        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = (int) (UIUtils.getScreenWidth(this));
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        // 这里还可以设置lp.x，lp.y在x轴，y轴上的坐标，只是这个位置是基于Gravity的
        window.setAttributes(lp);

        mCboxMakeSure.setOnCheckedChangeListener((buttonView, isChecked) -> mTxtLogoff.setEnabled(isChecked));

        mTxtCancel.setOnClickListener(v -> finish());

        mTxtLogoff.setOnClickListener(v -> new AccountDialog(LogoffActivity.this, () -> {
            mPresenter.logout(new SimpleObserver<Object>(this) {

                @Override
                public void onSuccess(Object o) {
                    logoutSuccess();
                }
            });
        }).show());
    }

    @Override
    public void logoutSuccess() {
        ToastUtils.showShort("注销成功");
        RouterUtil.goToActivity(RouterConfig.USER_LOGIN);
    }
}
