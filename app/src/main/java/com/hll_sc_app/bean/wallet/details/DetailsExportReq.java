package com.hll_sc_app.bean.wallet.details;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/5/7
 */

public class DetailsExportReq {
    private int actionType;
    private String email;
    private int isBindEmail;
    private String userID;
    private String typeCode;
    private Params params;

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIsBindEmail() {
        return isBindEmail;
    }

    public void setIsBindEmail(int isBindEmail) {
        this.isBindEmail = isBindEmail;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public Params getParams() {
        return params;
    }

    public void setParams(Params params) {
        this.params = params;
    }

    static public class Params {
        private FinancialParams fnancialDetail;

        public FinancialParams getFnancialDetail() {
            return fnancialDetail;
        }

        public void setFnancialDetail(FinancialParams fnancialDetail) {
            this.fnancialDetail = fnancialDetail;
        }
    }

    static public class FinancialParams {
        private String beginTime;
        private String endTime;
        private String groupID;
        private String settleUnitID;

        public String getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(String beginTime) {
            this.beginTime = beginTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public String getSettleUnitID() {
            return settleUnitID;
        }

        public void setSettleUnitID(String settleUnitID) {
            this.settleUnitID = settleUnitID;
        }
    }
}
