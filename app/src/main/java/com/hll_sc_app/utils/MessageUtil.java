package com.hll_sc_app.utils;

import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.rest.Message;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/6
 */

public class MessageUtil {
    private Disposable mTimer;
    private AtomicBoolean mRelease;
    private String mEmployeeID;
    private String mGroupID;

    public MessageUtil() {
        mRelease = new AtomicBoolean(false);
        start();
    }

    public void start() {
        // 查询消息
//        if (!BuildConfig.isDebug)
        queryUnreadMessage();
    }

    private void queryUnreadMessage() {
        if (mRelease.get()) return;
        mTimer = Observable.timer(mEmployeeID != null ? 3000 : 0, TimeUnit.MILLISECONDS)
                .subscribe(aLong -> {
                    if (mEmployeeID == null || mGroupID == null) {
                        UserBean user = GreenDaoUtils.getUser();
                        mEmployeeID = user.getEmployeeID();
                        mGroupID = user.getGroupID();
                    }
                    Message.queryMessage(mEmployeeID, mGroupID, new Observer<Object>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            // no-op
                        }

                        @Override
                        public void onNext(Object o) {
                            queryUnreadMessage();
                        }

                        @Override
                        public void onError(Throwable e) {
                            queryUnreadMessage();
                        }

                        @Override
                        public void onComplete() {
                            // no-op
                        }
                    });
                });
    }

    public void dispose() {
        mRelease.set(true);
        if (mTimer != null && !mTimer.isDisposed()) {
            mTimer.isDisposed();
        }
    }
}
