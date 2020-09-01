package com.hll_sc_app.app.goods.assign;

import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.goods.GoodsAssignBean;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/8/25
 */
public class GoodsAssignAdapter extends BaseQuickAdapter<GoodsAssignBean, BaseViewHolder> {

    public GoodsAssignAdapter() {
        super(R.layout.item_goods_assign);
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        holder.addOnClickListener(R.id.iga_del).addOnClickListener(R.id.iga_root);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, GoodsAssignBean item) {
        helper
                .setText(R.id.iga_name, item.getPurchaserName())
                .setText(R.id.iga_num, String.format("包含：%s 种商品", CommonUtils.formatNum(item.getProductNum())));
    }

    private int getItemPosition(GoodsAssignBean item) {
        return item != null && mData != null && !mData.isEmpty() ? mData.indexOf(item) : -1;
    }

    void replaceData(GoodsAssignBean oldData, GoodsAssignBean newData) {
        int position = getItemPosition(oldData);
        if (position == -1 || newData == null) {
            return;
        }
        setData(position, newData);
    }

    void removeData(GoodsAssignBean data) {
        int position = getItemPosition(data);
        if (position > -1) {
            remove(position);
        }
    }
}
