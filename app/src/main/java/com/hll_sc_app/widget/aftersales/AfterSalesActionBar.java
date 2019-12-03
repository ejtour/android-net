package com.hll_sc_app.widget.aftersales;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.bean.common.ButtonAction;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.citymall.util.ToastUtils;

import java.util.HashMap;
import java.util.List;

import static com.hll_sc_app.bean.common.ButtonAction.BUTTON_NEGATIVE;
import static com.hll_sc_app.bean.common.ButtonAction.BUTTON_POSITIVE;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */

public class AfterSalesActionBar extends LinearLayout implements View.OnClickListener {
    private static final int ACTION_CUSTOMER = 2;
    private static final int ACTION_REJECT = 3;
    private static final int ACTION_DRIVER = 4;
    private static final int ACTION_WAREHOUSE = 5;
    private static final int ACTION_FINANCE = 6;
    private static final int ACTION_REAPPLY = 7;
    private static final int ACTION_CANCEL = 8;
    private static final int ACTION_COMPLAIN = 9;
    private static final int ACTION_CLOSE = 10;

    private final HashMap<Integer, ButtonAction> MAP;
    private OnClickListener mListener;

    {
        MAP = new HashMap<>();
        MAP.put(ACTION_CUSTOMER, new ButtonAction(BUTTON_POSITIVE, R.id.asa_customer, "客服审核"));
        MAP.put(ACTION_DRIVER, new ButtonAction(BUTTON_POSITIVE, R.id.asa_driver, "司机提货"));
        MAP.put(ACTION_WAREHOUSE, new ButtonAction(BUTTON_POSITIVE, R.id.asa_warehouse, "仓库收货"));
        MAP.put(ACTION_FINANCE, new ButtonAction(BUTTON_POSITIVE, R.id.asa_finance, "财务审核"));
        MAP.put(ACTION_REAPPLY, new ButtonAction(BUTTON_POSITIVE, R.id.asa_reapply, "重新申请"));

        MAP.put(ACTION_REJECT, new ButtonAction(BUTTON_NEGATIVE, R.id.asa_reject, "驳回"));
        MAP.put(ACTION_CANCEL, new ButtonAction(BUTTON_NEGATIVE, R.id.asa_cancel, "取消退换"));
        MAP.put(ACTION_COMPLAIN, new ButtonAction(BUTTON_NEGATIVE, R.id.asa_complain, "生成投诉单"));
        MAP.put(ACTION_CLOSE, new ButtonAction(BUTTON_NEGATIVE, R.id.asa_close, "关闭退款"));
    }

    private boolean isItem;

    public AfterSalesActionBar(Context context) {
        this(context, null);
    }

    public AfterSalesActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AfterSalesActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // 加载布局
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AfterSalesActionBar);
        isItem = typedArray.getBoolean(R.styleable.AfterSalesActionBar_sab_item, false);
        typedArray.recycle();
        setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mListener = l;
    }

    public void setData(List<Integer> buttons) {
        if (CommonUtils.isEmpty(buttons))  // 如果按钮数组为 0
            setVisibility(GONE);
        else {
            removeAllViews();
            for (int key : buttons) {
                ButtonAction action = MAP.get(key);
                if (action == null || isItem && key == ACTION_COMPLAIN) continue;
                TextView view = createButton(action.type);
                view.setId(action.id);
                view.setText(action.label);
                addView(view);
            }
            setVisibility(getChildCount() == 0 ? GONE : VISIBLE);
        }
    }

    private TextView createButton(int viewType) {
        TextView textView = new TextView(getContext());
        textView.setTextSize(12);
        textView.setGravity(Gravity.CENTER);
        textView.setPadding(UIUtils.dip2px(isItem ? 16 : 26), 0, UIUtils.dip2px(isItem ? 16 : 26), 0);
        textView.setMinWidth(UIUtils.dip2px(isItem ? 80 : 100));
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, UIUtils.dip2px(isItem ? 25 : 30));
        textView.setLayoutParams(params);
        params.rightMargin = UIUtils.dip2px(10);

        switch (viewType) {
            case BUTTON_POSITIVE:
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundResource(isItem ? R.drawable.bg_button_large_solid_primary : R.drawable.bg_button_mid_solid_primary);
                break;
            case BUTTON_NEGATIVE:
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                textView.setBackgroundResource(isItem ? R.drawable.bg_button_large_stroke_primary : R.drawable.bg_button_mid_stroke_primary);
                break;
        }
        textView.setOnClickListener(this);
        return textView;
    }

    @Override
    public void onClick(View v) {
        int right = 0;
        switch (v.getId()) {
            case R.id.asa_customer:
                right = R.string.right_returnedPurchaseCheck_examine;
                break;
            case R.id.asa_driver:
                right = R.string.right_returnedPurchaseCheck_dirverReceive;
                break;
            case R.id.asa_warehouse:
                right = R.string.right_returnedPurchaseCheck_warehouseReceive;
                break;
            case R.id.asa_finance:
                right = R.string.right_returnedPurchaseCheck_financeExamine;
                break;
            case R.id.asa_reject:
                right = R.string.right_returnedPurchaseCheck_reject;
                break;
        }
        if (right != 0 && !RightConfig.checkRight(v.getContext().getString(right))) {
            ToastUtils.showShort(v.getContext().getString(R.string.right_tips));
            return;
        }
        mListener.onClick(v);
    }
}