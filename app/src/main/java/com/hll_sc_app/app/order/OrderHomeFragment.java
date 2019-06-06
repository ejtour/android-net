package com.hll_sc_app.app.order;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.common.OrderType;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.OrderEvent;
import com.hll_sc_app.bean.order.OrderParam;
import com.hll_sc_app.citymall.util.ViewUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 首页-订单管理
 *
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/4
 */
@Route(path = RouterConfig.ROOT_HOME_ORDER)
public class OrderHomeFragment extends BaseLoadFragment {
    Unbinder unbinder;
    @BindView(R.id.fmo_options)
    ImageView mOptions;
    @BindView(R.id.fmo_clear_search)
    ImageView mClearSearch;
    @BindView(R.id.fmo_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.fmo_pager)
    ViewPager mPager;
    private final OrderParam mOrderParam = new OrderParam();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_order, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        showStatusBar();
        initView();
        return rootView;
    }

    private void initView() {
        mPager.setAdapter(new OrderListFragmentPager(getChildFragmentManager(), OrderType.values()));
        mPager.setOffscreenPageLimit(2);
        mTabLayout.setViewPager(mPager, OrderType.getTitles());
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            ((ViewGroup.MarginLayoutParams) mOptions.getLayoutParams()).topMargin = ViewUtils.getStatusBarHeight(requireContext());
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.fmo_search, R.id.fmo_clear_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.fmo_search:
                showToast("搜索");
                break;
            case R.id.fmo_clear_search:
                showToast("清除");
                break;
        }
    }

    class OrderListFragmentPager extends FragmentPagerAdapter {

        private final OrderType[] mTypes;

        OrderListFragmentPager(FragmentManager fm, OrderType[] types) {
            super(fm);
            mTypes = types;
        }

        @Override
        public Fragment getItem(int position) {
            return OrderManageFragment.newInstance(mTypes[position], mOrderParam);
        }

        @Override
        public int getCount() {
            return 7;
        }
    }
}