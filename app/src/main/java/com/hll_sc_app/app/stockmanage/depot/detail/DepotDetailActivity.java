package com.hll_sc_app.app.stockmanage.depot.detail;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.app.deliverymanage.range.DeliveryRangeActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.GoodsSearch;
import com.hll_sc_app.app.stockmanage.depot.category.DepotCategoryActivity;
import com.hll_sc_app.app.stockmanage.depot.edit.DepotEditActivity;
import com.hll_sc_app.app.stockmanage.selectproduct.ProductSelectActivity;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.event.SingleListEvent;
import com.hll_sc_app.bean.goods.GoodsBean;
import com.hll_sc_app.bean.stockmanage.DepotGoodsReq;
import com.hll_sc_app.bean.stockmanage.DepotResp;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.LogUtil;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.adapter.ViewPagerAdapter;
import com.hll_sc_app.widget.ScrollableViewPager;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.stockmanage.DepotCategoryView;
import com.hll_sc_app.widget.stockmanage.DepotGoodsView;
import com.hll_sc_app.widget.stockmanage.DepotRangeView;
import com.hll_sc_app.widget.stockmanage.DepotTabItemView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/9
 */
@Route(path = RouterConfig.ACTIVITY_DEPOT_DETAIL)
public class DepotDetailActivity extends BaseLoadActivity implements IDepotDetailContract.IDepotDetailView {
    private static final int REQ_CODE = 0x290;
    @BindView(R.id.add_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.add_tag)
    TextView mTag;
    @BindView(R.id.add_name)
    TextView mName;
    @BindView(R.id.add_no)
    TextView mNo;
    @BindView(R.id.add_status)
    TextView mStatus;
    @BindView(R.id.add_contact)
    TextView mContact;
    @BindView(R.id.add_address)
    TextView mAddress;
    @BindView(R.id.add_tab_layout)
    TabLayout mTabLayout;
    @BindView(R.id.add_view_pager)
    ScrollableViewPager mViewPager;
    @Autowired(name = "object0")
    String mID;
    private DepotResp mResp;
    private IDepotDetailContract.IDepotDetailPresenter mPresenter;
    private DepotRangeView mRangeView;
    private DepotCategoryView mCategoryView;
    private DepotGoodsView mGoodsView;
    private boolean mHasChanged;
    private List<GoodsBean> mGoodsBeans;

