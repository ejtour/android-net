package com.hll_sc_app.app.orientationsale.list;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.orientation.OrientationListBean;

public class OrientationListAdapter extends BaseQuickAdapter<OrientationListBean, BaseViewHolder> {

    public OrientationListAdapter() {
        super(R.layout.item_orientation_list);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrientationListBean item) {
        helper.setText(R.id.txt_cooperation_name, item.getPurchaserName());
        helper.setText(R.id.txt_product_num, "包含："+ item.getProductNum().toString() +" 种商品");

    }
}
