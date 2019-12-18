package com.hll_sc_app.app.report.profit.shop;

import android.view.Gravity;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.app.report.profit.BaseProfitActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.widget.report.ExcelRow;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/17
 */

@Route(path = RouterConfig.REPORT_PROFIT_SHOP)
public class ShopProfitActivity extends BaseProfitActivity {
    private static final int[] WIDTH_ARRAY = {150, 120, 110, 110, 80, 80, 80};

    @Override
    protected ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]), Gravity.CENTER_VERTICAL);
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL);
        for (int i = 2; i < WIDTH_ARRAY.length; i++) {
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
        return new String[]{"采购商集团", "采购商门店", "含税应收金额(元)", "不含税应收金额(元)", "采购成本(元)", "毛利额(元)", "毛利率"};
    }

    @Override
    protected int getType() {
        return 2;
    }
}
