package com.hll_sc_app.base.widget;

import android.app.Activity;
import android.content.Context;

import com.hll_sc_app.citymall.App;

import java.util.concurrent.atomic.AtomicInteger;

import static android.os.Build.VERSION.SDK_INT;

public class Loading {

    private static Loading loading;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    private Context mContext;
    private LoadView loadView;

    public static Loading instance() {
        if (loading == null) {
            loading = new Loading();
        }
        return loading;
    }

    public void show(Context context) {
        if (App.INSTANCE == null || context == null) {
            return;
        }
        if (mContext == null || !context.getClass().equals(mContext.getClass())) {
            if (loadView != null && loadView.isShowing()) {
                loadView.dismiss();
            }
            mContext = context;
            atomicInteger.getAndSet(0);
            loadView = LoadView.cProgressDialog(context, null, false, null);
        }

        boolean isFinished;
        if (SDK_INT >= 17) {
            isFinished = ((Activity) mContext).isDestroyed();
        } else {
            isFinished = ((Activity) mContext).isFinishing();
        }
        if (!isFinished && atomicInteger.incrementAndGet() == 1) {
            if (!loadView.isShowing()) {
                loadView.show();
            }
        }
    }

    public void dismiss() {
        if (mContext == null || loadView == null) {
            return;
        }
        if (!loadView.isShowing()) {
            mContext = null;
            loadView = null;
            atomicInteger.getAndSet(0);
        } else if (atomicInteger.decrementAndGet() <= 0) {
            loadView.dismiss();
            mContext = null;
            loadView = null;
        }
    }

}
