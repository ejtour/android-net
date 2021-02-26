package com.hll_sc_app.base.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;

import androidx.appcompat.widget.AppCompatEditText;

import com.hll_sc_app.base.R;

/**
 * 带删除键的输入框
 */
public class ClearEditText extends AppCompatEditText implements OnFocusChangeListener, TextWatcher {
    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    private OnFocusChangeListener onFocusChangeListener;
    private int intrinsicWidth;

    private TextChangedListener textChangedListener;

    public ClearEditText(Context context) {
        this(context, null);
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // 焦点状态全选
        setSelectAllOnFocus(true);
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.drawable.base_close);
        }
        intrinsicWidth = mClearDrawable.getIntrinsicWidth();
        mClearDrawable.setBounds(0, 0, intrinsicWidth, mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);
        setOnFocusChangeListener(null);
        addTextChangedListener(this);
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     */
    protected void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    @Override
    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.onFocusChangeListener = onFocusChangeListener;
        super.setOnFocusChangeListener(this);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
        if (onFocusChangeListener != null) {
            onFocusChangeListener.onFocusChange(v, hasFocus);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // not do
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (textChangedListener != null) {
            textChangedListener.afterTextChanged(s);
        }
    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation() {
        this.setAnimation(shakeAnimation(5));
    }

    /**
     * 晃动动画
     *
     * @param counts 1秒钟晃动多少下
     */
    public static Animation shakeAnimation(int counts) {
        Animation translateAnimation = new TranslateAnimation(0, 10, 0, 0);
        translateAnimation.setInterpolator(new CycleInterpolator(counts));
        translateAnimation.setDuration(1000);
        return translateAnimation;
    }

    public int getIntrinsicWidth() {
        return intrinsicWidth;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            //可用状态
            if (this.getTag() != null) {
                this.setHint((CharSequence) this.getTag());
            }
        } else {
            //不可用状态
            this.setTag(this.getHint());
            this.setHint("");
        }
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count, int after) {
        if (isFocused()) {
            setClearIconVisible(s.length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] != null && event.getAction() == MotionEvent.ACTION_UP && (event.getX() > (getWidth() - getPaddingRight() - mClearDrawable.getIntrinsicWidth()) && (event.getX() < ((getWidth() - getPaddingRight()))))) {
            setText("");
        }
        return super.onTouchEvent(event);
    }

    public void addTextChangedListener(TextChangedListener textChangedListener) {
        this.textChangedListener = textChangedListener;
    }

    public interface TextChangedListener {
        void afterTextChanged(Editable s);
    }

}
