package com.hll_sc_app.app.complainmanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.complainmanage.add.ComplainMangeAddActivity;
import com.hll_sc_app.app.complainmanage.detail.ComplainMangeDetailActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.TitleBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.hll_sc_app.bean.window.OptionType.OPTION_COMPLAIN_ADD;

@Route(path = RouterConfig.ACTIVITY_COMPLAIN_MANAGE_LIST)
public class ComplainManageActivity extends BaseLoadActivity {
    @BindView(R.id.title_bar)
    TitleBar mTitle;
    @BindView(R.id.tab)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private Unbinder unbinder;

    private ContextOptionsWindow mMenuWindow;

    private String[] tabTitle = {"未处理", "已处理"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
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
        List<ComplainListFragment> list = new ArrayList<>();
        if (UserConfig.crm()) {
            mTitle.setRightText("新增");
            mTitle.setRightBtnClick(v -> {
                ComplainMangeAddActivity.start(null, ComplainMangeDetailActivity.SOURCE.COMPLAIN_MANAGE);
            });
            list.add(ComplainListFragment.newInstance(""));
            mViewPager.setAdapter(new ComplainListFragmentAdapter(getSupportFragmentManager(), list));
            mTabLayout.setVisibility(View.GONE);
        } else {
            initMenuWindow();
            mTitle.setRightBtnClick(v -> {
//                mMenuWindow.showAsDropDownFix(mTitle, Gravity.RIGHT);
                ComplainMangeAddActivity.start(null, ComplainMangeDetailActivity.SOURCE.COMPLAIN_MANAGE);
            });
            list.add(ComplainListFragment.newInstance("1"));
            list.add(ComplainListFragment.newInstance("5"));
            mViewPager.setAdapter(new ComplainListFragmentAdapter(getSupportFragmentManager(), list));
            mTabLayout.setViewPager(mViewPager, tabTitle);
        }
    }

    private void initMenuWindow() {
        if (mMenuWindow == null) {
            List<OptionsBean> optionsBeans = new ArrayList<>();
            OptionsBean optionsBean1 = new OptionsBean(R.drawable.ic_menu_pencil, OPTION_COMPLAIN_ADD);
            OptionsBean optionsBean2 = new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_COMPLAIN_EXPORT);
            optionsBeans.add(optionsBean1);
            optionsBeans.add(optionsBean2);
            mMenuWindow = new ContextOptionsWindow(this);
            mMenuWindow.refreshList(optionsBeans);
            mMenuWindow.setListener((adapter, view, position) -> {
                switch (position) {
                    case 0:
                        mMenuWindow.dismiss();
                        ComplainMangeAddActivity.start(null, ComplainMangeDetailActivity.SOURCE.COMPLAIN_MANAGE);
                        break;
                    case 1:
                        mMenuWindow.dismiss();
                        ((ComplainListFragmentAdapter) mViewPager.getAdapter()).getFragments().get(mViewPager.getCurrentItem()).showCheckBox(true);
                        break;
                    default:
                        break;
                }
            });
        }
    }

    private class ComplainListFragmentAdapter extends FragmentPagerAdapter {
        private List<ComplainListFragment> fragments;

        public ComplainListFragmentAdapter(FragmentManager fm, List<ComplainListFragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        public List<ComplainListFragment> getFragments() {
            return fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();

        }

    }

}
