package com.hll_sc_app.app.invoice.select.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.R;
import com.hll_sc_app.base.BaseLoadActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.utils.router.RouterUtil;
import com.hll_sc_app.bean.invoice.InvoiceParam;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.DatePickerDialog;
import com.hll_sc_app.widget.SimpleDecoration;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

@Route(path = RouterConfig.INVOICE_SELECT_ORDER)
public class SelectOrderActivity extends BaseLoadActivity {
    @BindView(R.id.iso_date)
    TextView mDate;
    @BindView(R.id.iso_total_amount)
    TextView mTotalAmount;
    @BindView(R.id.iso_order_amount)
    TextView mOrderAmount;
    @BindView(R.id.iso_refund_amount)
    TextView mRefundAmount;
    @BindView(R.id.iso_bottom_amount)
    TextView mBottomAmount;
    @BindView(R.id.iso_list_view)
    RecyclerView mListView;
    @BindView(R.id.iso_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.iso_bottom_group)
    Group mBottomGroup;
    private DatePickerDialog mDatePickerDialog;

    /**
     * @param shopID 门店id
     */
    public static void start(String shopID) {
        RouterUtil.goToActivity(RouterConfig.INVOICE_SELECT_ORDER, shopID);
    }

    private final InvoiceParam mParam = new InvoiceParam();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, ContextCompat.getColor(this, R.color.colorPrimary));
        setContentView(R.layout.activity_invoice_select_order);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {
        Date endDate = new Date();
        mParam.setStartTime(CalendarUtils.getDateBefore(endDate, 31));
        mParam.setEndTime(CalendarUtils.getDateBefore(endDate, 1));
        updateDateText();
    }

    private void initView() {
        SimpleDecoration decor = new SimpleDecoration(ContextCompat.getColor(this, R.color.color_eeeeee), UIUtils.dip2px(1));
        decor.setLineMargin(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0, Color.WHITE);
        mListView.addItemDecoration(decor);
    }

    private void updateDateText() {
        mDate.setText(String.format("%s-%s", CalendarUtils.format(mParam.getStartTime(), Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(mParam.getEndTime(), Constants.SLASH_YYYY_MM_DD)));
    }

    @OnClick({R.id.iso_filter_btn, R.id.iso_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iso_filter_btn:
                filterDate();
                break;
            case R.id.iso_next:
                showToast("下一步待添加");
                break;
        }
    }

    void filterDate() {
        if (mDatePickerDialog == null) {
            Calendar begin = Calendar.getInstance();
            begin.add(Calendar.YEAR, -3);
            mDatePickerDialog = DatePickerDialog.newBuilder(this)
                    .setBeginTime(begin.getTimeInMillis())
                    .setEndTime(CalendarUtils.getDateBefore(new Date(), 1).getTime())
                    .setSelectBeginTime(mParam.getStartTime().getTime())
                    .setSelectEndTime(mParam.getEndTime().getTime())
                    .setTitle("按时间筛选")
                    .setCancelable(false)
                    .setCallback(new DatePickerDialog.SelectCallback() {
                        @Override
                        public void select(Date beginTime, Date endTime) {
                            mParam.setStartTime(beginTime);
                            mParam.setEndTime(endTime);
                            updateDateText();
                        }
                    })
                    .create();
        }
        mDatePickerDialog.show();
    }
}