    public static void start(Activity cxt, int reqCode, String id) {
        Object[] args = {id};
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_DEPOT_DETAIL, cxt, reqCode, args);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_depot_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        mPresenter = DepotDetailPresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
        initView();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void initView() {
        mTitleBar.setLeftBtnClick(v -> onBackPressed());
        mRangeView = new DepotRangeView(this);
        mCategoryView = new DepotCategoryView(this);
        mGoodsView = new DepotGoodsView(this);
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(mRangeView, mCategoryView, mGoodsView);
        pagerAdapter.setTitles(new String[]{"配送范围", "存储分类", "存储单品"});
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        mTabLayout.setupWithViewPager(mViewPager);
        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                tab.setCustomView(new DepotTabItemView(this));
                tab.setIcon(R.drawable.bg_primary_ind);
            }
        }
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                DepotTabItemView customView = (DepotTabItemView) tab.getCustomView();
                if (customView != null) customView.select();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                DepotTabItemView customView = (DepotTabItemView) tab.getCustomView();
                if (customView != null) customView.unSelect();
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // no-op
            }
        });

        mRangeView.setOnClickListener(v -> DeliveryRangeActivity.start(this, REQ_CODE, mResp));

        mCategoryView.setOnClickListener(v -> DepotCategoryActivity.start(this, REQ_CODE, mResp));

        mGoodsView.setOnClickListener(v -> getGoodsBeans());

        mGoodsView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        mGoodsView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(DepotDetailActivity.this, searchContent, GoodsSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresenter.searchGoodsList();
            }
        });
        mGoodsView.setOnDelClickListener(v -> mPresenter.delGoods(v.getTag().toString()));
    }

    @OnClick(R.id.add_edit)
    public void edit() {
        if (mResp == null) {
            return;
        }
        DepotEditActivity.start(this, REQ_CODE, mResp);
    }

    @Override
    public void setData(DepotResp resp) {
        mResp = resp;
        mName.setText(resp.getHouseName());
        mTag.setVisibility(resp.getIsDefault() == 1 ? View.VISIBLE : View.GONE);
        mNo.setText(String.format("编号：%s", resp.getHouseCode()));
        SpannableString ss = new SpannableString("状态：" + (resp.getIsActive() == 1 ? "启用" : "停用"));
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(this, R.color.color_7ed321)), 3, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mStatus.setText(ss);
        mContact.setText(String.format("联系方式：%s / %s", resp.getCharge(), PhoneUtil.formatPhoneNum(mResp.getLinkTel())));
        mAddress.setText(String.format("仓库地址：%s", resp.getAddress()));

        mRangeView.setData(resp);
        mCategoryView.setData(resp.getWarehouseStoreCategoryList());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQ_CODE) {
            mHasChanged = true;
            mPresenter.start();
        }
        if (resultCode == Constants.SEARCH_RESULT_CODE && data != null) {
            String name = data.getStringExtra("name");
            if (!TextUtils.isEmpty(name))
                mGoodsView.showSearchContent(true, name);
        }
    }

    @Override
    public void onBackPressed() {
        if (mHasChanged) {
            setResult(RESULT_OK);
        }
        super.onBackPressed();
    }

    @Override
    public void hideLoading() {
        mGoodsView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void setGoodsList(List<GoodsBean> list, boolean append) {
        mGoodsView.setData(list, append);
    }

    @Override
    public String getSearchWords() {
        if (mGoodsView == null) return null;
        return mGoodsView.getSearchWords();
    }

    @Override
    public String getDepotID() {
        return mID;
    }

    @Override
    public void removeSuccess() {
        mHasChanged = true;
        mGoodsView.removeSuccess();
    }

    @Override
    public void cacheAllGoods(List<GoodsBean> list) {
        mGoodsBeans = list == null ? new ArrayList<>() : list;
        ProductSelectActivity.start("存储单品", "", new ArrayList<>(mGoodsBeans));
    }

    @Override
    public void saveSuccess() {
        mHasChanged = true;
        mGoodsBeans = null;
        mPresenter.searchGoodsList();
    }

    private void getGoodsBeans() {
        if (mGoodsBeans == null) {
            mPresenter.getAllGoods();
            return;
        }
        ProductSelectActivity.start("存储单品", "", new ArrayList<>(mGoodsBeans));
    }

    @Subscribe
    public void handleGoodsEvent(SingleListEvent<GoodsBean> event) {
        if (event.getClazz() == GoodsBean.class && mResp != null) {
            DepotGoodsReq req = new DepotGoodsReq();
            req.setGroupID(mResp.getGroupID());
            req.setHouseID(mResp.getId());
            req.setIsWholeCountry(mResp.getIsWholeCountry());
            List<String> delList = new ArrayList<>();
            List<DepotGoodsReq.DepotGoodsBean> list = new ArrayList<>();
            req.setDeleteProductIDList(delList);
            req.setWarehouseStoreProductList(list);
            if (!CommonUtils.isEmpty(event.getList())) {
                if (!CommonUtils.isEmpty(mGoodsBeans)) {
                    for (GoodsBean bean : mGoodsBeans) {
                        if (!event.getList().contains(bean)) {
                            delList.add(bean.getProductID());
                        }
                    }
                }
                for (GoodsBean bean : event.getList()) {
                    DepotGoodsReq.DepotGoodsBean goodsBean = new DepotGoodsReq.DepotGoodsBean();
                    list.add(goodsBean);
                    goodsBean.setProductCode(bean.getProductCode());
                    goodsBean.setProductID(bean.getProductID());
                    goodsBean.setProductName(bean.getProductName());
                }
            } else {
                for (GoodsBean bean : mGoodsBeans) {
                    delList.add(bean.getProductID());
                }
            }
            mPresenter.saveGoodsList(req);
        }
    }
}
