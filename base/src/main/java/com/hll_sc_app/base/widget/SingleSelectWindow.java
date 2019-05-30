package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.base.R;
import com.hll_sc_app.citymall.util.CommonUitls;
import com.hll_sc_app.citymall.util.PinyinUtils;
import com.hll_sc_app.citymall.util.ViewUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * 单选通用选择窗口
 *
 * @author 胡永城
 * @date 2017/8/29
 */
public class SingleSelectWindow<T> extends BasePopupWindow {

    private View mRootView;
    private SearchView mSearchBar;
    private OnSingleSelectListener<T> mOnSingleSelectListener;
    private List<T> mData;
    private ContentWarpper<T> mWrapper;
    private SingleAdapter mAdapter;
    private SearchTask<T> mSearchTask;
    private T mSelected;
    private String type;

    public SingleSelectWindow(Activity activity, List<T> data, SingleSelectWindow.ContentWarpper<T> wrapper) {
        super(activity);
        this.mData = data;
        this.mWrapper = wrapper;
        initWindow(getMaxWidth());
        initView();
    }

    private void initWindow(int width) {
        mRootView = View.inflate(mActivity, R.layout.base_window_single_select, null);
        this.setContentView(mRootView);
        this.setWidth(width);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0);
        this.setBackgroundDrawable(dw);
    }

    private int getMaxWidth() {
        TextView tv = (TextView) View.inflate(mActivity, R.layout.base_item_single_select, null).findViewById(R.id.txt_select_name);
        int maxWidth = 0;
        int temp;
        if (!CommonUitls.isEmpty(mData)) {
            for (T t : mData) {
                if (maxWidth < (temp = (int) tv.getPaint().measureText(mWrapper.getName(t)))) {
                    maxWidth = temp;
                }
            }
        }
        int buf = 64;
        if (maxWidth > ViewUtils.dip2px(mActivity, 200F) + buf) {
            maxWidth = ViewUtils.dip2px(mActivity, 200F);
        } else if (maxWidth + buf < ViewUtils.dip2px(mActivity, 80F)) {
            maxWidth = ViewUtils.dip2px(mActivity, 80F);
        } else {
            maxWidth += buf;
        }
        return maxWidth;
    }

    private void initView() {
        RecyclerView recyclerView = (RecyclerView) mRootView.findViewById(R.id.list_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mAdapter = new SingleAdapter(mData);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                dismiss();
                if (mOnSingleSelectListener != null) {
                    mAdapter.notifyDataSetChanged();
                    mOnSingleSelectListener.onSelected(mAdapter.getItem(position));
                }
            }
        });
        mSearchBar = (SearchView) mRootView.findViewById(R.id.search_bar);
        mSearchBar.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // no-op
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!SingleSelectWindow.this.isShowing()) {
                    return;
                }
                if (TextUtils.isEmpty(s)) {
                    update(mData);
                } else {
                    if (mSearchTask != null) {
                        mSearchTask.cancel(true);
                    }
                    mSearchTask = new SearchTask<T>(SingleSelectWindow.this, mData, mWrapper);
                    mSearchTask.execute(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mRootView.findViewById(R.id.search_parent).setVisibility((mData == null || mData.size() < 9) ? View.GONE : View.VISIBLE);
    }

    private void update(List<T> data) {
        mAdapter.setNewData(data);
    }

    public SingleSelectWindow(Activity activity, List<T> data, int width, SingleSelectWindow.ContentWarpper<T> wrapper) {
        super(activity);
        this.mData = data;
        this.mWrapper = wrapper;
        initWindow(width);
        initView();
        update(data);

    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setOnSingleSelectListener(OnSingleSelectListener<T> onSingleSelectListener) {
        this.mOnSingleSelectListener = onSingleSelectListener;
    }

    public void setSelected(T selected) {
        this.mSelected = selected;
    }

    public interface OnSingleSelectListener<T> {

        void onSelected(T t);

    }

    public interface ContentWarpper<T> {
        String getName(T item);
    }

    /**
     * 检索品项异步任务
     */
    private static class SearchTask<E> extends AsyncTask<String, Integer, List<E>> {
        private WeakReference<SingleSelectWindow> mWeakView;
        private WeakReference<List<E>> mWeakList;
        private WeakReference<ContentWarpper<E>> mWrapper;

        SearchTask(SingleSelectWindow window, List<E> data, ContentWarpper<E> wrapper) {
            this.mWeakView = new WeakReference<>(window);
            this.mWeakList = new WeakReference<>(data);
            this.mWrapper = new WeakReference<>(wrapper);
        }

        @Override
        protected List<E> doInBackground(String... strings) {
            List<E> temp = new ArrayList<>();
            if (CommonUitls.isEmpty(mWeakList.get())) {
                return temp;
            }
            for (E e : mWeakList.get()) {
                if (isCancelled()) {
                    break;
                }
                if (PinyinUtils.find(mWrapper.get().getName(e), strings[0])) {
                    temp.add(e);
                }
            }
            return temp;
        }

        @Override
        protected void onPostExecute(List<E> data) {
            if (!isCancelled() && mWeakView.get().isShowing()) {
                mWeakView.get().update(data);
            }
        }
    }

    private class SingleAdapter extends BaseQuickAdapter<T, BaseViewHolder> {
        SingleAdapter(@Nullable List<T> data) {
            super(R.layout.base_item_single_select, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            TextView view = helper.getView(R.id.txt_select_name);
            if (helper.getLayoutPosition() == 0) {
                view.setBackgroundResource(R.drawable.base_color_tab_bg_top);
            } else if (helper.getLayoutPosition() == getItemCount() - 1) {
                view.setBackgroundResource(R.drawable.base_color_tab_bg_bottom);
            } else {
                view.setBackgroundResource(R.drawable.base_color_tab_bg_normal);
            }
            view.setText(mWrapper.getName(item));
            view.setSelected(item.equals(mSelected));
        }
    }
}
