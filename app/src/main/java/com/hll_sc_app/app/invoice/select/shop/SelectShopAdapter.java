package com.hll_sc_app.app.invoice.select.shop;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/12
 */

public class SelectShopAdapter extends BaseQuickAdapter<PurchaserShopBean, BaseViewHolder> {
    SelectShopAdapter() {
        super(R.layout.item_invoice_select_shop);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchaserShopBean item) {
        ((GlideImageView) helper.setText(R.id.iss_shop_name, item.getShopName())
                .setText(R.id.iss_group_name, item.getPurchaserName())
                .setText(R.id.iss_contact, String.format("联系人：%s/%s", item.getShopAdmin(), item.getShopPhone()))
                .getView(R.id.iss_image)).setImageURL(item.getImagePath());
        helper.getView(R.id.iss_check_box).setSelected(item.isSelect());
    }

    @Override
    public void setNewData(@Nullable List<PurchaserShopBean> data) {
        preProcess(data);
        super.setNewData(data);
    }

    private void preProcess(@Nullable List<PurchaserShopBean> data) {
        if (!CommonUtils.isEmpty(mData) && !CommonUtils.isEmpty(data)) {
            for (PurchaserShopBean resp : data) {
                for (PurchaserShopBean bean : mData) {
                    if (resp.getShopID().equals(bean.getShopID())) {
                        resp.setSelect(bean.isSelect());
                        break;
                    }
                }
            }
        }
    }
}
