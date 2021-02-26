package com.hll_sc_app.base.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.githang.statusbar.StatusBarCompat;
import com.hll_sc_app.citymall.util.ViewUtils;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/1/11
 */
public class StatusBarUtil {
    private static final String TAG = "StatusBarUtil";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setStatusBarColor(Activity activity, @ColorInt int statusColor) {
        StatusBarCompat.setStatusBarColor(activity, statusColor);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setTranslucent(@NonNull Activity activity) {
        setTranslucent(activity, false);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void setTranslucent(@NonNull Activity activity, boolean isLightStatusBar) {
        Window window = activity.getWindow();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            StatusBarCompat.setTranslucent(window, true);
        }
        StatusBarCompat.setLightStatusBar(window, isLightStatusBar);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void fitSystemWindowsWithPaddingTop(View... views) {
        for (View view : views) {
            view.setPadding(view.getPaddingLeft(),
                    view.getPaddingTop() + ViewUtils.getStatusBarHeight(view.getContext()),
                    view.getPaddingRight(), view.getPaddingBottom());
            if (view.getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                continue;
            }
            view.getLayoutParams().height += ViewUtils.getStatusBarHeight(view.getContext());
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void fitSystemWindowsWithHeight(View... views) {
        for (View view : views) {
            if (view.getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                continue;
            }
            view.getLayoutParams().height += ViewUtils.getStatusBarHeight(view.getContext());
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void fitSystemWindowsWithMarginTop(View... views) {
        for (View view : views) {
            ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin += ViewUtils.getStatusBarHeight(view.getContext());
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void resetSystemWindowsWithPaddingTop(View... views) {
        for (View view : views) {
            view.setPadding(view.getPaddingLeft(),
                    view.getPaddingTop() - ViewUtils.getStatusBarHeight(view.getContext()),
                    view.getPaddingRight(), view.getPaddingBottom());
            if (view.getLayoutParams().height == ViewGroup.LayoutParams.WRAP_CONTENT) {
                continue;
            }
            view.getLayoutParams().height -= ViewUtils.getStatusBarHeight(view.getContext());
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void resetSystemWindowsWithMarginTop(View... views) {
        for (View view : views) {
            ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin -= ViewUtils.getStatusBarHeight(view.getContext());
        }
    }
}
