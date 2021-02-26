package com.hll_sc_app.app.report.loss.customer;

import android.view.Gravity;
import android.view.View;

import androidx.core.content.ContextCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.loss.BaseLossActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.daterange.DateRangeWindow;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.TriangleView;
import com.hll_sc_app.widget.report.ExcelRow;

import java.util.Date;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */

@Route(path = RouterConfig.REPORT_CUSTOMER_LOSS)
public class CustomerLossActivity extends BaseLossActivity {
    private static final int[] WIDTH_ARRAY = {90, 100, 100, 100, 100};
    private DateRangeWindow mDateRangeWindow;

    @Override
    protected String getFlag() {
        return "0";
    }

    @Override
    protected String getHeaderTitle() {
        return "客户流失率统计";
    }

    @Override
    protected void initData() {
        Date currentDate = new Date();
        Date firstDate = CalendarUtils.getFirstDateInMonth(currentDate);
        mDate.setTag(R.id.date_start, firstDate);
        mDate.setTag(R.id.date_end, currentDate);
        super.initData();
    }

    @Override
    protected void updateSelectDate() {
        Date startDate = (Date) mDate.getTag(R.id.date_start);
        Date endDate = (Date) mDate.getTag(R.id.date_end);
        getReq().put("startDate", CalendarUtils.toLocalDate(startDate));
        getReq().put("endDate", CalendarUtils.toLocalDate(endDate));
        mDate.setText(String.format("%s - %s", CalendarUtils.format(startDate, Constants.SLASH_YYYY_MM_DD),
                CalendarUtils.format(endDate, Constants.SLASH_YYYY_MM_DD)));
        super.updateSelectDate();
    }

    @Override
    protected void toSelectDate(View view) {
        mDateArrow.update(TriangleView.TOP, ContextCompat.getColor(this, R.color.colorPrimary));
        mDate.setTextColor(ContextCompat.getColor(this, R.color.colorPrimary));
        if (mDateRangeWindow == null) {
            mDateRangeWindow = new DateRangeWindow(this);
            mDateRangeWindow.setOnRangeChangedListener((start, end) -> {
                mDate.setTag(R.id.date_end, end);
                mDate.setTag(R.id.date_start, start);
                updateSelectDate();
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
    protected View generateHeader() {
        ExcelRow row = new ExcelRow(this);
        row.updateChildView(WIDTH_ARRAY.length);
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        for (int i = 0; i < WIDTH_ARRAY.length; i++) {
            array[i] = ExcelRow.ColumnData.createDefaultHeader(UIUtils.dip2px(WIDTH_ARRAY[i]));
        }
        row.updateItemData(array);
        row.updateRowDate("日期", "下单门店数", "流失门店数", "新增流失门店数", "流失率");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    @Override
    protected ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        for (int i = 1; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }
}
