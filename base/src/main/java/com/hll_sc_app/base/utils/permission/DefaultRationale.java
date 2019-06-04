package com.hll_sc_app.base.utils.permission;

import android.app.AlertDialog;
import android.content.Context;
import android.text.TextUtils;

import com.hll_sc_app.base.R;
import com.yanzhenjie.permission.Permission;
import com.yanzhenjie.permission.Rationale;
import com.yanzhenjie.permission.RequestExecutor;

import java.util.List;

/**
 * 多次询问
 *
 * @author 朱英松
 * @date 2018/3/13
 */
public final class DefaultRationale implements Rationale<List<String>> {

    @Override
    public void showRationale(Context context, List<String> permissions, RequestExecutor executor) {
        List<String> permissionNames = Permission.transformText(context, permissions);
        String message = context.getString(R.string.base_message_permission_rationale, TextUtils.join("\n",
            permissionNames));

        DetachableClickListener wrapCancel = DetachableClickListener.wrap((dialog, which) -> executor.cancel());
        DetachableClickListener wrapConfirm = DetachableClickListener.wrap((dialog, which) -> executor.execute());
        AlertDialog dialog = new AlertDialog.Builder(context)
            .setCancelable(false)
            .setTitle("提示")
            .setMessage(message)
            .setNegativeButton("取消", wrapCancel)
            .setPositiveButton("确定", wrapConfirm)
            .create();
        wrapCancel.clearOnDetach(dialog);
        wrapConfirm.clearOnDetach(dialog);
        dialog.show();
    }
}