package com.hll_sc_app.bean.aptitude;

import com.hll_sc_app.base.utils.UserConfig;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public class AptitudeInfoReq {
    private List<AptitudeInfoKV> aptitudeList;
    private String groupID;

    public AptitudeInfoReq() {
        groupID = UserConfig.getGroupID();
    }

    public List<AptitudeInfoKV> getAptitudeList() {
        return aptitudeList;
    }

    public void setAptitudeList(List<AptitudeInfoKV> aptitudeList) {
        this.aptitudeList = aptitudeList;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }
}
