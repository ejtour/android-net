package com.hll_sc_app.base.http;

import android.arch.lifecycle.LifecycleOwner;

import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.UseCaseException;

import java.lang.ref.WeakReference;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/10
 */

public abstract class SimpleObserver<T> extends BaseCallback<T> {
    /**
     * 使用软引用，避免内存泄漏
     */
    private WeakReference<ILoadView> mView;
    private boolean mShowLoading;

    protected SimpleObserver(ILoadView view) {
        this(view, true);
    }

    protected SimpleObserver(ILoadView view, boolean showLoading) {
        mView = new WeakReference<>(view);
        mShowLoading = showLoading;
    }

    public void startReq() {
        if (mShowLoading && mView.get() != null) {
            mView.get().showLoading();
        }
    }

    public void reqOver() {
        if (mView.get() != null) {
            mView.get().hideLoading();
        }
    }

    public LifecycleOwner getOwner() {
        LifecycleOwner owner = null;
        if (mView.get() != null) {
            owner = mView.get().getOwner();
        }
        if (owner != null) {
            return owner;
        }
        throw new IllegalStateException("Owner 为空");
    }

    @Override
    public void onNext(T resp) {
        if (mView.get() != null && mView.get().isActive()) {
            super.onNext(resp);
        }
    }

    @Override
    public void onFailure(UseCaseException e) {
        if (mView.get() != null) {
            mView.get().showError(e);
        }
    }
}
