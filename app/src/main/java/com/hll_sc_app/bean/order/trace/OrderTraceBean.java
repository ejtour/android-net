package com.hll_sc_app.bean.order.trace;

import android.text.TextUtils;

import com.hll_sc_app.utils.Constants;
import com.hll_sc_app.utils.DateUtil;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/9
 */

public class OrderTraceBean {
    private String label;
    private String desc;
    private String time;

    public OrderTraceBean(String label, String desc, String time) {
        this.label = label;
        this.desc = desc;
        this.time = time;
    }

    public String getLabel() {
        return label;
    }

    public String getDesc() {
        return desc;
    }

    public String getTime() {
        return TextUtils.isEmpty(time) ? null : DateUtil.getReadableTime(time, Constants.SIGNED_YYYY_MM_DD_HH_MM_SS);
    }
}
