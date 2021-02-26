package com.hll_sc_app.bean.bill;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/8
 */

@IntDef({BillStatus.NOT_SETTLE, BillStatus.SETTLED, BillStatus.PART_SETTLED})
@Retention(RetentionPolicy.SOURCE)
public @interface BillStatus {
    int NOT_SETTLE = 1;
    int SETTLED = 2;
    int PART_SETTLED = 3;
}
