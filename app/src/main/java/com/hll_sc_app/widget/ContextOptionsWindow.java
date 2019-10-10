package com.hll_sc_app.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.IntDef;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.window.OptionsBean;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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
    private OptionsAdapter mAdapter;
    @BindView(R.id.wco_arrow)
    TriangleView mArrow;
    @BindView(R.id.wco_top_list)
    RecyclerView mTopListView;

    private int type = TYPE.top;
    public ContextOptionsWindow(Activity context) {
        super(context);
        initWindow(context);
        initView();
    }

    public ContextOptionsWindow(Activity context, @TYPE int type) {
        super(context);
        this.type = type;
        initWindow(context);
        initView();
    }

    private void initView() {
        mAdapter = new OptionsAdapter();
        if (type == TYPE.top) {
            mListView.setVisibility(View.VISIBLE);
            mListView.setAdapter(mAdapter);
        } else {
            mTopListView.setVisibility(View.VISIBLE);
            mTopListView.setAdapter(mAdapter);
            mArrow.update(1, Color.parseColor("#b3000000"));
        }
    }

    private void initWindow(Activity context) {
        View rootView = View.inflate(context, R.layout.window_context_options, null);
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable());
    }

    public ContextOptionsWindow refreshList(List<OptionsBean> list) {
        mAdapter.setNewData(list);
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
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            super.showAsDropDown(anchor, xOff, yOff, gravity);
        } else {
            super.showAsDropDown(anchor);
        }
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mArrow.getLayoutParams();
        params.gravity = gravity;

        LinearLayout.LayoutParams params2 = (LinearLayout.LayoutParams) mListView.getLayoutParams();
        params2.gravity = gravity;

        LinearLayout.LayoutParams params3 = (LinearLayout.LayoutParams) mTopListView.getLayoutParams();
        params3.gravity = gravity;
    }

    class OptionsAdapter extends BaseQuickAdapter<OptionsBean, BaseViewHolder> {

        OptionsAdapter() {
            super(R.layout.item_context_options);
        }

        @Override
        protected void convert(BaseViewHolder helper, OptionsBean item) {
            helper.setGone(R.id.ico_icon, item.getIconRes() != 0);
            helper.setImageResource(R.id.ico_icon, item.getIconRes())
                    .setText(R.id.ico_label, item.getLabel());
        }
    }

    @IntDef({TYPE.bottom, TYPE.top})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {
        int top = 1;
        int bottom = 2;
    }
}
