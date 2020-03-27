package com.hll_sc_app.bean.wallet;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 钱包实名认证数据
 *
 * @author zc
 */
public class WalletInfo implements Parcelable {

    /**
     * 未开通 1
     * 开通审核中 2
     * 开通审核成功 3 目前来说 3不会出现 直接为5 签约是自动开启的
     * 开通审核失败 4
     * 签约认证成功 5
     */
    public final static int STATUS_NOT_OPEN = 1;
    public final static int STATUS_VERIFYING = 2;
    public final static int STATUS_VERIFY_SUCCESS = 3;
    public final static int STATUS_VERIFY_FAIL = 4;
    public final static int STATUS_AUTHEN_SUCCESS = 5;


    private String settleUnitID;
    private String settleUnitName;
    private String groupID;
    private int userType;
    private String outUserID;
    private String outUserName;
    private float settleBalance;
    private String bankAccount;
    private String bankName;
    private String bankCode;
    private String bankNO;
    private int receiverType;
    private String receiverName;
    private String receiverPhone;
    private String receiverMobile;
    private String receiverEmail;
    private String receiverLinkman;
    private int accountCheckStatus;
    private String remark;
    private String poundageMinAmount;
    private String minTransferAmount;
    private String maxTransferDays;
    private String poundageAmount;
    private String settleMode;
    private String packageRemainAmount;
    private String merchantNo;
    private String createTime;
    private int processStatus;
    private String spMerchantNo;
    private String companyName;
    private String companyShortName;
    private String operatorName;
    private String operatorDuty;
    private String operatorMobile;
    private float withdrawalAmount;
    private float freezeBalance;
    private int signStatus;
    private int openPayStatus;
    private String openPayFailMsg;
    private int unitType;
    private String operatorEmail;
    private String licenseProvinceName;
    private String customerServiceTel;
    private String licenseDistrictName;
    private String licenseProvinceCode;
    private String licenseCityCode;
    private String licenseDistrictCode;
    private String industryCode;
    private String licenseCityName;
    private String businessCategoryName;
    private String processInstanceID;
    private String processFailReason;
    private String auditLimitDate;
    private int bankPersonType;
    private String unionBankNo;
    private String businessAddress;
    private String settleBankName;
    private String activityStatus;
    private String imgProxyContract;
    private String proxyProtocol;
    private String imgBankLicense;
    private String activityEffect;
    private String normalStageStatus;
    private int syncBankStatus;
    private String syncBankFailMsg;
    private String withdrawIsAllow;
    private String groupName;
    private String imgLicense ;
    private String licenseCode  ;
    private String licenseAddress  ;
    private String busiScope ;
    private int lpCardType = -1;
    private String lpIDCardPeriodBeginDate;
    private String lpIDCardPeriod;
    private String lpName;
    private String lpIDCardNo;
    private String lpPhone;
    private String imgLPIDCardFront;
    private String imgLPIDCardBack;
    private String contactIDCardNo;
    private String imgBusiDoor;
    private String imgBusiEnv;
    private String imgBusiCounter;


    public String getImgBusiDoor() {
        return imgBusiDoor;
    }

    public void setImgBusiDoor(String imgBusiDoor) {
        this.imgBusiDoor = imgBusiDoor;
    }

    public String getImgBusiEnv() {
        return imgBusiEnv;
    }

    public void setImgBusiEnv(String imgBusiEnv) {
        this.imgBusiEnv = imgBusiEnv;
    }

    public String getImgBusiCounter() {
        return imgBusiCounter;
    }

    public void setImgBusiCounter(String imgBusiCounter) {
        this.imgBusiCounter = imgBusiCounter;
    }

    public String getContactIDCardNo() {
        return contactIDCardNo;
    }

    public void setContactIDCardNo(String contactIDCardNo) {
        this.contactIDCardNo = contactIDCardNo;
    }

    public String getImgLPIDCardFront() {
        return imgLPIDCardFront;
    }

    public void setImgLPIDCardFront(String imgLPIDCardFront) {
        this.imgLPIDCardFront = imgLPIDCardFront;
    }

    public String getImgLPIDCardBack() {
        return imgLPIDCardBack;
    }

    public void setImgLPIDCardBack(String imgLPIDCardBack) {
        this.imgLPIDCardBack = imgLPIDCardBack;
    }

