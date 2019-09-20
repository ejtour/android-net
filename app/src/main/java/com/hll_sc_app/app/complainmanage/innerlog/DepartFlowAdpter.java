package com.hll_sc_app.app.complainmanage.innerlog;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;

import java.util.List;

public class DepartFlowAdpter extends TagAdapter<String> {

    private LayoutInflater mLayoutInflater;
    private GradientDrawable drawable;
    private String textColor;

    public DepartFlowAdpter(Context context, List<String> datas) {
        super(datas);
        mLayoutInflater = LayoutInflater.from(context);
        drawable = new GradientDrawable();
        drawable.setColor(Color.parseColor("#69C0FF"));
        drawable.setCornerRadius(UIUtils.dip2px(13));
        drawable.setShape(GradientDrawable.RECTANGLE);

    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        TextView textView = (TextView) mLayoutInflater.inflate(R.layout.list_item_flow_depart, parent, false);
        textView.setText(s);
        if (!TextUtils.isEmpty(textColor)) {
            textView.setTextColor(Color.parseColor(textColor));
        }
        textView.setBackground(drawable);
        return textView;
    }

    @Override
    public boolean setSelected(int position, String t) {
        return false;
    }

    public GradientDrawable getDrawable() {
        return drawable;
    }


    public void setBackgroundColorandTextColor(String bgColor, String textColor) {
        drawable.setColor(Color.parseColor(bgColor));
        this.textColor = textColor;
        notifyDataChanged();
    }
}
