package com.hll_sc_app.app.report.refund.reason;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
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
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.bean.BaseMapReq;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.report.refund.RefundReasonBean;
import com.hll_sc_app.bean.window.OptionType;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.ColorStr;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.Utils;
import com.hll_sc_app.widget.ContextOptionsWindow;
import com.hll_sc_app.widget.EmptyView;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.report.PieMarker;
import com.hll_sc_app.widget.report.ReportEmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.Arrays;
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
    @BindView(R.id.rrs_type)
    TextView mType;
    @BindView(R.id.rrs_date)
    TextView mDate;
    @BindView(R.id.rrs_chart)
    PieChart mPie;
    @BindView(R.id.rrs_list_view)
    RecyclerView mRecyclerView;
    @BindView(R.id.rrs_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.rrs_type_arrow)
    TriangleView mTypeArrow;
    @BindView(R.id.rrs_date_arrow)
    TriangleView mDateArrow;
    @BindView(R.id.rrs_empty)
    ReportEmptyView mCharEmpty;
    private Unbinder unbinder;
    private DateRangeWindow mDateRangeWindow;
    private ContextOptionsWindow mOptionsWindow;
    private IRefundReasonContract.IPresent mPresenter;
    private ListAdapter mAdapter;
    private BaseMapReq.Builder mReq = BaseMapReq.newBuilder();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_report_refund_reason_statistics);
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
        mAdapter = new ListAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRefreshLayout.setOnRefreshListener(refreshLayout -> mPresenter.refresh());

        //设置饼状图
        //画统计图
        mPie.setUsePercentValues(true);
        mPie.getDescription().setEnabled(false);
        mPie.setExtraOffsets(5, 5, 5, 5);
        mPie.setDragDecelerationFrictionCoef(0.5f);
        mPie.setRotationEnabled(true);
        mPie.animateY(1400, Easing.EaseInOutQuad);
        //设置饼状图里的文字大小
        mPie.setEntryLabelTextSize(0f);
        //不绘画中间
        mPie.setDrawHoleEnabled(false);
        //设置统计维度显示
        Legend l = mPie.getLegend();
        l.setDrawInside(false);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        l.setWordWrapEnabled(true);
        l.setEnabled(true);

        mPie.setMarker(new PieMarker(this, mPie));
    }

    private void initData() {
        mReq.put("groupID", UserConfig.getGroupID());
        mReq.put("sign", "1");
        Date currentDate = new Date();
        Date firstDate = CalendarUtils.getFirstDateInMonth(currentDate);
        Date lastDate = CalendarUtils.getLastDateInMonth(currentDate);
        mDate.setTag(R.id.date_start, firstDate);
        mDate.setTag(R.id.date_end, lastDate);
        updateSelectDate();
        mPresenter.start();
    }

    private void updateSelectDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        mReq.put("startDate", CalendarUtils.toLocalDate(startDate));
        mReq.put("endDate", CalendarUtils.toLocalDate(endDate));
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
    }

    @OnClick(R.id.rrs_type_btn)
    public void selectType(View view) {
        mTypeArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mType.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow(this);
            List<OptionsBean> optionsBeans = new ArrayList<>();
            optionsBeans.add(new OptionsBean(R.drawable.ic_menu_all, OptionType.OPTION_ALL));
            optionsBeans.add(new OptionsBean(R.drawable.ic_menu_deposit, OptionType.OPTION_NOT_CONTAIN_DEPOSIT));
            mOptionsWindow.refreshList(optionsBeans);
            mOptionsWindow.setListener((adapter, view1, position) -> {
                OptionsBean item = (OptionsBean) adapter.getItem(position);
                if (item == null) return;
                mOptionsWindow.dismiss();
                mReq.put("sign", TextUtils.equals(item.getLabel(), OptionType.OPTION_ALL) ? "1" : "2");
                mPresenter.start();
                mType.setText(item.getLabel());
            });
            mOptionsWindow.setOnDismissListener(() -> {
                mTypeArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_666666));
                mType.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
        }
        mOptionsWindow.showAsDropDownFix(view, Gravity.LEFT);
    }

    @OnClick(R.id.rrs_date_btn)
    public void selectDate(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                mDate.setTag(R.id.date_end, end);
                mDate.setTag(R.id.date_start, start);
                updateSelectDate();
                mPresenter.start();
            });
            mDateRangeWindow.setOnDismissListener(() -> {
                mDateArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(this, R.color.color_dddddd));
                mDate.setTextColor(ContextCompat.getColor(this, R.color.color_666666));
            });
            mDateRangeWindow.setReset(false);
            mDateRangeWindow.setSelectCalendarRange((Date) mDate.getTag(R.id.date_start), (Date) mDate.getTag(R.id.date_end));
        }
        mDateRangeWindow.showAsDropDownFix(view);
    }

    @Override
    public BaseMapReq.Builder getReq() {
        return mReq;
    }

    @Override
    public void setData(List<RefundReasonBean> list) {
        if (list.size() > 0) {
            mCharEmpty.setVisibility(View.GONE);
            mPie.setVisibility(View.VISIBLE);
            //列表数据
            mAdapter.setNewData(list);
            //饼状图
            ArrayList<PieEntry> entries = new ArrayList<>();
            for (RefundReasonBean refundReasonBean : list) {
                if (refundReasonBean.getProportion() == 0) {
                    continue;
                }
                entries.add(new PieEntry((float) refundReasonBean.getProportion(), refundReasonBean.getRefundReasonDesc()));
            }
            if (entries.size() > 0) {
                PieDataSet dataSet = new PieDataSet(entries, "");
                //颜色
                List<Integer> colors = Arrays.asList(ColorStr.CHART_COLOR_ARRAY);
                dataSet.setColors(colors);
                dataSet.setValueTextSize(0f);
                PieData data = new PieData(dataSet);
                mPie.setData(data);
                mPie.invalidate();
            } else {
                mCharEmpty.setVisibility(View.VISIBLE);
                mPie.setVisibility(View.GONE);
            }

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

    private static class ListAdapter extends BaseQuickAdapter<RefundReasonBean, BaseViewHolder> {
        ListAdapter() {
            super(R.layout.list_item_refund_reason_statics);
        }

        @Override
        protected void convert(BaseViewHolder helper, RefundReasonBean item) {
            LinearLayout mContainer = helper.getView(R.id.ll_container);
            TextView mNumber = helper.getView(R.id.txt_number);
            TextView mReason = helper.getView(R.id.txt_reason);
            TextView mMoney = helper.getView(R.id.txt_money);
            TextView mProportion = helper.getView(R.id.txt_proportion);

            mNumber.setText(String.valueOf(helper.getLayoutPosition() + 1));
            mReason.setText(item.getRefundReasonDesc());
            mMoney.setText(CommonUtils.formatMoney(item.getAmount()));
            mProportion.setText(Utils.numToPercent(item.getProportion()));
            mContainer.setBackgroundColor(Color.parseColor(helper.getLayoutPosition() % 2 == 0 ? "#ffffff" : "#FAFAFA"));
        }
    }
}
