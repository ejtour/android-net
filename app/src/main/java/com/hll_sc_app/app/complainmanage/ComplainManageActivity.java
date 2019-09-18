package com.hll_sc_app.app.complainmanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.complain.ComplainListResp;
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
public class ComplainManageActivity extends BaseLoadActivity implements IComplainManageContract.IView {
    @BindView(R.id.title_bar)
    TitleBar mTitle;
    @BindView(R.id.tab)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    private Unbinder unbinder;

    private ContextOptionsWindow mMenuWindow;

    private String[] tabTitle = {"未处理", "已处理"};
    private int[] status = {1, 5};

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
        initMenuWindow();
        mTitle.setRightBtnClick(v -> {
            mMenuWindow.showAsDropDownFix(mTitle, Gravity.RIGHT);
        });
        mViewPager.setAdapter(new ComplainListFragmentAdapter(getSupportFragmentManager(), tabTitle, status));
        mTabLayout.setViewPager(mViewPager, tabTitle);
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

    @Override
    public void queryListSuccess(ComplainListResp resp, boolean isMore) {
        //no
    }

    @Override
    public int getComplaintStatus() {
        return -1;
    }

    @Override
    public void showCheckBox(boolean isCheck) {
        //no
    }

    private class ComplainListFragmentAdapter extends FragmentPagerAdapter {
        private List<ComplainListFragment> fragments = new ArrayList<>();
        private String[] titles;

        public ComplainListFragmentAdapter(FragmentManager fm, String[] titles, int[] status) {
            super(fm);
            this.titles = titles;
            for (int i = 0; i < titles.length; i++) {
                fragments.add(ComplainListFragment.newInstance(status[i]));
            }
        }

        public List<ComplainListFragment> getFragments() {
            return fragments;
        }

        public void setFragments(List<ComplainListFragment> fragments) {
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.titles.length;

        }

    }

}
