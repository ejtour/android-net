package com.hll_sc_app.app.cooperation.application;

import android.content.Intent;
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
import com.hll_sc_app.app.cooperation.application.thirdpart.CooperationThirdPartFragment;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.PurchaserNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.SearchView;

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
    }

    private void initView() {
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(CooperationApplicationActivity.this,
                        searchContent, PurchaserNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                toSearchApply(searchContent);
            }
        });
        mListFragment = new ArrayList<>();
        mListFragment.add(CooperationPlatformFragment.newInstance());
        mListFragment.add(CooperationThirdPartFragment.newInstance());
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

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (!CommonUtils.isEmpty(mListFragment)) {
            for (BaseCooperationApplicationFragment fragment : mListFragment) {
                fragment.refresh();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
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
