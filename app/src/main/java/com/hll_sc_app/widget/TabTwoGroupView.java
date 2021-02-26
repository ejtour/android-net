package com.hll_sc_app.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.widget.report.ReportTipsView;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/7
 */

public class TabTwoGroupView extends ConstraintLayout {
    public TabTwoGroupView(Context context) {
        this(context, null);
    }

    public TabTwoGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TabTwoGroupView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View.inflate(context, R.layout.item_tab_two_refresh_layout, this);
    }

    /**
     * @param anchor 指定在该 anchor 顶部添加提示
     * @param tips   提示文本
     */
    public void addTips(View anchor, String tips) {
        ReportTipsView tipsView = new ReportTipsView(getContext());
        tipsView.setTips(tips);
        ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_CONSTRAINT, UIUtils.dip2px(30));
        params.topToBottom = R.id.trl_tab_one_btn;
        params.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
        params.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
        tipsView.setId(R.id.aid_tips);
        tipsView.setLayoutParams(params);
        addView(tipsView, getChildCount() - 1);

        params = (LayoutParams) anchor.getLayoutParams();
        params.topToBottom = R.id.aid_tips;
    }
}
