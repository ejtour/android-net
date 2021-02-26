package com.hll_sc_app.app.crm.customer.partner;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.app.crm.customer.BaseCustomerActivity;
import com.hll_sc_app.app.crm.customer.PartnerHelper;
import com.hll_sc_app.app.search.stratery.PartnerSearch;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.utils.adapter.SimplePagerAdapter;

import java.util.Arrays;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

@Route(path = RouterConfig.CRM_CUSTOMER_PARTNER)
public class CustomerPartnerActivity extends BaseCustomerActivity {
    private PartnerHelper mPartnerHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchView.setHint("搜索客户");
        mTitleBar.setRightBtnClick(this::showWindow);
        mFragments = Arrays.asList(CustomerPartnerFragment.newInstance(false), CustomerPartnerFragment.newInstance(true));
        mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager(), mFragments));
        mTabLayout.setViewPager(mViewPager, new String[]{"我的合作客户", "全部合作客户"});
    }

    private void showWindow(View view) {
        if (mPartnerHelper == null) {
            mPartnerHelper = new PartnerHelper();
        }
        mPartnerHelper.showOption(this, view,  Gravity.END);
    }

    @Override
    public void reload(boolean includeCurrent) {
        for (Fragment fragment : mFragments) {
            ((CustomerPartnerFragment) fragment).reload();
        }
    }

    @Override
    protected String getSearchKey() {
        return PartnerSearch.class.getSimpleName();
    }
}
