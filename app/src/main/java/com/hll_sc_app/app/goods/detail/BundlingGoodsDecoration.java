package com.hll_sc_app.app.goods.detail;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;

/**
 * 商品详情-组合商品的分割线加号
 *
 * @author zhuyingsong
 * @date 2019-6-14
 */
class BundlingGoodsDecoration extends RecyclerView.ItemDecoration {
    private final int mWidth;
    private final int mLineWidth;
    private final Bitmap mBitmap;

    BundlingGoodsDecoration(Context context) {
        mWidth = UIUtils.dip2px(10);
        mLineWidth = UIUtils.dip2px(30);
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.base_ic_img_add);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int width = mLineWidth / 2 - mWidth / 2;
        int top = parent.getHeight() / 2 - mWidth / 2;
        int bottom = top + mWidth;
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount - 1; i++) {
            View child = parent.getChildAt(i);
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) child.getLayoutParams();
            int left = child.getRight() + params.rightMargin + width;
            int right = left + mWidth;
            c.drawBitmap(mBitmap, null, new Rect(left, top, right, bottom), null);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (parent.getAdapter() == null) {
            return;
        }
        int childCount = parent.getAdapter().getItemCount();
        if (parent.getChildAdapterPosition(view) == childCount - 1) {
            return;
        }
        outRect.set(0, 0, mLineWidth, 0);
    }
}
