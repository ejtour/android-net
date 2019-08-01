package com.hll_sc_app.bean.wallet;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/30
 */

public class AuthInfo implements Parcelable {

    private int groupType=0;
    private String busiPermissionBeginDate;
    private String busiPermissionEndDate;
    private String imgContactProxyContract;
    private String proxyProtocol;
    private String companyName;
    private String companyShortName;
    private String licenseCode;
    private String imgBankLicense;
    private String businessAddress;
    private String contactIDCardNo;
    private String imgHandProxyIDCardFront;
    private String imgProxyContract;
    private String comBankBranchName;
    private String imgTaxLicense;
    private String proxyAddress;
    private String bankCityName;
    private String imgBusiDoor;
    private String lpIDCardPeriod;
    private String strLicensePeriod;
    private String imgLPIDCardFront;
    private String bankProvinceCode;
    private String comBankProvinceName;
    private String licenseProvinceCode;
    private String groupName;
    private String imgProxyIDCardBack;
    private String licenseBeginDate;
    private int bankPersonType;
    private String imgHandProxyIDCardBack;
    private String imgBusiCounter;
    private String submitterName;
    private int status;
    private String merchantNo;
    private String groupCreateTime;
    private String customerServiceTel;
    private String actionTime;
    private int lpCardType;
    private String industry;
    private String operatorName;
    private String settleUnitName;
    private int unitType;
    private String strLpIDCardPeriodBeginDate;
    private String licensePeriod;
    private String lpIDCardNo;
    private String licenseAddress;
    private String licenseCityName;
    private String industryName;
    private String bankCode;
    private String showBankName;
    private String imgBusiEnv;
    private String address;
    private String actionBy;
    private String operatorDuty;
    private String imgHandLPIDCard;
    private String imgContactIDCardBack;
    private String companyLocation;
    private String createTime;
    private String operatorMobile;
    private String orgLicenceCode;
    private String bankProvinceName;
    private String comBankAccount;
    private String proxyIDCardPeriod;
    private String proxyName;
    private String licenseProvinceName;
    private String strLicenseBeginDate;
    private String imgBusiPermission;
    private String proxyTel;
    private String imgProxyIDCardFront;
    private String operatorEmail;
    private String lpPhone;
    private String comBankCityName;
    private String busiScope;
    private String bankAccount;
    private String licenseDistrictName;
    private String imgContactIDCardFront;
    private String groupID;
    private String imgOrgLicense;
    private int legalPersonSource;
    private String submitterTel;
    private String licenseName;
    private String proxyIDCardBeginDate;
    private String comReceiverName;
    private String unit;
    private String comBankNo;
    private String lpName;
    private String settleUnitID;
    private String bankCityCode;
    private String imgMerchantEntry;
    private String proxyIDCardNo;
    private String lpIDCardPeriodBeginDate;
    private String bankName;
    private String companyUrl;
    private String licenseDistrictCode;
    private int isImgBusiPermission;
    private String strLpIDCardPeriod;
    private String imgLPIDCardBack;
    private int industryType;
    private String comBankProvinceCode;
    private String bankNo;
    private String lpAddress;
    private String receiverName;
    private String docID;
    private String bankLocation;
    private String comBankCityCode;
    private String imgLicense;
    private String comBankName;
    private String licenseCityCode;
    private String weixinMPAPPID;
    public static final Creator<AuthInfo> CREATOR = new Creator<AuthInfo>() {
        @Override
        public AuthInfo createFromParcel(Parcel source) {
            return new AuthInfo(source);
        }

        @Override
        public AuthInfo[] newArray(int size) {
            return new AuthInfo[size];
        }
    };

    public String getImgContactProxyContract() {
        return imgContactProxyContract;
    }

    public String getBusiPermissionBeginDate() {
        return busiPermissionBeginDate;
    }

    public void setBusiPermissionBeginDate(String busiPermissionBeginDate) {
        this.busiPermissionBeginDate = busiPermissionBeginDate;
    }

    public String getBusiPermissionEndDate() {
        return busiPermissionEndDate;
    }

    public void setBusiPermissionEndDate(String busiPermissionEndDate) {
        this.busiPermissionEndDate = busiPermissionEndDate;
    }

    public void setImgContactProxyContract(String imgContactProxyContract) {
        this.imgContactProxyContract = imgContactProxyContract;
    }

