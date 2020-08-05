package com.hll_sc_app.app.report.marketing.order;

import android.view.Gravity;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.app.report.marketing.detail.MarketingDetailActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.report.marketing.MarketingDetailResp;
import com.hll_sc_app.bean.report.marketing.MarketingOrderBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.report.ExcelFooter;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/28
 */
@Route(path = RouterConfig.REPORT_MARKETING_ORDER)
public class MarketingOrderActivity extends MarketingDetailActivity {
    private static final int[] WIDTH_ARRAY = {40, 130, 120, 200, 120, 120, 120};
    private static final int[] FOOTER_WIDTH_ARRAY = {171, 120, 200, 120, 120, 120};
    private ExcelFooter mFooter;

    @Override
    public int getOpType() {
        return 3;
    }

    @Override
    public int[] getWidthArray() {
        return WIDTH_ARRAY;
    }

    @Override
    public CharSequence[] getTitleArray() {
        return new CharSequence[]{"序号", "订单号", "下单时间", "下单门店", "原单金额", "实际金额", "优惠金额"};
    }

    @Override
    public String getHeaderTitle() {
        return "活动拉动单数";
    }

    @Override
    public View generateFooter() {
        mFooter = new ExcelFooter(this);
        mFooter.updateChildView(FOOTER_WIDTH_ARRAY.length);
        mFooter.updateItemData(generateFooterColumnData());
        return mFooter;
    }

    private ExcelRow.ColumnData[] generateFooterColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[FOOTER_WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(FOOTER_WIDTH_ARRAY[0]));
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(FOOTER_WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL);
        for (int i = 2; i < FOOTER_WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(FOOTER_WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    @Override
    public ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL);
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]));
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL);
        for (int i = 4; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    @Override
    public void handleData(ExcelLayout excel, MarketingDetailResp resp) {
        mFooter.updateRowDate(resp.convertToOrderRowData().toArray(new CharSequence[]{}));
        mIndex.set(0);
        List<MarketingOrderBean> list = resp.getBillList();
        if (!CommonUtils.isEmpty(list)) {
            for (MarketingOrderBean bean : list) {
                bean.setSequenceNo(mIndex.incrementAndGet());
            }
        }
        excel.setData(list, false);
    }
}
