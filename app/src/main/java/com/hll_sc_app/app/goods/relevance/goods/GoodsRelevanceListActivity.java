package com.hll_sc_app.app.goods.relevance.goods;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.goods.relevance.goods.fragment.BaseGoodsRelevanceFragment;
import com.hll_sc_app.app.goods.relevance.goods.fragment.relevance.GoodsRelevanceListFragment;
import com.hll_sc_app.app.goods.relevance.goods.fragment.unrelevance.GoodsUnRelevanceListFragment;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsNameSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.LoginInterceptor;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.event.GoodsRelevanceRefreshEvent;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.order.detail.TransferDetailBean;
import com.hll_sc_app.bean.order.transfer.TransferBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
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
public class GoodsRelevanceListActivity extends BaseLoadActivity implements IGoodsRelevanceListContract.IGoodsRelevanceListView {
    public static final int REQ_KEY = 0x654;
    public static final String TRANSFER_KEY = "transfer";
    public static final String PURCHASER_KEY = "purchaser";
    static final String[] STR_TITLE = {"未关联", "已关联"};
    @BindView(R.id.searchView)
    SearchView mSearchView;
    @BindView(R.id.rl_toolbar)
    RelativeLayout mRlToolbar;
    @BindView(R.id.tab)
    SlidingTabLayout mTab;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @Autowired(name = PURCHASER_KEY)
    PurchaserBean purchaserBean;
    @Autowired(name = TRANSFER_KEY)
    TransferBean transferBean;
    private List<BaseGoodsRelevanceFragment> mListFragment;
    private GoodsRelevanceListPresenter mPresenter;

    /**
     * start
     *
     * @param purchaser 采购商集团
     * @param transfer  转单详情
     */
    public static void start(Activity context, PurchaserBean purchaser, TransferBean transfer) {
        Postcard postcard = ARouter.getInstance()
                .build(RouterConfig.GOODS_RELEVANCE_LIST)
                .withParcelable(PURCHASER_KEY, purchaser)
                .withParcelable(TRANSFER_KEY, transfer)
                .setProvider(new LoginInterceptor());
        if (context != null) postcard.navigation(context, REQ_KEY);
        else postcard.navigation();
    }

    public static void start(Activity context, TransferBean transfer) {
        start(context, null, transfer);
    }

    public static void start(PurchaserBean purchaser) {
        start(null, purchaser, null);
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
        if (transferBean != null) {
            mPresenter = GoodsRelevanceListPresenter.newInstance(transferBean.getId());
            mPresenter.register(this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        LinearLayout llContent = mSearchView.getContentView();
        if (llContent != null) {
            llContent.setBackgroundResource(R.drawable.bg_search_text);
            llContent.setGravity(Gravity.CENTER_VERTICAL);
            mSearchView.setTextColorWhite();
        }
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(GoodsRelevanceListActivity.this,
                        searchContent, GoodsNameSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                refreshFragment();
            }
        });
        mListFragment = new ArrayList<>();
        GoodsUnRelevanceListFragment relevanceListFragment = GoodsUnRelevanceListFragment.newInstance(purchaserBean);
        mListFragment.add(relevanceListFragment);
        GoodsRelevanceListFragment unRelevanceListFragment = GoodsRelevanceListFragment.newInstance(purchaserBean);
        mListFragment.add(unRelevanceListFragment);
        FragmentListAdapter adapter = new FragmentListAdapter(getSupportFragmentManager(), mListFragment);
        mViewPager.setAdapter(adapter);
        mTab.setViewPager(mViewPager, STR_TITLE);
    }

    public void refreshFragment() {
        if (!CommonUtils.isEmpty(mListFragment)) {
            if (transferBean != null) { // 对转单数据进行本地处理
                showTransferDetail(transferBean);
                return;
            }
            for (BaseGoodsRelevanceFragment fragment : mListFragment) {
                fragment.refreshFragment(mSearchView.getSearchContent());
            }
        }
    }

    /**
     * 请求转单明细
     */
    public void reqTransferDetail() {
        if (mPresenter != null) mPresenter.reqTransferDetail();
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

    @Subscribe
    public void onEvent(GoodsRelevanceRefreshEvent event) {
        if (mPresenter == null) refreshFragment();
        else reqTransferDetail();
    }

    @Override
    @OnClick(R.id.img_close)
    public void onBackPressed() {
        if (transferBean != null) {
            Intent intent = new Intent();
            intent.putExtra(TRANSFER_KEY, transferBean);
            setResult(RESULT_OK, intent);
        }
        super.onBackPressed();
    }

    @Override
    public void showTransferDetail(TransferBean transferBean) {
        this.transferBean = transferBean;
        List<TransferDetailBean> detailBeans = new ArrayList<>();
        if (!CommonUtils.isEmpty(transferBean.getDetailList()))
            for (TransferDetailBean bean : transferBean.getDetailList()) {
                if (bean.getHomologous() == 0 && (TextUtils.isEmpty(mSearchView.getSearchContent())
                        || bean.getGoodsName().contains(mSearchView.getSearchContent())))
                    detailBeans.add(bean);
            }
        mListFragment.get(0).refreshList(detailBeans);
        detailBeans = new ArrayList<>();
        if (!CommonUtils.isEmpty(transferBean.getDetailList()))
            for (TransferDetailBean bean : transferBean.getDetailList()) {
                if (bean.getHomologous() == 1 && (TextUtils.isEmpty(mSearchView.getSearchContent())
                        || bean.getProductName().contains(mSearchView.getSearchContent())))
                    detailBeans.add(bean);
            }
        mListFragment.get(1).refreshList(detailBeans);
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
