package com.hll_sc_app.app.marketingsetting.selectarea;

import android.graphics.Color;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.bean.AreaBean;

import java.util.List;

public class ProvinceAdapter extends BaseQuickAdapter<AreaBean, BaseViewHolder> {

    private int currentIndex = 0;

    public ProvinceAdapter(@Nullable List<AreaBean> data) {
        super(R.layout.item_goods_custom_category_top, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, AreaBean item) {
        helper.setText(R.id.txt_categoryName, item.getName());

        //当前点击的
        if (helper.getLayoutPosition() == currentIndex) {
            helper.setBackgroundColor(R.id.txt_categoryName, 0xFFF3F3F3)
                    .setTextColor(R.id.txt_categoryName, Color.parseColor("#5695D2"));
        }//已勾选
        else if (item.isSelect()) {
            helper.setTextColor(R.id.txt_categoryName, Color.parseColor("#5695D2"))
                    .setBackgroundColor(R.id.txt_categoryName, 0xFFFFFFFF);
        } else {
            //默认状态
            helper.setTextColor(R.id.txt_categoryName, Color.parseColor("#666666"))
                    .setBackgroundColor(R.id.txt_categoryName, 0xFFFFFFFF);
        }
    }

    public void clickUpdate(int currentIndex) {
        this.currentIndex = currentIndex;
        notifyDataSetChanged();
    }
}
