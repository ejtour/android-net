package com.hll_sc_app.app.agreementprice;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.GlobalPreference;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.RefreshQuotationList;
import com.hll_sc_app.bean.user.GroupParamBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.rest.User;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 我的-协议价管理
 *
 * @author zhuyingsong
 * @date 2019/7/8
 */
@Route(path = RouterConfig.MINE_AGREEMENT_PRICE, extras = Constant.LOGIN_EXTRA)
public class AgreementPriceActivity extends BaseLoadActivity {
    @BindView(R.id.apm_tab_layout)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.apm_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.viewPager)
    ViewPager mViewPager;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    private PagerAdapter mAdapter;
    private ContextOptionsWindow mOptionsWindow;

    public static void start(ILoadView context) {
        User.queryGroupParam("7", new SimpleObserver<List<GroupParamBean>>(context) {
            @Override
            public void onSuccess(List<GroupParamBean> groupParamBeans) {
                GlobalPreference.putParam(Constants.ONLY_RECEIVE, groupParamBeans.get(0).getParameValue() == 2);
                RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE);
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        EventBus.getDefault().post(new RefreshQuotationList());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price_manager);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        mTitleBar.setRightBtnClick(this::showAddWindow);
        ArrayList<BaseAgreementPriceFragment> list = new ArrayList<>(2);
        list.add((BaseAgreementPriceFragment) RouterUtil.getFragment(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION));
        boolean onlyReceive = GlobalPreference.getParam(Constants.ONLY_RECEIVE, false);
        if (!onlyReceive) {
            list.add((BaseAgreementPriceFragment) RouterUtil.getFragment(RouterConfig.MINE_AGREEMENT_PRICE_GOODS));
        } else {
            mTitleBar.setHeaderTitle("报价单");
            mTabLayout.setVisibility(View.GONE);
        }
        mAdapter = new PagerAdapter(getSupportFragmentManager(), list);
        mViewPager.setAdapter(mAdapter);
        if (!onlyReceive) {
            mTabLayout.setViewPager(mViewPager, new String[]{"报价单", "商品"});
        }
        mSearchView.setContentClickListener(searchContent ->
                RouterUtil.goToActivity(RouterConfig.MINE_AGREEMENT_PRICE_SEARCH, mViewPager.getCurrentItem()));
    }

    /**
     * 导出到邮箱
     */
    private void showAddWindow(View v) {
        if (mOptionsWindow == null) {
            List<OptionsBean> list = new ArrayList<>();
            list.add(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_AGREEMENT_PRICE_EXPORT));
            mOptionsWindow = new ContextOptionsWindow(this).setListener((adapter, view, position) -> {
                mOptionsWindow.dismiss();
                if (!RightConfig.checkRight(getString(R.string.right_agreementPrice_export))) {
                    showToast(getString(R.string.right_tips));
                    return;
                }
                BaseAgreementPriceFragment fragment = mAdapter.getItem(mViewPager.getCurrentItem());
                if (fragment != null) {
                    fragment.toExport();
                }
            }).refreshList(list);
        }
        mOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    public static class PagerAdapter extends FragmentPagerAdapter {
        private List<BaseAgreementPriceFragment> mFragmentList;

        public PagerAdapter(FragmentManager fm, List<BaseAgreementPriceFragment> fragmentList) {
            super(fm);
            this.mFragmentList = fragmentList;
        }

        @Override
        public BaseAgreementPriceFragment getItem(int position) {
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
