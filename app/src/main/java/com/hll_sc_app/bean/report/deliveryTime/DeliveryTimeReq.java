package com.hll_sc_app.bean.report.deliveryTime;

/**
 * @author chukun
 * @date 2019.08.16
 * 配送及时率的请求参数
 */
public class DeliveryTimeReq {

    private String  startDate;
    private String  endDate;
    private String  groupID;
    private Integer needNearlyData;
    private int     pageNum;
    private int     pageSize;
    private int     timeFlag;

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public Integer getNeedNearlyData() {
        return needNearlyData;
    }

    public void setNeedNearlyData(Integer needNearlyData) {
        this.needNearlyData = needNearlyData;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTimeFlag() {
        return timeFlag;
    }

    public void setTimeFlag(int timeFlag) {
        this.timeFlag = timeFlag;
    }
}
