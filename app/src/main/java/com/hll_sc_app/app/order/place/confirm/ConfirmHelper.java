package com.hll_sc_app.app.order.place.confirm;

import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;

import androidx.core.content.ContextCompat;

import com.hll_sc_app.R;
import com.hll_sc_app.citymall.util.CommonUtils;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/2/10
 */

public class ConfirmHelper {
    static SpannableString getAmount(Context context, double amount, double depositAmount, int type) {
        String source;
        int endPos;
        if (depositAmount > 0) {
            source = String.format("¥%s(含押金：¥%s)", CommonUtils.formatMoney(amount),
                    CommonUtils.formatMoney(depositAmount));
            endPos = source.indexOf("(");
        } else {
            source = String.format("¥%s", CommonUtils.formatMoney(amount));
            endPos = source.length();
        }
        SpannableString ss = new SpannableString(source);
        ss.setSpan(new RelativeSizeSpan(type == 2 ? 1.6f : 1.3f), type == 2 ? 1 : 0, type == 2 ? source.indexOf(".") : endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        if (type == 0) {
            ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, R.color.color_222222)),
                    0, endPos, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return ss;
    }
}
