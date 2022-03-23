package com.hll_sc_app.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.window.OptionsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/10
 */

public class ContextOptionsWindow extends BasePopupWindow implements View.OnClickListener {
    @BindView(R.id.wco_list)
    RecyclerView mListView;
    @BindView(R.id.wco_arrow)
    TriangleView mArrow;
    private OptionsAdapter mAdapter;

    public ContextOptionsWindow(Activity context) {
        super(context);
        initWindow(context);
        initView();
    }

    private void initView() {
        mAdapter = new OptionsAdapter();
        mAdapter.bindToRecyclerView(mListView);
    }

    private void initWindow(Activity context) {
        View rootView = View.inflate(context, R.layout.window_context_options, null);
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable());
    }

    public ContextOptionsWindow refreshList(List<OptionsBean> list) {
        mAdapter.setNewData(list);
        return this;
    }

    public ContextOptionsWindow setItemGravity(int gravity) {
        mAdapter.setItemGravity(gravity);
        return this;
    }

    public ContextOptionsWindow setListener(BaseQuickAdapter.OnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
        return this;
    }

    public ContextOptionsWindow setListPadding(int left, int top, int right, int bottom) {
        mListView.setPadding(0, top, 0, bottom);
        mAdapter.setItemPadding(left, right);
        return this;
    }

    public ContextOptionsWindow setItemHeight(int itemHeight) {
        mAdapter.setItemHeight(itemHeight);
        return this;
    }

    public void showAsDropDownFix(View anchor, int gravity) {
        showAsDropDownFix(anchor, 0, 0, gravity);
    }

    public void showAsDropDownFix(View anchor, int xOff, int yOff, int gravity) {
        int[] location = new int[2];
        anchor.getLocationOnScreen(location);
        getContentView().measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        int windowWidth = getContentView().getMeasuredWidth();
        int windowHeight = getContentView().getMeasuredHeight();
        int hgrav = Gravity.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(anchor))
                & Gravity.HORIZONTAL_GRAVITY_MASK;
        ConstraintLayout.LayoutParams arrowParams = (ConstraintLayout.LayoutParams) mArrow.getLayoutParams();
        ConstraintLayout.LayoutParams listParams = (ConstraintLayout.LayoutParams) mListView.getLayoutParams();
        boolean showTop = location[1] + anchor.getHeight() + windowHeight >= UIUtils.getScreenHeight(anchor.getContext());
        int x = 0;
        switch (hgrav) {
            case Gravity.RIGHT:
                arrowParams.startToStart = -1;
                arrowParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                x = location[0] + anchor.getWidth() - windowWidth + xOff;
                break;
            case Gravity.LEFT:
                arrowParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                arrowParams.endToEnd = -1;
                x = location[0] + xOff;
                break;
            case Gravity.CENTER_HORIZONTAL:
                arrowParams.startToStart = arrowParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                x = location[0] + anchor.getWidth() / 2 - windowWidth / 2 + xOff;
                break;
        }
        if (showTop) {
            arrowDown(arrowParams, listParams);
            showAtLocation(anchor, Gravity.NO_GRAVITY,
                    x, location[1] - windowHeight + yOff);
        } else {
            arrowUp(arrowParams, listParams);
            showAtLocation(anchor, Gravity.NO_GRAVITY,
                    x, location[1] + anchor.getHeight() + yOff);
        }
        mArrow.post(mAdapter::notifyDataSetChanged);
    }

    private void arrowUp(ConstraintLayout.LayoutParams arrowParams, ConstraintLayout.LayoutParams listParams) {
        mArrow.update(TriangleView.TOP, ContextCompat.getColor(mActivity, R.color.color0_7_000000));
        arrowParams.topToBottom = -1;
        arrowParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        listParams.topToBottom = R.id.wco_arrow;
        listParams.topToTop = -1;
    }

    private void arrowDown(ConstraintLayout.LayoutParams arrowParams, ConstraintLayout.LayoutParams listParams) {
        mArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(mActivity, R.color.color0_7_000000));
        arrowParams.topToTop = -1;
        arrowParams.topToBottom = R.id.wco_list;
        listParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        listParams.topToBottom = -1;
    }

    @Override
    public void onClick(View v) {
        mAdapter.getOnItemClickListener().onItemClick(mAdapter, v, ((int) v.getTag()));
    }

    class OptionsAdapter extends BaseQuickAdapter<OptionsBean, BaseViewHolder> {
        private int mGravity;
        private int mLeftPadding;
        private int mRightPadding;
        private int mItemHeight;

        OptionsAdapter() {
            super(R.layout.item_context_options);
            mLeftPadding = UIUtils.dip2px(10);
            mRightPadding = UIUtils.dip2px(10);
            mItemHeight = UIUtils.dip2px(48);
        }

        void setItemGravity(int gravity) {
            mGravity = gravity;
        }

        void setItemPadding(int deltaLeft, int deltaRight) {
            mLeftPadding = UIUtils.dip2px(10) + deltaLeft;
            mRightPadding = UIUtils.dip2px(10) + deltaRight;
        }

        void setItemHeight(int itemHeight) {
            mItemHeight = itemHeight;
        }

        @Override
        protected void convert(BaseViewHolder helper, OptionsBean item) {
            LinearLayout itemView = (LinearLayout) helper.itemView;
            ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
            layoutParams.height = mItemHeight;
            if (mGravity != 0) {
                itemView.setGravity(mGravity);
            }
            itemView.setLayoutParams(layoutParams);
            helper.itemView.setPadding(mLeftPadding, 0, mRightPadding, 0);
            helper.setImageResource(R.id.ico_icon, item.getIconRes())
                    .setText(R.id.ico_label, item.getLabel())
                    .setGone(R.id.ico_icon, item.getIconRes() != 0);
        }
    }
}
