package com.hll_sc_app.app.crm.customer.plan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.app.crm.customer.BaseCustomerActivity;
import com.hll_sc_app.app.crm.customer.plan.add.AddVisitPlanActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.utils.adapter.SimplePagerAdapter;

import java.util.Arrays;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/23
 */

@Route(path = RouterConfig.CRM_CUSTOMER_PLAN)
public class VisitPlanActivity extends BaseCustomerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(v -> AddVisitPlanActivity.start(this, null));
        mFragments = Arrays.asList(VisitPlanFragment.newInstance(false), VisitPlanFragment.newInstance(true));
        mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager(), mFragments));
        mTabLayout.setViewPager(mViewPager, new String[]{"我的拜访计划", "全部拜访计划"});
        mSearchView.setHint("搜索拜访计划");
    }

    @Override
    public void reload(boolean includeCurrent) {
        for (Fragment fragment : mFragments) {
            ((VisitPlanFragment) fragment).reload(includeCurrent);
        }
    }

    @Override
    protected String getSearchKey() {
        return CommonSearch.class.getSimpleName();
    }
}
