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

    public GridSimpleDecoration(int space) {
        this.mSpace = space;
    }

    public GridSimpleDecoration() {
        this(UIUtils.dip2px(10));
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() == null || !(parent.getLayoutManager() instanceof GridLayoutManager)) {
            return;
        }
        int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        int remain = parent.getAdapter().getItemCount() % spanCount;
        int lastLineCount = remain == 0 ? spanCount : remain;
        outRect.bottom = parent.getChildLayoutPosition(view) >= parent.getAdapter().getItemCount() - lastLineCount // 如果是最后一行
                ? 0
                : mSpace;
        outRect.left = parent.getChildLayoutPosition(view) % spanCount == 0 // 如果是第一列
                ? 0
                : mSpace;
    }
}
