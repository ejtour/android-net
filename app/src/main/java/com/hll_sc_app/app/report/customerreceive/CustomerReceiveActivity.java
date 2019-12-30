package com.hll_sc_app.app.report.customerreceive;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.customerreive.ReceiveCustomerBean;
import com.hll_sc_app.bean.report.customerreive.ReceiveCustomerResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
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
    @Autowired(name = "object0")
    String mName;
    @Autowired(name = "object1")
    String mId;
    private ICustomerReceiveContract.ICustomerReceivePresenter mPresenter;
    private CustomerReceiveAdapter mAdapter;
    private DateRangeWindow mDateRangeWindow;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private EmptyView mEmptyView;

    /**
     * @param name 采购商名称
     * @param id   采购商id
     */
    public static void start(String name, String id) {
        RouterUtil.goToActivity(RouterConfig.REPORT_CUSTOMER_RECEIVE, name, id);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_tab_two_refresh_layout);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
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
        mAdapter = new CustomerReceiveAdapter();
        int space = UIUtils.dip2px(10);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, space));
        mListView.setAdapter(mAdapter);
        mTitleBar.setHeaderTitle(!TextUtils.isEmpty(mName) ? mName : "客户收货查询");
        if (!TextUtils.isEmpty(mId)) {
            mListView.setPadding(space, 0, space, 0);
            mPurchaser.setText("全部");
        } else {
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
                mPresenter.start();
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

    @Override
    public void setData(ReceiveCustomerResp resp, boolean append) {
        List<ReceiveCustomerBean> records = resp.getRecords();
        if (append) {
            if (!CommonUtils.isEmpty(records)) {
                mAdapter.addData(records);
            }
        } else {
            if (CommonUtils.isEmpty(records)) {
                initEmptyView();
                mEmptyView.reset();
                mEmptyView.setTips("没有数据哦");
            }
            mAdapter.setNewData(records);
        }
        mRefreshView.setEnableLoadMore(records != null && records.size() == 20);
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
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }
}
