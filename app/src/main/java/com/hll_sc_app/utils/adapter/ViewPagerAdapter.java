package com.hll_sc_app.utils.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.PagerAdapter;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/31
 */

public class ViewPagerAdapter extends PagerAdapter {

    private final View[] mViews;
    private String[] mTitles;

    public ViewPagerAdapter(View... views) {
        mViews = views;
    }

    @Override
    public int getCount() {
        return mViews.length;
    }

    public void setTitles(String[] titles) {
        mTitles = titles;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(mViews[position]);
        return mViews[position];
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // super.destroyItem(container, position, object);
        container.removeView(mViews[position]);
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitles != null) {
            return mTitles[position];
        }
        return super.getPageTitle(position);
    }
}
