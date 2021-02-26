package com.hll_sc_app.app.report.marketing.shop;

import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.widget.TextViewCompat;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hll_sc_app.R;
import com.hll_sc_app.app.report.marketing.detail.MarketingDetailActivity;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RouterConfig;
import com.hll_sc_app.bean.report.marketing.MarketingDetailResp;
import com.hll_sc_app.bean.report.marketing.MarketingShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/28
 */
@Route(path = RouterConfig.REPORT_MARKETING_SHOP)
public class MarketingShopActivity extends MarketingDetailActivity {
    private static final int[] WIDTH_ARRAY = {40, 120, 140, 90, 100};
    private TextView mOrderNum;
    private TextView mAmount;

    @Override
    public int getOpType() {
        return 2;
    }

    @Override
    public int[] getWidthArray() {
        return WIDTH_ARRAY;
    }

    @Override
    public CharSequence[] getTitleArray() {
        return new CharSequence[]{"序号", "门店名称", "集团名称", "订单量", "销售金额"};
    }

    @Override
    public String getHeaderTitle() {
        return "参与活动门店";
    }

    @Override
    public View generateFooter() {
        int space = UIUtils.dip2px(10);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(45)));
        frameLayout.setPadding(space, 0, space, 0);
        frameLayout.setBackgroundResource(R.drawable.base_bg_shadow_bottom_bar);
        mOrderNum = new TextView(this);
        TextViewCompat.setTextAppearance(mOrderNum, R.style.TextAppearance_City22_Little);
        mOrderNum.setText("合计订单量：0");
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL;
        frameLayout.addView(mOrderNum, lp);

        mAmount = new TextView(this);
        TextViewCompat.setTextAppearance(mAmount, R.style.TextAppearance_City22_Little);
        mAmount.setText("合计销售金额：¥0.00");
        lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        frameLayout.addView(mAmount, lp);
        return frameLayout;
    }

    @Override
    public ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        array[1] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[1]), Gravity.CENTER_VERTICAL);
        array[2] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[2]), Gravity.CENTER_VERTICAL);
        array[3] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[3]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        array[4] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[4]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        return array;
    }

    @Override
    public void handleData(ExcelLayout excel, MarketingDetailResp resp) {
        mOrderNum.setText(String.format("合计订单量：%s", CommonUtils.formatNum(resp.getTotalBillNum())));
        mAmount.setText(String.format("合计销售金额：¥%s", CommonUtils.formatMoney(resp.getTotalSaleAmount())));
        mIndex.set(0);
        List<MarketingShopBean> list = resp.getShopList();
        if (!CommonUtils.isEmpty(list)) {
            for (MarketingShopBean bean : list) {
                bean.setSequenceNo(mIndex.incrementAndGet());
            }
        }
        excel.setData(list, false);
    }
}
