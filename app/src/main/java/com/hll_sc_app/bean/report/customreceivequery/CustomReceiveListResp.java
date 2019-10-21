package com.hll_sc_app.bean.report.customreceivequery;

import java.util.List;

/***
 * 客户收货查询
 * 列表请求响应
 */
public class CustomReceiveListResp {
    private int totalSize;
    private List<CustomReceiveBean> list;


    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }

    public List<CustomReceiveBean> getList() {
        return list;
    }

    public void setList(List<CustomReceiveBean> list) {
        this.list = list;
    }

    public static class CustomReceiveBean {
        private String no;//CK201910170556
        private String statusName;//状态
        private int status;//状态
        private String typeName;
        private int type;//类型
        private double count;//数量
        private double money;//金额

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getStatusName() {
            return statusName;
        }

        public void setStatusName(String statusName) {
            this.statusName = statusName;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public double getCount() {
            return count;
        }

        public void setCount(double count) {
            this.count = count;
        }

        public double getMoney() {
            return money;
        }

        public void setMoney(double money) {
            this.money = money;
        }
    }
}
