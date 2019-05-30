package com.hll_sc_app.receiver;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * 处理App所有Activity的生命周期回调
 *
 * @author zhuyingsong
 * @date 2019/4/8
 */
public class ActivityLifecycleHandler implements Application.ActivityLifecycleCallbacks {
    /**
     * 运行在后台
     */
    private static boolean mRunBack;
    private Listener mListener;
    private int appCount;


    public ActivityLifecycleHandler(Listener listener) {
        this.mListener = listener;
    }

    public static boolean isApplicationInForeground() {
        return !mRunBack;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        appCount++;
        if (mRunBack) {
            mRunBack = false;
            if (mListener != null) {
                mListener.backToFront();
            }
        }
    }

    @Override
    public void onActivityResumed(Activity activity) {
    }

    @Override
    public void onActivityPaused(Activity activity) {
    }

    @Override
    public void onActivityStopped(Activity activity) {
        appCount--;
        if (appCount == 0) {
            mRunBack = true;
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    public interface Listener {
        /**
         * 回到前台
         */
        void backToFront();
    }
}
