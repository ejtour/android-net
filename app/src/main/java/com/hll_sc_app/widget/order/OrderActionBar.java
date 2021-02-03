package com.hll_sc_app.widget.order;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.TextViewCompat;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.common.ButtonAction;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.ViewUtils;
import com.hll_sc_app.widget.ContextOptionsWindow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.hll_sc_app.bean.common.ButtonAction.BUTTON_NEGATIVE;
import static com.hll_sc_app.bean.common.ButtonAction.BUTTON_NEGATIVE_GRAY;
import static com.hll_sc_app.bean.common.ButtonAction.BUTTON_POSITIVE;
import static com.hll_sc_app.bean.common.ButtonAction.TIP_CENTER;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/13
 */

public class OrderActionBar extends RelativeLayout {
    private static final int ACTION_RECEIVE = 1;
    private static final int ACTION_CANCEL = 2;
    private static final int ACTION_DELIVER = 3;
    public static final int ACTION_INSPECTION = 4;
    private static final int ACTION_CONFIRM = 5;
    private static final int ACTION_SETTLE = 6;
    private static final int ACTION_REJECT = 7;
    private static final int ACTION_REFUND = 8;
    private static final int ACTION_REFUND_DETAIL = 9;
    private static final int ACTION_MODIFY = 10;
    public static final int ACTION_REMARK = 12;
    private final HashMap<Integer, ButtonAction> MAP;
    private TextView mMore;
    private int mSpace;
    private int mMaxWidth;
    private List<OptionsBean> mList;
    private ContextOptionsWindow mOptionsWindow;

    {
        MAP = new HashMap<>();
        MAP.put(ACTION_RECEIVE, new ButtonAction(BUTTON_NEGATIVE, R.id.oab_receive, "立即接单"));
        MAP.put(ACTION_DELIVER, new ButtonAction(BUTTON_NEGATIVE, R.id.oab_deliver, "确认发货"));
        MAP.put(ACTION_INSPECTION, new ButtonAction(BUTTON_NEGATIVE, R.id.oab_inspection, "验货"));
        MAP.put(ACTION_SETTLE, new ButtonAction(BUTTON_NEGATIVE, R.id.oab_settle, "立即收款"));

        MAP.put(ACTION_REFUND, new ButtonAction(BUTTON_NEGATIVE_GRAY, R.id.oab_refund, "申请退换货"));
        MAP.put(ACTION_CANCEL, new ButtonAction(BUTTON_NEGATIVE_GRAY, R.id.oab_cancel, "取消订单"));
        MAP.put(ACTION_REJECT, new ButtonAction(BUTTON_NEGATIVE_GRAY, R.id.oab_reject, "拒收"));
        MAP.put(ACTION_MODIFY, new ButtonAction(BUTTON_NEGATIVE_GRAY, R.id.oab_modify, "修改发货信息"));
        MAP.put(ACTION_REFUND_DETAIL, new ButtonAction(BUTTON_NEGATIVE_GRAY, R.id.oab_refund_detail, "查看退款进度"));
        MAP.put(ACTION_REMARK, new ButtonAction(BUTTON_NEGATIVE_GRAY, R.id.oab_remark, "商家备注"));

        MAP.put(ACTION_CONFIRM, new ButtonAction(TIP_CENTER, R.id.oab_confirm, "已提交验货数量，等待客户确认"));
    }

    private OnClickListener mListener;

    public OrderActionBar(Context context) {
        this(context, null);
    }

