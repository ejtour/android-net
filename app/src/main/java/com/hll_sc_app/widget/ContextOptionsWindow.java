package com.hll_sc_app.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
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
    private OptionsAdapter mAdapter;
    @BindView(R.id.triangle_arrow)
    TriangleView triangleView;
    @BindView(R.id.select_dialog)
    LinearLayout linearLayout;

    public ContextOptionsWindow(Activity context) {
        super(context);
        initWindow(context);
        initView();
    }

    private void initView() {
        mAdapter = new OptionsAdapter();
        mListView.setAdapter(mAdapter);
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

    public ContextOptionsWindow setListener(BaseQuickAdapter.OnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
        return this;
    }

    public void setTriangleViewStyle(int left,int top,int right,int bottom ){
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        LinearLayout.LayoutParams layoutParams = triangleView.getLayoutParams();
        layoutParams.setMargins(left,top,right,bottom);
        triangleView.setLayoutParams(layoutParams);
        if(left>0) {
            linearLayout.setGravity(Gravity.LEFT);
        }else if(right>0){
            linearLayout.setGravity(Gravity.RIGHT);
        }
    }

    class OptionsAdapter extends BaseQuickAdapter<OptionsBean, BaseViewHolder> {

        OptionsAdapter() {
            super(R.layout.item_context_options);
        }

        @Override
        protected void convert(BaseViewHolder helper, OptionsBean item) {
            helper.setImageResource(R.id.ico_icon, item.getIconRes())
                    .setText(R.id.ico_label, item.getLabel());
        }
    }
}
