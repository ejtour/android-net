package com.hll_sc_app.widget.right;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.router.RightConfig;
import com.hll_sc_app.citymall.util.ToastUtils;

/**
 * 权限控制
 *
 * @author zhuyingsong
 * @date 2019/3/19
 */
public class RightTextView extends AppCompatTextView implements View.OnClickListener {
    private String mRightCode;
    private OnClickListener mOutSideListener;

    public RightTextView(Context context) {
        super(context, null);
    }

    public RightTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RightTextView);
        mRightCode = array.getString(R.styleable.RightTextView_rightCode);
        array.recycle();
    }

    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        this.mOutSideListener = l;
        super.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (mOutSideListener == null) {
            return;
        }
        if (RightConfig.checkRight(mRightCode)) {
            mOutSideListener.onClick(v);
        } else {
            ToastUtils.showShort(getContext().getString(R.string.right_tips));
        }
    }
}
