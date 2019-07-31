package com.hll_sc_app.app.setting.account.unbindmainaccount;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.widget.ScrollableViewPager;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.account.UnbindMainAccountReq;
import com.hll_sc_app.bean.event.LogoutEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 更改集团手机号
 *
 * @author zc
 */
@Route(path = RouterConfig.ACTIVITY_CHANGE_GROUP_PHONE)
public class UnbindMainAccountActivity extends BaseLoadActivity implements IUnbindMainAccountContract.IView {
    @BindView(R.id.view_pager)
    ScrollableViewPager mViewPager;
    @BindView(R.id.next_step)
    TextView mNextStep;
    @BindView(R.id.step_two)
    TextView mStepTwoTitle;
    @BindView(R.id.step_three)
    TextView mStepThreeTitle;
    @BindView(R.id.line_one)
    View mLineOne;
    @BindView(R.id.line_two)
    View mLineTwo;
    private Unbinder unbinder;
    private List<IUnbindMainAccountContract.IFragment> fragments = new ArrayList<>();
    private UnbindMainAccountViewPagerAdapter mAdapter;
    private IUnbindMainAccountContract.IPresent mPresent;
    private UnbindMainAccountReq mUnbindMainAccountReq = new UnbindMainAccountReq();
    /**
     * viewpager中显示的fragme的index
     **/
    private int curFragmentIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        setContentView(R.layout.activity_change_group_phone);
        unbinder = ButterKnife.bind(this);
        mPresent = UnbindMainAccountPresent.newInstance();
        mPresent.register(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        unbinder.unbind();
        //销毁倒计时
        for (IUnbindMainAccountContract.IFragment fragment : fragments) {
            fragment.stopCountdown();
        }
        super.onDestroy();
    }

    private void initView() {
        initFragments();
        initViewPager();
    }

    /**
     * 初始化每个步骤的fragment
     */
    private void initFragments() {
        //第一步：验证原手机号
        UnbindMainAccountOneFragment fragment = new UnbindMainAccountOneFragment();
        fragment.bind(this);
        fragments.add(fragment);
        //第二步：绑定新手机号
        UnbindMainAccountTwoFragment fragment1 = new UnbindMainAccountTwoFragment();
        fragment1.bind(this);
        fragments.add(fragment1);
        //第三步：重新设置密码
        UnbindMainAccountThreeFragment fragment2 = new UnbindMainAccountThreeFragment();
        fragment2.bind(this);
        fragments.add(fragment2);
    }

    /***
     * 初始化viewpager
     */
    private void initViewPager() {
        mAdapter = new UnbindMainAccountViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mAdapter);

    }

    @OnClick({R.id.next_step, R.id.step_one, R.id.step_two,})
    public void onCLick(View v) {
        switch (v.getId()) {
            case R.id.next_step:
                if (curFragmentIndex == 0) {
                    mUnbindMainAccountReq.setOriginLoginPhone(getCurrentFragent().getPhoneNumber());
                    mUnbindMainAccountReq.setOriginVeriCode(getCurrentFragent().getIdentifyCode());
                    getCheckIdentifyCodeResult(true);
                } else if (curFragmentIndex == 1) {
                    mUnbindMainAccountReq.setNewLoginPhone(getCurrentFragent().getPhoneNumber());
                    mUnbindMainAccountReq.setNewVeriCode(getCurrentFragent().getIdentifyCode());
                    getCheckIdentifyCodeResult(true);
                } else {
                    String newPwd = getCurrentFragent().getNewPassword();
                    String checkPwd = getCurrentFragent().getCheckPassword();
                    if (!TextUtils.equals(newPwd, checkPwd)) {
                        showToast("两次密码不一致，请重新输入");
                    } else {
                        mUnbindMainAccountReq.setNewPWD(newPwd);
                        mUnbindMainAccountReq.setCheckLoginPWD(checkPwd);
                        mPresent.unBindMainAccount(mUnbindMainAccountReq);
                    }

                }
                break;
            case R.id.step_one:
                if (curFragmentIndex > 0) {
                    curFragmentIndex = 0;
                    mViewPager.setCurrentItem(curFragmentIndex, true);
                    updateStepTitle();
                }
                break;
            case R.id.step_two:
                if (curFragmentIndex > 1) {
                    curFragmentIndex = 1;
                    mViewPager.setCurrentItem(curFragmentIndex, true);
                    updateStepTitle();
                }
                break;
            default:
                break;
        }
    }

    private IUnbindMainAccountContract.IFragment getCurrentFragent() {
        return fragments.get(curFragmentIndex);
    }

    /**
     * 更新阶段title的样式
     */
    private void updateStepTitle() {
        if (curFragmentIndex == 0) {
            mNextStep.setText("下一步");
            mStepTwoTitle.setTextColor(Color.parseColor("#999999"));
            mLineOne.setBackgroundColor(Color.parseColor("#BBBBBB"));
            mStepThreeTitle.setTextColor(Color.parseColor("#999999"));
            mLineTwo.setBackgroundColor(Color.parseColor("#BBBBBB"));
        } else if (curFragmentIndex == 1) {
            mNextStep.setText("下一步");
            mStepTwoTitle.setTextColor(Color.parseColor("#222222"));
            mLineOne.setBackgroundResource(R.color.base_colorPrimary);
            mStepThreeTitle.setTextColor(Color.parseColor("#999999"));
            mLineTwo.setBackgroundColor(Color.parseColor("#BBBBBB"));
        } else if (curFragmentIndex == 2) {
            mNextStep.setText("完成");
            mStepThreeTitle.setTextColor(Color.parseColor("#222222"));
            mLineTwo.setBackgroundResource(R.color.base_colorPrimary);
        }
    }

    @Override
    public void getCheckIdentifyCodeResult(boolean isSuccess) {
        if (isSuccess) {
            //移动到下一步
            curFragmentIndex++;
            mViewPager.setCurrentItem(curFragmentIndex, true);
            updateStepTitle();
        } else {
            showToast("验证码校验失败");
        }
    }

    @Override
    public void updateNextStepButtonStatus(boolean isEnable) {
        mNextStep.setEnabled(isEnable);
    }

    @Override
    public void getUnbindResult(boolean isSuccess) {
        if (isSuccess) {
            mPresent.logout();
        }
    }

    @Override
    public void logoutSuccess() {
        finish();
        UserConfig.clearToken();
        //通知退出消息:让设置页面setResult 从而4个顶级fragment开始执行退出的逻辑
        EventBus.getDefault().post(new LogoutEvent());
    }
}
