package com.hll_sc_app.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.citymall.util.CommonUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/1/14
 */

public class SearchSelectionWindow<T> extends BasePopupWindow {
    @BindView(R.id.wss_list_view)
    RecyclerView mListView;
    @BindView(R.id.wss_refresh_layout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.wss_search_label)
    TextView mSearchLabel;
    @BindView(R.id.wss_search_edit)
    EditText mSearchEdit;
    @BindView(R.id.wss_clear_search)
    ImageView mClearSearch;
    private T mSelect;
    private SingleSelectionWindow.WrapperName<T> mWrapperName;
    private ListAdapter mAdapter;
    private SearchSelectionCallback<T> mCallback;

    public SearchSelectionWindow(Activity activity, SingleSelectionWindow.WrapperName<T> wrapperName) {
        super(activity);
        View rootView = View.inflate(mActivity, R.layout.window_search_selection, null);
        ButterKnife.bind(this, rootView);
        mWrapperName = wrapperName;
        rootView.setOnClickListener(v -> dismiss());
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0xbb000000);
        this.setBackgroundDrawable(dw);
        mAdapter = new ListAdapter();
        mListView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            dismiss();
            if (mCallback != null) {
                mSelect = mAdapter.getItem(position);
                mAdapter.notifyDataSetChanged();
                mCallback.select(mSelect);
            }
        });
        mRefreshLayout.setEnableLoadMore(false);
        mRefreshLayout.setEnableRefresh(false);
    }

    @OnClick({R.id.wss_clear_search, R.id.wss_search_btn})
    public void toSearch() {
        if (mCallback != null) {
            mCallback.search();
        }
    }

    @OnTextChanged(R.id.wss_search_edit)
    public void onTextChanged(CharSequence s) {
        mClearSearch.setVisibility(s.length() == 0 ? View.GONE : View.VISIBLE);
    }

    @OnEditorAction(R.id.wss_search_edit)
    public boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) toSearch();
        return true;
    }

    /**
     * 设置选中的item
     *
     * @param select 选中的item
     */
    public SearchSelectionWindow<T> setSelect(T select) {
        this.mSelect = select;
        return this;
    }

    public SearchSelectionWindow<T> refreshList(List<T> list) {
        mAdapter.setNewData(list);
        return this;
    }

    public SearchSelectionWindow<T> setSearchLabel(String label) {
        mSearchLabel.setText(label);
        return this;
    }

    public void addList(List<T> list) {
        if (!CommonUtils.isEmpty(list)) {
            mAdapter.addData(list);
        }
    }

    public String getSearchWords() {
        return mSearchEdit.getText().toString().trim();
    }

    public SearchSelectionWindow<T> setCallback(SearchSelectionCallback<T> callback) {
        this.mCallback = callback;
        return this;
    }

    public SearchSelectionWindow<T> setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener loadMoreListener) {
        mRefreshLayout.setOnRefreshLoadMoreListener(loadMoreListener);
        return this;
    }

    public SearchSelectionWindow<T> setEnableLoadMore(boolean loadMore) {
        mRefreshLayout.setEnableLoadMore(loadMore);
        return this;
    }

    public SearchSelectionWindow<T> setEnableRefresh(boolean refresh) {
        mRefreshLayout.setEnableRefresh(refresh);
        return this;
    }

    public void closeHeaderOrFooter() {
        mRefreshLayout.closeHeaderOrFooter();
    }

    public interface SearchSelectionCallback<T> {
        void search();

        void select(T t);
    }

    private class ListAdapter extends BaseQuickAdapter<T, BaseViewHolder> {

        ListAdapter() {
            super(R.layout.item_window_purchaser_select);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            TextView textView = helper.getView(R.id.txt_purchaserName);
            textView.setText(mWrapperName.getName(item));
            textView.setSelected(mSelect == item);
        }
    }
}
