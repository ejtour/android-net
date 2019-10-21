package com.hll_sc_app.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UIUtils;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.base.widget.ClearEditText;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 多选或单选的window
 * 供应商弹窗
 * 数据类型写泛型 是想这种多选交互页面可以复用
 * 支持翻页请求
 * todo 翻页那种 helper.getLayoutPosition()是否会是正确的值 待测试-zc
 *
 * @author zc
 */
public class MutipleSelecteWindow<T> extends BasePopupWindow {
    private RecyclerView mRecyclerView;
    private WindowAdapter mAdapter;
    private List<T> mData;
    private List<Integer> selectedIndexs = new ArrayList<>();
    private TextView mReset;
    private TextView mConfirm;
    private SmartRefreshLayout mRefreshLayout;
    private LinearLayout mLLSearch;
    private ClearEditText mEdtSearch;
    private TextView mTxtSearch;
    private Config<T> mConfig;
    private Activity mActivity;

    public MutipleSelecteWindow(Activity activity, List<T> data, Config<T> uiConfig) {
        super(activity);
        mActivity = activity;
        mData = data;
        mConfig = uiConfig;
        ViewGroup contentView = (ViewGroup) View.inflate(mActivity, R.layout.window_multiple_select, null);
        contentView.setOnClickListener(v -> dismiss());
        this.setContentView(contentView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        ColorDrawable dw = new ColorDrawable(0xbb000000);
        this.setBackgroundDrawable(dw);
        this.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        mRefreshLayout = contentView.findViewById(R.id.refresh_layout);
        mRecyclerView = contentView.findViewById(R.id.supplyer_list);
        mReset = contentView.findViewById(R.id.reset);
        mConfirm = contentView.findViewById(R.id.confirm);
        mLLSearch = contentView.findViewById(R.id.ll_search);
        mEdtSearch = contentView.findViewById(R.id.edt_search);
        mTxtSearch = contentView.findViewById(R.id.txt_search);

        if (!mConfig.enableMultiple()) {
            contentView.findViewById(R.id.option_layout).setVisibility(View.GONE);
        }
        initRecyclerView();
        bindButtonEvent();
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        mAdapter = new WindowAdapter(mData);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            onItemClickEvent(adapter, position);
        });
        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            onItemClickEvent(adapter, position);
        });
        mLLSearch.setVisibility(mConfig.isHasSearch() ? View.VISIBLE : View.GONE);
        mEdtSearch.setHint(mConfig.searchHint());
    }

    private void bindButtonEvent() {
        mReset.setOnClickListener(v -> {
            selectedIndexs.removeAll(selectedIndexs);
            mAdapter.notifyDataSetChanged();
        });
        mConfirm.setOnClickListener(v -> {
            dismiss();
            mConfig.onConfirm(selectedIndexs);
        });

        if (mConfig.isHasSearch()) {
            mTxtSearch.setOnClickListener(v -> {
                mConfig.search(mEdtSearch.getText().toString());
            });
            mEdtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        mConfig.search(mEdtSearch.getText().toString());
                    }
                    return true;
                }
            });
        }
    }

    private void onItemClickEvent(BaseQuickAdapter adapter, int position) {
        //单选模式 每次点击前都清除已选的 不能多选
        if (!mConfig.enableMultiple()) {
            selectedIndexs.removeAll(selectedIndexs);
        }
        //全选的操作 点击了全部这个item
        if (mConfig.enableSelectAll() && position == 0) {
            int itemCount = adapter.getItemCount();
            if (selectedIndexs.size() != itemCount) {
                selectedIndexs.removeAll(selectedIndexs);
                for (int i = 0; i < adapter.getItemCount(); i++) {
                    selectedIndexs.add(i);
                }
            } else {
                selectedIndexs.removeAll(selectedIndexs);
            }
        } else {//不是全选操作
            if (selectedIndexs.contains(position)) {
                selectedIndexs.remove(Integer.valueOf(position));
                if (mConfig.enableSelectAll()) {
                    selectedIndexs.remove(Integer.valueOf(0));
                }
            } else {
                selectedIndexs.add(position);
                if (mConfig.enableSelectAll() && (selectedIndexs.size() == adapter.getItemCount() - 1)) {
                    selectedIndexs.add(Integer.valueOf(0));
                }
            }
        }
        mAdapter.notifyDataSetChanged();

        //单选 点击item后直接关闭了
        if (!mConfig.enableMultiple()) {
            dismiss();
            mConfig.onConfirm(selectedIndexs);
        }
    }

    public void setContentViewHeight(int height) {
        ViewGroup.LayoutParams layoutParams = mRecyclerView.getLayoutParams();
        if (height == ViewGroup.LayoutParams.WRAP_CONTENT || height == ViewGroup.LayoutParams.MATCH_PARENT) {
            layoutParams.height = height;
        } else {
            layoutParams.height = UIUtils.dip2px(height);
        }
        mRecyclerView.setLayoutParams(layoutParams);
    }

    public void addData(List<T> data) {
        mAdapter.addData(data);
    }

    public void setNewData(List<T> data) {
        mAdapter.setNewData(data);
    }

    public void setSelectedIndexs(List<Integer> selectedIndexs) {
        this.selectedIndexs = selectedIndexs;
    }

    public void addSelectedIndex(int index) {
        selectedIndexs.add(index);
    }

    public void closeHeaderOrFooter() {
        mRefreshLayout.closeHeaderOrFooter();
    }

    public void setEnableLoadMore(boolean isLoadMore) {
        mRefreshLayout.setEnableLoadMore(isLoadMore);
    }

    public void setEnableRefresh(boolean isRefresh) {
        mRefreshLayout.setEnableRefresh(isRefresh);
    }

    public void setOnRefreshLoadMoreListener(OnRefreshLoadMoreListener loadMoreListener) {
        mRefreshLayout.setOnRefreshLoadMoreListener(loadMoreListener);
    }

    @Override
    public void showAsDropDown(View anchor, int xOff, int yOff) {
        mConfig.showBefore();
        super.showAsDropDown(anchor, xOff, yOff);
    }

    @Override
    public void showAtLocation(View parent, int gravity, int x, int y) {
        mConfig.showBefore();
        super.showAtLocation(parent, gravity, x, y);
    }

    @Override
    public void dismiss() {
        mConfig.dismissBefore();
        super.dismiss();
    }

    public List<T> getAllData() {
        return mAdapter.getData();
    }

    public interface Config<T> {
        /**
         * 是否显示全部
         *
         * @return
         */
        boolean enableSelectAll();

        /**
         * 是否多选
         *
         * @return
         */
        boolean enableMultiple();

        /**
         * 显示名称的方法
         *
         * @param item
         * @return
         */
        String getName(T item);

        /**
         * 点击事件处理方法
         *
         * @param selectedIndexs
         */
        void onConfirm(List<Integer> selectedIndexs);

        /**
         * 显示前的回调
         */
        void showBefore();

        /***
         * 关闭的回调
         */
        void dismissBefore();

        /**
         * 搜索
         */
        default void search(String content) {

        }

        /**
         * 是否包含搜索
         *
         * @return
         */
        default boolean isHasSearch() {
            return false;
        }

        /**
         * 默认搜索提示词
         *
         * @return
         */
        default String searchHint() {
            return "";
        }
    }

    private class WindowAdapter extends BaseQuickAdapter<T, BaseViewHolder> {
        public WindowAdapter(@Nullable List<T> data) {
            super(R.layout.list_item_multiple_select_window, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, T item) {
            helper.getView(R.id.checkbox).setVisibility(mConfig.enableMultiple() ? View.VISIBLE : View.GONE);
            TextView mSupplyerName = helper.getView(R.id.supplyer_name);
            mSupplyerName.setText(mConfig.getName(item));
            if (selectedIndexs.contains(helper.getLayoutPosition())) {
                ((CheckBox) helper.getView(R.id.checkbox)).setChecked(true);
                mSupplyerName.setTextColor(Color.parseColor("#5695D2"));
            } else {
                ((CheckBox) helper.getView(R.id.checkbox)).setChecked(false);
                mSupplyerName.setTextColor(Color.parseColor("#666666"));
            }

            helper.addOnClickListener(R.id.checkbox);
        }
    }
}
