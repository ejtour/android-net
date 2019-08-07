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
import com.hll_sc_app.bean.user.CategoryItem;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 注册-完善资料-选择经营品类弹窗
 *
 * @author 朱英松
 * @date 2019/6/6
 */
public class RegisterCategoryWindow extends BaseShadowPopupWindow {
    @BindView(R.id.flowLayout)
    TagFlowLayout mFlowLayout;
    private FlowAdapter mAdapter;
    private ItemSelectListener mListener;

    public RegisterCategoryWindow(Activity context) {
        super(context);
        View view = View.inflate(context, R.layout.window_register_category, null);
        ButterKnife.bind(this, view);
        this.setContentView(view);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(UIUtils.dip2px(420));
        this.setAnimationStyle(R.style.BasePopupAnimation);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));
    }

    public void setList(List<CategoryItem> list) {
        if (mFlowLayout != null) {
            mAdapter = new FlowAdapter(mActivity, list);
            mFlowLayout.setAdapter(mAdapter);
        }
    }

    public List<CategoryItem> getSelectList() {
        List<CategoryItem> list = new ArrayList<>();
        if (mFlowLayout != null && mAdapter != null) {
            Set<Integer> set = mFlowLayout.getSelectedList();
            for (Integer integer : set) {
                list.add(mAdapter.getItem(integer));
            }
        }
        return list;
    }


    public void setSelectList(List<String> selectList) {
        if (mFlowLayout != null) {
            mAdapter.setSelectedList(selectList);
        }
    }

    void setListener(ItemSelectListener listener) {
        this.mListener = listener;
    }

    @OnClick({R.id.img_close, R.id.txt_confirm})
    public void onViewClicked(View view) {
        if (view.getId() == R.id.txt_confirm && mListener != null) {
            mListener.confirm();
        }
        dismiss();
    }

    public interface ItemSelectListener {
        /**
         * 确定
         */
        void confirm();
    }

    private static class FlowAdapter extends TagAdapter<CategoryItem> {
        private LayoutInflater mInflater;
        private List<CategoryItem> mList;

        FlowAdapter(Context context, List<CategoryItem> list) {
            super(list);
            mList = list;
            mInflater = LayoutInflater.from(context);
        }

        void setSelectedList(List<String> selectList) {
            if (CommonUtils.isEmpty(mList) || CommonUtils.isEmpty(selectList)) {
                return;
            }
            Map<String, Integer> map = new HashMap<>();
            for (int i = 0, size = mList.size(); i < size; i++) {
                map.put(mList.get(i).getCategoryID(), i);
            }

            Set<Integer> set = new HashSet<>();
            for (String s : selectList) {
                if (map.containsKey(s)) {
                    set.add(map.get(s));
                }
            }
            setSelectedList(set);
        }

        @Override
        public View getView(FlowLayout flowLayout, int position, CategoryItem s) {
            TextView tv = (TextView) mInflater.inflate(R.layout.view_item_purchase_filter, flowLayout, false);
            tv.setText(s.getCategoryName());
            return tv;
        }
    }
}
