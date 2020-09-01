package com.hll_sc_app.app.setting.tax.goodsselect;

import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.user.CategoryItem;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/1
 */

public class GoodsCategoryAdapter extends BaseQuickAdapter<CategoryItem, BaseViewHolder> {

    private CategoryItem mBean;

    public GoodsCategoryAdapter() {
        super(R.layout.item_goods_select_category);
    }

    public void select(CategoryItem bean) {
        mBean = bean;
        notifyDataSetChanged();
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryItem item) {
        TextView itemView = (TextView) helper.itemView;
        itemView.setText(item.getCategoryName());
        itemView.setSelected(mBean == item);
    }
}
