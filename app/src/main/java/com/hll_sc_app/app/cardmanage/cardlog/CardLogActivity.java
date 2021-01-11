package com.hll_sc_app.app.cardmanage.cardlog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.bean.cardmanage.CardLogResp;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.EmptyView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@Route(path = RouterConfig.ACTIVITY_CARD_MANAGE_LOG, extras = Constant.LOGIN_EXTRA)
public class CardLogActivity extends BaseLoadActivity implements ICardLogContract.IView {

    @BindView(R.id.txt_date)
    TextView mTxtDate;
    @BindView(R.id.refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.list_view)
    RecyclerView mListView;
    @BindView(R.id.ll_filter)
    LinearLayout mLlFilter;
    private Unbinder unbinder;
    @Autowired(name = "object0",required = true)
    String mCardNo;

    private LogAdpater mAdapter;

    private ICardLogContract.IPresent mPresent;

    private DateRangeWindow mDateWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage_log);
        ARouter.getInstance().inject(this);
        unbinder = ButterKnife.bind(this);
        initView();
        mPresent = CardLogPresent.newInstance();
        mPresent.register(this);
        mPresent.queryLogList(true);

    }

    public static void start(String cardNo) {
        RouterUtil.goToActivity(RouterConfig.ACTIVITY_CARD_MANAGE_LOG,cardNo);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    private void initView() {
        Calendar initEnd = Calendar.getInstance();
        Date initStart = CalendarUtils.getFirstDateInMonth(initEnd.getTime());
        mTxtDate.setTag(R.id.date_start, CalendarUtils.format(initStart, CalendarUtils.FORMAT_SERVER_DATE));
        mTxtDate.setTag(R.id.date_end, CalendarUtils.format(initEnd.getTime(), CalendarUtils.FORMAT_SERVER_DATE));
        String startStr = CalendarUtils.format(initStart, Constants.SLASH_YYYY_MM_DD);
        String endStr = CalendarUtils.format(initEnd.getTime(), Constants.SLASH_YYYY_MM_DD);
        mTxtDate.setText(String.format("%s-%s", startStr, endStr));

        mAdapter = new LogAdpater(null);
        mListView.setAdapter(mAdapter);
        mRefreshLayout.setEnableRefresh(true);
        mRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresent.getMore();
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresent.refresh();
            }
        });

        mTxtDate.setOnClickListener(v -> {
            if (mDateWindow == null) {
                mDateWindow = new DateRangeWindow(this);
                mDateWindow.setSelectCalendarRange(initEnd.get(Calendar.YEAR), initEnd.get(Calendar.MONTH) + 1, 1,
                        initEnd.get(Calendar.YEAR), initEnd.get(Calendar.MONTH) + 1, initEnd.get(Calendar.DATE));
                mDateWindow.setOnRangeSelectListener((start, end) -> {
                    if (start == null && end == null) {
                        mTxtDate.setText(null);
                        mTxtDate.setTag(R.id.date_start, "");
                        mTxtDate.setTag(R.id.date_end, "");
                        return;
                    }
                    if (start != null && end != null) {
                        Calendar calendarStart = Calendar.getInstance();
                        calendarStart.setTimeInMillis(start.getTimeInMillis());
                        Calendar calendarEnd = Calendar.getInstance();
                        calendarEnd.setTimeInMillis(end.getTimeInMillis());

                        String startS = CalendarUtils.format(calendarStart.getTime(), Constants.SLASH_YYYY_MM_DD);
                        String endS = CalendarUtils.format(calendarEnd.getTime(), Constants.SLASH_YYYY_MM_DD);
                        mTxtDate.setText(String.format("%s-%s", startS, endS));
                        mTxtDate.setTag(R.id.date_start, CalendarUtils.format(calendarStart.getTime(),
                                CalendarUtils.FORMAT_SERVER_DATE));
                        mTxtDate.setTag(R.id.date_end, CalendarUtils.format(calendarEnd.getTime(),
                                CalendarUtils.FORMAT_SERVER_DATE));
                        mPresent.refresh();
                    }
                });
            }
            mDateWindow.showAsDropDown(mLlFilter);
        });
    }

    @Override
    public void querySuccess(List<CardLogResp.CardLogBean> cardLogBeans, boolean isMore) {
        if (isMore) {
            mAdapter.addData(cardLogBeans);
        } else {
            mAdapter.setEmptyView(EmptyView.newBuilder(this).setTipsTitle("当前日期下，没有卡日志噢").create());
            mAdapter.setNewData(cardLogBeans);
        }
        mRefreshLayout.setEnableLoadMore(false);
        if (!CommonUtils.isEmpty(cardLogBeans)) {
            mRefreshLayout.setEnableLoadMore(cardLogBeans.size() == mPresent.getPageSize());
        }
    }

    @Override
    public void hideLoading() {
        super.hideLoading();
        mRefreshLayout.closeHeaderOrFooter();
    }

    @Override
    public String getStartDate() {
        Object o = mTxtDate.getTag(R.id.date_start);
        if (o == null) {
            return "";
        } else {
            return o.toString() + "000000";
        }
    }

    @Override
    public String getEndDate() {
        Object o = mTxtDate.getTag(R.id.date_end);
        if (o == null) {
            return "";
        } else {
            return o.toString() + "235959";
        }
    }

    @Override
    public String getCardNo() {
        return mCardNo;
    }

    private class LogAdpater extends BaseQuickAdapter<CardLogResp.CardLogBean, BaseViewHolder> {

        public LogAdpater(@Nullable List<CardLogResp.CardLogBean> data) {
            super(R.layout.list_item_card_manage_log, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, CardLogResp.CardLogBean item) {
            helper.setText(R.id.txt_type, item.getHandleTypeText())
                    .setText(R.id.txt_operater, "操作人：" + item.getHandleBy())
                    .setText(R.id.txt_time, CalendarUtils.getDateFormatString(item.getHandleTime(), "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm:ss"))
                    .setText(R.id.txt_remark, item.getRemark());
        }
    }
}
