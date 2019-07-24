package com.hll_sc_app.app.cooperation.application;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.ViewGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cooperation.application.platform.CooperationPlatformFragment;
import com.hll_sc_app.app.order.search.OrderSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.CooperationInviteSearchEvent;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.SearchView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 合作采购商-我收到的申请
 *
 * @author zhuyingsong
 * @date 2019/7/24
 */
@Route(path = RouterConfig.COOPERATION_PURCHASER_APPLICATION, extras = Constant.LOGIN_EXTRA)
public class CooperationApplicationActivity extends BaseLoadActivity {
    static final String[] STR_TITLE = {"平台申请", "第三方申请"};
    @BindView(R.id.tab)
    SlidingTabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    private List<BaseCooperationApplicationFragment> mListFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cooperation_application);
        ARouter.getInstance().inject(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
        ButterKnife.bind(this);
        initView();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        int padding = UIUtils.dip2px(10);
        mSearchView.setPadding(0, padding, 0, 0);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                OrderSearchActivity.start(searchContent, OrderSearchActivity.FROM_COOPERATION_SEARCH);
            }

            @Override
            public void toSearch(String searchContent) {
                toSearchApply(searchContent);
            }
        });
        mListFragment = new ArrayList<>();
        mListFragment.add(CooperationPlatformFragment.newInstance());
        mListFragment.add(CooperationPlatformFragment.newInstance());
        mViewPager.setAdapter(new FragmentListAdapter(getSupportFragmentManager(), mListFragment));
        mTab.setViewPager(mViewPager, STR_TITLE);
    }

    private void toSearchApply(String searchContent) {
        if (!CommonUtils.isEmpty(mListFragment)) {
            for (BaseCooperationApplicationFragment fragment : mListFragment) {
                fragment.toSearch(searchContent);
            }
        }
    }

    @Subscribe
    public void onEvent(CooperationInviteSearchEvent event) {
        String name = event.getName();
        if (!TextUtils.isEmpty(name)) {
            mSearchView.showSearchContent(true, name);
        }
    }

    @OnClick(R.id.img_close)
    public void onViewClicked() {
        finish();
    }

    class FragmentListAdapter extends FragmentPagerAdapter {
        private List<BaseCooperationApplicationFragment> mListFragment;

        FragmentListAdapter(FragmentManager fm, List<BaseCooperationApplicationFragment> list) {
            super(fm);
            this.mListFragment = list;
        }

        @Override
        public int getCount() {
            return mListFragment.size();
        }

        @Override
        public BaseCooperationApplicationFragment getItem(int position) {
            return mListFragment.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // no-op
        }
    }
}
