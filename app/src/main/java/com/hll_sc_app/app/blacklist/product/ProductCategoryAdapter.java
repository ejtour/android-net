package com.hll_sc_app.app.blacklist.product;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.user.CategoryItem;

public class ProductCategoryAdapter extends BaseQuickAdapter<CategoryItem, BaseViewHolder> {

    ProductCategoryAdapter() {
        super(R.layout.item_goods_custom_category_top);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryItem item) {
        TextView txtCategoryName = helper.getView(R.id.txt_categoryName);
        txtCategoryName.setText(item.getCategoryName());
        txtCategoryName.setSelected(item.isSelected());
    }
}
