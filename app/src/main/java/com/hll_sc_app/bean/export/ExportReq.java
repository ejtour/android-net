package com.hll_sc_app.bean.export;

import java.util.List;

/**
 * 统一导出文件请求参数
 *
 * @author zhuyingsong
 * @since 2019/7/3
 */

public class ExportReq {
    /**
     * 1.直接导出2.发送邮件3.查询服务器文件地址
     */
    private String actionType = "2";
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 是否绑定邮箱 0.否1.是
     */
    private String isBindEmail;
    /**
     * 请求参数
     */
    private ParamsBean params;
    /**
     * 导出投诉列表(complaint_info)
     * 导出售价变更日志（priceFlow）
     * 导出协议价列表（quotation）
     * 代仓商品导出（warehouse_product）
     * 平台分类（category）
     * 库存流水（stock_flow）
     * 规格上下记录导出（skuShelfFlow），
     * 供应商常采清单（supplier_common_export）
     * 合作关系（cooperation）
     * 税率（taxRate）
     * 商品价格（product_price）
     * 财务明细 (fnancialDetail)
     * 发票业务 (invoice)
     * 代仓预警值（stock_warn_num）
     * 售价管理 (sell_price)
     * 协议价（common_quotation）
     * 活动列表（discount）
     */
    private String typeCode;
    /**
     * 用户ID
     */
    private String userID;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getActionType() {
        return actionType;
    }

    public void setActionType(String actionType) {
        this.actionType = actionType;
    }

    public String getIsBindEmail() {
        return isBindEmail;
    }

    public void setIsBindEmail(String isBindEmail) {
        this.isBindEmail = isBindEmail;
    }

    public ParamsBean getParams() {
        return params;
    }

