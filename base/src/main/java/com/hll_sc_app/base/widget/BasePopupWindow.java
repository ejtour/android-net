package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * 针对7.0处理的PopupWindow
 *
 * @author zhuyingsong
 * @date 2017/6/29
 */
public class BasePopupWindow extends PopupWindow {
    protected Activity mActivity;

    public BasePopupWindow(Activity activity) {
        super(activity);
        mActivity = activity;
    }

    public void showAsDropDownFix(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            WindowManager wm = (WindowManager) getContentView().getContext().getSystemService(Context.WINDOW_SERVICE);
            int screenHeight = wm.getDefaultDisplay().getHeight();
            setHeight(screenHeight - location[1] - anchor.getHeight());
            super.showAtLocation(anchor, Gravity.NO_GRAVITY, 0, location[1] + anchor.getHeight());
        } else {
            super.showAsDropDown(anchor, 0, 0);
        }
    }

    public void showAsDropDownFix(View anchor, int gravity) {
        if (android.os.Build.VERSION.SDK_INT >= 19) {
            super.showAsDropDown(anchor, 0, 0, gravity);
        } else {
            super.showAsDropDown(anchor);
        }
    }

    @Override
    public void showAsDropDown(View anchor, int xOff, int yOff) {
        if (Build.VERSION.SDK_INT >= 24) {
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            WindowManager wm = (WindowManager) getContentView().getContext().getSystemService(Context.WINDOW_SERVICE);
            int screenHeight = wm.getDefaultDisplay().getHeight();
            setHeight(screenHeight - location[1] - anchor.getHeight() - yOff);
            super.showAtLocation(anchor, Gravity.NO_GRAVITY, xOff, location[1] + anchor.getHeight() + yOff);
        } else {
            super.showAsDropDown(anchor, xOff, yOff);
        }
    }

}
