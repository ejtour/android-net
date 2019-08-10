package com.hll_sc_app.bean.export;

import android.text.TextUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/14
 */

public class OrderExportReq {
    private List<String> subBillIds;
    private String groupID;
    private String email;
    private int isBindEmail;

    public OrderExportReq(List<String> subBillIds, String groupID, String email) {
        this.subBillIds = subBillIds;
        this.groupID = groupID;
        this.email = email;
        this.isBindEmail = TextUtils.isEmpty(email) ? 0 : 1;
    }

    public List<String> getSubBillIds() {
        return subBillIds;
    }

    public String getGroupID() {
        return groupID;
    }

    public String getEmail() {
        return email;
    }

    public int getIsBindEmail() {
        return isBindEmail;
    }
}
