package com.hll_sc_app.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hll_sc_app.R;
import com.hll_sc_app.base.widget.BasePopupWindow;
import com.hll_sc_app.bean.window.NameValue;
import com.hll_sc_app.impl.IStringListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/4
 */

public class OrgSelectWindow extends BasePopupWindow {
    @BindView(R.id.wos_search_type)
    TextView mSearchType;
    @BindView(R.id.wos_search_edit)
    EditText mSearchEdit;
    @BindView(R.id.wos_list_view)
    RecyclerView mListView;
    @BindView(R.id.wos_clear_search)
    ImageView mClearSearch;
    private IStringListener mListener;
    private OrgAdapter mAdapter;

    public OrgSelectWindow(Activity activity) {
        super(activity);
        View rootView = View.inflate(mActivity, R.layout.window_org_select, null);
        rootView.setOnClickListener(v -> dismiss());
        ButterKnife.bind(this, rootView);
        this.setContentView(rootView);
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.setBackgroundDrawable(new ColorDrawable(0xbb000000));
        this.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        initView();
    }

    private void initView() {
        mAdapter = new OrgAdapter();
        mAdapter.setEmptyView(EmptyView.newBuilder(mActivity)
                .setTipsTitle("咦，没有搜索到哦~")
                .create());
        mListView.setAdapter(mAdapter);
    }

    public OrgSelectWindow setSearchType(String type) {
        mSearchType.setText(type);
        return this;
    }

    public OrgSelectWindow setSearchListener(IStringListener listener) {
        mListener = listener;
        return this;
    }

    public OrgSelectWindow setClickListener(BaseQuickAdapter.OnItemClickListener listener) {
        mAdapter.setOnItemClickListener(listener);
        return this;
    }

    public OrgSelectWindow setListData(List<NameValue> list) {
        mAdapter.setNewData(list);
        return this;
    }

    public void select(NameValue nameValue) {
        mAdapter.select(nameValue);
    }

    @OnClick({R.id.wos_search_btn, R.id.wos_clear_search})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.wos_search_btn:
                toSearch();
                break;
            case R.id.wos_clear_search:
                mSearchEdit.setText("");
                toSearch();
                break;
        }
    }

    private void toSearch() {
        mListener.callback(getSearchWords());
    }

    @OnTextChanged(R.id.wos_search_edit)
    public void onTextChanged(CharSequence s) {
        mClearSearch.setVisibility(s.length() == 0 ? View.GONE : View.VISIBLE);
    }

    @OnEditorAction(R.id.wos_search_edit)
    public boolean onEditorAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) toSearch();
        return true;
    }

    public String getSearchWords() {
        return mSearchEdit.getText().toString().trim();
    }

    private static class OrgAdapter extends BaseQuickAdapter<NameValue, BaseViewHolder> {

        private NameValue mValue;

        OrgAdapter() {
            super(R.layout.item_window_purchaser_select);
        }

        void select(NameValue value) {
            mValue = value;
            notifyDataSetChanged();
        }

        @Override
        protected void convert(BaseViewHolder helper, NameValue item) {
            TextView textView = helper.getView(R.id.txt_purchaserName);
            textView.setText(item.getName());
            textView.setSelected(mValue != null && mValue.getValue().equals(item.getValue()));
        }
    }
}