    public void setParams(ParamsBean params) {
        this.params = params;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public static class ParamsBean {

        private CommonQuotation commonQuotation;
        /**
         * 规格上下架日志导出
         */
        private SkuShelfFlowBean skuShelfFlow;
        /**
         * 协议价请求参数
         */
        private SupplierQuotationBean supplierQuotation;
        /**
         * 售价变更请求参数
         */
        private PriceFlowBean priceFlow;
        /**
         * 合作关系请求参数
         */
        private CooperationBean cooperation;

        /**
         * 财务明细导出
         */
        private FinancialParams fnancialDetail;

        /**
         * 发票业务导出
         */
        private InvoiceParams invoice;

        /**
         * 代仓库存预警
         */
        private StockWarnNum stockWarnNum;

        /**
         * 售价管理导出
         *
         * @return
         */
        private SellPrice sellPrice;

        /**
         * 供应商常采清单
         */
        private CommonExport supplierCommonExport;

        private Discount discount;

        public Discount getDiscount() {
            return discount;
        }

        public void setDiscount(Discount discount) {
            this.discount = discount;
        }

        private String groupID;
        private String pageNum;
        private String pageSize;
        private String houseID;
        private String businessType;
        private String searchKey;
        private String createTimeStart;
        private String createTimeEnd;

        public CommonQuotation getCommonQuotation() {
            return commonQuotation;
        }

        public void setCommonQuotation(CommonQuotation commonQuotation) {
            this.commonQuotation = commonQuotation;
        }

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
        }

        public String getPageNum() {
            return pageNum;
        }

        public void setPageNum(String pageNum) {
            this.pageNum = pageNum;
        }

        public String getPageSize() {
            return pageSize;
        }

        public void setPageSize(String pageSize) {
            this.pageSize = pageSize;
        }

        public String getHouseID() {
            return houseID;
        }

        public void setHouseID(String houseID) {
            this.houseID = houseID;
        }

        public String getBusinessType() {
            return businessType;
        }

        public void setBusinessType(String businessType) {
            this.businessType = businessType;
        }

        public String getSearchKey() {
            return searchKey;
        }

        public void setSearchKey(String searchKey) {
            this.searchKey = searchKey;
        }

        public String getCreateTimeStart() {
            return createTimeStart;
        }

        public void setCreateTimeStart(String createTimeStart) {
            this.createTimeStart = createTimeStart;
        }

        public String getCreateTimeEnd() {
            return createTimeEnd;
        }

        public void setCreateTimeEnd(String createTimeEnd) {
            this.createTimeEnd = createTimeEnd;
        }

        public SellPrice getSellPrice() {
            return sellPrice;
        }

        public void setSellPrice(SellPrice sellPrice) {
            this.sellPrice = sellPrice;
        }

        public CommonExport getSupplierCommonExport() {
            return supplierCommonExport;
        }

        public void setSupplierCommonExport(CommonExport supplierCommonExport) {
            this.supplierCommonExport = supplierCommonExport;
        }

        public StockWarnNum getStockWarnNum() {
            return stockWarnNum;
        }

        public void setStockWarnNum(StockWarnNum stockWarnNum) {
            this.stockWarnNum = stockWarnNum;
        }

        public InvoiceParams getInvoice() {
            return invoice;
        }

        public void setInvoice(InvoiceParams invoice) {
            this.invoice = invoice;
        }

        public FinancialParams getFnancialDetail() {
            return fnancialDetail;
        }

        public void setFnancialDetail(FinancialParams fnancialDetail) {
            this.fnancialDetail = fnancialDetail;
        }

        public CooperationBean getCooperation() {
            return cooperation;
        }

        public void setCooperation(CooperationBean cooperation) {
            this.cooperation = cooperation;
        }

        public PriceFlowBean getPriceFlow() {
            return priceFlow;
        }

        public void setPriceFlow(PriceFlowBean priceFlow) {
            this.priceFlow = priceFlow;
        }

        public SupplierQuotationBean getSupplierQuotation() {
            return supplierQuotation;
        }

        public void setSupplierQuotation(SupplierQuotationBean supplierQuotation) {
            this.supplierQuotation = supplierQuotation;
        }

        public SkuShelfFlowBean getSkuShelfFlow() {
            return skuShelfFlow;
        }

        public void setSkuShelfFlow(SkuShelfFlowBean skuShelfFlow) {
            this.skuShelfFlow = skuShelfFlow;
        }

        public static class SkuShelfFlowBean {
            private String groupID;

            public String getGroupID() {
                return groupID;
            }

            public void setGroupID(String groupID) {
                this.groupID = groupID;
            }
        }

        public static class SupplierQuotationBean {
            private String groupID;
            private List<String> billNos;

            public String getGroupID() {
                return groupID;
            }

            public void setGroupID(String groupID) {
                this.groupID = groupID;
            }

            public List<String> getBillNos() {
                return billNos;
            }

            public void setBillNos(List<String> billNos) {
                this.billNos = billNos;
            }
        }

        public static class CooperationBean {
            private String groupID;

            public String getGroupID() {
                return groupID;
            }

            public void setGroupID(String groupID) {
                this.groupID = groupID;
            }
        }

        public static class PriceFlowBean {
            private String groupID;
            private String endTime;
            private String startTime;
            private String productName;

            public String getGroupID() {
                return groupID;
            }

            public void setGroupID(String groupID) {
                this.groupID = groupID;
            }

            public String getEndTime() {
                return endTime;
            }

            public void setEndTime(String endTime) {
                this.endTime = endTime;
            }

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }
        }

        public static class FinancialParams {
            private String beginTime;
            private String endTime;
            private String groupID;
            private String settleUnitID;
            private String transType;

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

            public String getTransType() {
                return transType;
            }

            public void setTransType(String transType) {
                this.transType = transType;
            }
        }

        public static class InvoiceParams {
            private String startTime;
            private String endTime;
            private String groupID;
            private int invoiceStatus;

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
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

            public int getInvoiceStatus() {
                return invoiceStatus;
            }

            public void setInvoiceStatus(int invoiceStatus) {
                this.invoiceStatus = invoiceStatus;
            }
        }

        public static class StockWarnNum {
            private String cargoOwnerID;
            private String groupID;
            private String houseID;

            public String getCargoOwnerID() {
                return cargoOwnerID;
            }

            public void setCargoOwnerID(String cargoOwnerID) {
                this.cargoOwnerID = cargoOwnerID;
            }

            public String getGroupID() {
                return groupID;
            }

            public void setGroupID(String groupID) {
                this.groupID = groupID;
            }

            public String getHouseID() {
                return houseID;
            }

            public void setHouseID(String houseID) {
                this.houseID = houseID;
            }
        }


        public static class SellPrice {
            private String groupID;
            private String name;
            private String productStatus;
            private String shopProductCategoryThreeIds;
            private String isWareHourse;

            public String getGroupID() {
                return groupID;
            }

