package com.hll_sc_app.app.report.productSale;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.report.resp.product.ProductSaleTopDetail;

import java.net.HttpRetryException;
import java.util.List;

public class ProductAggregationAdapter extends BaseQuickAdapter<ProductSaleTopDetail, BaseViewHolder> {

    ProductAggregationAdapter() {
        super(R.layout.item_product_sale_detail);
    }
    private int count;
    private boolean isSalesNum = false;

    public boolean isSalesNum() {
        return isSalesNum;
    }

    public void setSalesNum(boolean salesNum) {
        isSalesNum = salesNum;
    }

    @Override
    protected void convert(BaseViewHolder helper, ProductSaleTopDetail item) {
        helper.setText(R.id.report_product_sales_top_id,count++);
        helper.setText(R.id.report_product_sales_top_product,item.getProductName());
        helper.setText(R.id.report_product_sales_top_spec,item.getProductSpec());
        if(isSalesNum) {
            helper.setText(R.id.report_product_sales_top_sale, item.getOrderNum() + "");
        }else{
            helper.setText(R.id.report_product_sales_top_sale,item.getOrderAmount()+"");
        }
    }
}
