package com.hll_sc_app.app.aptitude;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.aptitude.enterprise.AptitudeEnterpriseFragment;
import com.hll_sc_app.app.aptitude.enterprise.add.AptitudeEnterpriseAddActivity;
import com.hll_sc_app.app.aptitude.info.AptitudeInfoFragment;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.utils.adapter.SimplePagerAdapter;
import com.hll_sc_app.widget.TitleBar;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_simple_tab_pager);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mTitleBar.setHeaderTitle("资质管理");
        mTitleBar.setRightBtnClick(this::rightClick);
        onPageSelected(0);
        mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager(),
                Arrays.asList(AptitudeInfoFragment.newInstance(), AptitudeEnterpriseFragment.newInstance()),
                Arrays.asList("基础信息", "企业资质")));
        mTabLayout.setViewPager(mViewPager);
    }

    @OnPageChange(R.id.stp_view_pager)
    void onPageSelected(int position) {
        mTitleBar.setRightText(position == 0 ? "保存" : "新增");
    }

    void rightClick(View view) {
        if (!RightConfig.checkRight(getString(R.string.right_aptitude_update))) {
            showToast(getString(R.string.right_tips));
            return;
        }
        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter == null) return;
        Object o = adapter.instantiateItem(mViewPager, mViewPager.getCurrentItem());
        if (mViewPager.getCurrentItem() == 0) {
            ((AptitudeInfoFragment) o).save();
        } else if (mViewPager.getCurrentItem() == 1) {
            AptitudeEnterpriseAddActivity.start(this, ((AptitudeEnterpriseFragment) o).getTypes());
        }
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
        PagerAdapter adapter = mViewPager.getAdapter();
        if (adapter != null) {
            ((Fragment) adapter.instantiateItem(mViewPager, mViewPager.getCurrentItem())).onActivityResult(requestCode, resultCode, data);
        }
    }
}
