package com.hll_sc_app.app.crm.customer.search;

import android.graphics.Color;
import android.os.Parcelable;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.bean.agreementprice.quotation.PurchaserShopBean;
import com.hll_sc_app.bean.customer.CustomerBean;
import com.hll_sc_app.bean.goods.PurchaserBean;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/27
 */

public class CustomerSearchAdapter extends BaseQuickAdapter<Parcelable, BaseViewHolder> {
    private Parcelable mCur;

    CustomerSearchAdapter() {
        super(R.layout.item_single_selection);
    }

    void select(Parcelable value) {
        mCur = value;
        notifyDataSetChanged();
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
        helper.itemView.setPadding(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0);
        helper.itemView.setBackgroundColor(Color.WHITE);
        return helper;
    }

    @Override
    protected void convert(BaseViewHolder helper, Parcelable item) {
        String name = null;
        if (item instanceof CustomerBean) {
            CustomerBean bean = (CustomerBean) item;
            name = bean.getCustomerName();
        } else if (item instanceof PurchaserShopBean) {
            name = ((PurchaserShopBean) item).getShopName();
        } else if (item instanceof PurchaserBean) {
            name = ((PurchaserBean) item).getPurchaserName();
        }
        helper.setText(R.id.iss_label, name)
                .setGone(R.id.iss_check, mCur == item);
    }
}
