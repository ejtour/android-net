package com.hll_sc_app.app.goods;

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
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.order.OrderManageFragment;
import com.hll_sc_app.app.order.common.OrderType;
import com.hll_sc_app.base.BaseLoadFragment;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.order.OrderParam;
import com.hll_sc_app.citymall.util.ViewUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 首页Fragment
 *
 * @author 朱英松
 * @date 2018/12/19
 */
@Route(path = RouterConfig.ROOT_HOME_GOODS)
public class GoodsHomeFragment extends BaseLoadFragment implements GoodsHomeFragmentContract.IHomeView {
    static final String[] STR_TITLE = {"普通商品", "组合商品", "押金商品", "代仓商品"};
    static final String[] STR_ACTION_TYPE = {"normalProduct", "bundlingGoods", "depositProduct", "supplierWarehouse"};
    @BindView(R.id.space)
    View mSpace;
    Unbinder unbinder;
    @BindView(R.id.tab)
    SlidingTabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_goods, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        showStatusBar();
        initView();
        return rootView;
    }

    private void showStatusBar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mSpace.getLayoutParams();
            params.height = ViewUtils.getStatusBarHeight(requireContext());
        }
    }

    private void initView() {
        mViewPager.setAdapter(new OrderListFragmentPager(getChildFragmentManager(), STR_ACTION_TYPE));
        mViewPager.setOffscreenPageLimit(2);
        mTab.setViewPager(mViewPager, STR_TITLE);
    }

    class OrderListFragmentPager extends FragmentPagerAdapter {
        private String[] mActionTypes;

        OrderListFragmentPager(FragmentManager fm, String[] types) {
            super(fm);
            mActionTypes = types;
        }

        @Override
        public Fragment getItem(int position) {
            return OrderManageFragment.newInstance(OrderType.CANCELED, new OrderParam());
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // no-op
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}