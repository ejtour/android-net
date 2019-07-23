package com.hll_sc_app.app.report.orderGoods;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsBean;
import com.hll_sc_app.bean.report.orderGoods.OrderGoodsParam;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.SimpleDecoration;
import com.hll_sc_app.widget.TitleBar;
import com.hll_sc_app.widget.TriangleView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Calendar;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/22
 */

@Route(path = RouterConfig.REPORT_ORDER_GOODS)
public class OrderGoodsActivity extends BaseLoadActivity implements IOrderGoodsContract.IOrderGoodsView {
    @BindView(R.id.aog_title_bar)
    TitleBar mTitleBar;
    @BindView(R.id.aog_purchaser)
    TextView mPurchaser;
    @BindView(R.id.aog_purchaser_arrow)
    TriangleView mPurchaserArrow;
    @BindView(R.id.aog_date)
    TextView mDate;
    @BindView(R.id.aog_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.aog_list_view)
    RecyclerView mListView;
    @BindView(R.id.aog_refresh_view)
    SmartRefreshLayout mRefreshView;
    private ContextOptionsWindow mOptionsWindow;
    private DateRangeWindow mDateRangeWindow;
    private IOrderGoodsContract.IOrderGoodsPresenter mPresenter;
    private final OrderGoodsParam mParam = new OrderGoodsParam();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_goods);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        mPresenter = OrderGoodsPresenter.newInstance(mParam);
        mPresenter.register(this);
        mPresenter.start();
    }

    private void initView() {
        mTitleBar.setRightBtnClick(this::showOptionsWindow);
        OrderGoodsAdapter adapter = new OrderGoodsAdapter();
        mListView.addItemDecoration(new SimpleDecoration(Color.TRANSPARENT, UIUtils.dip2px(10)));
        mListView.setAdapter(adapter);
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

    private void showOptionsWindow(View v) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this)
                    .refreshList(Collections.singletonList(new OptionsBean(R.drawable.ic_export_option, OptionType.OPTION_EXPORT_DETAIL_INFO)))
                    .setListener((adapter, view, position) -> {
                        mOptionsWindow.dismiss();
                        export(null);
                    });
        }
        mOptionsWindow.showAsDropDownFix(v, Gravity.END);
    }

    private void export(String email) {
        mPresenter.export(email);
    }

    @OnClick({R.id.aog_purchaser_btn, R.id.aog_date_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.aog_purchaser_btn:
                break;
            case R.id.aog_date_btn:
                break;
        }
    }

    @Override
    public void hideLoading() {
        mRefreshView.closeHeaderOrFooter();
        super.hideLoading();
    }

    @Override
    public void showList(List<OrderGoodsBean> list, boolean append) {
        OrderGoodsAdapter adapter = (OrderGoodsAdapter) mListView.getAdapter();
        if (append) adapter.addData(list);
        else adapter.setNewData(list);
    }

    @Override
    public void bindEmail() {
        Utils.bindEmail(this, this::export);
    }

    @Override
    public void exportSuccess(String email) {
        Utils.exportSuccess(this, email);
    }

    @Override
    public void exportFailure(String msg) {
        Utils.exportFailure(this, msg);
    }

    /**
     * 初始化日期弹窗
     */
    private void initDateWindow() {
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                String beginDate = mParam.getStartDate();
                String endDate = mParam.getEndDate();
                if (start == null && end == null) {
                    mParam.setStartDate(null);
                    mParam.setEndDate(null);
                    mDate.setText("按日期筛选");
                    if (beginDate != null && endDate != null) {
                        mPresenter.getOrderGoodsDetails(true);
                    }
                    return;
                }
                if (start != null && end != null) {
                    Calendar calendarStart = Calendar.getInstance();
                    calendarStart.setTimeInMillis(start.getTimeInMillis());
                    String startStr = CalendarUtils.format(calendarStart.getTime(), CalendarUtils.FORMAT_DATE_TIME);
                    Calendar calendarEnd = Calendar.getInstance();
                    calendarEnd.setTimeInMillis(end.getTimeInMillis());
                    String endStr = CalendarUtils.format(calendarEnd.getTime(), CalendarUtils.FORMAT_DATE_TIME);
                    mParam.setStartDate(CalendarUtils.toLocalDate(calendarStart.getTime()));
                    mParam.setEndDate(CalendarUtils.toLocalDate(calendarEnd.getTime()));
                    mDate.setText(String.format("%s~%s", startStr, endStr));
                    if ((beginDate == null && endDate == null) ||
                            !mParam.getStartDate().equals(beginDate) ||
                            !mParam.getEndDate().equals(endDate)) {
                        mPresenter.getOrderGoodsDetails(true);
                    }
                }
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
    }
}
