package com.hll_sc_app.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

public class ContextOptionsWindow extends BasePopupWindow {
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
        mAdapter.setGravity(gravity);
        return this;
    }

    public ContextOptionsWindow setListener(BaseQuickAdapter.OnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
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
        if (showTop) arrowDown(anchor, arrowParams, listParams);
        else arrowUp(anchor, arrowParams, listParams);
        switch (hgrav) {
            case Gravity.RIGHT:
                arrowParams.startToStart = -1;
                arrowParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                PopupWindowCompat.showAsDropDown(this, anchor, xOff, yOff, gravity);
                break;
            case Gravity.LEFT:
                arrowParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID;
                arrowParams.endToEnd = -1;
                PopupWindowCompat.showAsDropDown(this, anchor, xOff, yOff, gravity);
                break;
            case Gravity.CENTER_HORIZONTAL:
                arrowParams.startToStart = arrowParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID;
                int x = location[0] + anchor.getWidth() / 2 - windowWidth / 2 + xOff;
                if (showTop) showAtLocation(anchor, Gravity.NO_GRAVITY,
                        x, location[1] - anchor.getHeight() + yOff);
                else showAtLocation(anchor, Gravity.NO_GRAVITY,
                        x, location[1] + anchor.getHeight() + yOff);
                break;
        }
        mArrow.post(mAdapter::notifyDataSetChanged);
    }

    private void arrowUp(View anchor, ConstraintLayout.LayoutParams arrowParams, ConstraintLayout.LayoutParams listParams) {
        mArrow.update(TriangleView.TOP, ContextCompat.getColor(anchor.getContext(), R.color.color0_7_000000));
        arrowParams.topToBottom = -1;
        arrowParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        listParams.topToBottom = R.id.wco_arrow;
        listParams.topToTop = -1;
    }

    private void arrowDown(View anchor, ConstraintLayout.LayoutParams arrowParams, ConstraintLayout.LayoutParams listParams) {
        mArrow.update(TriangleView.BOTTOM, ContextCompat.getColor(anchor.getContext(), R.color.color0_7_000000));
        arrowParams.topToTop = -1;
        arrowParams.topToBottom = R.id.wco_list;
        listParams.topToTop = ConstraintLayout.LayoutParams.PARENT_ID;
        listParams.topToBottom = -1;
    }

    class OptionsAdapter extends BaseQuickAdapter<OptionsBean, BaseViewHolder> {
        private int mGravity;

        OptionsAdapter() {
            super(R.layout.item_context_options);
        }

        void setGravity(int gravity) {
            mGravity = gravity;
        }

        @Override
        protected void convert(BaseViewHolder helper, OptionsBean item) {
            if (mGravity != 0) {
                int width = getRecyclerView().getWidth();
                if (width != 0) {
                    LinearLayout itemView = (LinearLayout) helper.itemView;
                    ViewGroup.LayoutParams layoutParams = itemView.getLayoutParams();
                    layoutParams.width = width;
                    itemView.setGravity(mGravity);
                    itemView.setLayoutParams(layoutParams);
                }
            }
            helper.setImageResource(R.id.ico_icon, item.getIconRes())
                    .setText(R.id.ico_label, item.getLabel())
                    .setGone(R.id.ico_icon, item.getIconRes() != 0);
        }
    }
}
