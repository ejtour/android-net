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
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.common.PurchaserShopBean;
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
    @BindView(R.id.rcr_in)
    TextView mInAmount;
    @BindView(R.id.rcr_out)
    TextView mOutAmount;
    @BindView(R.id.rcr_list_view)
    RecyclerView mListView;
    @BindView(R.id.rcr_refresh_view)
    SmartRefreshLayout mRefreshView;
    @Autowired(name = "parcelable")
    ReceiveCustomerBean mBean;
    @Autowired(name = "startDate")
    Date mStartDate;
    @Autowired(name = "endDate")
    Date mEndDate;
    private ICustomerReceiveContract.ICustomerReceivePresenter mPresenter;
    private CustomerReceiveAdapter mAdapter;
    private DateRangeWindow mDateRangeWindow;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private EmptyView mEmptyView;
    private SearchSelectionWindow<PurchaserBean> mPurchaserWindow;
    private SearchSelectionWindow<PurchaserShopBean> mShopWindow;
    private boolean mWindowInit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_customer_receive);
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
            mReq.put("groupID", mBean.getGroupID());
            mReq.put("purchaserID", mBean.getPurchaserID());
            mReq.put("isShow", String.valueOf(mBean.isShow()));
        }
        if (mStartDate == null) {
            mStartDate = mEndDate = new Date();
        }
        updateSelectedDate();
        mPresenter = CustomerReceivePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectedDate() {
        mDate.setText(String.format("%s - %s", CalendarUtils.format(mStartDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(mEndDate, Constants.SLASH_YYYY_MM_DD)));
        mReq.put("startDate", CalendarUtils.toLocalDate(mStartDate));
        mReq.put("endDate", CalendarUtils.toLocalDate(mEndDate));
    }

    private void initView() {
        mAdapter = new CustomerReceiveAdapter(mBean);
        int space = UIUtils.dip2px(10);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, space));
        mListView.setAdapter(mAdapter);
        mTitleBar.setRightBtnVisible(false);
        if (isShop()) {
            mTitleBar.setHeaderTitle(mBean.getPurchaserName());
            mListView.setPadding(space, 0, space, 0);
            mPurchaser.setText("全部");
            mInAmount.setVisibility(View.VISIBLE);
            mInAmount.setText(String.format("总计进货：¥%s", CommonUtils.formatMoney(mBean.getPurchaseAmount())));
            mOutAmount.setVisibility(View.VISIBLE);
            mOutAmount.setText(String.format("总计退货：¥%s", CommonUtils.formatMoney(mBean.getReturnsAmount())));
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
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ARouter.getInstance().build(mBean == null ? RouterConfig.REPORT_CUSTOMER_RECEIVE : RouterConfig.ACTIVITY_QUERY_CUSTOM_RECEIVE)
                    .withParcelable("parcelable", mAdapter.getItem(position))
                    .withSerializable("startDate", mStartDate)
                    .withSerializable("endDate", mEndDate)
                    .navigation();
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
            mShopWindow = new SearchSelectionWindow<>(this, PurchaserShopBean::getShopName)
                    .setOnRefreshLoadMoreListener(listener)
                    .setSearchLabel("门店")
                    .setCallback(new SearchSelectionWindow.SearchSelectionCallback<PurchaserShopBean>() {
                        @Override
                        public void search() {
                            mPresenter.windowLoad();
                        }

                        @Override
                        public void select(PurchaserShopBean purchaserShopBean) {
                            boolean isAll = TextUtils.isEmpty(purchaserShopBean.getExtShopID());
                            if (isAll) {
                                mReq.put("demandID", "");
                                mReq.put("shopName", "");
                                mPurchaser.setText("全部");
                            } else {
                                mReq.put("demandID", purchaserShopBean.getExtShopID());
                                mReq.put("shopName", purchaserShopBean.getShopName());
                                mPurchaser.setText(purchaserShopBean.getShopName());
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
                                mReq.put("purchaserID", "");
                                mReq.put("purchaserName", "");
                                mPurchaser.setText("采购商");
                            } else {
                                mReq.put("groupID", purchaserBean.getExtGroupID());
                                mReq.put("purchaserID", purchaserBean.getPurchaserID());
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
                mStartDate = start;
                mEndDate = end;
                updateSelectedDate();
                mPresenter.loadList();
            });
            mDateRangeWindow.setMaxDayRange(31);
            mDateRangeWindow.setReset(false);
            mDateRangeWindow.setSelectCalendarRange(mStartDate, mEndDate);
        }
        mDateRangeWindow.setOnDismissListener(() -> {
            mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
            mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
        });
        mDateRangeWindow.showAsDropDownFix(view);
    }

    @OnClick(R.id.trl_tab_one_btn)
    public void showSelectionWindow(View view) {
        if (!mWindowInit) {
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
    public String getPurchaserID() {
        return isShop() ? mBean.getPurchaserID() : "";
    }

    @Override
    public void setShopData(List<PurchaserShopBean> list) {
        mWindowInit = true;
        if (!CommonUtils.isEmpty(list)) {
            PurchaserShopBean all = new PurchaserShopBean();
            all.setShopName("全部");
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
        mPurchaserWindow.setEnableLoadMore(list != null && list.size() == 10);
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
