package com.hll_sc_app.bean.aptitude;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2/8/21.
 */
public class AptitudeExpireResp {
    private boolean hasExpiration;
    private String reminderMessage;

    public boolean isHasExpiration() {
        return hasExpiration;
    }

    public void setHasExpiration(boolean hasExpiration) {
        this.hasExpiration = hasExpiration;
    }

    public String getReminderMessage() {
        return reminderMessage;
    }

    public void setReminderMessage(String reminderMessage) {
        this.reminderMessage = reminderMessage;
    }
}
