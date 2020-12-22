package com.hll_sc_app.app.warehouse.detail;

import android.text.TextUtils;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.PhoneUtil;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.warehouse.WarehouseShopBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/24
 */
public class WarehouseShopAdapter extends BaseQuickAdapter<WarehouseShopBean, BaseViewHolder> {

    private final boolean mCanSelect;

    public WarehouseShopAdapter() {
        super(R.layout.item_cooperation_purchaser_shop);
        mCanSelect = false;
    }

    public WarehouseShopAdapter(boolean canSelect) {
        super(R.layout.item_cooperation_purchaser_shop);
        mCanSelect = canSelect;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = super.onCreateDefViewHolder(parent, viewType);
        viewHolder.setGone(R.id.txt_agree, false).setGone(R.id.img_select, mCanSelect)
                .addOnClickListener(R.id.content);
        return viewHolder;
    }

    @Override
    protected void convert(BaseViewHolder helper, WarehouseShopBean item) {
        helper.setText(R.id.txt_shopName, item.getShopName())
                .setText(R.id.txt_shopAdmin, "联系人：" + getString(item.getLinkman()) + " / "
                        + getString(PhoneUtil.formatPhoneNum(item.getMobile())))
                .setText(R.id.txt_shopAddress, "地址：" + getString(item.getShopAddress()));
        helper.getView(R.id.img_select).setSelected(item.isSelect());
        GlideImageView imageView = helper.getView(R.id.img_imagePath);
        if (TextUtils.equals(item.getIsActive(), "0")) {
            imageView.setDisableImageUrl(item.getLogoUrl(), GlideImageView.DISABLE_SHOP);
        } else {
            imageView.setImageURL(item.getLogoUrl());
        }
    }

    private String getString(String str) {
        return TextUtils.isEmpty(str) ? "无" : str;
    }
}
