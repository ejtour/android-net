package com.hll_sc_app.app.marketingsetting.selectarea;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.bean.AreaBean;

import java.util.List;

public class CityAdapter extends BaseQuickAdapter<AreaBean.ChildBeanX, BaseViewHolder> {


    public CityAdapter(@Nullable List<AreaBean.ChildBeanX> data) {
        super(R.layout.item_delivery_area, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AreaBean.ChildBeanX item) {
        helper.setText(R.id.txt_name, item.getName())
                .setTextColor(R.id.txt_name, getTextColor(item));
        ImageView imgSelect = helper.getView(R.id.img_select);

        if (TextUtils.equals(item.getFlag(), "1")) {
            imgSelect.setSelected(true);
            imgSelect.setEnabled(false);
        } else {
            imgSelect.setEnabled(true);
            imgSelect.setSelected(TextUtils.equals("3", item.getFlag()));
        }
    }

    private int getTextColor(AreaBean.ChildBeanX item) {
        int color = Color.parseColor("#666666");
        switch (item.getFlag()) {
            case "1":
                color = Color.parseColor("#999999");
                break;
            case "2":
                color = Color.parseColor("#666666");
                break;
            case "3":
                color = Color.parseColor("#222222");
                break;
            default:
                break;
        }
        return color;
    }

}
