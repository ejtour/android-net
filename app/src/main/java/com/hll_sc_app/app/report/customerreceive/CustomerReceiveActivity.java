package com.hll_sc_app.app.report.customerreceive;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.event.ShopSearchEvent;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.customerreceive.ReceiveCustomerBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SearchSelectionWindow;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/30
 */

@Route(path = RouterConfig.REPORT_CUSTOMER_RECEIVE)
public class CustomerReceiveActivity extends BaseLoadActivity implements ICustomerReceiveContract.ICustomerReceiveView {
    @BindView(R.id.trl_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.trl_tab_one)
    TextView mPurchaser;
    @BindView(R.id.trl_tab_one_arrow)
    TriangleView mPurchaserArrow;
    @BindView(R.id.trl_tab_two)
    TextView mDate;
    @BindView(R.id.trl_tab_two_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.trl_list_view)
    RecyclerView mListView;
    @BindView(R.id.trl_refresh_view)
    SmartRefreshLayout mRefreshView;
    @Autowired(name = "parcelable")
    ReceiveCustomerBean mBean;
    private ICustomerReceiveContract.ICustomerReceivePresenter mPresenter;
    private CustomerReceiveAdapter mAdapter;
    private DateRangeWindow mDateRangeWindow;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private EmptyView mEmptyView;
    private SearchSelectionWindow<PurchaserBean> mPurchaserWindow;
    private SearchSelectionWindow<ShopSearchEvent> mShopWindow;
    private boolean mWindowInit;

