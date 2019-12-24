package com.hll_sc_app.app.setting.remind;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.dialog.SuccessDialog;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.user.RemindResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.kyleduo.switchbutton.SwitchButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import butterknife.OnFocusChange;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/1
 */
@Route(path = RouterConfig.SETTING_REMIND)
public class RemindSettingActivity extends BaseLoadActivity implements IRemindSettingContract.IRemindSettingView {
    @BindView(R.id.ars_switch)
    SwitchButton mSwitch;
    @BindView(R.id.ars_label_setting)
    TextView mLabel;
    @BindView(R.id.ars_times)
    EditText mTimes;
    @BindView(R.id.ars_bottom_group)
    Group mBottomGroup;
    private IRemindSettingContract.IRemindSettingPresenter mPresenter;
    private boolean mNotify = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_setting_remind);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        mPresenter = RemindSettingPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    @OnCheckedChanged(R.id.ars_switch)
    public void onCheckedChanged(boolean isChecked) {
        if (!mNotify) return;
        SuccessDialog.newBuilder(this)
                .setImageTitle(R.drawable.ic_dialog_failure)
                .setImageState(R.drawable.ic_dialog_state_failure)
                .setMessageTitle(isChecked ? "确认开启提醒设置" : "确认关闭提醒设置")
                .setMessage(isChecked ? "开启后，订货数量超出设置倍数会提醒您" : "关闭后，订货数量超过设置倍数不会提醒您")
                .setCancelable(false)
                .setButton((dialog, item) -> {
                    dialog.dismiss();
                    if (item == 1) {
                        save();
                    } else {
                        changeCheckedWithoutNotify(!mSwitch.isChecked());
                    }
                }, "取消", "确定")
                .create()
                .show();
    }

    @Override
    public void setData(RemindResp resp) {
        boolean checked = resp.getIsOpen() == 1;
        changeCheckedWithoutNotify(checked);
        mTimes.setText(CommonUtils.formatNumber(Math.abs(resp.getRemindTimes())));
        success();
    }

    private void changeCheckedWithoutNotify(boolean checked) {
        mNotify = false;
        mSwitch.setChecked(checked);
        mNotify = true;
    }

    @Override
    public void success() {
        boolean checked = mSwitch.isChecked();
        mTimes.setEnabled(checked);
        int color = ContextCompat.getColor(this, checked ? R.color.color_222222 : R.color.color_aeaeae);
        mLabel.setTextColor(color);
        mTimes.setTextColor(color);
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        changeCheckedWithoutNotify(!mSwitch.isChecked());
    }

    @OnClick(R.id.ars_save)
    public void save() {
        mLabel.requestFocus();
        UIUtils.hideActivitySoftKeyboard(this);
        if (CommonUtils.getDouble(mTimes.getText().toString()) == 0) {
            showToast("倍数必须大于零");
            if (!mSwitch.isChecked()) {
                changeCheckedWithoutNotify(true);
            }
            return;
        }
        mPresenter.update(mSwitch.isChecked(), mTimes.getText().toString());
    }

    @OnFocusChange(R.id.ars_times)
    public void onFocusChanged(boolean hasFocus) {
        mBottomGroup.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
    }
}
