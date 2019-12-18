package com.hll_sc_app.app.cardmanage.recharge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.cardmanage.CardManageBean;
import com.hll_sc_app.widget.NoScrollViewPager;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = RouterConfig.ACTIVITY_CARD_MANAGE_RECHARGE)
public class CardManageRechargeActivity extends BaseLoadActivity implements ICardManageRechargeContract.IView {
    @Autowired(name = "parcelable")
    CardManageBean mCardManageBean;
    @BindView(R.id.title_bar)
    TitleBar mTitbleBar;
    @BindView(R.id.view_pager)
    NoScrollViewPager mViewPager;

    private Unbinder unbinder;
    private ICardManageRechargeContract.IPresent mPresent;
    private List<Fragment> stepFragments;

    public static void start(CardManageBean bean) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_CARD_MANAGE_RECHARGE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_card_manage_recharge);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        mPresent = CardManageRechargePresent.newInstance();
        mPresent.register(this);
        initView();
    }

    private void initView() {
        stepFragments = new ArrayList<>();
        stepFragments.add(CardRechargeStepOneFragment.newInstance(mCardManageBean));
        stepFragments.add(CardRechargeStepTwoFragment.newInstance());
        mViewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        mViewPager.setScroll(false);
        mTitbleBar.setLeftBtnClick(v -> {
            if (mViewPager.getCurrentItem() == 1) {
                mViewPager.setCurrentItem(0, true);
            } else {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void toNextStep(CardManageBean bean) {
        mCardManageBean = bean;
        ((CardRechargeStepTwoFragment) stepFragments.get(1)).updateValue(bean);
        mViewPager.setCurrentItem(1, true);
    }

    @Override
    public void recharge() {
        mPresent.recharge(mCardManageBean.getCashBalanceText(), mCardManageBean.getGiftBalanceText(), mCardManageBean.getRemark(),mCardManageBean.getPayType());
    }

    @Override
    public String getCardId() {
        return mCardManageBean.getId();
    }

    @Override
    public void rechargeSuccess() {
        showToast("充值成功");
        ARouter.getInstance().build(RouterConfig.ACTIVITY_CARD_MANAGE_LIST)
                .setProvider(new LoginInterceptor())
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .navigation(CardManageRechargeActivity.this);
    }

    @Override
    public void onBackPressed() {
        if (mViewPager.getCurrentItem() == 1) {
            mViewPager.setCurrentItem(0, true);
        } else {
            super.onBackPressed();
        }
    }

    private class FragmentAdapter extends FragmentPagerAdapter {
        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return stepFragments.get(position);
        }


        @Override
        public int getCount() {
            return stepFragments.size();
        }
    }
}
