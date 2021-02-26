package com.hll_sc_app.app.report.productsale;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTop10Bean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/25
 */

public class ProductSalesAdapter extends BaseQuickAdapter<ProductSaleTop10Bean, BaseViewHolder> {
    static final int TYPE_SALES = 1;
    static final int TYPE_AMOUNT = 2;

    @IntDef({TYPE_SALES, TYPE_AMOUNT})
    @Retention(RetentionPolicy.SOURCE)
    @interface Type {
    }

    private int mType;

    public ProductSalesAdapter() {
        super(R.layout.item_product_sales);
    }

    public void setNewData(@Nullable List<ProductSaleTop10Bean> data, @Type int type) {
        mType = type;
        super.setNewData(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductSaleTop10Bean item) {
        int position = mData.indexOf(item);
        helper.setText(R.id.ips_rank, String.valueOf(position + 1))
                .setText(R.id.ips_name, item.getProductName())
                .setText(R.id.ips_spec, item.getProductSpec())
                .setText(R.id.ips_sales, mType == TYPE_SALES ? CommonUtils.formatNum(item.getOrderNum()) : CommonUtils.formatMoney(item.getOrderAmount()));
        helper.itemView.setBackgroundResource(position % 2 == 0 ? android.R.color.white : R.color.color_fafafa);
    }
}
