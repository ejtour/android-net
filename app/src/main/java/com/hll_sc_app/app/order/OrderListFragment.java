package com.hll_sc_app.app.order;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.base.BaseLazyFragment;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/4
 */

public class OrderListFragment extends BaseLazyFragment {
    @Override
    protected View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        TextView textView = new TextView(container.getContext());
        textView.setText(getTitle());
        return textView;
    }

    @Override
    protected void initData() {

    }
}
