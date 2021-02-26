package com.hll_sc_app.app.marketingsetting.coupon.usedetail;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = RouterConfig.ACTIVITY_MARKETING_COUPON_USE_DETAIL_LIST)
public class UseDetailActivity extends BaseActivity {
    @BindView(R.id.tab)
    SlidingTabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @Autowired(name = "object0")
    String id;
    @Autowired(name = "object1")
    int unusedCount;
    @Autowired(name = "object2")
    int usedCount;
    @Autowired(name = "object3")
    int invalidCount;
    private Unbinder unbinder;

    private int[] couponStatus = {1, 2, 3};

    public static void start(String id, int unusedCount, int usedCount, int invalidCount) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_MARKETING_COUPON_USE_DETAIL_LIST, id, unusedCount, usedCount, invalidCount);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_coupon_use_list);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initFragment();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initFragment() {
        mViewPager.setAdapter(new CouponUsePagerAdapter(getSupportFragmentManager(), couponStatus));
        String[] titles = {"未使用(" + unusedCount + ")", "已使用(" + usedCount + ")", "已失效(" + invalidCount + ")"};
        mTab.setViewPager(mViewPager, titles);
    }

    class CouponUsePagerAdapter extends FragmentPagerAdapter {
        private int[] couponStatus;

        CouponUsePagerAdapter(FragmentManager fm, int[] status) {
            super(fm);
            couponStatus = status;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public UseDetailFragment getItem(int position) {
            return UseDetailFragment.newInstance(couponStatus[position], id);
        }

    }
}
