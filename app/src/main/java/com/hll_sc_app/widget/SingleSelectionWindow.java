package com.hll_sc_app.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BasePopupWindow;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/15
 */

public class SingleSelectionWindow<T> extends BasePopupWindow implements View.OnClickListener {
    @BindView(R.id.wss_list_view)
    RecyclerView mListView;
    private T mSelect;
    private List<T> mList;
    private ListAdapter mAdapter;
    private OnSelectListener<T> mSelectListener;
    private WrapperName<T> mWrapperName;
    private OnClickWindow mClickListener;
    private int mGravity = Gravity.CENTER_VERTICAL;

    public SingleSelectionWindow(Activity context, WrapperName<T> wrapperName) {
        super(context);
        View rootView = View.inflate(mActivity, R.layout.window_single_selection, null);
        ButterKnife.bind(this, rootView);
        mWrapperName = wrapperName;
        rootView.setOnClickListener(this);
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xbb000000);
        this.setBackgroundDrawable(dw);
        mAdapter = new ListAdapter(mList);
        mListView.setAdapter(mAdapter);
        mListView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(context, R.color.color_eeeeee), UIUtils.dip2px(1)));
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            dismiss();
            if (mSelectListener != null) {
                mSelect = mAdapter.getItem(position);
                mAdapter.notifyDataSetChanged();
                mSelectListener.onSelectItem(mSelect);
            }
        });
    }

    /**
     * 设置选中的item
     *
     * @param select 选中的item
     */
    public void setSelect(T select) {
        this.mSelect = select;
    }

    /**
     * 清空选中的item，选中第一个
     */
    public void clearSelect() {
        if (mAdapter != null) {
            this.mSelect = mAdapter.getItem(0);
            mAdapter.notifyDataSetChanged();
        }
    }

    public void fixedHeight(int height) {
        mListView.getLayoutParams().height = height;
    }

    public void hideDivider() {
        mListView.removeItemDecorationAt(0);
    }

    /**
     * 刷新列表
     *
     * @param list 数据源
     */
    public void refreshList(List<T> list) {
        this.mList = list;
        mAdapter.setNewData(list);
    }

    /**
     * 设置选中监听器
     *
     * @param selectListener 选中监听—
     */
    public void setSelectListener(OnSelectListener<T> selectListener) {
        this.mSelectListener = selectListener;
    }

    public void setTextGravity(int gravity) {
        mGravity = gravity;
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        if (this.mClickListener != null) {
            this.mClickListener.onClick(view, this);
        } else {
            dismiss();
        }
    }

    public void addClickListener(OnClickWindow listener) {
        this.mClickListener = listener;
    }

    public interface OnClickWindow {
        void onClick(View view, SingleSelectionWindow window);
    }

    /**
     * 选中回调接口
     *
     * @param <T>
     */
    public interface OnSelectListener<T> {
        /**
         * 选中item
         *
         * @param t item
         */
        void onSelectItem(T t);
    }

    /**
     * 包装接口
     */
    public interface WrapperName<T> {
        /**
         * 要显示的字符串
         *
         * @param t 对象
         * @return 要显示的字符串
         */
        String getName(T t);
    }

    /**
     * 列表适配器
     */
    private class ListAdapter extends BaseQuickAdapter<T, BaseViewHolder> {
        ListAdapter(@Nullable List<T> data) {
            super(R.layout.item_single_selection, data);
        }

        @Override
        protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder helper = super.onCreateDefViewHolder(parent, viewType);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) helper.getView(R.id.iss_label).getLayoutParams();
            params.gravity = mGravity;
            return helper;
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            TextView txtType = helper.getView(R.id.iss_label);
            if (mWrapperName != null) {
                txtType.setText(mWrapperName.getName(item));
            } else if (item instanceof String) {
                txtType.setText((String) item);
            }
            helper.setGone(R.id.iss_check, mSelect == item);
            txtType.setSelected(mSelect == item);
        }
    }
}