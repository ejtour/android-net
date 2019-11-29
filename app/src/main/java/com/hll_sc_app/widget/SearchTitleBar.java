package com.hll_sc_app.widget;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.hll_sc_app.R;
import com.hll_sc_app.impl.IChangeListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/29
 */

public class SearchTitleBar extends RelativeLayout {
    @BindView(R.id.stb_search_edit)
    EditText mSearchEdit;
    @BindView(R.id.stb_search_clear)
    ImageView mSearchClear;
    private IChangeListener mListener;

    public SearchTitleBar(Context context) {
        this(context, null);
    }

    public SearchTitleBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchTitleBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        View view = View.inflate(context, R.layout.view_search_title_bar, this);
        ButterKnife.bind(this, view);
        if (getBackground() == null) setBackgroundResource(R.drawable.base_bg_shadow_top_bar);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SearchTitleBar);
        String hint = typedArray.getString(R.styleable.SearchTitleBar_stb_hint);
        mSearchEdit.setHint(hint);
        typedArray.recycle();
    }

    @OnClick(R.id.stb_close)
    public void close() {
        if (getContext() instanceof Activity) {
            ((Activity) getContext()).onBackPressed();
        }
    }

    public void setOnSearchListener(IChangeListener listener) {
        mListener = listener;
    }

    @OnClick(R.id.stb_search_button)
    public void toSearch() {
        if (mListener != null) mListener.onChanged();
    }

    @OnClick(R.id.stb_search_clear)
    public void clear() {
        mSearchEdit.setText("");
        toSearch();
    }

    @OnEditorAction(R.id.stb_search_edit)
    public boolean editAction(int actionId) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            toSearch();
        }
        return true;
    }

    @OnTextChanged(R.id.stb_search_edit)
    public void onTextChanged(CharSequence s) {
        mSearchClear.setVisibility(s.toString().length() > 0 ? View.VISIBLE : View.GONE);
    }

    public String getSearchContent() {
        return mSearchEdit.getText().toString().trim();
    }
}
