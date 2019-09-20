package com.hll_sc_app.bean.order.place;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/20
 */

public class OrderCommitReq {
    /**
     * 1 购物车提交
     * 2 立即购买提交
     * 3 代客下单提交
     */
    private int isFromShopcart;
    private List<DiscountBean> discountList;
    private List<ExecuteDateBean> executeDateDtoList;
    private List<PayBean> payList;
    private String purchaserID;
    private String purchaserShopID;
    private List<RemarkBean> remarkDtoList;
    private String shopCartKey;

    public int getIsFromShopcart() {
        return isFromShopcart;
    }

    public void setIsFromShopcart(int isFromShopcart) {
        this.isFromShopcart = isFromShopcart;
    }

    public List<DiscountBean> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<DiscountBean> discountList) {
        this.discountList = discountList;
    }

    public List<ExecuteDateBean> getExecuteDateDtoList() {
        return executeDateDtoList;
    }

    public void setExecuteDateDtoList(List<ExecuteDateBean> executeDateDtoList) {
        this.executeDateDtoList = executeDateDtoList;
    }

    public List<PayBean> getPayList() {
        return payList;
    }

    public void setPayList(List<PayBean> payList) {
        this.payList = payList;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserShopID() {
        return purchaserShopID;
    }

    public void setPurchaserShopID(String purchaserShopID) {
        this.purchaserShopID = purchaserShopID;
    }

    public List<RemarkBean> getRemarkDtoList() {
        return remarkDtoList;
    }

    public void setRemarkDtoList(List<RemarkBean> remarkDtoList) {
        this.remarkDtoList = remarkDtoList;
    }

    public String getShopCartKey() {
        return shopCartKey;
    }

    public void setShopCartKey(String shopCartKey) {
        this.shopCartKey = shopCartKey;
    }

    public static class ExecuteDateBean {
        private int isWareHouse;
        private String subBillExecuteDate;
        private String subBillExecuteEndDate;
        private String supplierID;

        public int getIsWareHouse() {
            return isWareHouse;
        }

        public void setIsWareHouse(int isWareHouse) {
            this.isWareHouse = isWareHouse;
        }

        public String getSubBillExecuteDate() {
            return subBillExecuteDate;
        }

        public void setSubBillExecuteDate(String subBillExecuteDate) {
            this.subBillExecuteDate = subBillExecuteDate;
        }

        public String getSubBillExecuteEndDate() {
            return subBillExecuteEndDate;
        }

        public void setSubBillExecuteEndDate(String subBillExecuteEndDate) {
            this.subBillExecuteEndDate = subBillExecuteEndDate;
        }

        public String getSupplierID() {
            return supplierID;
        }

        public void setSupplierID(String supplierID) {
            this.supplierID = supplierID;
        }
    }

    public static class DiscountBean {
        private double discountAmount;
        private String groupID;
        private List<DiscountSpecBean> specList;

        public double getDiscountAmount() {
            return discountAmount;
        }

        public void setDiscountAmount(double discountAmount) {
            this.discountAmount = discountAmount;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public List<DiscountSpecBean> getSpecList() {
            return specList;
        }

        public void setSpecList(List<DiscountSpecBean> specList) {
            this.specList = specList;
        }

        public static class DiscountSpecBean {
            private double discountAmount;
            private String discountID;
            private String productID;
            private String ruleID;
            private String specID;

            public double getDiscountAmount() {
                return discountAmount;
            }

            public void setDiscountAmount(double discountAmount) {
                this.discountAmount = discountAmount;
            }

            public String getDiscountID() {
                return discountID;
            }

            public void setDiscountID(String discountID) {
                this.discountID = discountID;
            }

            public String getProductID() {
                return productID;
            }

            public void setProductID(String productID) {
                this.productID = productID;
            }

            public String getRuleID() {
                return ruleID;
            }

            public void setRuleID(String ruleID) {
                this.ruleID = ruleID;
            }

            public String getSpecID() {
                return specID;
            }

            public void setSpecID(String specID) {
                this.specID = specID;
            }
        }
    }

    public static class PayBean {
        private int payType;
        private String supplierID;
        private int wareHourseStatus;

        public int getPayType() {
            return payType;
        }

        public void setPayType(int payType) {
            this.payType = payType;
        }

        public String getSupplierID() {
            return supplierID;
        }

        public void setSupplierID(String supplierID) {
            this.supplierID = supplierID;
        }

        public int getWareHourseStatus() {
            return wareHourseStatus;
        }

        public void setWareHourseStatus(int wareHourseStatus) {
            this.wareHourseStatus = wareHourseStatus;
        }
    }

    public static class RemarkBean {
        private int isWareHouse;
        private String purchaserShopID;
        private String remark;
        private String supplyShopID;

        public int getIsWareHouse() {
            return isWareHouse;
        }

        public void setIsWareHouse(int isWareHouse) {
            this.isWareHouse = isWareHouse;
        }

        public String getPurchaserShopID() {
            return purchaserShopID;
        }

        public void setPurchaserShopID(String purchaserShopID) {
            this.purchaserShopID = purchaserShopID;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getSupplyShopID() {
            return supplyShopID;
        }

        public void setSupplyShopID(String supplyShopID) {
            this.supplyShopID = supplyShopID;
        }
    }
}
