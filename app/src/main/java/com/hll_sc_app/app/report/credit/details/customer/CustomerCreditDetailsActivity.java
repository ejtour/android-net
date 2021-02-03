package com.hll_sc_app.app.report.credit.details.customer;

import android.view.Gravity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.app.report.credit.details.BaseCreditDetailsActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.widget.report.ExcelRow;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/18
 */

@Route(path = RouterConfig.REPORT_CREDIT_DETAILS_CUSTOMER)
public class CustomerCreditDetailsActivity extends BaseCreditDetailsActivity {
    private static final int[] WIDTH_ARRAY = {90, 150, 120, 110, 110, 100, 100, 80};

    @Override
    protected ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL);
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL);
        for (int i = 3; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    @Override
    protected int[] getWidthArray() {
        return WIDTH_ARRAY;
    }

    @Override
    protected String[] getExcelHeaderText() {
        return new String[]{"日期", "客户集团", "客户门店", "含税应收金额(元)", "不含税应收金额(元)", "已收款金额(元)", "未收款金额(元)", "毛利率"};
    }

    @Override
    public boolean isDaily() {
        return false;
    }
}
