package com.hll_sc_app.app.report.marketing.product;

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
import com.hll_sc_app.bean.report.marketing.MarketingProductBean;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.widget.report.ExcelLayout;
import com.hll_sc_app.widget.report.ExcelRow;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/28
 */
@Route(path = RouterConfig.REPORT_MARKETING_PRODUCT)
public class MarketingProductActivity extends MarketingDetailActivity {
    private static final int[] WIDTH_ARRAY = {40, 130, 200, 120, 150, 90, 120};
    private TextView mAmount;

    @Override
    public int getOpType() {
        return 1;
    }

    @Override
    public int[] getWidthArray() {
        return WIDTH_ARRAY;
    }

    @Override
    public CharSequence[] getTitleArray() {
        return new CharSequence[]{"序号", "商品编码", "商品名称", "规格/单位", "商品分类", "活动销量", "销售金额"};
    }

    @Override
    public String getHeaderTitle() {
        return "参与活动商品";
    }

    @Override
    public View generateFooter() {
        int space = UIUtils.dip2px(10);
        FrameLayout frameLayout = new FrameLayout(this);
        frameLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(45)));
        frameLayout.setPadding(space, 0, space, 0);
        frameLayout.setBackgroundResource(R.drawable.base_bg_shadow_bottom_bar);
        TextView label = new TextView(this);
        TextViewCompat.setTextAppearance(label, R.style.TextAppearance_City22_Little);
        label.setText("合计销售额：");
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL;
        frameLayout.addView(label, lp);

        mAmount = new TextView(this);
        TextViewCompat.setTextAppearance(mAmount, R.style.TextAppearance_City22_Little);
        mAmount.setText("¥0.00");
        lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL | Gravity.RIGHT;
        frameLayout.addView(mAmount, lp);
        return frameLayout;
    }

    @Override
    public ExcelRow.ColumnData[] generateColumnData() {
        ExcelRow.ColumnData[] array = new ExcelRow.ColumnData[WIDTH_ARRAY.length];
        array[0] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[0]));
        for (int i = 1; i < 5; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL);
        }
        for (int i = 5; i < WIDTH_ARRAY.length; i++) {
            array[i] = new ExcelRow.ColumnData(UIUtils.dip2px(WIDTH_ARRAY[i]), Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        }
        return array;
    }

    @Override
    public void handleData(ExcelLayout excel, MarketingDetailResp resp) {
        mAmount.setText(String.format("¥%s", CommonUtils.formatMoney(resp.getTotalSaleAmount())));
        mIndex.set(0);
        List<MarketingProductBean> list = resp.getProductList();
        if (!CommonUtils.isEmpty(list)) {
            for (MarketingProductBean bean : list) {
                bean.setSequenceNo(mIndex.incrementAndGet());
            }
        }
        excel.setData(list, false);
    }
}
