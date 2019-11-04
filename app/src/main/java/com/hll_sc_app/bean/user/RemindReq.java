package com.hll_sc_app.bean.user;

import com.hll_sc_app.base.utils.UserConfig;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/4
 */

public class RemindReq {
    private String groupID = UserConfig.getGroupID();
    private boolean isOpen;
    private String remindTimes;

    public RemindReq(boolean isOpen, String remindTimes) {
        this.isOpen = isOpen;
        this.remindTimes = remindTimes;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public String getRemindTimes() {
        return remindTimes;
    }

    public void setRemindTimes(String remindTimes) {
        this.remindTimes = remindTimes;
    }
}
