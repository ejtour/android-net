package com.hll_sc_app.widget;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hll_sc_app.base.utils.UIUtils;

/**
 * 为 RecyclerView　指定分割线
 *
 * @author zhuyingsong
 * @date 2019/7/30
 */

public class GridSimpleDecoration extends RecyclerView.ItemDecoration {
    private int mSpace;
    private int mCount;

    public GridSimpleDecoration(int count) {
        this.mCount = count;
        this.mSpace = UIUtils.dip2px(10);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() == null) {
            return;
        }
        outRect.left = mSpace;
        outRect.bottom = mSpace;
        if (parent.getChildLayoutPosition(view) % mCount == 0) {
            outRect.left = 0;
        }
    }
}
