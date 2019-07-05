package com.hll_sc_app.app.goods.relevance.goods;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.relevance.goods.fragment.BaseGoodsRelevanceFragment;
import com.hll_sc_app.app.goods.relevance.goods.fragment.relevance.GoodsRelevanceListFragment;
import com.hll_sc_app.app.goods.relevance.goods.fragment.unrelevance.GoodsUnRelevanceListFragment;
import com.hll_sc_app.app.order.search.OrderSearchActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.GoodsRelevanceListSearchEvent;
import com.hll_sc_app.widget.SearchView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 第三方商品关联-采购商列表-关联商品列表
 *
 * @author zhuyingsong
 * @date 2019/7/4
 */
@Route(path = RouterConfig.GOODS_RELEVANCE_LIST, extras = Constant.LOGIN_EXTRA)
public class GoodsRelevanceListActivity extends BaseLoadActivity {
    static final String[] STR_TITLE = {"未关联", "已关联"};
    @Autowired(name = "object0")
    String mGroupId;
    @Autowired(name = "object1")
    String mResourceType;
    @Autowired(name = "object2")
    String mOperateModel;
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlToolbar;
    @BindView(R.id.tab)
    SlidingTabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    private FragmentListAdapter mFragmentAdapter;
    private GoodsUnRelevanceListFragment mUnRelevanceListFragment;
    private GoodsRelevanceListFragment mRelevanceListFragment;
    private List<BaseGoodsRelevanceFragment> mListFragment;

    /**
     * start
     *
     * @param groupId      采购商集团 ID
     * @param resourceType 采购商来源(对接时获取) 1-哗啦啦供应链 2-天财供应链
     * @param operateModel 经营模式：0：集团模式 1：单店模式
     */
    public static void start(String groupId, String resourceType, String operateModel) {
        RouterUtil.goToActivity(RouterConfig.GOODS_RELEVANCE_LIST, groupId, resourceType, operateModel);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_relevance_list);
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
        mSearchView.setPadding(0, 0, 0, 0);
        mSearchView.setBackgroundResource(R.color.base_colorPrimary);
        LinearLayout llContent = mSearchView.getContentView();
        if (llContent != null) {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) llContent.getLayoutParams();
            params.setMargins(0, 0, 0, 0);
            llContent.setBackgroundResource(R.drawable.bg_search_text);
            llContent.setGravity(Gravity.CENTER_VERTICAL);
            mSearchView.setTextColorWhite();
        }
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                OrderSearchActivity.start(searchContent, OrderSearchActivity.FROM_GOODS_RELEVANCE_LIST);
            }

            @Override
            public void toSearch(String searchContent) {
                //TODO 刷新未关联、已关联列表
            }
        });
        mListFragment = new ArrayList<>();
        mRelevanceListFragment = GoodsRelevanceListFragment.newInstance(mGroupId, mResourceType, mOperateModel);
        mListFragment.add(mRelevanceListFragment);
        mUnRelevanceListFragment = GoodsUnRelevanceListFragment.newInstance(mGroupId, mResourceType, mOperateModel);
        mListFragment.add(mUnRelevanceListFragment);
        mFragmentAdapter = new FragmentListAdapter(getSupportFragmentManager(), mListFragment);
        mViewPager.setAdapter(mFragmentAdapter);
        mTab.setViewPager(mViewPager, STR_TITLE);
    }

    @Subscribe
    public void onEvent(GoodsRelevanceListSearchEvent event) {
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
        private List<BaseGoodsRelevanceFragment> mListFragment;

        FragmentListAdapter(FragmentManager fm, List<BaseGoodsRelevanceFragment> list) {
            super(fm);
            this.mListFragment = list;
        }

        @Override
        public int getCount() {
            return mListFragment.size();
        }

        @Override
        public BaseGoodsRelevanceFragment getItem(int position) {
            return mListFragment.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // no-op
        }
    }
}
