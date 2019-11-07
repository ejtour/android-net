package com.hll_sc_app.bean.cardmanage;

import java.util.List;

public class CardLogResp {
    private List<CardLogBean> records;
    private int total;

    public List<CardLogBean> getRecords() {
        return records;
    }

    public void setRecords(List<CardLogBean> records) {
        this.records = records;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public static class CardLogBean {
        private String cardNo;
        private String handleBy;
        private String handleTime;
        private int handleType;
        private String remark;

        public String getHandleTypeText() {
            if (handleType == 1) {
                return "开卡";
            } else if (handleType == 2) {
                return "冻结";
            } else if (handleType == 3) {
                return "解除冻结";
            } else if (handleType == 4) {
                return "注销";
            } else {
                return "";
            }
        }

        public String getCardNo() {
            return cardNo;
        }

        public void setCardNo(String cardNo) {
            this.cardNo = cardNo;
        }

        public String getHandleBy() {
            return handleBy;
        }

        public void setHandleBy(String handleBy) {
            this.handleBy = handleBy;
        }

        public String getHandleTime() {
            return handleTime;
        }

        public void setHandleTime(String handleTime) {
            this.handleTime = handleTime;
        }

        public int getHandleType() {
            return handleType;
        }

        public void setHandleType(int handleType) {
            this.handleType = handleType;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
