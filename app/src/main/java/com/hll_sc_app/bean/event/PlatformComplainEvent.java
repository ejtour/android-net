package com.hll_sc_app.bean.event;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 平台投诉
 */
public class PlatformComplainEvent {

    public PlatformComplainEvent(int target, int event) {
        this.target = target;
        this.event = event;
    }

    private @ComplainManageEvent.TARGET
    int target;
    private @ComplainManageEvent.EVENT
    int event;

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    @IntDef({TARGET.DETAIL, TARGET.LIST})
    @Retention(RetentionPolicy.SOURCE)
    public @interface TARGET {
        int LIST = 1;
        int DETAIL = 2;
    }

    @IntDef({EVENT.REFRESH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EVENT {
        int REFRESH = 1;
    }
}
