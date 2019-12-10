package com.hll_sc_app.bean.message;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/28
 */

public class UnreadResp {
    @SerializedName(value = "unreadNum", alternate = "feedbackUnreadNum")
    private int unreadNum;

    public int getUnreadNum() {
        return unreadNum;
    }

    public void setUnreadNum(int unreadNum) {
        this.unreadNum = unreadNum;
    }
}
