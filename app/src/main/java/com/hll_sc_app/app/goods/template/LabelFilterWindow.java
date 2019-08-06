package com.hll_sc_app.app.goods.template;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.goods.LabelBean;
import com.hll_sc_app.utils.Tuple;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 从商品库导入-行业标签过滤
 *
 * @author 朱英松
 * @date 2019/6/28
 */
class LabelFilterWindow extends BasePopupWindow {
    @BindView(R.id.flowLayout)
    TagFlowLayout mFlowLayout;
    @BindView(R.id.txt_reset)
    TextView mTxtReset;
    @BindView(R.id.txt_confirm)
    TextView mTxtConfirm;
    @BindView(R.id.ll_content)
    LinearLayout mLlContent;
    private FlowAdapter mAdapter;
    private SelectConfirmListener mListener;

    LabelFilterWindow(Activity context) {
        super(context);
        View view = View.inflate(context, R.layout.window_template_label_filter, null);
        view.setOnClickListener(v -> dismiss());
        ButterKnife.bind(this, view);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xbb000000));
    }

    public void setList(List<LabelBean> list) {
        if (mFlowLayout != null) {
            mAdapter = new FlowAdapter(mActivity, list);
            mFlowLayout.setAdapter(mAdapter);
        }
    }

    Tuple<List<String>, List<String>> getSelectList() {
        Tuple<List<String>, List<String>> tuple = new Tuple<>();
        List<String> listId = new ArrayList<>();
        List<String> listName = new ArrayList<>();
        if (mFlowLayout != null && mAdapter != null) {
            Set<Integer> set = mFlowLayout.getSelectedList();
            for (Integer integer : set) {
                listName.add(mAdapter.getItem(integer).getLabelName());
                listId.add(mAdapter.getItem(integer).getLabelID());
            }
        }
        tuple.setKey1(listName);
        tuple.setKey2(listId);
        return tuple;
    }

    void setListener(SelectConfirmListener listener) {
        this.mListener = listener;
    }

    @OnClick({R.id.txt_reset, R.id.txt_confirm})
    public void onViewClicked(View view) {
        int id = view.getId();
        if (id == R.id.txt_reset) {
            mAdapter.setSelectedList(new HashSet<>());
            mAdapter.notifyDataChanged();
        } else if (view.getId() == R.id.txt_confirm && mListener != null) {
            mListener.confirm();
            dismiss();
        }
    }

    interface SelectConfirmListener {
        /**
         * 确定
         */
        void confirm();
    }

    private static class FlowAdapter extends TagAdapter<LabelBean> {
        private LayoutInflater mInflater;

        FlowAdapter(Context context, List<LabelBean> list) {
            super(list);
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public View getView(FlowLayout flowLayout, int position, LabelBean s) {
            TextView tv = (TextView) mInflater.inflate(R.layout.view_item_label_filter, flowLayout, false);
            tv.setText(s.getLabelName());
            return tv;
        }
    }
}
