package com.hll_sc_app.app.report.refund.search;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.widget.SearchTitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = RouterConfig.REPORT_REFUNDED_SEARCH)
public class RefundSearchActivity extends BaseLoadActivity {
    private static final int REQ_CODE = 0x460;
    public static final int INT_PURCHASER = 1;
    @BindView(R.id.rrs_title_bar)
    SearchTitleBar mTitleBar;
    @BindView(R.id.rrs_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.rrs_view_pager)
    ViewPager mViewPager;
    @Autowired(name = "object0")
    int mTag;
    private RefundSearchActivity.PagerAdapter mAdapter;

    public static void start(Activity context) {
        RouterUtil.goToActivity(RouterConfig.REPORT_REFUNDED_SEARCH, context, REQ_CODE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_refund_search);
        ARouter.getInstance().inject(this);
        ButterKnife.bind(this);
        initView();
    }


    public void initView(){
        mTitleBar.setOnSearchListener(this::toSearch);
        ArrayList<RefundSearchFragment> list = new ArrayList<>(2);
        //0-搜索集团 1-搜索门店
        list.add(RefundSearchFragment.newInstance("0"));
        list.add(RefundSearchFragment.newInstance("1"));
        mAdapter = new RefundSearchActivity.PagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setViewPager(mViewPager, new String[]{"采购商集团", "采购商门店"});
        if (mTag == INT_PURCHASER) {
            mViewPager.setCurrentItem(1);
        }
    }

    private void toSearch() {
        RefundSearchFragment fragment = mAdapter.getItem(mViewPager.getCurrentItem());
        if (fragment != null) {
            fragment.toSearch();
        }
    }

    public String getSearchText() {
        return mTitleBar.getSearchContent();
    }

    static class PagerAdapter extends FragmentPagerAdapter {
        private List<RefundSearchFragment> mFragmentList;

        public PagerAdapter(FragmentManager fm, List<RefundSearchFragment> fragmentList) {
            super(fm);
            this.mFragmentList = fragmentList;
        }

        @Override
        public RefundSearchFragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // no-op
        }

        @Override
        public int getCount() {
            return mFragmentList == null ? 0 : mFragmentList.size();
        }
    }

}

