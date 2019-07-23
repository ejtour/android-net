package com.hll_sc_app.app.setting.account;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 设置界面
 *
 * @author zhuyingsong
 * @date 2019/7/11
 */
@Route(path = RouterConfig.SETTING_ACCOUNT)
public class AccountManageActivity extends BaseLoadActivity {

    @BindView(R.id.txt_change_group_phone)
    TextView mChangePhoneNumber;
    @BindView(R.id.txt_unbind_group)
    TextView mUnbindGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_account);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        //根据AccountType 显示或隐藏选项
        UserBean userBean = GreenDaoUtils.getUser();
        if (userBean != null && !TextUtils.isEmpty(userBean.getAccountType()) &&
            userBean.getAccountType().equalsIgnoreCase("1")) {
            mUnbindGroup.setVisibility(View.VISIBLE);
            mChangePhoneNumber.setVisibility(View.GONE);
        } else {
            mUnbindGroup.setVisibility(View.GONE);
            mChangePhoneNumber.setVisibility(View.VISIBLE);
        }
    }

    @OnClick({R.id.img_close, R.id.txt_change_password, R.id.txt_change_group_phone, R.id.txt_unbind_group})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.img_close) {
            finish();
        } else if (view.getId() == R.id.txt_change_password) {
            RouterUtil.goToActivity(RouterConfig.USER_CHANGE);
        } else if (view.getId() == R.id.txt_change_group_phone) {
            RouterUtil.goToActivity(RouterConfig.ACTIVITY_CHANGE_GROUP_PHONE);
        } else if (view.getId() == R.id.txt_unbind_group) {
            RouterUtil.goToActivity(RouterConfig.ACTIVITY_UNBIND_GROUP);
        }
    }
}
