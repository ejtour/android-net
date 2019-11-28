package com.hll_sc_app.app.marketingsetting.product;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hll_sc_app.R;
import com.hll_sc_app.app.marketingsetting.adapter.MarketingListAdapter;
import com.hll_sc_app.app.marketingsetting.product.add.ProductMarketingAddActivity;
import com.hll_sc_app.app.marketingsetting.product.check.ProductMarketingCheckActivity;
import com.hll_sc_app.app.search.SearchActivity;
import com.hll_sc_app.app.search.stratery.DiscountSearch;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.event.MarketingEvent;
import com.hll_sc_app.bean.marketingsetting.MarketingListResp;
import com.hll_sc_app.bean.marketingsetting.MarketingStatusBean;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchView;
import com.hll_sc_app.widget.SingleSelectionWindow;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 商品促销列表
 */
@Route(path = RouterConfig.ACTIVITY_MARKETING_PRODUCT_LIST, extras = Constant.LOGIN_EXTRA)
public class ProductMarketingListActivity extends BaseLoadActivity implements IProductMarketingContract.IView {
    private final String FORMAT_DATE = "yyyyMMdd";
    public static final int TYPE_ORDER = 1;
    public static final int TYPE_PRODUCT = 2;
    @BindView(R.id.search_view)
    SearchView mSearchView;
    @BindView(R.id.ll_list)
    LinearLayout mListContainer;
    @BindView(R.id.ll_empty)
    LinearLayout mEmptyContainer;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.recyclerView)
    RecyclerView mList;
    @BindView(R.id.view_divider_filter)
    View mFilterDivider;
    @BindView(R.id.txt_filter_status)
    TextView mFilterStatus;
    @BindView(R.id.txt_filter_date)
    TextView mFilterDate;
    @BindView(R.id.btn_add)
    TextView mEmptyTxtAdd;
    @BindView(R.id.txt_empty_title)
    TextView mEmptyTitle;
    @Autowired(name = "object0")
    int mDiscountType;
    @BindView(R.id.txt_title_right)
    TextView mTxtTitleRight;
    @BindView(R.id.ll_button_bottom)
    LinearLayout mLlButtonBottom;
    @BindView(R.id.view_divider_1)
    View mViewDivider1;
    @BindView(R.id.txt_filter_type)
    TextView mTxtFilterType;


    private Unbinder unbinder;
    private IProductMarketingContract.IPresenter mPresent;
    private SingleSelectionWindow<MarketingStatusBean> mStatusWindow;
    private SingleSelectionWindow<NameValue> mTypeWindow;
    private DateRangeWindow mDateRangeWindow;
    private MarketingStatusBean mStatusSelected;
    private MarketingListAdapter mListAdapter;
    private String mFilterStartTime;
    private String mFilterEndTime;
    private String mFilterType = "";

    public static void start(int discountType) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_MARKETING_PRODUCT_LIST, discountType);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing_product_list);
        unbinder = ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
        initView();
        mPresent = ProductMarketingPresenter.newInstance();
        mPresent.register(this);
        mPresent.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        mEmptyTxtAdd.setText(String.format("新增一个%s促销活动", getType()));
        mEmptyTitle.setText(String.format("您还没有设置过%s促销活动", getType()));
        initSearch();
        initList();
        mListContainer.setVisibility(View.GONE);
        mEmptyContainer.setVisibility(View.GONE);

        if (mDiscountType == TYPE_PRODUCT) {
            mTxtTitleRight.setText("导出");
            mLlButtonBottom.setVisibility(View.VISIBLE);
            mTxtTitleRight.setOnClickListener(v -> {
                mPresent.export("");
            });
        } else {
            mTxtTitleRight.setText("新增");
            mLlButtonBottom.setVisibility(View.GONE);
            mTxtTitleRight.setOnClickListener(v -> {
                ProductMarketingAddActivity.start(null, mDiscountType);
            });
            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) mViewDivider1.getLayoutParams();
            params.startToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
            mTxtFilterType.setText("");
        }

    }


    /**
     * 判断是商品促销还是订单促销
     *
     * @return
     */
    private String getType() {
        return mDiscountType == TYPE_ORDER ? "订单" : "商品";
    }

    /**
     * *头部搜索样式
     */
    private void initSearch() {
        mSearchView.setSearchBackgroundColor(R.drawable.bg_search_text);
        mSearchView.setTextColorWhite();
        mSearchView.setSearchTextLeft();
        mSearchView.setContentClickListener(new SearchView.ContentClickListener() {
            @Override
            public void click(String searchContent) {
                SearchActivity.start(ProductMarketingListActivity.this,
                        getDiscountName(), DiscountSearch.class.getSimpleName());
            }

            @Override
            public void toSearch(String searchContent) {
                mPresent.refreshList();
            }
        });
    }

    /**
     * 初始化列表配置
     */
    private void initList() {
        mList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mListAdapter = new MarketingListAdapter(null);
        mListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.txt_check_detail) {
                ProductMarketingCheckActivity.start(mListAdapter.getItem(position).getId(), mDiscountType);
            }
        });
        mList.setAdapter(mListAdapter);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.getMoreList();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refreshList();
            }
        });
    }

    @Override
    public void showMarketingStatus(List<MarketingStatusBean> marketingStatusBeans) {
        if (mStatusWindow == null) {
            mStatusWindow = new SingleSelectionWindow<>(this, marketingStatusBean -> {
                return marketingStatusBean.getValue();
            });
            mStatusWindow.setSelectListener(marketingStatusBean -> {
                mStatusSelected = marketingStatusBean;
                mFilterStatus.setText(marketingStatusBean.getValue());
                mPresent.refreshList();

            });
        }
        mStatusWindow.refreshList(marketingStatusBeans);
    }

    @Override
    public String getDiscountName() {
        return mSearchView.getSearchContent();
    }

    @Override
    public String getDiscountStatus() {
        return mStatusSelected == null ? "" : mStatusSelected.getKey();
    }


    @Override
    public void showList(List<MarketingListResp.MarketingBean> marketingBeans) {
        if (mPresent.getPageNum() > 1) {
            mListAdapter.addData(marketingBeans);
        } else {
            if (marketingBeans.size() == 0) {
                if (isFilterModal()) {
                    mListAdapter.setNewData(null);
                    mListAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("没有符合条件的数据哦~").create());
                } else {
                    mListContainer.setVisibility(View.GONE);
                    mEmptyContainer.setVisibility(View.VISIBLE);
                }

            } else {
                mListContainer.setVisibility(View.VISIBLE);
                mEmptyContainer.setVisibility(View.GONE);
                mListAdapter.setNewData(marketingBeans);
            }
        }
    }

    @Override
    public String getStartTime() {
        return mFilterStartTime;
    }

    @Override
    public String getEndTime() {
        return mFilterEndTime;
    }

    @Override
    public String getDiscountType() {
        return mDiscountType + "";
    }

    /**
     * 是否是过滤模式
     *
     * @return
     */
    private boolean isFilterModal() {
        return !TextUtils.isEmpty(getDiscountStatus())
                || !TextUtils.isEmpty(getStartTime())
                || !TextUtils.isEmpty(getEndTime())
                || !TextUtils.isEmpty(getDiscountName());
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @OnClick({R.id.txt_filter_status_true, R.id.txt_filter_date_true, R.id.img_close, R.id.txt_add, R.id.btn_add, R.id.txt_filter_type_true})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txt_filter_status_true:
                if (mStatusWindow != null) {
                    mStatusWindow.showAsDropDown(mFilterDivider);
                } else {
                    showToast("正在请求促销状态");
                }
                break;
            case R.id.txt_filter_date_true:
                if (mDateRangeWindow == null) {
                    mDateRangeWindow = new DateRangeWindow(this);
                    mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                        if (start != null && end != null) {
                            Calendar calendarStart = Calendar.getInstance();
                            calendarStart.setTimeInMillis(start.getTimeInMillis());
                            mFilterStartTime = CalendarUtils.format(calendarStart.getTime(), FORMAT_DATE);
                            mFilterStartTime += "0000";
                            Calendar calendarEnd = Calendar.getInstance();
                            calendarEnd.setTimeInMillis(end.getTimeInMillis());
                            mFilterEndTime = CalendarUtils.format(calendarEnd.getTime(), FORMAT_DATE);
                            mFilterEndTime += "2359";
                        } else {
                            mFilterStartTime = "";
                            mFilterEndTime = "";
                        }
                        mPresent.refreshList();
                    });
                }
                mDateRangeWindow.showAsDropDown(mFilterDivider);
                break;
            case R.id.img_close:
                finish();
                break;
            case R.id.btn_add:
            case R.id.txt_add:
                ProductMarketingAddActivity.start(null, mDiscountType);
                break;
            case R.id.txt_filter_type_true:
                if (mTypeWindow == null) {
                    mTypeWindow = new SingleSelectionWindow<>(this, NameValue::getName);
                    List<NameValue> nameValues = new ArrayList<>();
                    nameValues.add(new NameValue("全部", ""));
                    nameValues.add(new NameValue("直降", "4"));
                    nameValues.add(new NameValue("赠券", "1"));
                    nameValues.add(new NameValue("满减", "2"));
                    nameValues.add(new NameValue("打折", "3"));
                    mTypeWindow.refreshList(nameValues);
                    mTypeWindow.setSelectListener(nameValue -> {
                        mFilterType = nameValue.getValue();
                        mPresent.refreshList();
                    });
                }
                break;
            default:
                break;
        }
    }

    @Subscribe()
    public void onEvent(MarketingEvent event) {
        if (event.getTarget() != MarketingEvent.Target.MARKETING_PRODUCT_LIST) {
            return;
        }
        // 商品属性列表展示
        if (event.isRefresh()) {
            mPresent.refreshList();
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

    @Override
    public String getFilterType() {
        return mFilterType;
    }
}
