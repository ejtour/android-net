package com.hll_sc_app.bean.aptitude;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public class AptitudeInfoResp {
    private List<String> floorSpace;
    private List<String> landStatus;
    private List<String> processManCount;
    private List<String> qualityManCount;
    private List<String> companyManCount;
    private List<String> bankName;
    private List<String> bankAccount;
    private List<String> majorProduct;
    private List<String> invoiceType;
    private List<String> productCapacity;
    private List<String> deliveryCycle;
    private List<String> paymentWay;
    private List<String> paymentCycle;
    private List<String> productStandard;
    private List<String> productCertification;

    private List<String> certificateAuthorization;
    private List<String> certificateAuthorizationImage;
    private List<String> manufacturerInfo;
    private List<String> manufacturerInfoImage;
    private List<String> supplyFor;
    private List<String> hasQualitySystem;
    private List<String> hasStandardProcedure;

    private String processList(List<String> in) {
        return CommonUtils.isEmpty(in) ? null : in.get(0);
    }

    public String getFloorSpace() {
        return processList(floorSpace);
    }

    public void setFloorSpace(List<String> floorSpace) {
        this.floorSpace = floorSpace;
    }

    public String getLandStatus() {
        return processList(landStatus);
    }

    public void setLandStatus(List<String> landStatus) {
        this.landStatus = landStatus;
    }

    public String getProcessManCount() {
        return processList(processManCount);
    }

    public void setProcessManCount(List<String> processManCount) {
        this.processManCount = processManCount;
    }

    public String getQualityManCount() {
        return processList(qualityManCount);
    }

    public void setQualityManCount(List<String> qualityManCount) {
        this.qualityManCount = qualityManCount;
    }

    public String getCompanyManCount() {
        return processList(companyManCount);
    }

    public void setCompanyManCount(List<String> companyManCount) {
        this.companyManCount = companyManCount;
    }

    public String getBankName() {
        return processList(bankName);
    }

    public void setBankName(List<String> bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return processList(bankAccount);
    }

    public void setBankAccount(List<String> bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getMajorProduct() {
        return processList(majorProduct);
    }

    public void setMajorProduct(List<String> majorProduct) {
        this.majorProduct = majorProduct;
    }

    public String getInvoiceType() {
        return processList(invoiceType);
    }

    public void setInvoiceType(List<String> invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getProductCapacity() {
        return processList(productCapacity);
    }

    public void setProductCapacity(List<String> productCapacity) {
        this.productCapacity = productCapacity;
    }

    public String getDeliveryCycle() {
        return processList(deliveryCycle);
    }

    public void setDeliveryCycle(List<String> deliveryCycle) {
        this.deliveryCycle = deliveryCycle;
    }

    public List<String> getPaymentWay() {
        return paymentWay;
    }

    public void setPaymentWay(List<String> paymentWay) {
        this.paymentWay = paymentWay;
    }

    public String getPaymentCycle() {
        return processList(paymentCycle);
    }

    public void setPaymentCycle(List<String> paymentCycle) {
        this.paymentCycle = paymentCycle;
    }

    public String getProductStandard() {
        return processList(productStandard);
    }

    public void setProductStandard(List<String> productStandard) {
        this.productStandard = productStandard;
    }

    public List<String> getProductCertification() {
        return productCertification;
    }

    public void setProductCertification(List<String> productCertification) {
        this.productCertification = productCertification;
    }

    public String getCertificateAuthorization() {
        return processList(certificateAuthorization);
    }

    public void setCertificateAuthorization(List<String> certificateAuthorization) {
        this.certificateAuthorization = certificateAuthorization;
    }

    public String getCertificateAuthorizationImage() {
        return processList(certificateAuthorizationImage);
    }

    public void setCertificateAuthorizationImage(List<String> certificateAuthorizationImage) {
        this.certificateAuthorizationImage = certificateAuthorizationImage;
    }

    public String getManufacturerInfo() {
        return processList(manufacturerInfo);
    }

    public void setManufacturerInfo(List<String> manufacturerInfo) {
        this.manufacturerInfo = manufacturerInfo;
    }

    public String getManufacturerInfoImage() {
        return processList(manufacturerInfoImage);
    }

    public void setManufacturerInfoImage(List<String> manufacturerInfoImage) {
        this.manufacturerInfoImage = manufacturerInfoImage;
    }

    public String getSupplyFor() {
        return processList(supplyFor);
    }

    public void setSupplyFor(List<String> supplyFor) {
        this.supplyFor = supplyFor;
    }

    public String getHasQualitySystem() {
        return processList(hasQualitySystem);
    }

    public void setHasQualitySystem(List<String> hasQualitySystem) {
        this.hasQualitySystem = hasQualitySystem;
    }

    public String getHasStandardProcedure() {
        return processList(hasStandardProcedure);
    }

    public void setHasStandardProcedure(List<String> hasStandardProcedure) {
        this.hasStandardProcedure = hasStandardProcedure;
    }
}
