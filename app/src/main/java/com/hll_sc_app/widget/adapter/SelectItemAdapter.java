package com.hll_sc_app.widget.adapter;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;

import java.util.List;

public class SelectItemAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {
    private Config<T> config;

    public SelectItemAdapter(@Nullable List<T> data, Config<T> config) {
        super(R.layout.list_item_select_view, data);
        this.config = config;
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        TextView mName = helper.getView(R.id.txt_name);
        mName.setText(this.config.getName(item));
        mName.setTextColor(Color.parseColor(this.config.isSelected(item) ? "#5695D2" : "#666666"));
        helper.setVisible(R.id.img_ok, this.config.isSelected(item));
    }

    public interface Config<T> {
        String getName(T t);

        boolean isSelected(T t);
    }
}
