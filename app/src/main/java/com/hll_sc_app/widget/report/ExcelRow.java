package com.hll_sc_app.widget.report;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.utils.ColorStr;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/23
 */

public class ExcelRow extends LinearLayout {
    private List<TextView> mTextViewList = new ArrayList<>();

    public ExcelRow(Context context) {
        this(context, null);
    }

    public ExcelRow(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ExcelRow(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(30)));
    }

    /**
     * @param num 表格列数
     */
    public void updateChildView(int num) {
        removeAllViews();
        mTextViewList.clear();
        for (int i = 0; i < num; i++) {
            TextView textView = new TextView(getContext());
            mTextViewList.add(textView);
            LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(10);
            textView.setPadding(UIUtils.dip2px(10), 0, UIUtils.dip2px(10), 0);
            textView.setTextColor(ContextCompat.getColor(getContext(), R.color.color_222222));
            addView(textView, layoutParams);
            if (i < num - 1) {
                View div = new View(getContext());
                LayoutParams divParams = new LayoutParams(UIUtils.dip2px(1), LayoutParams.MATCH_PARENT);
                div.setBackgroundColor(ContextCompat.getColor(getContext(), R.color.color_dddddd));
                addView(div, divParams);
            }
        }
    }

    public static class ColumnData {
        public ColumnData(int width, int gravity) {
            this(width, gravity, Color.parseColor(ColorStr.COLOR_222222), 10);
        }

        public ColumnData(int width) {
            this(width, Gravity.CENTER, Color.parseColor(ColorStr.COLOR_222222), 10);
        }

        public ColumnData(int width, int gravity, int color, int textSize) {
            this.width = width;
            this.gravity = gravity;
            this.color = color;
            this.textSize = textSize;
        }

        public ColumnData(int width, int color, int textSize) {
            this(width, Gravity.CENTER, color, textSize);
        }

        public static ColumnData createDefaultHeader(int width) {
            return new ColumnData(width, Color.parseColor(ColorStr.COLOR_999999), 10);
        }

        int width;
        int gravity;
        int color;
        int textSize;
    }

    /**
     * 设置每列的格式
     */
    public void updateItemData(ColumnData... columnData) {
        if (columnData.length == 0 || columnData.length != mTextViewList.size()) return;
        for (int i = 0; i < columnData.length; i++) {
            TextView textView = mTextViewList.get(i);
            ColumnData data = columnData[i];
            textView.setGravity(data.gravity);
            LayoutParams params = (LayoutParams) textView.getLayoutParams();
            params.width = data.width;
            textView.setTextSize(data.textSize);
            textView.setTextColor(data.color);
        }
    }

    /**
     * 设置每列的数据
     */
    public void updateRowDate(CharSequence... contentArray) {
        if (contentArray.length == 0 || contentArray.length != mTextViewList.size()) return;
        for (int i = 0; i < contentArray.length; i++) {
            mTextViewList.get(i).setText(contentArray[i]);
        }
    }
}
