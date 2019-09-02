package com.hll_sc_app.widget;

import android.graphics.Canvas;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/6
 * <p>
 * 本类通过获取 ViewHolder 对象 的 itemView 成员的 tag 属性，如果为 true，表明是粘性头部，对其进行测量绘制。
 * STICKY_TYPE 要保证和 Adapter 中对应类型一致。
 */

public class StickyItemDecoration extends RecyclerView.ItemDecoration {

    public static final int STICKY_TYPE = 0x00000444;
    /**
     * 吸附的itemView
     */
    private View mStickyItemView;

    /**
     * 吸附itemView 距离顶部
     */
    private int mStickyItemViewMarginTop;

    /**
     * 吸附itemView 高度
     */
    private int mStickyItemViewHeight;

    /**
     * adapter
     */
    private RecyclerView.Adapter mAdapter;

    /**
     * viewHolder
     */
    private RecyclerView.ViewHolder mViewHolder;

    /**
     * position list
     */
    private List<Integer> mStickyPositionList = new ArrayList<>();

    /**
     * layout manager
     */
    private LinearLayoutManager mLayoutManager;

    /**
     * 绑定数据的position
     */
    private int mBindDataPosition = -1;

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        if (parent.getAdapter().getItemCount() <= 0) return;

        if (!(parent.getLayoutManager() instanceof LinearLayoutManager)) {
            throw new IllegalStateException("only support LinearLayoutManager");
        }

        mLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
        /*
         * 滚动过程中当前的UI是否可以找到吸附的view
         */
        boolean currentUIFindStickView = false;

        for (int m = 0, size = parent.getChildCount(); m < size; m++) {
            View view = parent.getChildAt(m);

            /*
             * 如果是吸附的view
             */
            if (((boolean) view.getTag())) {
                currentUIFindStickView = true;
                getStickyViewHolder(parent);
                cacheStickyViewPosition(m);

                if (view.getTop() <= 0) {
                    bindDataForStickyView(mLayoutManager.findFirstVisibleItemPosition(), parent.getMeasuredWidth());
                } else if (mStickyPositionList.size() > 0) {
                    if (mStickyPositionList.size() == 1) {
                        bindDataForStickyView(mStickyPositionList.get(0), parent.getMeasuredWidth());
                    } else {
                        int currentPosition = getStickyViewPositionOfRecyclerView(m);
                        int indexOfCurrentPosition = mStickyPositionList.lastIndexOf(currentPosition);
                        if (indexOfCurrentPosition >= 1)
                            bindDataForStickyView(mStickyPositionList.get(indexOfCurrentPosition - 1),
                                parent.getMeasuredWidth());
                    }
                }

                if (view.getTop() > 0 && view.getTop() <= mStickyItemViewHeight) {
                    mStickyItemViewMarginTop = mStickyItemViewHeight - view.getTop();
                } else {
                    mStickyItemViewMarginTop = 0;

                    View nextStickyView = getNextStickyView(parent);
                    if (nextStickyView != null && nextStickyView.getTop() <= mStickyItemViewHeight) {
                        mStickyItemViewMarginTop = mStickyItemViewHeight - nextStickyView.getTop();
                    }

                }

                drawStickyItemView(c);
                break;
            }
        }

        if (!currentUIFindStickView) {
            mStickyItemViewMarginTop = 0;
            drawStickyItemView(c);
        }
    }

    /**
     * 得到吸附viewHolder
     */
    private void getStickyViewHolder(RecyclerView recyclerView) {
        if (mAdapter != null) return;

        mAdapter = recyclerView.getAdapter();
        mViewHolder = mAdapter.onCreateViewHolder(recyclerView, STICKY_TYPE);
        mStickyItemView = mViewHolder.itemView;
    }

    /**
     * 缓存吸附的view position
     */
    private void cacheStickyViewPosition(int m) {
        int position = getStickyViewPositionOfRecyclerView(m);
        if (!mStickyPositionList.contains(position)) {
            mStickyPositionList.add(position);
        }
    }

    /**
     * 给StickyView绑定数据
     */
    private void bindDataForStickyView(int position, int width) {
        if (mBindDataPosition == position || mViewHolder == null) return;

        mBindDataPosition = position;
        mAdapter.onBindViewHolder(mViewHolder, mBindDataPosition);
        measureLayoutStickyItemView(width);
        mStickyItemViewHeight = mViewHolder.itemView.getBottom() - mViewHolder.itemView.getTop();
    }

    /**
     * 得到吸附view在RecyclerView中 的position
     */
    private int getStickyViewPositionOfRecyclerView(int m) {
        return mLayoutManager.findFirstVisibleItemPosition() + m;
    }

    /**
     * 得到下一个吸附View
     */
    private View getNextStickyView(RecyclerView parent) {
        int num = 0;
        View nextStickyView = null;
        for (int m = 0, size = parent.getChildCount(); m < size; m++) {
            View view = parent.getChildAt(m);
            if (((boolean) view.getTag())) {
                nextStickyView = view;
                num++;
            }
            if (num == 2) break;
        }
        return num >= 2 ? nextStickyView : null;
    }

    /**
     * 绘制吸附的itemView
     */
    private void drawStickyItemView(Canvas canvas) {
        if (mStickyItemView == null) return;

        int saveCount = canvas.save();
        canvas.translate(0, -mStickyItemViewMarginTop);
        mStickyItemView.draw(canvas);
        canvas.restoreToCount(saveCount);
    }

    /**
     * 计算布局吸附的itemView
     */
    private void measureLayoutStickyItemView(int parentWidth) {
        if (mStickyItemView == null || !mStickyItemView.isLayoutRequested()) return;

        int widthSpec = View.MeasureSpec.makeMeasureSpec(parentWidth, View.MeasureSpec.EXACTLY);
        int heightSpec;

        ViewGroup.LayoutParams layoutParams = mStickyItemView.getLayoutParams();
        if (layoutParams != null && layoutParams.height > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(layoutParams.height, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }

        mStickyItemView.measure(widthSpec, heightSpec);
        mStickyItemView.layout(0, 0, mStickyItemView.getMeasuredWidth(), mStickyItemView.getMeasuredHeight());
    }

    /**
     * 粘性头部数据更新
     */
    public void notifyChanged() {
        if (mAdapter == null || mViewHolder == null || mAdapter.getItemCount() == 0 || mLayoutManager == null)
            return;
        mBindDataPosition = -1;
        mLayoutManager.scrollToPosition(0);
        mStickyPositionList.clear();
    }
}
