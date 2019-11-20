package com.hll_sc_app.bean.customer;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/11/20
 */

public class CustomerBean implements Parcelable {
    public static final Creator<CustomerBean> CREATOR = new Creator<CustomerBean>() {
        @Override
        public CustomerBean createFromParcel(Parcel in) {
            return new CustomerBean(in);
        }

        @Override
        public CustomerBean[] newArray(int size) {
            return new CustomerBean[size];
        }
    };
    /**
     * 1:新增客户；2:修改客户
     */
    @Expose(deserialize = false)
    private int actionType;
    @Expose(deserialize = false)
    private CustomerAreaBean customerArea;
    @Expose(serialize = false)
    private String customerProvince;
    @Expose(serialize = false)
    private String customerCity;
    @Expose(serialize = false)
    private String customerDistrict;
    @Expose(serialize = false)
    private String customerProvinceCode;
    @Expose(serialize = false)
    private String customerCityCode;
    @Expose(serialize = false)
    private String customerDistrictCode;
    private String customerAddress;
    private String groupID;
    private String employeeID;
    /**
     * 客户级别：A-高价值客户；B-普通客户；C-低价值客户
     */
    private String customerLevel;
    private String customerName;
    private String customerLinkman;
    /**
     * 客户状态：1-初步接触；2-意向客户；3-报价客户；4-合作客户；5-搁置客户
     */
    private int customerStatus;
    private String customerPhone;
    /**
     * 客户类型：1-单店；2-连锁
     */
    private int customerType;
    private String visitTime;
    private String createTime;
    private int shopCount;
    /**
     * 客户来源：1-陌生拜访；2-老客户；3-客户介绍；4-市场活动；5-其他
     */
    private int customerSource;
    private String id;
    private String customerAdmin;

    public CustomerBean() {
    }

    protected CustomerBean(Parcel in) {
        actionType = in.readInt();
        customerAddress = in.readString();
        customerDistrictCode = in.readString();
        customerProvince = in.readString();
        groupID = in.readString();
        employeeID = in.readString();
        customerCity = in.readString();
        customerLevel = in.readString();
        customerName = in.readString();
        customerLinkman = in.readString();
        customerStatus = in.readInt();
        customerDistrict = in.readString();
        customerPhone = in.readString();
        customerType = in.readInt();
        visitTime = in.readString();
        createTime = in.readString();
        customerCityCode = in.readString();
        shopCount = in.readInt();
        customerProvinceCode = in.readString();
        customerSource = in.readInt();
        id = in.readString();
        customerAdmin = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(actionType);
        dest.writeString(customerAddress);
        dest.writeString(customerDistrictCode);
        dest.writeString(customerProvince);
        dest.writeString(groupID);
        dest.writeString(employeeID);
        dest.writeString(customerCity);
        dest.writeString(customerLevel);
        dest.writeString(customerName);
        dest.writeString(customerLinkman);
        dest.writeInt(customerStatus);
        dest.writeString(customerDistrict);
        dest.writeString(customerPhone);
        dest.writeInt(customerType);
        dest.writeString(visitTime);
        dest.writeString(createTime);
        dest.writeString(customerCityCode);
        dest.writeInt(shopCount);
        dest.writeString(customerProvinceCode);
        dest.writeInt(customerSource);
        dest.writeString(id);
        dest.writeString(customerAdmin);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void preProcess() {
        CustomerAreaBean customerArea = new CustomerAreaBean();
        customerArea.setCustomerCity(getCustomerCity());
        customerArea.setCustomerCityCode(getCustomerCityCode());
        customerArea.setCustomerDistrict(getCustomerDistrict());
        customerArea.setCustomerDistrictCode(getCustomerDistrictCode());
        customerArea.setCustomerProvince(getCustomerProvince());
        customerArea.setCustomerProvinceCode(getCustomerProvinceCode());
        setCustomerArea(customerArea);
    }

    public int getActionType() {
        return actionType;
    }

    public void setActionType(int actionType) {
        this.actionType = actionType;
    }

    public CustomerAreaBean getCustomerArea() {
        return customerArea;
    }

    public void setCustomerArea(CustomerAreaBean customerArea) {
        this.customerArea = customerArea;
    }

    public String getCustomerAddress() {
        return customerAddress;
    }

    public void setCustomerAddress(String customerAddress) {
        this.customerAddress = customerAddress;
    }

    public String getCustomerDistrictCode() {
        return customerDistrictCode;
    }

    public void setCustomerDistrictCode(String customerDistrictCode) {
        this.customerDistrictCode = customerDistrictCode;
    }

    public String getCustomerProvince() {
        return customerProvince;
    }

    public void setCustomerProvince(String customerProvince) {
        this.customerProvince = customerProvince;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public String getCustomerLevel() {
        return customerLevel;
    }

    public void setCustomerLevel(String customerLevel) {
        this.customerLevel = customerLevel;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerLinkman() {
        return customerLinkman;
    }

    public void setCustomerLinkman(String customerLinkman) {
        this.customerLinkman = customerLinkman;
    }

    public int getCustomerStatus() {
        return customerStatus;
    }

    public void setCustomerStatus(int customerStatus) {
        this.customerStatus = customerStatus;
    }

    public String getCustomerDistrict() {
        return customerDistrict;
    }

    public void setCustomerDistrict(String customerDistrict) {
        this.customerDistrict = customerDistrict;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public int getCustomerType() {
        return customerType;
    }

    public void setCustomerType(int customerType) {
        this.customerType = customerType;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCustomerCityCode() {
        return customerCityCode;
    }

    public void setCustomerCityCode(String customerCityCode) {
        this.customerCityCode = customerCityCode;
    }

    public int getShopCount() {
        return shopCount;
    }

    public void setShopCount(int shopCount) {
        this.shopCount = shopCount;
    }

    public String getCustomerProvinceCode() {
        return customerProvinceCode;
    }

    public void setCustomerProvinceCode(String customerProvinceCode) {
        this.customerProvinceCode = customerProvinceCode;
    }

    public int getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(int customerSource) {
        this.customerSource = customerSource;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCustomerAdmin() {
        return customerAdmin;
    }

    public void setCustomerAdmin(String customerAdmin) {
        this.customerAdmin = customerAdmin;
    }
}