    public static void start(ReceiveCustomerBean bean) {
        RouterUtil.goToActivity(RouterConfig.REPORT_CUSTOMER_RECEIVE, bean);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_tab_two_refresh_layout);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
        initData();
    }

    private void initData() {
        mReq.put("supplierID", UserConfig.getGroupID());
        if (!isShop()) {
            mReq.put("type", "1");
        } else {
            mReq.put("type", "2");
            mReq.put("isShow", String.valueOf(mBean.isShow()));
        }
        Date date = new Date();
        mDate.setTag(R.id.date_start, date);
        mDate.setTag(R.id.date_end, date);
        updateSelectedDate();
        mPresenter = CustomerReceivePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectedDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
        mReq.put("startDate", CalendarUtils.toLocalDate(startDate));
        mReq.put("endDate", CalendarUtils.toLocalDate(endDate));
    }

    private void initView() {
        mAdapter = new CustomerReceiveAdapter(mBean);
        int space = UIUtils.dip2px(10);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, space));
        mListView.setAdapter(mAdapter);
        if (isShop()) {
            mTitleBar.setHeaderTitle(mBean.getPurchaserName());
            mListView.setPadding(space, 0, space, 0);
            mPurchaser.setText("全部");
            View header = View.inflate(this, R.layout.view_report_customer_receive_header, null);
            header.<TextView>findViewById(R.id.crh_in).setText(String.format("总计进货：¥%s", CommonUtils.formatMoney(mBean.getPuchaseAmount())));
            header.<TextView>findViewById(R.id.crh_out).setText(String.format("总计退货：¥%s", CommonUtils.formatMoney(mBean.getReturnsAmount())));
            mAdapter.setHeaderView(header);
        } else {
            mTitleBar.setHeaderTitle("客户收货查询");
            mListView.setPadding(space, space, space, 0);
            mPurchaser.setText("采购商");
        }
        mRefreshView.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.loadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.refresh();
            }
        });
        initWindow();
    }

    private void initWindow() {
        OnRefreshLoadMoreListener listener = new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.windowLoadMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.windowRefresh();
            }
        };
        PopupWindow.OnDismissListener onDismissListener = () -> {
            mPurchaserArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
            mPurchaser.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
        };
        if (isShop()) {
            mShopWindow = new SearchSelectionWindow<>(this, ShopSearchEvent::getName)
                    .setOnRefreshLoadMoreListener(listener)
                    .setSearchLabel("门店")
                    .setCallback(new SearchSelectionWindow.SearchSelectionCallback<ShopSearchEvent>() {
                        @Override
                        public void search() {
                            mPresenter.windowLoad();
                        }

                        @Override
                        public void select(ShopSearchEvent shopSearchEvent) {
                            boolean isAll = TextUtils.isEmpty(shopSearchEvent.getShopMallId());
                            if (isAll) {
                                mReq.put("demandID", "");
                                mReq.put("shopName", "");
                                mPurchaser.setText("全部");
                            } else {
                                mReq.put("demandID", shopSearchEvent.getShopMallId());
                                mReq.put("shopName", shopSearchEvent.getName());
                                mPurchaser.setText(shopSearchEvent.getName());
                            }
                            mPresenter.loadList();
                        }
                    });
            mShopWindow.setOnDismissListener(onDismissListener);
        } else {
            mPurchaserWindow = new SearchSelectionWindow<>(this, PurchaserBean::getPurchaserName)
                    .setEnableLoadMore(true)
                    .setOnRefreshLoadMoreListener(listener)
                    .setSearchLabel("集团")
                    .setCallback(new SearchSelectionWindow.SearchSelectionCallback<PurchaserBean>() {
                        @Override
                        public void search() {
                            mPresenter.windowLoad();
                        }

                        @Override
                        public void select(PurchaserBean purchaserBean) {
                            boolean isAll = TextUtils.isEmpty(purchaserBean.getExtGroupID());
                            if (isAll) {
                                mReq.put("groupID", "");
                                mReq.put("purchaserName", "");
                                mPurchaser.setText("采购商");
                            } else {
                                mReq.put("groupID", purchaserBean.getExtGroupID());
                                mReq.put("purchaserName", purchaserBean.getPurchaserName());
                                mPurchaser.setText(purchaserBean.getPurchaserName());
                            }
                            mPresenter.loadList();
                        }
                    });
            mPurchaserWindow.setOnDismissListener(onDismissListener);
        }
    }

    @OnClick(R.id.trl_tab_two_btn)
    public void showDateRangeWindow(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                mDate.setTag(R.id.date_start, start);
                mDate.setTag(R.id.date_end, end);
                updateSelectedDate();
                mPresenter.loadList();
            });
            mDateRangeWindow.setReset(false);
            mDateRangeWindow.setSelectCalendarRange((Date) mDate.getTag(R.id.date_start), (Date) mDate.getTag(R.id.date_end));
        }
        mDateRangeWindow.setOnDismissListener(() -> {
            mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
            mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
        });
        mDateRangeWindow.showAsDropDownFix(view);
    }

    @OnClick(R.id.trl_tab_one_btn)
    public void showSelectionWindow(View view) {
        if (mWindowInit) {
            mPresenter.windowLoad();
            return;
        }
        mPurchaserArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mPurchaser.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        getCurWindow().showAsDropDownFix(view);
    }

    public SearchSelectionWindow getCurWindow() {
        return isShop() ? mShopWindow : mPurchaserWindow;
    }

    @Override
    public void setData(List<ReceiveCustomerBean> list, boolean append) {
        if (append) {
            if (!CommonUtils.isEmpty(list)) {
                mAdapter.addData(list);
            }
        } else {
            if (CommonUtils.isEmpty(list)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("没有数据哦");
            }
            mAdapter.setNewData(list);
        }
        mRefreshView.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    @Override
    public boolean isShop() {
        return mBean != null;
    }

    @Override
    public String getSearchWords() {
        return getCurWindow().getSearchWords();
    }

    @Override
    public void setShopData(List<ShopSearchEvent> list) {
        mWindowInit = true;
        if (!CommonUtils.isEmpty(list)) {
            ShopSearchEvent all = new ShopSearchEvent();
            all.setName("全部");
            list.add(0, all);
        }
        mShopWindow.refreshList(list);
    }

    @Override
    public void setPurchaserData(List<PurchaserBean> list, boolean append) {
        if (append) {
            mPurchaserWindow.addList(list);
        } else {
            mWindowInit = true;
            if (!CommonUtils.isEmpty(list)) {
                PurchaserBean all = new PurchaserBean();
                all.setPurchaserName("全部");
                list.add(0, all);
            }
            mPurchaserWindow.refreshList(list);
        }
        mPurchaserWindow.setEnableLoadMore(list != null && list.size() == 20);
    }

    @Override
    public void showError(UseCaseException e) {
        super.showError(e);
        if (e.getLevel() == UseCaseException.Level.NET) {
            initEmptyView();
            mEmptyView.setNetError();
        }
    }

    private void initEmptyView() {
        if (mEmptyView == null) {
            mEmptyView = EmptyView.newBuilder(this)
                    .setOnClickListener(mPresenter::start)
                    .create();
            mAdapter.setEmptyView(mEmptyView);
        }
    }

    @Override
    public void hideLoading() {
        if (mShopWindow != null) {
            mShopWindow.closeHeaderOrFooter();
        }
        if (mPurchaserWindow != null) {
            mPurchaserWindow.closeHeaderOrFooter();
        }
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }
}
