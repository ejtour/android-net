package com.hll_sc_app.app.order.transfer.inventory;

import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.order.transfer.InventoryBean;
import com.hll_sc_app.bean.order.transfer.InventoryCheckReq;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/4
 */

public class InventoryCheckAdapter extends BaseQuickAdapter<InventoryBean, BaseViewHolder> {
    public InventoryCheckAdapter(@Nullable List<InventoryBean> data) {
        super(R.layout.item_inventory_check, data);
        setOnItemChildClickListener((adapter, view, position) -> {
            InventoryBean item = getItem(position);
            if (item == null)
                return;
            switch (view.getId()) {
                case R.id.iic_cancel_btn:
                    if (item.getFlag() == 2)
                        return;
                    item.setFlag(2);
                    break;
                case R.id.iic_modify_btn:
                    if (item.getFlag() == 1)
                        return;
                    item.setFlag(1);
                    break;
            }
            notifyItemChanged(position);
        });
    }

    List<InventoryCheckReq.InventoryCheckBean> getReqList() {
        // 构造请求列表
        List<InventoryCheckReq.InventoryCheckBean> reqList = new ArrayList<>();
        for (InventoryBean bean : getData()) {
            reqList.add(new InventoryCheckReq.InventoryCheckBean(bean.getFlag(), bean.getGoodsNum(), bean.getId()));
        }
        return reqList;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        ((EditText) helper.getView(R.id.iic_goods_num_edit)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                InventoryBean item = getItem(helper.getAdapterPosition());
                if (item != null) {
                    if (s.toString().startsWith("."))
                        s.insert(0, "0");
                    if (!CommonUtils.checkMoneyNum(s.toString()) && s.length() > 1)
                        s.delete(s.length() - 1, s.length());
                    if (!TextUtils.isEmpty(s.toString()) && Double.parseDouble(s.toString()) > item.getStockNum()) {
                        s.clear();
                        ToastUtils.showShort(helper.itemView.getContext(), "不可超过该商品最大的库存");
                    }
                    item.setGoodsNum(TextUtils.isEmpty(s.toString()) ? 0 : Double.parseDouble(s.toString()));
                }
            }
        });
        helper.addOnClickListener(R.id.iic_cancel_btn)
                .addOnClickListener(R.id.iic_modify_btn);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, InventoryBean item) {
        boolean modify = item.getFlag() == 1;
        boolean cancel = item.getFlag() == 2;
        helper.setText(R.id.iic_name, item.getProductName())
                .setText(R.id.iic_spec_unit, String.format("规格／单位：%s/%s", item.getProductSpec(), item.getSaleUnitName()))
                .setText(R.id.iic_product_code, "商品编码：" + item.getProductCode())
                .setText(R.id.iic_sku_code, "SKU码：" + item.getSkuCode())
                .setText(R.id.iic_available_inventory, "可用库存：" + CommonUtils.formatNum(item.getStockNum()))
                .setText(R.id.iic_order_num, "订货数量:" + CommonUtils.formatNum(item.getOrderNum()))
                .setText(R.id.iic_goods_num_edit, item.getFlag() != 1 || item.getGoodsNum() == 0 ? "" : CommonUtils.formatNumber(item.getGoodsNum()))
                .setGone(R.id.iic_modify_check, modify)
                .setGone(R.id.iic_cancel_check, cancel);
        EditText editText = helper.getView(R.id.iic_goods_num_edit);
        editText.setEnabled(modify);
        if (modify) {
            editText.setHint("数量");
            editText.setHintTextColor(ContextCompat.getColor(helper.itemView.getContext(), R.color.color_999999));
        } else {
            editText.setHint("— —");
            editText.setHintTextColor(ContextCompat.getColor(helper.itemView.getContext(), R.color.color_222222));
        }
        helper.getView(R.id.iic_modify_btn).setSelected(modify);
        helper.getView(R.id.iic_modify_text).setSelected(modify);
        helper.getView(R.id.iic_cancel_btn).setSelected(cancel);
        helper.getView(R.id.iic_cancel_text).setSelected(cancel);
    }
}
