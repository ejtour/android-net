package com.hll_sc_app.utils;

import com.hll_sc_app.BuildConfig;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.http.BaseCallback;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.bean.event.MessageEvent;
import com.hll_sc_app.rest.Message;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/7/15
 */

public class MessageUtil {
    private Disposable mTimer;
    private AtomicBoolean mRelease;
    private AtomicBoolean mEnable;
    private int mUnreadNum;

    private MessageUtil() {
        mRelease = new AtomicBoolean(true);
        mEnable = new AtomicBoolean(!BuildConfig.isDebug);
    }

    private static class Holder {
        final static MessageUtil INSTANCE = new MessageUtil();
    }

    public static MessageUtil instance() {
        return Holder.INSTANCE;
    }

    public void start() {
        if (!mEnable.get() || !UserConfig.isLogin() || !mRelease.get()) {
            return;
        }
        mRelease.set(false);
        queryUnreadMessage(true);
    }

    public boolean toggle() {
        mEnable.set(!mEnable.get());
        if (!mEnable.get()) {
            stop();
        } else {
            start();
        }
        return mEnable.get();
    }

    public synchronized void setUnreadNum(int unreadNum) {
        if (UserConfig.isLogin()) {
            mUnreadNum = unreadNum;
        } else {
            mUnreadNum = 0;
        }
        String num = mUnreadNum <= 0 ? "" : mUnreadNum < 100 ? String.valueOf(mUnreadNum) : "99+";
        EventBus.getDefault().postSticky(new MessageEvent(num));
    }

    public int getUnreadNum() {
        return mUnreadNum;
    }

    private synchronized void queryUnreadMessage(boolean first) {
        if (mRelease.get()) {
            return;
        }
        dispose();
        mTimer = Observable.timer(first ? 0 : 3000, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> {
                    UserBean user = GreenDaoUtils.getUser();
                    if (user == null) {
                        stop();
                        return;
                    }
                    Message.queryMessage(user.getEmployeeID(), user.getGroupID(), new BaseCallback<Object>() {
                        @Override
                        public void onSuccess(Object o) {
                            queryUnreadMessage(false);
                        }

                        @Override
                        public void onFailure(UseCaseException e) {
                            queryUnreadMessage(false);
                        }
                    });
                });
    }

    private void dispose() {
        if (mTimer != null) {
            mTimer.isDisposed();
            mTimer = null;
        }
    }

    public synchronized void stop() {
        mRelease.set(true);
        dispose();
    }
}