    public String getProxyProtocol() {
        return proxyProtocol;
    }

    public void setProxyProtocol(String proxyProtocol) {
        this.proxyProtocol = proxyProtocol;
    }

    public int getGroupType() {
        return groupType;
    }

    public void setGroupType(int groupType) {
        this.groupType = groupType;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyShortName() {
        return companyShortName;
    }

    public void setCompanyShortName(String companyShortName) {
        this.companyShortName = companyShortName;
    }

    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    public String getImgBankLicense() {
        return imgBankLicense;
    }

    public void setImgBankLicense(String imgBankLicense) {
        this.imgBankLicense = imgBankLicense;
    }

    public String getContactIDCardNo() {
        return contactIDCardNo;
    }

    public void setContactIDCardNo(String contactIDCardNo) {
        this.contactIDCardNo = contactIDCardNo;
    }

    public String getImgHandProxyIDCardFront() {
        return imgHandProxyIDCardFront;
    }

    public void setImgHandProxyIDCardFront(String imgHandProxyIDCardFront) {
        this.imgHandProxyIDCardFront = imgHandProxyIDCardFront;
    }

    public String getImgProxyContract() {
        return imgProxyContract;
    }

    public void setImgProxyContract(String imgProxyContract) {
        this.imgProxyContract = imgProxyContract;
    }

    public String getComBankBranchName() {
        return comBankBranchName;
    }

    public void setComBankBranchName(String comBankBranchName) {
        this.comBankBranchName = comBankBranchName;
    }

    public String getImgTaxLicense() {
        return imgTaxLicense;
    }

    public void setImgTaxLicense(String imgTaxLicense) {
        this.imgTaxLicense = imgTaxLicense;
    }

    public String getProxyAddress() {
        return proxyAddress;
    }

    public void setProxyAddress(String proxyAddress) {
        this.proxyAddress = proxyAddress;
    }

    public String getBankCityName() {
        return bankCityName;
    }

    public void setBankCityName(String bankCityName) {
        this.bankCityName = bankCityName;
    }

    public String getImgBusiDoor() {
        return imgBusiDoor;
    }

    public void setImgBusiDoor(String imgBusiDoor) {
        this.imgBusiDoor = imgBusiDoor;
    }

    public String getLpIDCardPeriod() {
        return lpIDCardPeriod;
    }

    public void setLpIDCardPeriod(String lpIDCardPeriod) {
        this.lpIDCardPeriod = lpIDCardPeriod;
    }

    public String getStrLicensePeriod() {
        return strLicensePeriod;
    }

    public void setStrLicensePeriod(String strLicensePeriod) {
        this.strLicensePeriod = strLicensePeriod;
    }

    public String getImgLPIDCardFront() {
        return imgLPIDCardFront;
    }

    public void setImgLPIDCardFront(String imgLPIDCardFront) {
        this.imgLPIDCardFront = imgLPIDCardFront;
    }

    public String getBankProvinceCode() {
        return bankProvinceCode;
    }

    public void setBankProvinceCode(String bankProvinceCode) {
        this.bankProvinceCode = bankProvinceCode;
    }

    public String getComBankProvinceName() {
        return comBankProvinceName;
    }

    public void setComBankProvinceName(String comBankProvinceName) {
        this.comBankProvinceName = comBankProvinceName;
    }

    public String getLicenseProvinceCode() {
        return licenseProvinceCode;
    }

    public void setLicenseProvinceCode(String licenseProvinceCode) {
        this.licenseProvinceCode = licenseProvinceCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getImgProxyIDCardBack() {
        return imgProxyIDCardBack;
    }

    public void setImgProxyIDCardBack(String imgProxyIDCardBack) {
        this.imgProxyIDCardBack = imgProxyIDCardBack;
    }

    public String getLicenseBeginDate() {
        return licenseBeginDate;
    }

    public void setLicenseBeginDate(String licenseBeginDate) {
        this.licenseBeginDate = licenseBeginDate;
    }

    public int getBankPersonType() {
        return bankPersonType;
    }

    public void setBankPersonType(int bankPersonType) {
        this.bankPersonType = bankPersonType;
    }

    public String getImgHandProxyIDCardBack() {
        return imgHandProxyIDCardBack;
    }

    public void setImgHandProxyIDCardBack(String imgHandProxyIDCardBack) {
        this.imgHandProxyIDCardBack = imgHandProxyIDCardBack;
    }

    public String getImgBusiCounter() {
        return imgBusiCounter;
    }

    public void setImgBusiCounter(String imgBusiCounter) {
        this.imgBusiCounter = imgBusiCounter;
    }

    public String getSubmitterName() {
        return submitterName;
    }

    public void setSubmitterName(String submitterName) {
        this.submitterName = submitterName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getGroupCreateTime() {
        return groupCreateTime;
    }

    public void setGroupCreateTime(String groupCreateTime) {
        this.groupCreateTime = groupCreateTime;
    }

    public String getCustomerServiceTel() {
        return customerServiceTel;
    }

    public void setCustomerServiceTel(String customerServiceTel) {
        this.customerServiceTel = customerServiceTel;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public int getLpCardType() {
        return lpCardType;
    }

    public void setLpCardType(int lpCardType) {
        this.lpCardType = lpCardType;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getSettleUnitName() {
        return settleUnitName;
    }

    public void setSettleUnitName(String settleUnitName) {
        this.settleUnitName = settleUnitName;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public String getStrLpIDCardPeriodBeginDate() {
        return strLpIDCardPeriodBeginDate;
    }

    public void setStrLpIDCardPeriodBeginDate(String strLpIDCardPeriodBeginDate) {
        this.strLpIDCardPeriodBeginDate = strLpIDCardPeriodBeginDate;
    }

    public String getLicensePeriod() {
        return licensePeriod;
    }

    public void setLicensePeriod(String licensePeriod) {
        this.licensePeriod = licensePeriod;
    }

    public String getLpIDCardNo() {
        return lpIDCardNo;
    }

    public void setLpIDCardNo(String lpIDCardNo) {
        this.lpIDCardNo = lpIDCardNo;
    }

    public String getLicenseAddress() {
        return licenseAddress;
    }

    public void setLicenseAddress(String licenseAddress) {
        this.licenseAddress = licenseAddress;
    }

    public String getLicenseCityName() {
        return licenseCityName;
    }

    public void setLicenseCityName(String licenseCityName) {
        this.licenseCityName = licenseCityName;
    }

    public String getIndustryName() {
        return industryName;
    }

    public void setIndustryName(String industryName) {
        this.industryName = industryName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getShowBankName() {
        return showBankName;
    }

    public void setShowBankName(String showBankName) {
        this.showBankName = showBankName;
    }

    public String getImgBusiEnv() {
        return imgBusiEnv;
    }

    public void setImgBusiEnv(String imgBusiEnv) {
        this.imgBusiEnv = imgBusiEnv;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getOperatorDuty() {
        return operatorDuty;
    }

    public void setOperatorDuty(String operatorDuty) {
        this.operatorDuty = operatorDuty;
    }

    public String getImgHandLPIDCard() {
        return imgHandLPIDCard;
    }

    public void setImgHandLPIDCard(String imgHandLPIDCard) {
        this.imgHandLPIDCard = imgHandLPIDCard;
    }

    public String getImgContactIDCardBack() {
        return imgContactIDCardBack;
    }

    public void setImgContactIDCardBack(String imgContactIDCardBack) {
        this.imgContactIDCardBack = imgContactIDCardBack;
    }

    public String getCompanyLocation() {
        return companyLocation;
    }

    public void setCompanyLocation(String companyLocation) {
        this.companyLocation = companyLocation;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOperatorMobile() {
        return operatorMobile;
    }

    public void setOperatorMobile(String operatorMobile) {
        this.operatorMobile = operatorMobile;
    }

    public String getOrgLicenceCode() {
        return orgLicenceCode;
    }

    public void setOrgLicenceCode(String orgLicenceCode) {
        this.orgLicenceCode = orgLicenceCode;
    }

    public String getBankProvinceName() {
        return bankProvinceName;
    }

    public void setBankProvinceName(String bankProvinceName) {
        this.bankProvinceName = bankProvinceName;
    }

    public String getComBankAccount() {
        return comBankAccount;
    }

    public void setComBankAccount(String comBankAccount) {
        this.comBankAccount = comBankAccount;
    }

    public String getProxyIDCardPeriod() {
        return proxyIDCardPeriod;
    }

    public void setProxyIDCardPeriod(String proxyIDCardPeriod) {
        this.proxyIDCardPeriod = proxyIDCardPeriod;
    }

    public String getProxyName() {
        return proxyName;
    }

    public void setProxyName(String proxyName) {
        this.proxyName = proxyName;
    }

    public String getLicenseProvinceName() {
        return licenseProvinceName;
    }

    public void setLicenseProvinceName(String licenseProvinceName) {
        this.licenseProvinceName = licenseProvinceName;
    }

    public String getStrLicenseBeginDate() {
        return strLicenseBeginDate;
    }

    public void setStrLicenseBeginDate(String strLicenseBeginDate) {
        this.strLicenseBeginDate = strLicenseBeginDate;
    }

    public String getImgBusiPermission() {
        return imgBusiPermission;
    }

    public void setImgBusiPermission(String imgBusiPermission) {
        this.imgBusiPermission = imgBusiPermission;
    }

    public String getProxyTel() {
        return proxyTel;
    }

    public void setProxyTel(String proxyTel) {
        this.proxyTel = proxyTel;
    }

    public String getImgProxyIDCardFront() {
        return imgProxyIDCardFront;
    }

    public void setImgProxyIDCardFront(String imgProxyIDCardFront) {
        this.imgProxyIDCardFront = imgProxyIDCardFront;
    }

    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setOperatorEmail(String operatorEmail) {
        this.operatorEmail = operatorEmail;
    }

    public String getLpPhone() {
        return lpPhone;
    }

    public void setLpPhone(String lpPhone) {
        this.lpPhone = lpPhone;
    }

    public String getComBankCityName() {
        return comBankCityName;
    }

    public void setComBankCityName(String comBankCityName) {
        this.comBankCityName = comBankCityName;
    }

    public String getBusiScope() {
        return busiScope;
    }

    public void setBusiScope(String busiScope) {
        this.busiScope = busiScope;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getLicenseDistrictName() {
        return licenseDistrictName;
    }

    public void setLicenseDistrictName(String licenseDistrictName) {
        this.licenseDistrictName = licenseDistrictName;
    }

    public String getImgContactIDCardFront() {
        return imgContactIDCardFront;
    }

    public void setImgContactIDCardFront(String imgContactIDCardFront) {
        this.imgContactIDCardFront = imgContactIDCardFront;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getImgOrgLicense() {
        return imgOrgLicense;
    }

    public void setImgOrgLicense(String imgOrgLicense) {
        this.imgOrgLicense = imgOrgLicense;
    }

    public int getLegalPersonSource() {
        return legalPersonSource;
    }

    public void setLegalPersonSource(int legalPersonSource) {
        this.legalPersonSource = legalPersonSource;
    }

    public String getSubmitterTel() {
        return submitterTel;
    }

    public void setSubmitterTel(String submitterTel) {
        this.submitterTel = submitterTel;
    }

    public String getLicenseName() {
        return licenseName;
    }

    public void setLicenseName(String licenseName) {
        this.licenseName = licenseName;
    }

    public String getProxyIDCardBeginDate() {
        return proxyIDCardBeginDate;
    }

    public void setProxyIDCardBeginDate(String proxyIDCardBeginDate) {
        this.proxyIDCardBeginDate = proxyIDCardBeginDate;
    }

    public String getComReceiverName() {
        return comReceiverName;
    }

    public void setComReceiverName(String comReceiverName) {
        this.comReceiverName = comReceiverName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getComBankNo() {
        return comBankNo;
    }

    public void setComBankNo(String comBankNo) {
        this.comBankNo = comBankNo;
    }

    public String getLpName() {
        return lpName;
    }

    public void setLpName(String lpName) {
        this.lpName = lpName;
    }

    public String getSettleUnitID() {
        return settleUnitID;
    }

    public void setSettleUnitID(String settleUnitID) {
        this.settleUnitID = settleUnitID;
    }

    public String getBankCityCode() {
        return bankCityCode;
    }

    public void setBankCityCode(String bankCityCode) {
        this.bankCityCode = bankCityCode;
    }

    public String getImgMerchantEntry() {
        return imgMerchantEntry;
    }

    public void setImgMerchantEntry(String imgMerchantEntry) {
        this.imgMerchantEntry = imgMerchantEntry;
    }

    public String getProxyIDCardNo() {
        return proxyIDCardNo;
    }

    public void setProxyIDCardNo(String proxyIDCardNo) {
        this.proxyIDCardNo = proxyIDCardNo;
    }

    public String getLpIDCardPeriodBeginDate() {
        return lpIDCardPeriodBeginDate;
    }

    public void setLpIDCardPeriodBeginDate(String lpIDCardPeriodBeginDate) {
        this.lpIDCardPeriodBeginDate = lpIDCardPeriodBeginDate;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCompanyUrl() {
        return companyUrl;
    }

    public void setCompanyUrl(String companyUrl) {
        this.companyUrl = companyUrl;
    }

    public String getLicenseDistrictCode() {
        return licenseDistrictCode;
    }

    public void setLicenseDistrictCode(String licenseDistrictCode) {
        this.licenseDistrictCode = licenseDistrictCode;
    }

    public int getIsImgBusiPermission() {
        return isImgBusiPermission;
    }

    public void setIsImgBusiPermission(int isImgBusiPermission) {
        this.isImgBusiPermission = isImgBusiPermission;
    }

    public String getStrLpIDCardPeriod() {
        return strLpIDCardPeriod;
    }

    public void setStrLpIDCardPeriod(String strLpIDCardPeriod) {
        this.strLpIDCardPeriod = strLpIDCardPeriod;
    }

    public String getImgLPIDCardBack() {
        return imgLPIDCardBack;
    }

    public void setImgLPIDCardBack(String imgLPIDCardBack) {
        this.imgLPIDCardBack = imgLPIDCardBack;
    }

    public int getIndustryType() {
        return industryType;
    }

    public void setIndustryType(int industryType) {
        this.industryType = industryType;
    }

    public String getComBankProvinceCode() {
        return comBankProvinceCode;
    }

    public void setComBankProvinceCode(String comBankProvinceCode) {
        this.comBankProvinceCode = comBankProvinceCode;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getLpAddress() {
        return lpAddress;
    }

    public void setLpAddress(String lpAddress) {
        this.lpAddress = lpAddress;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getDocID() {
        return docID;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public String getBankLocation() {
        return bankLocation;
    }

    public void setBankLocation(String bankLocation) {
        this.bankLocation = bankLocation;
    }

    public String getComBankCityCode() {
        return comBankCityCode;
    }

    public void setComBankCityCode(String comBankCityCode) {
        this.comBankCityCode = comBankCityCode;
    }

    public String getImgLicense() {
        return imgLicense;
    }

    public void setImgLicense(String imgLicense) {
        this.imgLicense = imgLicense;
    }

    public String getComBankName() {
        return comBankName;
    }

    public void setComBankName(String comBankName) {
        this.comBankName = comBankName;
    }

    public String getLicenseCityCode() {
        return licenseCityCode;
    }

    public void setLicenseCityCode(String licenseCityCode) {
        this.licenseCityCode = licenseCityCode;
    }

    public String getWeixinMPAPPID() {
        return weixinMPAPPID;
    }

    public void setWeixinMPAPPID(String weixinMPAPPID) {
        this.weixinMPAPPID = weixinMPAPPID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.groupType);
        dest.writeString(this.busiPermissionBeginDate);
        dest.writeString(this.busiPermissionEndDate);
        dest.writeString(this.imgContactProxyContract);
        dest.writeString(this.proxyProtocol);
        dest.writeString(this.companyName);
        dest.writeString(this.companyShortName);
        dest.writeString(this.licenseCode);
        dest.writeString(this.imgBankLicense);
        dest.writeString(this.businessAddress);
        dest.writeString(this.contactIDCardNo);
        dest.writeString(this.imgHandProxyIDCardFront);
        dest.writeString(this.imgProxyContract);
        dest.writeString(this.comBankBranchName);
        dest.writeString(this.imgTaxLicense);
        dest.writeString(this.proxyAddress);
        dest.writeString(this.bankCityName);
        dest.writeString(this.imgBusiDoor);
        dest.writeString(this.lpIDCardPeriod);
        dest.writeString(this.strLicensePeriod);
        dest.writeString(this.imgLPIDCardFront);
        dest.writeString(this.bankProvinceCode);
        dest.writeString(this.comBankProvinceName);
        dest.writeString(this.licenseProvinceCode);
        dest.writeString(this.groupName);
        dest.writeString(this.imgProxyIDCardBack);
        dest.writeString(this.licenseBeginDate);
        dest.writeInt(this.bankPersonType);
        dest.writeString(this.imgHandProxyIDCardBack);
        dest.writeString(this.imgBusiCounter);
        dest.writeString(this.submitterName);
        dest.writeInt(this.status);
        dest.writeString(this.merchantNo);
        dest.writeString(this.groupCreateTime);
        dest.writeString(this.customerServiceTel);
        dest.writeString(this.actionTime);
        dest.writeInt(this.lpCardType);
        dest.writeString(this.industry);
        dest.writeString(this.operatorName);
        dest.writeString(this.settleUnitName);
        dest.writeInt(this.unitType);
        dest.writeString(this.strLpIDCardPeriodBeginDate);
        dest.writeString(this.licensePeriod);
        dest.writeString(this.lpIDCardNo);
        dest.writeString(this.licenseAddress);
        dest.writeString(this.licenseCityName);
        dest.writeString(this.industryName);
        dest.writeString(this.bankCode);
        dest.writeString(this.showBankName);
        dest.writeString(this.imgBusiEnv);
        dest.writeString(this.address);
        dest.writeString(this.actionBy);
        dest.writeString(this.operatorDuty);
        dest.writeString(this.imgHandLPIDCard);
        dest.writeString(this.imgContactIDCardBack);
        dest.writeString(this.companyLocation);
        dest.writeString(this.createTime);
        dest.writeString(this.operatorMobile);
        dest.writeString(this.orgLicenceCode);
        dest.writeString(this.bankProvinceName);
        dest.writeString(this.comBankAccount);
        dest.writeString(this.proxyIDCardPeriod);
        dest.writeString(this.proxyName);
        dest.writeString(this.licenseProvinceName);
        dest.writeString(this.strLicenseBeginDate);
        dest.writeString(this.imgBusiPermission);
        dest.writeString(this.proxyTel);
        dest.writeString(this.imgProxyIDCardFront);
        dest.writeString(this.operatorEmail);
        dest.writeString(this.lpPhone);
        dest.writeString(this.comBankCityName);
        dest.writeString(this.busiScope);
        dest.writeString(this.bankAccount);
        dest.writeString(this.licenseDistrictName);
        dest.writeString(this.imgContactIDCardFront);
        dest.writeString(this.groupID);
        dest.writeString(this.imgOrgLicense);
        dest.writeInt(this.legalPersonSource);
        dest.writeString(this.submitterTel);
        dest.writeString(this.licenseName);
        dest.writeString(this.proxyIDCardBeginDate);
        dest.writeString(this.comReceiverName);
        dest.writeString(this.unit);
        dest.writeString(this.comBankNo);
        dest.writeString(this.lpName);
        dest.writeString(this.settleUnitID);
        dest.writeString(this.bankCityCode);
        dest.writeString(this.imgMerchantEntry);
        dest.writeString(this.proxyIDCardNo);
        dest.writeString(this.lpIDCardPeriodBeginDate);
        dest.writeString(this.bankName);
        dest.writeString(this.companyUrl);
        dest.writeString(this.licenseDistrictCode);
        dest.writeInt(this.isImgBusiPermission);
        dest.writeString(this.strLpIDCardPeriod);
        dest.writeString(this.imgLPIDCardBack);
        dest.writeInt(this.industryType);
        dest.writeString(this.comBankProvinceCode);
        dest.writeString(this.bankNo);
        dest.writeString(this.lpAddress);
        dest.writeString(this.receiverName);
        dest.writeString(this.docID);
        dest.writeString(this.bankLocation);
        dest.writeString(this.comBankCityCode);
        dest.writeString(this.imgLicense);
        dest.writeString(this.comBankName);
        dest.writeString(this.licenseCityCode);
        dest.writeString(this.weixinMPAPPID);
    }

    public AuthInfo() {
    }

    protected AuthInfo(Parcel in) {
        this.groupType = in.readInt();
        this.busiPermissionBeginDate = in.readString();
        this.busiPermissionEndDate = in.readString();
        this.imgContactProxyContract = in.readString();
        this.proxyProtocol = in.readString();
        this.companyName = in.readString();
        this.companyShortName = in.readString();
        this.licenseCode = in.readString();
        this.imgBankLicense = in.readString();
        this.businessAddress = in.readString();
        this.contactIDCardNo = in.readString();
        this.imgHandProxyIDCardFront = in.readString();
        this.imgProxyContract = in.readString();
        this.comBankBranchName = in.readString();
        this.imgTaxLicense = in.readString();
        this.proxyAddress = in.readString();
        this.bankCityName = in.readString();
        this.imgBusiDoor = in.readString();
        this.lpIDCardPeriod = in.readString();
        this.strLicensePeriod = in.readString();
        this.imgLPIDCardFront = in.readString();
        this.bankProvinceCode = in.readString();
        this.comBankProvinceName = in.readString();
        this.licenseProvinceCode = in.readString();
        this.groupName = in.readString();
        this.imgProxyIDCardBack = in.readString();
        this.licenseBeginDate = in.readString();
        this.bankPersonType = in.readInt();
        this.imgHandProxyIDCardBack = in.readString();
        this.imgBusiCounter = in.readString();
        this.submitterName = in.readString();
        this.status = in.readInt();
        this.merchantNo = in.readString();
        this.groupCreateTime = in.readString();
        this.customerServiceTel = in.readString();
        this.actionTime = in.readString();
        this.lpCardType = in.readInt();
        this.industry = in.readString();
        this.operatorName = in.readString();
        this.settleUnitName = in.readString();
        this.unitType = in.readInt();
        this.strLpIDCardPeriodBeginDate = in.readString();
        this.licensePeriod = in.readString();
        this.lpIDCardNo = in.readString();
        this.licenseAddress = in.readString();
        this.licenseCityName = in.readString();
        this.industryName = in.readString();
        this.bankCode = in.readString();
        this.showBankName = in.readString();
        this.imgBusiEnv = in.readString();
        this.address = in.readString();
        this.actionBy = in.readString();
        this.operatorDuty = in.readString();
        this.imgHandLPIDCard = in.readString();
        this.imgContactIDCardBack = in.readString();
        this.companyLocation = in.readString();
        this.createTime = in.readString();
        this.operatorMobile = in.readString();
        this.orgLicenceCode = in.readString();
        this.bankProvinceName = in.readString();
        this.comBankAccount = in.readString();
        this.proxyIDCardPeriod = in.readString();
        this.proxyName = in.readString();
        this.licenseProvinceName = in.readString();
        this.strLicenseBeginDate = in.readString();
        this.imgBusiPermission = in.readString();
        this.proxyTel = in.readString();
        this.imgProxyIDCardFront = in.readString();
        this.operatorEmail = in.readString();
        this.lpPhone = in.readString();
        this.comBankCityName = in.readString();
        this.busiScope = in.readString();
        this.bankAccount = in.readString();
        this.licenseDistrictName = in.readString();
        this.imgContactIDCardFront = in.readString();
        this.groupID = in.readString();
        this.imgOrgLicense = in.readString();
        this.legalPersonSource = in.readInt();
        this.submitterTel = in.readString();
        this.licenseName = in.readString();
        this.proxyIDCardBeginDate = in.readString();
        this.comReceiverName = in.readString();
        this.unit = in.readString();
        this.comBankNo = in.readString();
        this.lpName = in.readString();
        this.settleUnitID = in.readString();
        this.bankCityCode = in.readString();
        this.imgMerchantEntry = in.readString();
        this.proxyIDCardNo = in.readString();
        this.lpIDCardPeriodBeginDate = in.readString();
        this.bankName = in.readString();
        this.companyUrl = in.readString();
        this.licenseDistrictCode = in.readString();
        this.isImgBusiPermission = in.readInt();
        this.strLpIDCardPeriod = in.readString();
        this.imgLPIDCardBack = in.readString();
        this.industryType = in.readInt();
        this.comBankProvinceCode = in.readString();
        this.bankNo = in.readString();
        this.lpAddress = in.readString();
        this.receiverName = in.readString();
        this.docID = in.readString();
        this.bankLocation = in.readString();
        this.comBankCityCode = in.readString();
        this.imgLicense = in.readString();
        this.comBankName = in.readString();
        this.licenseCityCode = in.readString();
        this.weixinMPAPPID = in.readString();
    }
}
