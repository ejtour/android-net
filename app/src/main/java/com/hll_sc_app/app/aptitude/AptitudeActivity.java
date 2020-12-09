package com.hll_sc_app.app.aptitude;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aptitude.enterprise.AptitudeEnterpriseFragment;
import com.hll_sc_app.app.aptitude.goods.AptitudeGoodsFragment;
import com.hll_sc_app.app.aptitude.info.AptitudeInfoFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnPageChange;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/24
 */
@Route(path = RouterConfig.APTITUDE)
public class AptitudeActivity extends BaseLoadActivity {

    @BindView(R.id.stp_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.stp_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.stp_view_pager)
    ViewPager mViewPager;
    private String mLicenseUrl;
    private ArrayList<Fragment> mFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_simple_tab_pager);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTabLayout.setBackgroundResource(R.drawable.bg_divider_bottom);
        mTitleBar.setHeaderTitle("资质管理");
        mTitleBar.setRightBtnClick(this::rightClick);
        mFragments = new ArrayList<>(Arrays.asList(AptitudeInfoFragment.newInstance(), AptitudeEnterpriseFragment.newInstance(), AptitudeGoodsFragment.newInstance()));
        mTabLayout.setViewPager(mViewPager, new String[]{"基础信息", "企业资质", "商品资质"}, this, mFragments);
        onPageSelected(0);
    }

    @OnPageChange(R.id.stp_view_pager)
    public void onPageSelected(int position) {
        Fragment fragment = mFragments.get(position);
        if (fragment instanceof IAptitudeCallback) {
            mTitleBar.setRightText(((IAptitudeCallback) fragment).isEditable() ? position == 2 ? "新增" : "保存" : "编辑");
        } else {
            mTitleBar.setRightBtnVisible(false);
        }
    }

    void rightClick(View view) {
        if (!RightConfig.checkRight(getString(R.string.right_aptitude_update))) {
            showToast(getString(R.string.right_tips));
            return;
        }
        ((IAptitudeCallback) mFragments.get(mViewPager.getCurrentItem())).rightClick();
    }

    public String getLicenseUrl() {
        return mLicenseUrl;
    }

    public void setLicenseUrl(String licenseUrl) {
        mLicenseUrl = licenseUrl;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFragments.get(mViewPager.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
    }
}
