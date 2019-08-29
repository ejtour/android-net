package com.hll_sc_app.bean.report.purchase;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/29
 */

public class ManHourReq {
    private List<ManHourReqBean> records;

    public List<ManHourReqBean> getRecords() {
        return records;
    }

    public void setRecords(List<ManHourReqBean> records) {
        this.records = records;
    }

    public static class ManHourReqBean {
        private String coopGroupName;
        private String groupID;
        private List<ManHourReqParam> params;

        public String getCoopGroupName() {
            return coopGroupName;
        }

        public void setCoopGroupName(String coopGroupName) {
            this.coopGroupName = coopGroupName;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public List<ManHourReqParam> getParams() {
            return params;
        }

        public void setParams(List<ManHourReqParam> params) {
            this.params = params;
        }
    }

    public static class ManHourReqParam {
        public ManHourReqParam(String paramKey, String paramValues) {
            this.paramKey = paramKey;
            this.paramValues = paramValues;
        }

        private String paramKey;
        private String paramValues;

        public String getParamKey() {
            return paramKey;
        }

        public String getParamValues() {
            return paramValues;
        }

    }
}
