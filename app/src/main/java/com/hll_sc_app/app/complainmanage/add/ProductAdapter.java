package com.hll_sc_app.app.complainmanage.add;

import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.RelativeSizeSpan;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.IntDef;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.glide.GlideImageView;
import com.hll_sc_app.bean.order.detail.OrderDetailBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.List;

public class ProductAdapter extends BaseQuickAdapter<OrderDetailBean, BaseViewHolder> {
    private int mType;
    private List<OrderDetailBean> mselectBean;

    public ProductAdapter(@Nullable List<OrderDetailBean> data, @TYPE int type, List<OrderDetailBean> select) {
        super(R.layout.list_item_complain_select_product, data);
        mType = type;
        mselectBean = select;
    }

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder holder = super.onCreateDefViewHolder(parent, viewType);
        holder.setGone(R.id.check_view, mType == TYPE.SELECT)
                .setGone(R.id.img_del, mType == TYPE.EDIT);
        return holder;
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetailBean item) {
        ((GlideImageView) helper.setText(R.id.txt_product_name, item.getProductName())
                .setText(R.id.txt_spec, item.getProductSpec())
                .setText(R.id.txt_price, getPrice(item.getProductPrice(), item.getSaleUnitName()))
                .getView(R.id.img_product))
                .setImageURL(item.getImgUrl());
        if (mselectBean != null) {
            ((CheckBox) helper.getView(R.id.check_view)).setChecked(mselectBean.contains(item));
        }
    }

    private SpannableString getPrice(double price, String unit) {
        if (unit == null) {
            unit = "";
        }
        String out = "¥" + CommonUtils.formatMoney(price) + "/" + unit;
        SpannableString spannableString = new SpannableString(out);
        spannableString.setSpan(new RelativeSizeSpan(1.5f), 1, out.indexOf("."), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    @IntDef({TYPE.EDIT, TYPE.SELECT})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
        int EDIT = 0;
        int SELECT = 1;
    }
}