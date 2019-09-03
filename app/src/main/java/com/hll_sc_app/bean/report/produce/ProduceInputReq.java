package com.hll_sc_app.bean.report.produce;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/30
 */

public class ProduceInputReq {
    private String classes;
    private String date;
    /**
     * 0-录入，1-修改
     */
    private int flag;
    private String groupID;
    private String inputPer;
    private List<ProduceDetailBean> records;

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getInputPer() {
        return inputPer;
    }

    public void setInputPer(String inputPer) {
        this.inputPer = inputPer;
    }

    public List<ProduceDetailBean> getRecords() {
        return records;
    }

    public void setRecords(List<ProduceDetailBean> records) {
        this.records = records;
    }
}
