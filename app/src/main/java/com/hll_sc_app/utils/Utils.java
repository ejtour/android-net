package com.hll_sc_app.utils;

import android.app.Activity;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import com.hll_sc_app.R;
import com.hll_sc_app.base.ILoadView;
import com.hll_sc_app.base.UseCaseException;
import com.hll_sc_app.base.http.SimpleObserver;
import com.hll_sc_app.bean.export.ExportResp;
import com.hll_sc_app.impl.IExportView;
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

    public static boolean checkPhone(String phone) {
        if (TextUtils.isEmpty(phone)) return false;
        phone = phone.replaceAll("\\s+", "");
        return phone.matches("^((13[0-9])|(14[57])|(15[0-35-9])|(16[6])|(17[0135-8])|(18[0-9])|(19[189]))\\d{8}$");
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
        export(context, "导出成功", R.drawable.ic_dialog_state_success, "已发送至邮箱\n" + email.replaceAll(";", "\n"), "我知道了", null);
    }

    public static void exportFailure(Activity context, String tip) {
        export(context, "导出失败", R.drawable.ic_dialog_state_failure, tip, "稍后再试", null);
    }

    public static void bindEmail(Activity context, ExportDialog.OnClickListener listener) {
        export(context, "您还没绑定邮箱", R.drawable.ic_dialog_state_failure, null, "绑定并导出", listener);
    }

    public static SimpleObserver<ExportResp> getExportObserver(IExportView export) {
        return new SimpleObserver<ExportResp>(export) {
            @Override
            public void onSuccess(ExportResp resp) {
                ILoadView view = getView();
                if (!(view instanceof IExportView)) return;
                if (!TextUtils.isEmpty(resp.getEmail()))
                    ((IExportView) view).exportSuccess(resp.getEmail());
                else ((IExportView) view).exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
            }

            @Override
            public void onFailure(UseCaseException e) {
                ILoadView view = getView();
                if (!(view instanceof IExportView)) return;
                if ("00120112037".equals(e.getCode())) ((IExportView) view).bindEmail();
                else if ("00120112038".equals(e.getCode()))
                    ((IExportView) view).exportFailure("当前没有可导出的数据");
                else ((IExportView) view).exportFailure("噢，服务器暂时开了小差\n攻城狮正在全力抢修");
            }
        };
    }
}
