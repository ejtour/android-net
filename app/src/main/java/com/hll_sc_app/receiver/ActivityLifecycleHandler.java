package com.hll_sc_app.receiver;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.hll_sc_app.app.submit.IBackType;
import com.hll_sc_app.utils.MessageUtil;

import java.util.ArrayList;
import java.util.List;

import me.leolin.shortcutbadger.ShortcutBadger;

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
    private final List<IBackType> mBackTypes;

    public ActivityLifecycleHandler(Listener listener) {
        this.mListener = listener;
        mBackTypes = new ArrayList<>();
    }

    public static boolean isApplicationInForeground() {
        return !mRunBack;
    }

    public IBackType getLastBackType() {
        return mBackTypes.size() > 0 ? mBackTypes.get(mBackTypes.size() - 1) : null;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (activity instanceof IBackType) {
            mBackTypes.add((IBackType) activity);
        }
    }

    @Override
    public void onActivityStarted(Activity activity) {
        appCount++;
        if (mRunBack) {
            mRunBack = false;
            if (mListener != null) {
                mListener.backToFront(activity);
            }
        }
        MessageUtil.instance().start();
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
            ShortcutBadger.applyCount(activity, MessageUtil.instance().getUnreadNum());
            mRunBack = true;
            MessageUtil.instance().stop();
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        if (activity instanceof IBackType) {
            mBackTypes.remove(activity);
        }
    }

    public interface Listener {
        /**
         * 回到前台
         */
        void backToFront(Activity activity);
    }
}
