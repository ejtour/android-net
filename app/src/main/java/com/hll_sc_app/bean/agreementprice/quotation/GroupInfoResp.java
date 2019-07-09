package com.hll_sc_app.bean.agreementprice.quotation;

import java.util.List;

/**
 * 正式签约的集团
 *
 * @author zhuyingsong
 * @date 2019-07-09
 */
public class GroupInfoResp {
    private List<GroupBean> groupInfos;
    private int totalNum;

    public List<GroupBean> getGroupInfos() {
        return groupInfos;
    }

    public void setGroupInfos(List<GroupBean> groupInfos) {
        this.groupInfos = groupInfos;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }
}
