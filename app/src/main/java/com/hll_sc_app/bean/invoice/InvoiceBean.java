package com.hll_sc_app.bean.invoice;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/8/9
 */

public class InvoiceBean {
    private String createTime;
    private String id;
    private String imagePath;
    private String invoiceNO;
    private double invoicePrice;
    private int invoiceStatus;
    private String invoiceTime;
    private int invoiceType;
    private String purchaserID;
    private String purchaserName;
    private String purchaserShopID;
    private String purchaserShopName;
    private String rejectTime;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getInvoiceNO() {
        return invoiceNO;
    }

    public void setInvoiceNO(String invoiceNO) {
        this.invoiceNO = invoiceNO;
    }

    public double getInvoicePrice() {
        return invoicePrice;
    }

    public void setInvoicePrice(double invoicePrice) {
        this.invoicePrice = invoicePrice;
    }

    public int getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(int invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public String getInvoiceTime() {
        return invoiceTime;
    }

    public void setInvoiceTime(String invoiceTime) {
        this.invoiceTime = invoiceTime;
    }

    public int getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(int invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getPurchaserID() {
        return purchaserID;
    }

    public void setPurchaserID(String purchaserID) {
        this.purchaserID = purchaserID;
    }

    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(String purchaserName) {
        this.purchaserName = purchaserName;
    }

    public String getPurchaserShopID() {
        return purchaserShopID;
    }

    public void setPurchaserShopID(String purchaserShopID) {
        this.purchaserShopID = purchaserShopID;
    }

    public String getPurchaserShopName() {
        return purchaserShopName;
    }

    public void setPurchaserShopName(String purchaserShopName) {
        this.purchaserShopName = purchaserShopName;
    }

    public String getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(String rejectTime) {
        this.rejectTime = rejectTime;
    }
}
