package com.hll_sc_app.base;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * recyclerView间距
 *
 * @author 胡永城
 * @date 2019/5/27
 */
public class LineItemDecoration extends RecyclerView.ItemDecoration {
    private int mTop = 0;
    private int mBottom = 5;

    public LineItemDecoration() {
        super();
    }

    public LineItemDecoration(int bottom) {
        this.mBottom = bottom;
        new LineItemDecoration();
    }

    public LineItemDecoration(int bottom, int top) {
        this.mBottom = bottom;
        this.mTop = top;
        new LineItemDecoration();
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() == null) {
            return;
        }
        int childItemCount = parent.getAdapter().getItemCount();
        if (parent.getChildAdapterPosition(view) == childItemCount - 1) {
            return;
        }
        if (parent.getChildAdapterPosition(view) == 0) {
            outRect.set(0, mTop, 0, mBottom);
        } else {
            outRect.set(0, 0, 0, mBottom);
        }
    }
}
