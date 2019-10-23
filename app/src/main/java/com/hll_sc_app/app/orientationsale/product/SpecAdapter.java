package com.hll_sc_app.app.orientationsale.product;

import android.support.annotation.IntDef;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.bean.goods.SpecsBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.hll_sc_app.app.orientationsale.product.SpecAdapter.FLAG.CHECK;
import static com.hll_sc_app.app.orientationsale.product.SpecAdapter.FLAG.DELETE;


public class SpecAdapter extends BaseQuickAdapter<SpecsBean, BaseViewHolder> {

    private int type;

    public SpecAdapter(@FLAG int type) {
        super(R.layout.list_item_orientation_spec_list);
        this.type = type;
    }

    @Override
    protected void convert(BaseViewHolder helper, SpecsBean item) {
        helper.addOnClickListener(R.id.img_delete)
                .setText(R.id.txt_spec_name, item.getSpecContent())
                .setText(R.id.txt_price, "¥" + CommonUtils.formatMoney(Double.parseDouble(item.getProductPrice())));
        if (type == CHECK) {
            helper.getView(R.id.img_select).setSelected(item.getAppointSellType() == 1);
        } else if (type == DELETE) {
            ((ImageView) helper.getView(R.id.img_select)).setImageResource(R.drawable.ic_delete);
        }
    }


    @IntDef({CHECK, FLAG.DELETE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FLAG {
        int CHECK = 1;
        int DELETE = 2;
    }
}
