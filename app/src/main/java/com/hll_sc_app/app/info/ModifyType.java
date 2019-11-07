package com.hll_sc_app.app.info;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/6
 */

@IntDef({ModifyType.EMAIL, ModifyType.CONTACT, ModifyType.PHONE,
        ModifyType.FAX, ModifyType.NAME, ModifyType.ID_CARD, ModifyType.GROUP_EMAIL})
@Retention(RetentionPolicy.SOURCE)
public @interface ModifyType {
    int EMAIL = 1;
    int CONTACT = 2;
    int PHONE = 3;
    int FAX = 4;
    int NAME = 5;
    int ID_CARD = 6;
    int GROUP_EMAIL = 7;
    int DOORWAY = 8;
    int LICENSE = 9;
    int OTHER = 10;
}
