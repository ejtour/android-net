package com.hll_sc_app.app.report.refundReason;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.githang.statusbar.StatusBarCompat;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.RefundReasonStaticsResp;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.SyncHorizontalScrollView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 退货原因统计
 *
 * @author zc
 */
@Route(path = RouterConfig.REFUND_REASON_STATICS)
public class RefundReasonActivity extends BaseLoadActivity implements IRefundReasonContract.IView {

    @BindView(R.id.txt_filter_deposit)
    TextView mTxtFilterDeposit;
    @BindView(R.id.txt_filter_date)
    TextView mTxtFilterDate;
    @BindView(R.id.chart_pie)
    PieChart mPie;
    @BindView(R.id.sync_scroll_title)
    SyncHorizontalScrollView mSyncScrollTitle;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.sync_scroll_content)
    SyncHorizontalScrollView mSyncScrollContent;
    @BindView(R.id.img_arrow)
    ImageView mArrowLeft;
    @BindView(R.id.img_arrow_date)
    ImageView mArrowRight;
    private Unbinder unbinder;

    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mOptionsWindow;
    private String mFilterStartDate;
    private String mFilterEndDate;
    private boolean mIsContainDeposit = true;

    private IRefundReasonContract.IPresent mPresenter;

    private ListAdapter mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_refund_reason_statistics);
        unbinder = ButterKnife.bind(this);
        mPresenter = RefundReasonPresenter.newInstance();
        mPresenter.register(this);
        initView();
        initData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        mSyncScrollTitle.setLinkageViews(mSyncScrollContent);
        mSyncScrollContent.setLinkageViews(mSyncScrollTitle);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mAdapter = new ListAdapter(null);
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                //no-op
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.queryRefundReasonStatics();
            }
        });

        //设置饼状图
        //画统计图
        mPie.setUsePercentValues(true);
        mPie.getDescription().setEnabled(false);
        mPie.setExtraOffsets(5, 0, 5, 0);
        mPie.setDragDecelerationFrictionCoef(0.5f);
        mPie.setRotationEnabled(false);
        mPie.animateY(1400, Easing.EaseInOutQuad);
        //设置饼状图里的文字大小
        mPie.setEntryLabelTextSize(0f);
        //不绘画中间
        mPie.setDrawHoleEnabled(false);
        //设置统计维度显示
        Legend l = mPie.getLegend();
        l.setEnabled(false);


    }

    private void initData() {
        Date date = new Date();
        Date preDate = CalendarUtils.getDateBeforeMonth(date, 1);
        setDate(preDate, date);
        mPresenter.queryRefundReasonStatics();
    }

    private void setDate(Date start, Date end) {
        String startStr = CalendarUtils.format(start, "yyyy/MM/dd");
        String endStr = CalendarUtils.format(end, "yyyy/MM/dd");
        mFilterStartDate = CalendarUtils.format(start, CalendarUtils.FORMAT_LOCAL_DATE);
        mFilterEndDate = CalendarUtils.format(end, CalendarUtils.FORMAT_LOCAL_DATE);
        mTxtFilterDate.setText(String.format("%s - %s", startStr, endStr));
    }

    @OnClick({R.id.img_close, R.id.txt_filter_deposit, R.id.txt_filter_date})
    public void onViewClicked(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.img_close:
                finish();
                break;
            case R.id.txt_filter_deposit:
                if (mOptionsWindow == null) {
                    mOptionsWindow = new ContextOptionsWindow(this);
                    List<OptionsBean> optionsBeans = new ArrayList<>();
                    optionsBeans.add(new OptionsBean(R.drawable.ic_menu_all, OptionType.OPTION_ALL));
                    optionsBeans.add(new OptionsBean(R.drawable.ic_menu_deposit, OptionType.OPTION_NOT_CONTAIN_DEPOSIT));
                    mOptionsWindow.refreshList(optionsBeans);
                    mOptionsWindow.setListener((adapter, view1, position) -> {
                        mOptionsWindow.dismiss();
                        mIsContainDeposit = (position == 0);
                        mPresenter.queryRefundReasonStatics();
                        mTxtFilterDeposit.setText(((OptionsBean) adapter.getItem(position)).getLabel());
                    });
                    mOptionsWindow.setOnDismissListener(() -> {
                        mArrowLeft.setRotation(0);
                    });
                }
                mArrowLeft.setRotation(180);
                mOptionsWindow.showAsDropDownFix(mTxtFilterDeposit, Gravity.LEFT);
                break;
            case R.id.txt_filter_date:
                if (mDateRangeWindow == null) {
                    mDateRangeWindow = new DateRangeWindow(this);
                    mDateRangeWindow.setOnRangeSelectListener((start, end) -> {
                        if (start != null && end != null) {
                            Calendar calendarStart = Calendar.getInstance();
                            calendarStart.setTimeInMillis(start.getTimeInMillis());
                            Calendar calendarEnd = Calendar.getInstance();
                            calendarEnd.setTimeInMillis(end.getTimeInMillis());
                            setDate(calendarStart.getTime(), calendarEnd.getTime());
                            mPresenter.queryRefundReasonStatics();
                        }
                    });
                    mDateRangeWindow.setOnDismissListener(() -> mArrowRight.setRotation(0));
                    //设置初始时间范围选择
                    Calendar end = Calendar.getInstance();
                    Calendar start = Calendar.getInstance();
                    start.add(Calendar.MONTH, -1);
                    mDateRangeWindow.setSelectCalendarRange(start.get(Calendar.YEAR), start.get(Calendar.MONTH) + 1, start.get(Calendar.DATE),
                            end.get(Calendar.YEAR), end.get(Calendar.MONTH) + 1, end.get(Calendar.DATE));
                }
                mArrowRight.setRotation(180);
                mDateRangeWindow.showAsDropDownFix(mTxtFilterDate);
                break;
            default:
                break;
        }
    }

    @Override
    public boolean isContainDeposit() {
        return mIsContainDeposit;
    }

    @Override
    public String getFilterStartDate() {
        return mFilterStartDate;
    }

    @Override
    public String getFilterEndDate() {
        return mFilterEndDate;
    }

    @Override
    public void querySuccess(RefundReasonStaticsResp staticsResp) {
        if (staticsResp.getRecords().size() > 0) {
            //列表数据
            mAdapter.setNewData(staticsResp.getRecords());
            //饼状图
            ArrayList<PieEntry> entries = new ArrayList<>();
            for (RefundReasonStaticsResp.RefundReasonBean refundReasonBean : staticsResp.getRecords()) {
                if (refundReasonBean.getProportion() == 0) {
                    continue;
                }
                entries.add(new PieEntry((float) refundReasonBean.getProportion(), refundReasonBean.getRefundReasonDesc()));
            }
            PieDataSet dataSet = new PieDataSet(entries, "Election Results");
            //颜色
            ArrayList<Integer> colors = new ArrayList<>();
            //设置
            for (int c : ColorTemplate.VORDIPLOM_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.JOYFUL_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.COLORFUL_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.LIBERTY_COLORS)
                colors.add(c);
            for (int c : ColorTemplate.PASTEL_COLORS)
                colors.add(c);
            colors.add(ColorTemplate.getHoloBlue());
            dataSet.setColors(colors);
            //横线
            dataSet.setValueLinePart1OffsetPercentage(80.f);
            dataSet.setValueLinePart1Length(0.2f);
            dataSet.setValueLinePart2Length(0.4f);
            //横线值
            dataSet.setValueTextSize(11f);
            dataSet.setValueFormatter(new ValueFormatter() {
                @Override
                public String getPieLabel(float value, PieEntry pieEntry) {
                    return String.format("%s: %s", pieEntry.getLabel(), new DecimalFormat("#.##").format(value)) + "%";
                }
            });

            dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
            PieData data = new PieData(dataSet);
            mPie.setData(data);
            mPie.invalidate();
        } else {
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTips("您还没有退货原因统计数据").create());
            mAdapter.setNewData(null);
        }

    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    private class ListAdapter extends BaseQuickAdapter<RefundReasonStaticsResp.RefundReasonBean, BaseViewHolder> {
        public ListAdapter(@Nullable List<RefundReasonStaticsResp.RefundReasonBean> data) {
            super(R.layout.list_item_refund_reason_statics, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, RefundReasonStaticsResp.RefundReasonBean item) {
            LinearLayout mContainer = helper.getView(R.id.ll_container);
            TextView mNumber = helper.getView(R.id.txt_number);
            TextView mReason = helper.getView(R.id.txt_reason);
            TextView mMoney = helper.getView(R.id.txt_money);
            TextView mProportion = helper.getView(R.id.txt_proportion);

            mNumber.setText(String.valueOf(helper.getLayoutPosition() + 1));
            mReason.setText(item.getRefundReasonDesc());
            mMoney.setText(CommonUtils.formatMoney(item.getAmount()));
            mProportion.setText(String.valueOf(item.getProportion() * 100 + "%"));
            mContainer.setBackgroundColor(Color.parseColor(helper.getLayoutPosition() % 2 == 0 ? "#ffffff" : "#FAFAFA"));

        }
    }
}