    public OrderActionBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OrderActionBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setBackgroundResource(R.drawable.base_bg_shadow_bottom_bar);
        initMoreButton(context);
    }

    private void initMoreButton(Context context) {
        mMore = new TextView(context);
        mMore.setText("更多");
        mSpace = ViewUtils.dip2px(context, 10);
        mMore.setPadding(mSpace, mSpace, mSpace, mSpace);
        mMore.setGravity(Gravity.CENTER);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        layoutParams.addRule(CENTER_VERTICAL);
        mMore.setLayoutParams(layoutParams);
        TextViewCompat.setTextAppearance(mMore, R.style.TextAppearance_City22_Small_Color999);
        mMore.measure(MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
        mMaxWidth = ViewUtils.getScreenWidth(context) - mMore.getMeasuredWidth();
        mMore.setOnClickListener(this::showMore);
    }

    private void showMore(View view) {
        if (mOptionsWindow == null) {
            mOptionsWindow = new ContextOptionsWindow((Activity) view.getContext())
                    .setListPadding(mSpace * 2, 0, mSpace * 2, 0)
                    .setItemGravity(Gravity.CENTER)
                    .setItemHeight(mSpace * 4)
                    .setListener((adapter, view1, position) -> {
                        OptionsBean item = (OptionsBean) adapter.getItem(position);
                        if (mListener == null || item == null) return;
                        int id = (int) item.getExtra();
                        view1.setId(id);
                        mListener.onClick(view1);
                        mOptionsWindow.dismiss();
                    });
        }
        mOptionsWindow.refreshList(mList)
                .showAsDropDownFix(mMore, Gravity.START);
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        mListener = l;
    }

    public void setData(List<Integer> buttonList, double diffPrice) {
        removeAllViews();
        addView(mMore);
        mMore.setVisibility(INVISIBLE);
        boolean center = UserConfig.crm() && buttonList.size() == 1;
        int totalWidth = 0;
        for (int i = buttonList.size() - 1; i >= 0; i--) {
            ButtonAction action = MAP.get(buttonList.get(i));
            if (action == null) continue;
            if (action.type != TIP_CENTER && !center) {
                totalWidth += Math.max(mMore.getPaint().measureText(action.label) + mSpace * 4, mSpace * 8.8) + mSpace;
                if (totalWidth > mMaxWidth) {
                    if (mList == null) mList = new ArrayList<>();
                    OptionsBean bean = new OptionsBean(action.label);
                    bean.setExtra(action.id);
                    mList.add(bean);
                    mMore.setVisibility(VISIBLE);
                    continue;
                }
            }
            TextView view = action.type != TIP_CENTER ? createButton(action.type, center) : createTip();
            view.setId(action.id);
            view.setText(action.label);
            addView(view);
        }
        if (diffPrice > 0) addDiffPriceTip(diffPrice);
    }

    private void addDiffPriceTip(double diffPrice) {
        TextView textView = new TextView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        params.rightMargin = UIUtils.dip2px(10);
        textView.setTextAppearance(getContext(), R.style.TextAppearance_City22_Small);
        params.addRule(CENTER_VERTICAL);
        if (getChildCount() == 0) params.addRule(ALIGN_PARENT_RIGHT);
        else params.addRule(LEFT_OF, getChildAt(getChildCount() - 1).getId());
        textView.setText(handleDiffPrice("客户仍需补差价¥" + diffPrice));
        addView(textView);
    }

    /**
     * @param viewType 按钮类型
     * @param center   居中展示按钮
     */
    private TextView createButton(int viewType, boolean center) {
        TextView textView = new TextView(getContext());
        textView.setPadding(UIUtils.dip2px(20), 0, UIUtils.dip2px(20), 0);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, UIUtils.dip2px(30));
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER);
        textView.setTextSize(12);

        params.addRule(CENTER_VERTICAL);
        if (center) {
            textView.setMinWidth(UIUtils.dip2px(200));
            params.addRule(CENTER_HORIZONTAL);
        } else {
            textView.setMinWidth(UIUtils.dip2px(88));
            params.rightMargin = UIUtils.dip2px(10);
            if (getChildCount() == 1) params.addRule(ALIGN_PARENT_RIGHT);
            else params.addRule(LEFT_OF, getChildAt(getChildCount() - 1).getId());
        }

        switch (viewType) {
            case BUTTON_POSITIVE:
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundResource(center ? R.drawable.bg_button_mid_solid_primary : R.drawable.bg_button_large_solid_primary);
                break;
            case BUTTON_NEGATIVE:
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                textView.setBackgroundResource(center ? R.drawable.bg_button_mid_stroke_primary : R.drawable.bg_button_large_stroke_primary);
                break;
            case BUTTON_NEGATIVE_GRAY:
                textView.setTextColor(ContextCompat.getColor(getContext(), R.color.color_333333));
                textView.setBackgroundResource(center ? R.drawable.bg_button_mid_stroke_gray : R.drawable.bg_button_large_stroke_gray);
                break;
        }
        textView.setOnClickListener(mListener);
        return textView;
    }

    private TextView createTip() {
        TextView textView = new TextView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        textView.setTextAppearance(getContext(), R.style.TextAppearance_City22_Small_Blue);
        params.addRule(CENTER_IN_PARENT);
        return textView;
    }

    private SpannableString handleDiffPrice(String source) {
        SpannableString ss = SpannableString.valueOf(source);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.color_ed5655)), source.indexOf("¥"), source.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        return ss;
    }
}
