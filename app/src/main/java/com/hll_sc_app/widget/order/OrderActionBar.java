package com.hll_sc_app.widget.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/13
 */

public class OrderActionBar extends LinearLayout {
    @BindView(R.id.oab_cancel)
    TextView mCancel;
    @BindView(R.id.oab_modify)
    TextView mModify;
    @BindView(R.id.oab_receive)
    TextView mReceive;
    @BindView(R.id.oab_deliver)
    TextView mDeliver;
    @BindView(R.id.oab_settle)
    TextView mSettle;
    @BindViews({R.id.oab_cancel, R.id.oab_modify, R.id.oab_receive, R.id.oab_deliver, R.id.oab_settle})
    List<View> mActionViews;

    public OrderActionBar(Context context) {
        this(context, null);
    }

    public OrderActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundResource(R.drawable.base_bg_shadow_bottom_bar);
        setGravity(Gravity.RIGHT | Gravity.CENTER_VERTICAL);
        setPadding(UIUtils.dip2px(10), 0, 0, 0);
        View view = View.inflate(context, R.layout.view_order_action_bar, this);
        ButterKnife.bind(this, view);
    }

    public void setData(List<Integer> buttonList) {
        ButterKnife.apply(mActionViews, (view, index) -> view.setVisibility(GONE));
        for (int i : buttonList) {
            switch (i) {
                case 1:
                    mReceive.setVisibility(VISIBLE);
                    break;
                case 2:
                    mCancel.setVisibility(VISIBLE);
                    break;
                case 3:
                    mDeliver.setVisibility(VISIBLE);
                    break;
                case 6:
                    mSettle.setVisibility(VISIBLE);
                    break;
                case 10:
                    mModify.setVisibility(VISIBLE);
                    break;
            }
        }
    }
}
