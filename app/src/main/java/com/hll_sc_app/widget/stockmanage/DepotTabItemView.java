package com.hll_sc_app.widget.stockmanage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hll_sc_app.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/9
 */

public class DepotTabItemView extends RelativeLayout {
    @BindView(android.R.id.text1)
    TextView mText1;
    @BindView(android.R.id.icon)
    ImageView mIcon;

    public DepotTabItemView(Context context) {
        this(context, null);
    }

    public DepotTabItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DepotTabItemView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        View view = View.inflate(context, R.layout.view_depot_tab_item, this);
        ButterKnife.bind(this, view);
    }

    public void select() {
        mText1.setTextSize(16);
        mIcon.setVisibility(VISIBLE);
    }

    public void unSelect() {
        mText1.setTextSize(13);
        mIcon.setVisibility(GONE);
    }
}
