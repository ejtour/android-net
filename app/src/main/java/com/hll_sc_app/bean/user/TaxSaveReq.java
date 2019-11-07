package com.hll_sc_app.bean.user;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/30
 */

public class TaxSaveReq {
    private String groupID;
    private List<TaxSaveBean> categorys;

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public List<TaxSaveBean> getCategorys() {
        return categorys;
    }

    public void setCategorys(List<TaxSaveBean> categorys) {
        this.categorys = categorys;
    }
}
