package com.hll_sc_app.app.user.register;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BaseShadowPopupWindow;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 注册-不同资料-选择经营品类弹窗
 *
 * @author 朱英松
 * @date 2019/6/6
 */
class RegisterCategoryWindow extends BaseShadowPopupWindow {
    @BindView(R.id.flowLayout)
    TagFlowLayout mFlowLayout;

    RegisterCategoryWindow(Activity context) {
        super(context);
        View view = View.inflate(context, R.layout.window_register_category, null);
        ButterKnife.bind(this, view);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(UIUtils.dip2px(280));
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("酒类" + i);
        }
        FlowAdapter adapter = new FlowAdapter(mActivity, list);
        mFlowLayout.setAdapter(adapter);
    }

    /**
     * 适配器
     */
    private static class FlowAdapter extends TagAdapter<String> {
        private LayoutInflater mInflater;

        FlowAdapter(Context context, List<String> list) {
            super(list);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(FlowLayout flowLayout, int position, String s) {
            TextView tv = (TextView) mInflater.inflate(R.layout.view_item_purchase_filter, flowLayout, false);
            tv.setText(s);
            return tv;
        }

        @Override
        public boolean setSelected(int position, String s) {
            return super.setSelected(position, s);
        }
    }
}
