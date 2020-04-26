package com.hll_sc_app.app.report.customersettle;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.goods.PurchaserBean;
import com.hll_sc_app.bean.report.customersettle.CustomerSettleBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.SingleSelectionWindow;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/24
 */
@Route(path = RouterConfig.REPORT_CUSTOMER_SETTLE)
public class CustomerSettleActivity extends BaseLoadActivity implements ICustomerSettleContract.ICustomerSettleView {
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
    @BindView(R.id.rcs_list_view)
    RecyclerView mListView;
    private ICustomerSettleContract.ICustomerSettlePresenter mPresenter;
    private CustomerSettleAdapter mAdapter;
    private DateRangeWindow mDateRangeWindow;
    private SingleSelectionWindow<PurchaserBean> mPurchaserWindow;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();
    private boolean mWindowInit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_customer_settle);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Date startDate = CalendarUtils.getFirstDateInMonth(CalendarUtils.getDateBeforeMonth(new Date(), 1));
        Date endDate = CalendarUtils.getLastDateInMonth(startDate);
        mDate.setTag(R.id.date_start, startDate);
        mDate.setTag(R.id.date_end, endDate);
        updateSelectDate();
        mReq.put("supplierId", UserConfig.getGroupID());
        mPresenter = CustomerSettlePresenter.newInstance();
        mPresenter.register(this);
        mPresenter.start();
    }

    private void updateSelectDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        getReq().put("startDate", CalendarUtils.toLocalDate(startDate));
        getReq().put("endDate", CalendarUtils.toLocalDate(endDate));
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
    }

    private void initView() {
        mTitleBar.setHeaderTitle("客户结算查询");
        mTitleBar.setRightBtnVisible(false);
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        mAdapter = new CustomerSettleAdapter();
        mListView.setAdapter(mAdapter);
        mPurchaser.setText("采购商");
    }

    @OnClick(R.id.rcs_see_detail)
    public void seeDetail() {

    }

    @OnClick(R.id.trl_tab_one_btn)
    public void showPurchaserWindow(View view) {
        if (!mWindowInit) {
            mPresenter.start();
            return;
        }
        mPurchaserArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mPurchaser.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        getPurchaserWindow().showAsDropDown(view);
    }

    @OnClick(R.id.trl_tab_two_btn)
    public void showDateRangeWindow(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                if (CalendarUtils.getDateBefore(end, 30).getTime() > start.getTime()) {
                    showToast("开始日期至结束日期限制选择31天以内");
                    return;
                }
                mDate.setTag(R.id.date_start, start);
                mDate.setTag(R.id.date_end, end);
                updateSelectDate();
                mPresenter.loadInfo();
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
    public void setData(List<CustomerSettleBean> list) {
        mAdapter.setNewData(list);
    }

    @Override
    public void setPurchaserData(List<PurchaserBean> list, boolean append) {
        if (append) {
            getPurchaserWindow().closeHeaderOrFooter();
            if (!CommonUtils.isEmpty(list)) {
                getPurchaserWindow().addList(list);
            }
        } else {
            getPurchaserWindow().refreshList(list);
            if (!mWindowInit) {
                mWindowInit = true;
                if (!CommonUtils.isEmpty(list)) {
                    PurchaserBean bean = list.get(0);
                    getPurchaserWindow().setSelect(bean);
                    selectPurchaser(bean);
                }
            }
        }
        getPurchaserWindow().setEnableLoadMore(list != null && list.size() == 10);
    }

    private SingleSelectionWindow<PurchaserBean> getPurchaserWindow() {
        if (mPurchaserWindow == null) {
            mPurchaserWindow = new SingleSelectionWindow<>(this, PurchaserBean::getPurchaserName);
            mPurchaserWindow.fixedHeight(UIUtils.dip2px(364));
            mPurchaserWindow.setSelectListener(purchaserBean -> {
                mPurchaserWindow.dismiss();
                selectPurchaser(purchaserBean);
            });
            mPurchaserWindow.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
                @Override
                public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                    mPresenter.windowLoadMore();
                }

                @Override
                public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                    // no-op
                }
            });
            mPurchaserWindow.setOnDismissListener(() -> {
                mPurchaserArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mPurchaser.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        return mPurchaserWindow;
    }

    private void selectPurchaser(PurchaserBean bean) {
        if (TextUtils.isEmpty(bean.getExtGroupID())) {
            mPurchaser.setText("采购商");
            mReq.put("groupId", "");
        } else {
            mPurchaser.setText(bean.getPurchaserName());
            mReq.put("groupId", bean.getExtGroupID());
        }
        mPresenter.loadInfo();
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }
}
