package com.hll_sc_app.utils.adapter;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/31
 */

public class SimplePagerAdapter extends FragmentPagerAdapter {

    private final List<Fragment> mFragments;
    private final List<String> mTitles;

    public SimplePagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        this(fm, fragments, null);
    }

    public SimplePagerAdapter(FragmentManager fm, List<Fragment> fragments, @Nullable List<String> titles) {
        super(fm);
        mFragments = fragments;
        mTitles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return CommonUtils.isEmpty(mTitles) ? super.getPageTitle(position) : mTitles.get(position);
    }
}