    public String getLpPhone() {
        return lpPhone;
    }

    public void setLpPhone(String lpPhone) {
        this.lpPhone = lpPhone;
    }

    public String getLpIDCardNo() {
        return lpIDCardNo;
    }

    public void setLpIDCardNo(String lpIDCardNo) {
        this.lpIDCardNo = lpIDCardNo;
    }

    public String getLpName() {
        return lpName;
    }

    public void setLpName(String lpName) {
        this.lpName = lpName;
    }

    public String getLpIDCardPeriodBeginDate() {
        return lpIDCardPeriodBeginDate;
    }

    public void setLpIDCardPeriodBeginDate(String lpIDCardPeriodBeginDate) {
        this.lpIDCardPeriodBeginDate = lpIDCardPeriodBeginDate;
    }

    public String getLpIDCardPeriod() {
        return lpIDCardPeriod;
    }

    public void setLpIDCardPeriod(String lpIDCardPeriod) {
        this.lpIDCardPeriod = lpIDCardPeriod;
    }

    public String getBusiScope() {
        return busiScope;
    }

    public void setBusiScope(String busiScope) {
        this.busiScope = busiScope;
    }

    public String getLicenseAddress() {
        return licenseAddress;
    }

    public void setLicenseAddress(String licenseAddress) {
        this.licenseAddress = licenseAddress;
    }

    /**
     * 营业执照有效期
     */
    private String licenseBeginDate  ;
    private String licensePeriod  ;

    public String getLicenseBeginDate() {
        return licenseBeginDate;
    }

    public void setLicenseBeginDate(String licenseBeginDate) {
        this.licenseBeginDate = licenseBeginDate;
    }

    public String getLicensePeriod() {
        return licensePeriod;
    }

    public void setLicensePeriod(String licensePeriod) {
        this.licensePeriod = licensePeriod;
    }

    public String getLicenseCode() {
        return licenseCode;
    }

    public void setLicenseCode(String licenseCode) {
        this.licenseCode = licenseCode;
    }

    public String getImgLicense() {
        return imgLicense;
    }

