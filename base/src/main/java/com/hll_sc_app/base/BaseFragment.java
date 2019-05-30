package com.hll_sc_app.base;

import android.arch.lifecycle.LifecycleOwner;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.View;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.TextView;

import com.hll_sc_app.citymall.util.LogUtil;

/**
 * <br>
 * <b>功能：</b>Fragment基类<br>
 * <b>作者：</b>HuYongcheng<br>
 * <b>日期：</b>2016/10/11<br>
 */
public class BaseFragment extends Fragment {

    protected View rootView;
    private SparseArray<View> mViews = new SparseArray<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d("ZYS", this.getClass().getName());
    }

    @Override
    public void onDestroyView() {
        mViews.clear();
        rootView = null;
        super.onDestroyView();
    }

    public boolean isActive() {
        return isAdded();
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
            view = rootView.findViewById(id);
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
