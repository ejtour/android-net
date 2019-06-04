package com.hll_sc_app.base.utils.permission;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.view.ViewTreeObserver;

/**
 * 多次询问
 *
 * @author 朱英松
 * @date 2018/3/13
 */
public final class DetachableClickListener implements DialogInterface.OnClickListener {
    private DialogInterface.OnClickListener delegateOrNull;

    private DetachableClickListener(DialogInterface.OnClickListener delegate) {
        this.delegateOrNull = delegate;
    }

    static DetachableClickListener wrap(DialogInterface.OnClickListener delegate) {
        return new DetachableClickListener(delegate);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (delegateOrNull != null) {
            delegateOrNull.onClick(dialog, which);
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    void clearOnDetach(Dialog dialog) {
        dialog.getWindow().getDecorView().getViewTreeObserver()
            .addOnWindowAttachListener(new ViewTreeObserver.OnWindowAttachListener() {
                @Override
                public void onWindowAttached() {
                }

                @Override
                public void onWindowDetached() {
                    DetachableClickListener.this.delegateOrNull = null;
                }
            });
    }
}
