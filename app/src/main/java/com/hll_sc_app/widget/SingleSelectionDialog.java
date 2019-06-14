package com.hll_sc_app.widget;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/14
 */

public class SingleSelectionDialog<T> extends BaseDialog {
    @BindView(R.id.dss_title)
    TextView mTitle;
    @BindView(R.id.dss_list_view)
    RecyclerView mListView;
    private final WrapperName<T> mWrapperName;
    private SingleSelectionAdapter mAdapter;
    private OnSelectListener<T> mListener;

    private SingleSelectionDialog(@NonNull Activity context, WrapperName<T> wrapperName) {
        super(context);
        mWrapperName = wrapperName;
    }

    public static <T> Builder<T> newBuilder(@NonNull Activity context, WrapperName<T> wrapperName) {
        return new Builder<T>(context, wrapperName);
    }

    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_single_selection, null);
        ButterKnife.bind(this, view);
        mListView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(getContext(), R.color.color_eeeeee), UIUtils.dip2px(1)));
        mAdapter = new SingleSelectionAdapter();
        mListView.setAdapter(mAdapter);
        return view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        if (window != null) {
            window.setWindowAnimations(R.style.BasePopupAnimation);
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = WindowManager.LayoutParams.MATCH_PARENT;
            attributes.height = UIUtils.dip2px(330);
            attributes.gravity = Gravity.BOTTOM;
            window.setAttributes(attributes);
            window.setBackgroundDrawableResource(android.R.color.white);
        }
    }

    private void refreshList(List<T> list) {
        mAdapter.setNewData(list);
    }

    private void setTitleText(CharSequence text) {
        mTitle.setText(text);
    }

    private void setOnSelectListener(OnSelectListener<T> listener) {
        mListener = listener;
    }

    private void select(T t) {
        mAdapter.select(t);
    }

    public interface OnSelectListener<T> {
        void onSelectItem(T t);
    }

    public interface WrapperName<T> {
        String getName(T t);
    }

    @OnClick(R.id.dss_close)
    public void onViewClicked() {
        dismiss();
    }

    class SingleSelectionAdapter extends BaseQuickAdapter<T, BaseViewHolder> {

        private T mT;

        private void select(T t) {
            mT = t;
        }

        private SingleSelectionAdapter() {
            super(R.layout.item_single_selection);
            setOnItemClickListener((adapter, view, position) -> {
                dismiss();
                if (mListener == null) {
                    return;
                }
                mT = getItem(position);
                mListener.onSelectItem(mT);
                notifyDataSetChanged();
            });
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            TextView label = helper.getView(R.id.iss_label);
            label.setText(mWrapperName.getName(item));
            label.setSelected(mT == item);
            helper.setGone(R.id.iss_check, mT == item);
        }
    }

    public static class Builder<T> {
        private SingleSelectionDialog<T> mDialog;

        Builder(Activity context, WrapperName<T> wrapperName) {
            mDialog = new SingleSelectionDialog<T>(context, wrapperName);
        }

        public Builder<T> refreshList(List<T> list) {
            mDialog.refreshList(list);
            return this;
        }

        public Builder<T> setTitleText(CharSequence text) {
            mDialog.setTitleText(text);
            return this;
        }

        public Builder<T> setOnSelectListener(OnSelectListener<T> listener) {
            mDialog.setOnSelectListener(listener);
            return this;
        }

        public Builder<T> select(T t) {
            mDialog.select(t);
            return this;
        }

        public SingleSelectionDialog create() {
            return mDialog;
        }
    }
}
