package com.hll_sc_app.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;

import androidx.fragment.app.Fragment;

import java.util.LinkedList;
import java.util.List;

/**
 * 键盘监听
 *
 * @author zhuyingsong
 * @date 2018/12/27
 */
public class KeyboardWatcher implements ViewTreeObserver.OnGlobalLayoutListener {

    private final List<SoftKeyboardStateListener> listeners = new LinkedList<SoftKeyboardStateListener>();
    private final View rootView;
    private boolean isSoftKeyboardOpened;
    private int statusBarHeight = -1;

    public KeyboardWatcher(Activity activity) {
        this.rootView = activity.findViewById(Window.ID_ANDROID_CONTENT);
        init();
    }

    public KeyboardWatcher(Fragment fragment) {
        this.rootView = fragment.getView();
        init();
    }

    private void init() {
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(this);
        //获取status_bar_height资源的ID
        int resourceId = rootView.getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            //根据资源ID获取响应的尺寸值
            statusBarHeight = rootView.getContext().getResources().getDimensionPixelSize(resourceId);
        }
    }

    private boolean isFullScreen(Activity activity) {
        return (activity.getWindow().getAttributes().flags &
                WindowManager.LayoutParams.FLAG_FULLSCREEN) == WindowManager.LayoutParams.FLAG_FULLSCREEN;
    }

    @Override
    public void onGlobalLayout() {
        final Rect r = new Rect();
        //r will be populated with the coordinates of your view that area still visible.
        rootView.getWindowVisibleDisplayFrame(r);

        final int heightDiff = rootView.getRootView().getHeight() - (r.bottom - r.top);
        if (!isSoftKeyboardOpened && heightDiff > rootView.getRootView().getHeight() / 4) {
            isSoftKeyboardOpened = true;
            if ((rootView.getContext() instanceof Activity)
                    && !isFullScreen((Activity) rootView.getContext())) {
                notifyOnSoftKeyboardOpened(heightDiff - statusBarHeight);
            } else {
                notifyOnSoftKeyboardOpened(heightDiff);
            }

        } else if (isSoftKeyboardOpened && heightDiff < rootView.getRootView().getHeight() / 4) {
            isSoftKeyboardOpened = false;
            notifyOnSoftKeyboardClosed();
        }
    }

    public void addSoftKeyboardStateListener(SoftKeyboardStateListener listener) {
        listeners.add(listener);
    }

    public void removeSoftKeyboardStateListener(SoftKeyboardStateListener listener) {
        listeners.remove(listener);
    }

    private void notifyOnSoftKeyboardOpened(int keyboardHeightInPx) {
        for (SoftKeyboardStateListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardOpened(keyboardHeightInPx);
            }
        }
    }

    private void notifyOnSoftKeyboardClosed() {
        for (SoftKeyboardStateListener listener : listeners) {
            if (listener != null) {
                listener.onSoftKeyboardClosed();
            }
        }
    }

    public interface SoftKeyboardStateListener {
        /**
         * open
         *
         * @param keyboardHeightInPx height
         */
        void onSoftKeyboardOpened(int keyboardHeightInPx);

        /**
         * close
         */
        void onSoftKeyboardClosed();
    }
}