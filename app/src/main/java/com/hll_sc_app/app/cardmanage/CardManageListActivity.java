package com.hll_sc_app.app.cardmanage;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.hll_sc_app.R;
import com.hll_sc_app.app.cardmanage.add.SelectPurchaserListActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.QueryPurchaserSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;

import java.util.Observable;
import java.util.Observer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = RouterConfig.ACTIVITY_CARD_MANAGE_LIST)
public class CardManageListActivity extends BaseLoadActivity implements ICardManageContract.IView {
    static final String[] STR_TITLE = {"启用中", "已冻结", "已注销"};
    private final CardMangeObservable mObservable = new CardMangeObservable();
    @BindView(R.id.title_bar)
    TitleBar mTitle;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.tab)
    SlidingTabLayout mTabLayout;
    @BindView(R.id.view_pager)
    ViewPager mViewpager;
    private Unbinder unbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage_list);
        unbinder = ButterKnife.bind(this);
        initView();
    }

    private void goToAdd() {
        SelectPurchaserListActivity.start(RouterConfig.ACTIVITY_CARD_MANAGE_LIST);
    }

    private void initView() {
        mTitle.setRightBtnClick(v -> {
            goToAdd();
        });
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(CardManageListActivity.this,
                        searchContent, QueryPurchaserSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                if (TextUtils.isEmpty(searchContent)) {
                    mSearchView.setTag("");
                }
                mObservable.notify("refresh");
            }
        });

        mViewpager.setAdapter(new FragmentListAdapter(getSupportFragmentManager()));
        mTabLayout.setViewPager(mViewpager, STR_TITLE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            String value = data.getStringExtra("value");
            mSearchView.setTag(value);
            mSearchView.showSearchContent(true, name);
        }
    }

    @Override
    public String getSearchText() {
        Object o = mSearchView.getTag();
        if (o == null) {
            return "";
        } else {
            return o.toString();
        }
    }

    private class CardMangeObservable extends Observable {
        void notify(Object object) {
            setChanged();
            notifyObservers(object);
        }
    }

    private class FragmentListAdapter extends FragmentPagerAdapter {

        FragmentListAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return STR_TITLE.length;
        }

        @Override
        public CardManageFragment getItem(int position) {
            return CardManageFragment.newInstance(position + 1);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Object o = super.instantiateItem(container, position);
            mObservable.addObserver((Observer) o);
            return o;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
            mObservable.deleteObserver((Observer) object);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mTabLayout.setCurrentTab(0);
        mObservable.notify("refresh");
    }
}
