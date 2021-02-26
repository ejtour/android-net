package com.hll_sc_app.app.setting.account.unbindmainaccount;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 更改集团绑定账号的viewpager适配器
 *
 * @author zc
 */
public class UnbindMainAccountViewPagerAdapter extends FragmentPagerAdapter {
    private List<IUnbindMainAccountContract.IFragment> fragmentList;

    public UnbindMainAccountViewPagerAdapter(FragmentManager fm, List<IUnbindMainAccountContract.IFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return (Fragment)fragmentList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // no-op
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
