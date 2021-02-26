package com.hll_sc_app.base.utils.permission;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.hll_sc_app.base.R;
import com.hll_sc_app.citymall.util.LogUtil;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * 权限询问工具类
 *
 * @author zhuyingsong
 * @date 2019/1/14
 */
public class RequestPermissionUtils {
    public static final String[] STORAGE = Build.VERSION.SDK_INT < Build.VERSION_CODES.Q ?
            new String[]{Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE} :
            new String[]{Permission.READ_EXTERNAL_STORAGE};
    public static final String[] STORAGE_CAMERA = Build.VERSION.SDK_INT < Build.VERSION_CODES.Q ?
            new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE, Permission.WRITE_EXTERNAL_STORAGE} :
            new String[]{Permission.CAMERA, Permission.READ_EXTERNAL_STORAGE};
    private String[] mPermissions;
    private PermissionListener mListener;
    private final WeakReference<Context> mContext;
    private boolean mForce;

    public RequestPermissionUtils(Context context, String[] permissions, boolean force, PermissionListener listener) {
        mPermissions = permissions;
        mListener = listener;
        mContext = new WeakReference<>(context);
        mForce = force;
    }

    public RequestPermissionUtils(Context context, String[] permissions, PermissionListener listener) {
        this.mContext = new WeakReference<>(context);
        this.mListener = listener;
        this.mPermissions = permissions;
    }

    public void requestPermission() {
        AndPermission.with(mContext.get()).runtime().permission(mPermissions).rationale(new DefaultRationale())
                .onDenied(data -> {
                    LogUtil.d("PERMISSION", "onDenied");
                    if (AndPermission.hasAlwaysDeniedPermission(mContext.get(), data)) {
                        showSettingDialog(data);
                    } else if (mForce) {
                        requestPermission();
                    }
                })
            .onGranted(data -> {
                LogUtil.d("PERMISSION", "onGranted");
                mListener.onGranted();
            })
            .start();
    }

    /**
     * 用户多次拒绝后弹出提示框
     *
     * @param permissions mPermissions
     */
    private void showSettingDialog(final List<String> permissions) {
        List<String> permissionNames = Permission.transformText(mContext.get(), permissions);
        String message = mContext.get().getString(R.string.base_message_permission_always_failed, TextUtils.join("\n",
                permissionNames));
        new AlertDialog.Builder(mContext.get())
                .setCancelable(false)
                .setTitle("提示")
                .setMessage(message)
                .setPositiveButton("设置", (dialog, which) -> setPermission())
                .setNegativeButton("取消", mForce ? (dialog, which) -> requestPermission() : null)
                .show();
    }

    /**
     * 跳转到设置页面用户手动开启
     * 返回后重新判断权限
     */
    private void setPermission() {
        AndPermission.with(mContext.get()).runtime().setting().onComeback(this::requestPermission).start();
    }


    public interface PermissionListener {
        /**
         * Action to be taken when all mPermissions are granted.
         */
        void onGranted();
    }
}
