package com.hll_sc_app.app.crm.customer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/20
 */

public abstract class BaseCustomerActivity extends BaseLoadActivity {
    @BindView(R.id.acc_search_view)
    protected SearchView mSearchView;
    @BindView(R.id.acc_title_bar)
    protected TitleBar mTitleBar;
    @BindView(R.id.acc_tab_layout)
    protected SlidingTabLayout mTabLayout;
    @BindView(R.id.acc_view_pager)
    protected ViewPager mViewPager;
    protected List<Fragment> mFragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crm_customer);
        ButterKnife.bind(this);
        mSearchView.setTextColorWhite();
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(BaseCustomerActivity.this, searchContent, getSearchKey());
            }

            @Override
            public void toSearch(String searchContent) {
                reload(true);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mSearchView.showSearchContent(true, name);
        }
        if (resultCode == RESULT_OK) reload(true);
    }

    public String getSearchWords() {
        return mSearchView.getSearchContent();
    }

    public abstract void reload(boolean includeCurrent);

    protected abstract String getSearchKey();
}
