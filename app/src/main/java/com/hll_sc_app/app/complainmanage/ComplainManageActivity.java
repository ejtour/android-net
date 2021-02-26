package com.hll_sc_app.app.complainmanage;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.complainmanage.add.ComplainMangeAddActivity;
import com.hll_sc_app.app.complainmanage.detail.ComplainMangeDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.utils.adapter.SimplePagerAdapter;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = RouterConfig.ACTIVITY_COMPLAIN_MANAGE_LIST)
public class ComplainManageActivity extends BaseLoadActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitle;
    @BindView(R.id.tab)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private Unbinder unbinder;
    private String[] tabTitle = {"未处理", "已处理"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_manage_list);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mTitle.setRightBtnClick(v -> {
            ComplainMangeAddActivity.start(null, ComplainMangeDetailActivity.SOURCE.COMPLAIN_MANAGE);
        });
        List<Fragment> list = new ArrayList<>();
        if (UserConfig.crm()) {
            list.add(ComplainListFragment.newInstance(""));
            mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager(), list));
            mTabLayout.setVisibility(View.GONE);
        } else {
            list.add(ComplainListFragment.newInstance("1"));
            list.add(ComplainListFragment.newInstance("5"));
            mViewPager.setAdapter(new SimplePagerAdapter(getSupportFragmentManager(), list));
            mTabLayout.setViewPager(mViewPager, tabTitle);
        }
    }
}
