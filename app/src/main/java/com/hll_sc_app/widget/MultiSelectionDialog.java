package com.hll_sc_app.widget;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.dialog.BaseDialog;
import com.hll_sc_app.base.utils.UIUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MultiSelectionDialog<T> extends BaseDialog {
    private final WrapperName<T> mWrapperName;
    @BindView(R.id.dss_title)
    TextView mTitle;
    @BindView(R.id.dss_confirm)
    TextView mConfirm;
    @BindView(R.id.dss_list_view)
    RecyclerView mListView;
    private MultiSelectionAdapter mAdapter;
    private OnSelectListener<T> mListener;

    private MultiSelectionDialog(@NonNull Activity context, WrapperName<T> wrapperName) {
        super(context);
        mWrapperName = wrapperName;
    }

    public static <T> Builder<T> newBuilder(@NonNull Activity context, WrapperName<T> wrapperName) {
        return new Builder<T>(context, wrapperName);
    }

    @Override
    public View onCreateView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.dialog_single_selection, null);
        ButterKnife.bind(this, view);
        mListView.addItemDecoration(new SimpleDecoration(ContextCompat.getColor(getContext(), R.color.color_eeeeee), UIUtils.dip2px(1)));
        mAdapter = new MultiSelectionAdapter();
        mAdapter.bindToRecyclerView(mListView);
        mConfirm.setVisibility(View.VISIBLE);
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

    private void select(List<T> items) {
        mAdapter.setSelected(items);
    }

    private void selectByKey(List<String> keys) {
        mAdapter.setSelectedByKey(keys);
    }


    @OnClick(R.id.dss_close)
    public void onViewClicked() {
        dismiss();
    }

    @OnClick(R.id.dss_confirm)
    void onConfirm() {
        if (mListener != null) {
            dismiss();
            mListener.onSelectItem(mAdapter.getSelected());
        }
    }

    public interface OnSelectListener<T> {
        void onSelectItem(List<T> ts);
    }

    public interface WrapperName<T> {
        String getName(T t);

        String getKey(T t);
    }


    public static class Builder<T> {
        private MultiSelectionDialog<T> mDialog;

        Builder(Activity context, WrapperName<T> wrapperName) {
            mDialog = new MultiSelectionDialog<T>(context, wrapperName);
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

        public Builder<T> select(List<T> t) {
            mDialog.select(t);
            return this;
        }

        public Builder<T> selectByKey(List<String> t) {
            mDialog.selectByKey(t);
            return this;
        }

        public MultiSelectionDialog<T> create() {
            return mDialog;
        }
    }

    class MultiSelectionAdapter extends BaseQuickAdapter<T, BaseViewHolder> {

        private List<T> mT = new ArrayList<>();

        private MultiSelectionAdapter() {
            super(R.layout.item_single_selection);
            setOnItemClickListener((adapter, view, position) -> {
                T item = getItem(position);
                int index = mT.indexOf(item);
                if (index > -1) {
                    mT.remove(index);
                } else {
                    mT.add(item);
                }
                notifyDataSetChanged();
            });
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            TextView label = helper.getView(R.id.iss_label);
            label.setText(mWrapperName.getName(item));
            label.setSelected(mT.contains(item));
            helper.setGone(R.id.iss_check, mT.contains(item));
        }

        public List<T> getSelected() {
            return mT;
        }

        private void setSelected(List<T> data) {
            mT = data;
            notifyDataSetChanged();
        }

        private void setSelectedByKey(List<String> keys) {
            List<T> allData = getData();
            mT.clear();
            for (int i = 0; i < allData.size(); i++) {
                if (keys.contains(mWrapperName.getKey(getItem(i)))) {
                    mT.add(getItem(i));
                }
            }
            notifyDataSetChanged();
        }
    }
}
