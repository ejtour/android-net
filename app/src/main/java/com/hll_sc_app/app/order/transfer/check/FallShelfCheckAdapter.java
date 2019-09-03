package com.hll_sc_app.app.order.transfer.check;

import android.support.annotation.Nullable;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.order.transfer.InventoryBean;
import com.hll_sc_app.bean.order.transfer.InventoryCheckReq;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/27
 */

public class FallShelfCheckAdapter extends BaseQuickAdapter<InventoryBean, BaseViewHolder> {
    FallShelfCheckAdapter(@Nullable List<InventoryBean> data) {
        super(R.layout.item_fall_shelf_check, data);
        setOnItemChildClickListener((adapter, view, position) -> {
            InventoryBean item = getItem(position);
            if (item == null)
                return;
            if (view.getId() == R.id.fsc_cancel_btn) {
                if (item.getFlag() == 3) item.setFlag(0);
                else item.setFlag(3);
            }
            notifyItemChanged(position);
        });
    }

    List<InventoryCheckReq.InventoryCheckBean> getReqList() {
        // 构造请求列表
        List<InventoryCheckReq.InventoryCheckBean> reqList = new ArrayList<>();
        for (InventoryBean bean : getData()) {
            if (bean.getFlag() == 3)
                reqList.add(new InventoryCheckReq.InventoryCheckBean(bean.getFlag(), bean.getGoodsNum(), bean.getId()));
        }
        return reqList;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.addOnClickListener(R.id.fsc_cancel_btn);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, InventoryBean item) {
        boolean cancel = item.getFlag() == 3;
        helper.setText(R.id.fsc_name, item.getProductName())
                .setText(R.id.fsc_spec_unit, String.format("规格／单位：%s/%s", item.getProductSpec(), item.getSaleUnitName()))
                .setText(R.id.fsc_product_code, "商品编码：" + item.getProductCode())
                .setText(R.id.fsc_sku_code, "SKU码：" + item.getSkuCode())
                .setGone(R.id.fsc_cancel_check, cancel);
        helper.getView(R.id.fsc_cancel_btn).setSelected(cancel);
        helper.getView(R.id.fsc_cancel_text).setSelected(cancel);
    }
}
