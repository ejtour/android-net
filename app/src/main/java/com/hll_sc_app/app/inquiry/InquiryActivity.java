package com.hll_sc_app.app.inquiry;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.widget.TitleBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/18
 */
@Route(path = RouterConfig.INQUIRY)
public class InquiryActivity extends BaseActivity {

    @BindView(R.id.stp_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.stp_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.stp_view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_tab_pager);
        ButterKnife.bind(this);
        mTabLayout.setIndicatorWidth(36);
        mTitleBar.setHeaderTitle("询价管理");
        mTabLayout.setTextSelectColor(ContextCompat.getColor(this, R.color.color_222222));
        mViewPager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        mTabLayout.setViewPager(mViewPager, new String[]{"全部", "询价中", "已报价", "已失效"});
    }

    static class PageAdapter extends FragmentPagerAdapter {

        public PageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return InquiryFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