            public void setGroupID(String groupID) {
                this.groupID = groupID;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProductStatus() {
                return productStatus;
            }

            public void setProductStatus(String productStatus) {
                this.productStatus = productStatus;
            }

            public String getShopProductCategoryThreeIds() {
                return shopProductCategoryThreeIds;
            }

            public void setShopProductCategoryThreeIds(String shopProductCategoryThreeIds) {
                this.shopProductCategoryThreeIds = shopProductCategoryThreeIds;
            }

            public String getIsWareHourse() {
                return isWareHourse;
            }

            public void setIsWareHourse(String isWareHourse) {
                this.isWareHourse = isWareHourse;
            }
        }

        public static class CommonExport{
            private String actionType;
            private int flag;
            private String supplyID;
            private String purchaserID;
            private String shopID;

            public String getActionType() {
                return actionType;
            }

            public void setActionType(String actionType) {
                this.actionType = actionType;
            }

            public int getFlag() {
                return flag;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public String getSupplyID() {
                return supplyID;
            }

            public void setSupplyID(String supplyID) {
                this.supplyID = supplyID;
            }

            public String getPurchaserID() {
                return purchaserID;
            }

            public void setPurchaserID(String purchaserID) {
                this.purchaserID = purchaserID;
            }

            public String getShopID() {
                return shopID;
            }

            public void setShopID(String shopID) {
                this.shopID = shopID;
            }
        }

        public static class CommonQuotation{
            private String billCreateBy;
            private List<String> billNos;
            /*报价单状态 1-未审核 2-已审核 3-已驳回 4-已取消 5-已到期 6-放弃*/
            private int billStatus;
            /*1协议价低于成本价*/
            private int costFlag;
            private List<String> categoryIDs;
            private String startDate;
            private String endDate;
            private String groupID;
            private String priceEndDate;
            private String priceStartDate;
            private String productName;
            private String purchaserID;
            private String searchParams;
            private List<String> productCode;
            private List<String> shopIDs;

            public String getBillCreateBy() {
                return billCreateBy;
            }

            public void setBillCreateBy(String billCreateBy) {
                this.billCreateBy = billCreateBy;
            }

            public List<String> getBillNos() {
                return billNos;
            }

            public void setBillNos(List<String> billNos) {
                this.billNos = billNos;
            }

            public int getBillStatus() {
                return billStatus;
            }

            public void setBillStatus(int billStatus) {
                this.billStatus = billStatus;
            }

            public int getCostFlag() {
                return costFlag;
            }

            public void setCostFlag(int costFlag) {
                this.costFlag = costFlag;
            }

            public List<String> getCategoryIDs() {
                return categoryIDs;
            }

            public void setCategoryIDs(List<String> categoryIDs) {
                this.categoryIDs = categoryIDs;
            }

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

            public String getPriceEndDate() {
                return priceEndDate;
            }

            public void setPriceEndDate(String priceEndDate) {
                this.priceEndDate = priceEndDate;
            }

            public String getPriceStartDate() {
                return priceStartDate;
            }

            public void setPriceStartDate(String priceStartDate) {
                this.priceStartDate = priceStartDate;
            }

            public String getProductName() {
                return productName;
            }

            public void setProductName(String productName) {
                this.productName = productName;
            }

            public String getPurchaserID() {
                return purchaserID;
            }

            public void setPurchaserID(String purchaserID) {
                this.purchaserID = purchaserID;
            }

            public String getSearchParams() {
                return searchParams;
            }

            public void setSearchParams(String searchParams) {
                this.searchParams = searchParams;
            }

            public List<String> getProductCode() {
                return productCode;
            }

            public void setProductCode(List<String> productCode) {
                this.productCode = productCode;
            }

            public List<String> getShopIDs() {
                return shopIDs;
            }

            public void setShopIDs(List<String> shopIDs) {
                this.shopIDs = shopIDs;
            }
        }


        public static class Discount{
            private String discountRuleType;
            private String discountStatus;
            private String endTime;
            private String groupID;
            private String startTime;
            private String discountType;

            public String getDiscountType() {
                return discountType;
            }

            public void setDiscountType(String discountType) {
                this.discountType = discountType;
            }

            public String getDiscountRuleType() {
                return discountRuleType;
            }

            public void setDiscountRuleType(String discountRuleType) {
                this.discountRuleType = discountRuleType;
            }

            public String getDiscountStatus() {
                return discountStatus;
            }

            public void setDiscountStatus(String discountStatus) {
                this.discountStatus = discountStatus;
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

            public String getStartTime() {
                return startTime;
            }

            public void setStartTime(String startTime) {
                this.startTime = startTime;
            }
        }
    }



}
