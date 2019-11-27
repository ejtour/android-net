package com.hll_sc_app.app.crm.customer.partner;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.crm.customer.BaseCustomerActivity;
import com.hll_sc_app.app.search.stratery.CommonSearch;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.utils.adapter.SimplePagerAdapter;
import com.hll_sc_app.widget.ContextOptionsWindow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/26
 */

@Route(path = RouterConfig.CRM_CUSTOMER_PARTNER)
public class CustomerPartnerActivity extends BaseCustomerActivity {
    private ContextOptionsWindow mOptionsWindow;

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
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_registered_option, OptionType.OPTION_CUSTOMER_REGISTERED));
            list.add(new OptionsBean(R.drawable.ic_unregistered_option, OptionType.OPTION_CUSTOMER_UNREGISTERED));
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(list)
                    .setListener((adapter, view1, position) -> {
                        mOptionsWindow.dismiss();
                        showToast("待添加");
                    });
        }
        mOptionsWindow.showAsDropDownFix(mTitleBar, Gravity.END);
    }

    @Override
    public void reload(boolean includeCurrent) {
        for (Fragment fragment : mFragments) {
            ((CustomerPartnerFragment) fragment).reload();
        }
    }

    @Override
    protected String getSearchKey() {
        return CommonSearch.class.getSimpleName();
    }
}
