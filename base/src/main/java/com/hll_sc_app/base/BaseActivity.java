package com.hll_sc_app.base;

import android.arch.lifecycle.LifecycleOwner;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.base.utils.Constant;
import com.hll_sc_app.base.utils.StatusBarUtil;
import com.hll_sc_app.base.widget.AutoDensity;
import com.hll_sc_app.citymall.App;
import com.hll_sc_app.citymall.util.LogUtil;
import com.hll_sc_app.citymall.util.ViewUtils;

import ly.count.android.sdk.Countly;

import static android.os.Build.VERSION.SDK_INT;

/**
 * <br>
 * <b>功能：</b>Activity基类<br>
 * <b>作者：</b>HuYongcheng<br>
 * <b>日期：</b>2016/10/11<br>
 */
public abstract class BaseActivity extends AppCompatActivity {

    private SparseArray<View> mViews = new SparseArray<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            // remove掉保存的Fragment
            savedInstanceState.remove("android:support:fragments");
        }
        super.onCreate(savedInstanceState);
        AutoDensity.setCustomDensity(this, getApplication());
        initSystemBar();
        App.pushActivity(this);
        ViewUtils.removeStatusBarColor(this);
        LogUtil.d("ZYS", "onCreate-" + this.getClass().getName());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null && requestCode == Constant.IMG_SELECT_REQ_CODE && resultCode == RESULT_OK) {
            if (getIntent() != null) {
                getIntent().putExtras(data);
            } else {
                setIntent(data);
            }
        }
    }

    protected void initSystemBar() {
        StatusBarUtil.setStatusBarColor(this, ContextCompat.getColor(this, R.color.base_colorPrimary));
    }

    @Override
    protected void onStart() {
        super.onStart();
        Countly.sharedInstance().onStart(this);
    }

    @Override
    protected void onStop() {
        Countly.sharedInstance().onStop();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (!App.popActivity(this)) {
            App.finishAll();
            throw new RuntimeException("出栈错误");
        }
        mViews.clear();
        LogUtil.d("ZYS", "onDestroy-" + this.getClass().getName());
        super.onDestroy();
    }

    public boolean isActive() {
        return SDK_INT >= 17 ? !isDestroyed() : !isFinishing();
    }


    public LifecycleOwner getOwner() {
        return this;
    }

    public void setText(int viewId, String text) {
        TextView tv = findView(viewId);
        tv.setText(text);
    }

    public <T extends View> T findView(int id) {
        View view = mViews.get(id);
        if (view == null) {
            view = findViewById(id);
            mViews.put(id, view);
        }
        return (T) view;
    }

    public void setImageResource(int viewId, int resId) {
        ImageView view = findView(viewId);
        view.setImageResource(resId);
    }

    public void setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = findView(viewId);
        view.setImageBitmap(bitmap);
    }

    public void setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = findView(viewId);
        view.setImageDrawable(drawable);
    }

    public void setBackgroundColor(int viewId, int color) {
        View view = findView(viewId);
        view.setBackgroundColor(color);
    }

    public void setBackgroundRes(int viewId, int backgroundRes) {
        View view = findView(viewId);
        view.setBackgroundResource(backgroundRes);
    }

    public void setTextColor(int viewId, int textColor) {
        TextView view = findView(viewId);
        view.setTextColor(textColor);
    }

    public void setVisible(int viewId, boolean visible) {
        View view = findView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    public void setChecked(int viewId, boolean checked) {
        Checkable view = findView(viewId);
        view.setChecked(checked);
    }

    /**
     * 关于事件的
     */
    public void setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = findView(viewId);
        view.setOnClickListener(listener);
    }

    public void setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = findView(viewId);
        view.setOnTouchListener(listener);
    }

    public void setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = findView(viewId);
        view.setOnLongClickListener(listener);
    }
}
