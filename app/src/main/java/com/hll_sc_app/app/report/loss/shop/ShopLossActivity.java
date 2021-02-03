package com.hll_sc_app.app.report.loss.shop;

import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.loss.BaseLossActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.base.widget.DateWindow;
import com.hll_sc_app.bean.report.loss.LossBean;
import com.hll_sc_app.citymall.util.CalendarUtils;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.widget.report.ExcelRow;

import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */

@Route(path = RouterConfig.REPORT_SHOP_LOSS)
public class ShopLossActivity extends BaseLossActivity {
    private static final int[] WIDTH_ARRAY = {40, 150, 120, 80, 100, 80, 90, 80, 90, 100};
    private AtomicInteger mIndex = new AtomicInteger();
    private DateWindow mDateWindow;

    @Override
    protected String getFlag() {
        return "1";
    }

    @Override
    protected String getHeaderTitle() {
        return "流失门店明细表";
    }

    @Override
    protected void initData() {
        mDate.setTag(CalendarUtils.getDateBefore(new Date(), 1));
        super.initData();
    }

    @Override
    protected void updateSelectDate() {
        Date date = (Date) mDate.getTag();
        getReq().put("startDate", CalendarUtils.toLocalDate(date));
        mDate.setText(CalendarUtils.format(date, Constants.SLASH_YYYY_MM_DD));
        super.updateSelectDate();
    }

    @Override
    protected void toSelectDate(View view) {
        if (mDateWindow == null) {
            mDateWindow = new DateWindow(this);
            mDateWindow.setCalendar(((Date) mDate.getTag()));
            mDateWindow.setSelectListener(date -> {
                mDate.setTag(date);
                updateSelectDate();
            });
        }
        mDateWindow.showAtLocation(getWindow().getDecorView(), Gravity.BOTTOM, 0, 0);
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
        row.updateRowDate("序号", "客户集团", "客户门店", "联系人", "联系方式", "销售代表", "最后下单日期", "门店下单量", "销售总额(元)", "单均(元)");
        row.setBackgroundResource(R.drawable.bg_excel_header);
        return row;
    }

    @Override
    protected ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        for (int i = 1; i < 4; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL);
        }
        for (int i = 4; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    @Override
    public void setData(List<LossBean> list, boolean append) {
        if (!append) {
            mIndex.set(0);
        }
        if (!CommonUtils.isEmpty(list)) {
            for (LossBean bean : list) {
                bean.setSequenceNo(String.valueOf(mIndex.incrementAndGet()));
            }
        }
        super.setData(list, append);
    }
}
