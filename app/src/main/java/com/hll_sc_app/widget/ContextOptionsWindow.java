package com.hll_sc_app.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.window.OptionsBean;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/10
 */

public class ContextOptionsWindow extends BasePopupWindow implements View.OnClickListener {
    @BindView(R.id.wco_list)
    LinearLayout mListView;
    @BindView(R.id.wco_arrow)
    TriangleView mArrow;
    private OptionsAdapter mAdapter;
    private int mGravity = Gravity.CENTER_VERTICAL;
    private int mItemHeight;
    private int mLeftPadding, mRightPadding;
    private int mTopPadding, mBottomPadding;

    public ContextOptionsWindow(Activity context) {
        super(context);
        initWindow(context);
        initView();
        mItemHeight = UIUtils.dip2px(48);
    }

    private void initView() {
        mAdapter = new OptionsAdapter();
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
        mListView.removeAllViews();
        if (!CommonUtils.isEmpty(list)) {
            for (int i = 0; i < list.size(); i++) {
                OptionsBean bean = list.get(i);
                mListView.addView(createItemView(bean.getIconRes(), bean.getLabel(), i));
            }
        }
        return this;
    }

    private View createItemView(int icon, String label, int position) {
        LinearLayout layout = new LinearLayout(mActivity);
        layout.setOnClickListener(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mItemHeight);
        layout.setGravity(mGravity);
        layout.setLayoutParams(params);
        layout.setPadding(UIUtils.dip2px(10) + mLeftPadding, mTopPadding, UIUtils.dip2px(10) + mRightPadding, mBottomPadding);
        if (icon != 0) {
            ImageView imageView = new ImageView(mActivity);
            params = new LinearLayout.LayoutParams(UIUtils.dip2px(12), UIUtils.dip2px(12));
            params.rightMargin = UIUtils.dip2px(10);
            imageView.setLayoutParams(params);
            imageView.setImageResource(icon);
            layout.addView(imageView);
        }
        TextView textView = new TextView(mActivity);
        TextViewCompat.setTextAppearance(textView, R.style.TextAppearance_City22_Small_White);
        textView.setText(label);
        params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setLayoutParams(params);
        layout.addView(textView);
        layout.setTag(position);
        return layout;
    }

    public ContextOptionsWindow setItemGravity(int gravity) {
        mGravity = gravity;
        if (mListView.getChildCount() != 0) {
            for (int i = 0; i < mListView.getChildCount(); i++) {
                ((LinearLayout) mListView.getChildAt(i)).setGravity(mGravity);
            }
        }
        return this;
    }

    public ContextOptionsWindow setListener(BaseQuickAdapter.OnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
        return this;
    }

    public ContextOptionsWindow setListPadding(int left, int top, int right, int bottom) {
        mLeftPadding = left;
        mRightPadding = right;
        mTopPadding = top;
        mBottomPadding = bottom;
        if (mListView.getChildCount() != 0) {
            for (int i = 0; i < mListView.getChildCount(); i++) {
                mListView.getChildAt(i).setPadding(UIUtils.dip2px(10) + left, top, UIUtils.dip2px(10) + right, bottom);
            }
        }
        return this;
    }

    public ContextOptionsWindow setItemHeight(int itemHeight) {
        mItemHeight = itemHeight;
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

    static class OptionsAdapter extends BaseQuickAdapter<OptionsBean, BaseViewHolder> {
        OptionsAdapter() {
            super(0);
        }

        @Override
        protected void convert(BaseViewHolder helper, OptionsBean item) {
            // no-op
        }
    }
}
