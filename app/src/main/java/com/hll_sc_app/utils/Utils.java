package com.hll_sc_app.utils;

import android.app.Activity;
import android.support.annotation.DrawableRes;

import com.hll_sc_app.R;
import com.hll_sc_app.widget.ExportDialog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/14
 */

public class Utils {
    /**
     * 邮箱是否符合规范
     *
     * @param email 邮箱
     * @return true-通过
     */
    public static boolean checkEmail(String email) {
        boolean flag;
        try {
            String regExp = "^[A-Za-z0-9\\u4e00-\\u9fa5]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$";
            Pattern regex = Pattern.compile(regExp);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    private static void export(Activity context, String title, @DrawableRes int state, String tip, String action, ExportDialog.OnClickListener listener) {
        ExportDialog.newBuilder(context)
                .setCancelable(false)
                .setTitle(title)
                .setState(state)
                .setTip(tip)
                .setButton(action, listener)
                .create().show();
    }

    public static void exportSuccess(Activity context, String email) {
        export(context, "导出成功", R.drawable.ic_dialog_state_success, "已发送至邮箱\n" + email, "我知道了", null);
    }

    public static void exportFailure(Activity context, String tip) {
        export(context, "导出失败", R.drawable.ic_dialog_state_failure, tip, "稍后再试", null);
    }

    public static void exportEmail(Activity context, ExportDialog.OnClickListener listener) {
        export(context, "您还没绑定邮箱", R.drawable.ic_dialog_state_failure, null, "绑定并导出", listener);
    }
}