    public void setImgLicense(String imgLicense) {
        this.imgLicense = imgLicense;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    private List<ReportListBean> reportList;

    public WalletInfo() {
    }

    public String getSettleUnitID() {
        return settleUnitID;
    }

    public void setSettleUnitID(String settleUnitID) {
        this.settleUnitID = settleUnitID;
    }

    public String getSettleUnitName() {
        return settleUnitName;
    }

    public void setSettleUnitName(String settleUnitName) {
        this.settleUnitName = settleUnitName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public String getOutUserID() {
        return outUserID;
    }

    public void setOutUserID(String outUserID) {
        this.outUserID = outUserID;
    }

    public String getOutUserName() {
        return outUserName;
    }

    public void setOutUserName(String outUserName) {
        this.outUserName = outUserName;
    }

    public float getSettleBalance() {
        return settleBalance;
    }

    public void setSettleBalance(float settleBalance) {
        this.settleBalance = settleBalance;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankNO() {
        return bankNO;
    }

    public void setBankNO(String bankNO) {
        this.bankNO = bankNO;
    }

    public int getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(int receiverType) {
        this.receiverType = receiverType;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getReceiverMobile() {
        return receiverMobile;
    }

    public void setReceiverMobile(String receiverMobile) {
        this.receiverMobile = receiverMobile;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getReceiverLinkman() {
        return receiverLinkman;
    }

    public void setReceiverLinkman(String receiverLinkman) {
        this.receiverLinkman = receiverLinkman;
    }

    public int getAccountCheckStatus() {
        return accountCheckStatus;
    }

    public void setAccountCheckStatus(int accountCheckStatus) {
        this.accountCheckStatus = accountCheckStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPoundageMinAmount() {
        return poundageMinAmount;
    }

    public void setPoundageMinAmount(String poundageMinAmount) {
        this.poundageMinAmount = poundageMinAmount;
    }

    public String getMinTransferAmount() {
        return minTransferAmount;
    }

    public void setMinTransferAmount(String minTransferAmount) {
        this.minTransferAmount = minTransferAmount;
    }

    public String getMaxTransferDays() {
        return maxTransferDays;
    }

    public void setMaxTransferDays(String maxTransferDays) {
        this.maxTransferDays = maxTransferDays;
    }

    public String getPoundageAmount() {
        return poundageAmount;
    }

    public void setPoundageAmount(String poundageAmount) {
        this.poundageAmount = poundageAmount;
    }

    public String getSettleMode() {
        return settleMode;
    }

    public void setSettleMode(String settleMode) {
        this.settleMode = settleMode;
    }

    public String getPackageRemainAmount() {
        return packageRemainAmount;
    }

    public void setPackageRemainAmount(String packageRemainAmount) {
        this.packageRemainAmount = packageRemainAmount;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getProcessStatus() {
        return processStatus;
    }

    public void setProcessStatus(int processStatus) {
        this.processStatus = processStatus;
    }

    public String getSpMerchantNo() {
        return spMerchantNo;
    }

    public void setSpMerchantNo(String spMerchantNo) {
        this.spMerchantNo = spMerchantNo;
    }

    public float getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(float withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public int getSignStatus() {
        return signStatus;
    }

    public void setSignStatus(int signStatus) {
        this.signStatus = signStatus;
    }

    public int getOpenPayStatus() {
        return openPayStatus;
    }

    public void setOpenPayStatus(int openPayStatus) {
        this.openPayStatus = openPayStatus;
    }

    public String getOpenPayFailMsg() {
        return openPayFailMsg;
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

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorDuty() {
        return operatorDuty;
    }

    public void setOperatorDuty(String operatorDuty) {
        this.operatorDuty = operatorDuty;
    }

    public String getOperatorMobile() {
        return operatorMobile;
    }

    public void setOperatorMobile(String operatorMobile) {
        this.operatorMobile = operatorMobile;
    }

    public String getOperatorEmail() {
        return operatorEmail;
    }

    public void setOperatorEmail(String operatorEmail) {
        this.operatorEmail = operatorEmail;
    }

    public void setOpenPayFailMsg(String openPayFailMsg) {
        this.openPayFailMsg = openPayFailMsg;
    }

    public int getUnitType() {
        return unitType;
    }

    public void setUnitType(int unitType) {
        this.unitType = unitType;
    }

    public String getCustomerServiceTel() {
        return customerServiceTel;
    }

    public void setCustomerServiceTel(String customerServiceTel) {
        this.customerServiceTel = customerServiceTel;
    }

    public String getLicenseProvinceCode() {
        return licenseProvinceCode;
    }

    public void setLicenseProvinceCode(String licenseProvinceCode) {
        this.licenseProvinceCode = licenseProvinceCode;
    }

    public String getLicenseCityCode() {
        return licenseCityCode;
    }

    public void setLicenseCityCode(String licenseCityCode) {
        this.licenseCityCode = licenseCityCode;
    }

    public String getLicenseDistrictCode() {
        return licenseDistrictCode;
    }

    public void setLicenseDistrictCode(String licenseDistrictCode) {
        this.licenseDistrictCode = licenseDistrictCode;
    }

    public String getIndustryCode() {
        return industryCode;
    }

    public void setIndustryCode(String industryCode) {
        this.industryCode = industryCode;
    }

    public String getLicenseProvinceName() {
        return licenseProvinceName;
    }

    public void setLicenseProvinceName(String licenseProvinceName) {
        this.licenseProvinceName = licenseProvinceName;
    }

    public String getLicenseDistrictName() {
        return licenseDistrictName;
    }

    public void setLicenseDistrictName(String licenseDistrictName) {
        this.licenseDistrictName = licenseDistrictName;
    }

    public String getLicenseCityName() {
        return licenseCityName;
    }

    public void setLicenseCityName(String licenseCityName) {
        this.licenseCityName = licenseCityName;
    }

    public String getBusinessCategoryName() {
        return businessCategoryName;
    }

    public void setBusinessCategoryName(String businessCategoryName) {
        this.businessCategoryName = businessCategoryName;
    }

    public float getFreezeBalance() {
        return freezeBalance;
    }

    public void setFreezeBalance(float freezeBalance) {
        this.freezeBalance = freezeBalance;
    }

    public String getProcessInstanceID() {
        return processInstanceID;
    }

    public void setProcessInstanceID(String processInstanceID) {
        this.processInstanceID = processInstanceID;
    }

    public String getProcessFailReason() {
        return processFailReason;
    }

    public void setProcessFailReason(String processFailReason) {
        this.processFailReason = processFailReason;
    }

    public String getAuditLimitDate() {
        return auditLimitDate;
    }

    public void setAuditLimitDate(String auditLimitDate) {
        this.auditLimitDate = auditLimitDate;
    }

    public int getBankPersonType() {
        return bankPersonType;
    }

    public void setBankPersonType(int bankPersonType) {
        this.bankPersonType = bankPersonType;
    }

    public String getUnionBankNo() {
        return unionBankNo;
    }

    public void setUnionBankNo(String unionBankNo) {
        this.unionBankNo = unionBankNo;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getSettleBankName() {
        return settleBankName;
    }

    public void setSettleBankName(String settleBankName) {
        this.settleBankName = settleBankName;
    }

    public String getActivityStatus() {
        return activityStatus;
    }

    public void setActivityStatus(String activityStatus) {
        this.activityStatus = activityStatus;
    }

    public String getImgProxyContract() {
        return imgProxyContract;
    }

    public void setImgProxyContract(String imgProxyContract) {
        this.imgProxyContract = imgProxyContract;
    }

    public String getProxyProtocol() {
        return proxyProtocol;
    }

    public void setProxyProtocol(String proxyProtocol) {
        this.proxyProtocol = proxyProtocol;
    }

    public String getImgBankLicense() {
        return imgBankLicense;
    }

    public void setImgBankLicense(String imgBankLicense) {
        this.imgBankLicense = imgBankLicense;
    }

    public String getActivityEffect() {
        return activityEffect;
    }

    public void setActivityEffect(String activityEffect) {
        this.activityEffect = activityEffect;
    }

    public String getNormalStageStatus() {
        return normalStageStatus;
    }

    public void setNormalStageStatus(String normalStageStatus) {
        this.normalStageStatus = normalStageStatus;
    }

    public int getSyncBankStatus() {
        return syncBankStatus;
    }

    public void setSyncBankStatus(int syncBankStatus) {
        this.syncBankStatus = syncBankStatus;
    }

    public String getSyncBankFailMsg() {
        return syncBankFailMsg;
    }

    public void setSyncBankFailMsg(String syncBankFailMsg) {
        this.syncBankFailMsg = syncBankFailMsg;
    }

    public String getWithdrawIsAllow() {
        return withdrawIsAllow;
    }

    public void setWithdrawIsAllow(String withdrawIsAllow) {
        this.withdrawIsAllow = withdrawIsAllow;
    }

    public List<ReportListBean> getReportList() {
        return reportList;
    }

    public void setReportList(List<ReportListBean> reportList) {
        this.reportList = reportList;
    }

    public static class ReportListBean {
        /**
         * settleUnitID : 105001
         * channelCode : AINONG
         * merchantNo : M100298908
         * payMethod : ALIPAY
         * reportIsSuccess : 1
         * reportStatus : 40
         * reportFailMsg :
         * qrcodeUrl :
         * channelId :
         * bankMerchantCode :
         */

        private String settleUnitID;
        private String channelCode;
        private String merchantNo;
        private String payMethod;
        private int reportIsSuccess;
        private int reportStatus;
        private String reportFailMsg;
        private String qrcodeUrl;
        private String channelId;
        private String bankMerchantCode;

        public String getSettleUnitID() {
            return settleUnitID;
        }

        public void setSettleUnitID(String settleUnitID) {
            this.settleUnitID = settleUnitID;
        }

        public String getChannelCode() {
            return channelCode;
        }

        public void setChannelCode(String channelCode) {
            this.channelCode = channelCode;
        }

        public String getMerchantNo() {
            return merchantNo;
        }

        public void setMerchantNo(String merchantNo) {
            this.merchantNo = merchantNo;
        }

        public String getPayMethod() {
            return payMethod;
        }

        public void setPayMethod(String payMethod) {
            this.payMethod = payMethod;
        }

        public int getReportIsSuccess() {
            return reportIsSuccess;
        }

        public void setReportIsSuccess(int reportIsSuccess) {
            this.reportIsSuccess = reportIsSuccess;
        }

        public int getReportStatus() {
            return reportStatus;
        }

        public void setReportStatus(int reportStatus) {
            this.reportStatus = reportStatus;
        }

        public String getReportFailMsg() {
            return reportFailMsg;
        }

        public void setReportFailMsg(String reportFailMsg) {
            this.reportFailMsg = reportFailMsg;
        }

        public String getQrcodeUrl() {
            return qrcodeUrl;
        }

        public void setQrcodeUrl(String qrcodeUrl) {
            this.qrcodeUrl = qrcodeUrl;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public String getBankMerchantCode() {
            return bankMerchantCode;
        }

        public void setBankMerchantCode(String bankMerchantCode) {
            this.bankMerchantCode = bankMerchantCode;
        }
    }

    public int getLpCardType() {
        return lpCardType;
    }

    public void setLpCardType(int lpCardType) {
        this.lpCardType = lpCardType;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.settleUnitID);
        dest.writeString(this.settleUnitName);
        dest.writeString(this.groupID);
        dest.writeInt(this.userType);
        dest.writeString(this.outUserID);
        dest.writeString(this.outUserName);
        dest.writeFloat(this.settleBalance);
        dest.writeString(this.bankAccount);
        dest.writeString(this.bankName);
        dest.writeString(this.bankCode);
        dest.writeString(this.bankNO);
        dest.writeInt(this.receiverType);
        dest.writeString(this.receiverName);
        dest.writeString(this.receiverPhone);
        dest.writeString(this.receiverMobile);
        dest.writeString(this.receiverEmail);
        dest.writeString(this.receiverLinkman);
        dest.writeInt(this.accountCheckStatus);
        dest.writeString(this.remark);
        dest.writeString(this.poundageMinAmount);
        dest.writeString(this.minTransferAmount);
        dest.writeString(this.maxTransferDays);
        dest.writeString(this.poundageAmount);
        dest.writeString(this.settleMode);
        dest.writeString(this.packageRemainAmount);
        dest.writeString(this.merchantNo);
        dest.writeString(this.createTime);
        dest.writeInt(this.processStatus);
        dest.writeString(this.spMerchantNo);
        dest.writeString(this.companyName);
        dest.writeString(this.companyShortName);
        dest.writeString(this.operatorName);
        dest.writeString(this.operatorDuty);
        dest.writeString(this.operatorMobile);
        dest.writeFloat(this.withdrawalAmount);
        dest.writeFloat(this.freezeBalance);
        dest.writeInt(this.signStatus);
        dest.writeInt(this.openPayStatus);
        dest.writeString(this.openPayFailMsg);
        dest.writeInt(this.unitType);
        dest.writeString(this.operatorEmail);
        dest.writeString(this.licenseProvinceName);
        dest.writeString(this.customerServiceTel);
        dest.writeString(this.licenseDistrictName);
        dest.writeString(this.licenseProvinceCode);
        dest.writeString(this.licenseCityCode);
        dest.writeString(this.licenseDistrictCode);
        dest.writeString(this.industryCode);
        dest.writeString(this.licenseCityName);
        dest.writeString(this.businessCategoryName);
        dest.writeString(this.processInstanceID);
        dest.writeString(this.processFailReason);
        dest.writeString(this.auditLimitDate);
        dest.writeInt(this.bankPersonType);
        dest.writeString(this.unionBankNo);
        dest.writeString(this.businessAddress);
        dest.writeString(this.settleBankName);
        dest.writeString(this.activityStatus);
        dest.writeString(this.imgProxyContract);
        dest.writeString(this.proxyProtocol);
        dest.writeString(this.imgBankLicense);
        dest.writeString(this.activityEffect);
        dest.writeString(this.normalStageStatus);
        dest.writeInt(this.syncBankStatus);
        dest.writeString(this.syncBankFailMsg);
        dest.writeString(this.withdrawIsAllow);
        dest.writeString(this.groupName);
        dest.writeString(this.imgLicense);
        dest.writeString(this.licenseCode);
        dest.writeString(this.licenseAddress);
        dest.writeString(this.busiScope);
        dest.writeInt(this.lpCardType);
        dest.writeString(this.lpIDCardPeriodBeginDate);
        dest.writeString(this.lpIDCardPeriod);
        dest.writeString(this.lpName);
        dest.writeString(this.lpIDCardNo);
        dest.writeString(this.lpPhone);
        dest.writeString(this.imgLPIDCardFront);
        dest.writeString(this.imgLPIDCardBack);
        dest.writeString(this.contactIDCardNo);
        dest.writeString(this.imgBusiDoor);
        dest.writeString(this.imgBusiEnv);
        dest.writeString(this.imgBusiCounter);
        dest.writeString(this.licenseBeginDate);
        dest.writeString(this.licensePeriod);
        dest.writeList(this.reportList);
    }

    protected WalletInfo(Parcel in) {
        this.settleUnitID = in.readString();
        this.settleUnitName = in.readString();
        this.groupID = in.readString();
        this.userType = in.readInt();
        this.outUserID = in.readString();
        this.outUserName = in.readString();
        this.settleBalance = in.readFloat();
        this.bankAccount = in.readString();
        this.bankName = in.readString();
        this.bankCode = in.readString();
        this.bankNO = in.readString();
        this.receiverType = in.readInt();
        this.receiverName = in.readString();
        this.receiverPhone = in.readString();
        this.receiverMobile = in.readString();
        this.receiverEmail = in.readString();
        this.receiverLinkman = in.readString();
        this.accountCheckStatus = in.readInt();
        this.remark = in.readString();
        this.poundageMinAmount = in.readString();
        this.minTransferAmount = in.readString();
        this.maxTransferDays = in.readString();
        this.poundageAmount = in.readString();
        this.settleMode = in.readString();
        this.packageRemainAmount = in.readString();
        this.merchantNo = in.readString();
        this.createTime = in.readString();
        this.processStatus = in.readInt();
        this.spMerchantNo = in.readString();
        this.companyName = in.readString();
        this.companyShortName = in.readString();
        this.operatorName = in.readString();
        this.operatorDuty = in.readString();
        this.operatorMobile = in.readString();
        this.withdrawalAmount = in.readFloat();
        this.freezeBalance = in.readFloat();
        this.signStatus = in.readInt();
        this.openPayStatus = in.readInt();
        this.openPayFailMsg = in.readString();
        this.unitType = in.readInt();
        this.operatorEmail = in.readString();
        this.licenseProvinceName = in.readString();
        this.customerServiceTel = in.readString();
        this.licenseDistrictName = in.readString();
        this.licenseProvinceCode = in.readString();
        this.licenseCityCode = in.readString();
        this.licenseDistrictCode = in.readString();
        this.industryCode = in.readString();
        this.licenseCityName = in.readString();
        this.businessCategoryName = in.readString();
        this.processInstanceID = in.readString();
        this.processFailReason = in.readString();
        this.auditLimitDate = in.readString();
        this.bankPersonType = in.readInt();
        this.unionBankNo = in.readString();
        this.businessAddress = in.readString();
        this.settleBankName = in.readString();
        this.activityStatus = in.readString();
        this.imgProxyContract = in.readString();
        this.proxyProtocol = in.readString();
        this.imgBankLicense = in.readString();
        this.activityEffect = in.readString();
        this.normalStageStatus = in.readString();
        this.syncBankStatus = in.readInt();
        this.syncBankFailMsg = in.readString();
        this.withdrawIsAllow = in.readString();
        this.groupName = in.readString();
        this.imgLicense = in.readString();
        this.licenseCode = in.readString();
        this.licenseAddress = in.readString();
        this.busiScope = in.readString();
        this.lpCardType = in.readInt();
        this.lpIDCardPeriodBeginDate = in.readString();
        this.lpIDCardPeriod = in.readString();
        this.lpName = in.readString();
        this.lpIDCardNo = in.readString();
        this.lpPhone = in.readString();
        this.imgLPIDCardFront = in.readString();
        this.imgLPIDCardBack = in.readString();
        this.contactIDCardNo = in.readString();
        this.imgBusiDoor = in.readString();
        this.imgBusiEnv = in.readString();
        this.imgBusiCounter = in.readString();
        this.licenseBeginDate = in.readString();
        this.licensePeriod = in.readString();
        this.reportList = new ArrayList<ReportListBean>();
        in.readList(this.reportList, ReportListBean.class.getClassLoader());
    }

    public static final Creator<WalletInfo> CREATOR = new Creator<WalletInfo>() {
        @Override
        public WalletInfo createFromParcel(Parcel source) {
            return new WalletInfo(source);
        }

        @Override
        public WalletInfo[] newArray(int size) {
            return new WalletInfo[size];
        }
    };
}