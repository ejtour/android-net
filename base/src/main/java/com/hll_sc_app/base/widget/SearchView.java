package com.hll_sc_app.base.widget;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.hll_sc_app.base.R;

/**
 * 集成搜索控件
 */
public class SearchView extends FrameLayout implements TextWatcher {

    private EditText editText;
    private TextWatcher textWatcher;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SearchView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View view = View.inflate(context, R.layout.base_view_search, null);
        addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        editText = (EditText) view.findViewById(R.id.search);
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (textWatcher != null) {
            textWatcher.beforeTextChanged(charSequence, i, i1, i2);
        }
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (textWatcher != null) {
            textWatcher.onTextChanged(charSequence, i, i1, i2);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (textWatcher != null) {
            textWatcher.afterTextChanged(editable);
        }
    }

    public void addTextChangedListener(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    public void setHint(CharSequence sequence) {
        editText.setHint(sequence);
    }

    public void setHint(int resid) {
        editText.setHint(resid);
    }

    public Editable getText() {
        return editText.getText();
    }

    public void setText(CharSequence sequence) {
        editText.setText(sequence);
    }

    public void setText(int resid) {
        editText.setText(resid);
    }

    public EditText getEditText() {
        return editText;
    }
}
