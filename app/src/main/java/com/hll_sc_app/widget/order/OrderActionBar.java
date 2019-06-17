package com.hll_sc_app.widget.order;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/13
 */

public class OrderActionBar extends LinearLayout {
    private static final int ACTION_RECEIVE = 1;
    private static final int ACTION_CANCEL = 2;
    private static final int ACTION_DELIVER = 3;
    private static final int ACTION_INSPECTION = 4;
    private static final int ACTION_CONFIRM = 5;
    private static final int ACTION_SETTLE = 6;
    private static final int ACTION_REJECT = 7;
    private static final int ACTION_MODIFY = 10;
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
    @BindView(R.id.oab_reject)
    TextView mReject;
    @BindView(R.id.oab_inspection)
    TextView mInspection;
    @BindViews({R.id.oab_cancel, R.id.oab_modify, R.id.oab_receive, R.id.oab_deliver, R.id.oab_settle, R.id.oab_reject, R.id.oab_inspection})
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
                case ACTION_RECEIVE:
                    mReceive.setVisibility(VISIBLE);
                    break;
                case ACTION_CANCEL:
                    mCancel.setVisibility(VISIBLE);
                    break;
                case ACTION_DELIVER:
                    mDeliver.setVisibility(VISIBLE);
                    break;
                case ACTION_INSPECTION:
                    mInspection.setVisibility(VISIBLE);
                    break;
                case ACTION_SETTLE:
                    mSettle.setVisibility(VISIBLE);
                    break;
                case ACTION_REJECT:
                    mReject.setVisibility(VISIBLE);
                    break;
                case ACTION_MODIFY:
                    mModify.setVisibility(VISIBLE);
                    break;
            }
        }
    }
}
