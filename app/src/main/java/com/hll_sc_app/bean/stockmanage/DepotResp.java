package com.hll_sc_app.bean.stockmanage;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hll_sc_app.bean.delivery.ProvinceListBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/5/8
 */
public class DepotResp implements Parcelable {
    private String note;
    private String actionTime;
    private String address;
    private String charge;
    private String actionBy;
    private String groupID;
    private int isOpeningBalance;
    private int source;
    private int isActive;
    private String linkTel;
    private int isOpenWms;
    private String houseName;
    private String createBy;
    private int isDefault;
    private String demandID;
    private String thirdHouseID;
    private String createTime;
    private String houseCode;
    private String action;
    private String alias;
    private String id;
    private int isWholeCountry;
    private String warehouseDeliveryRangeSummary;
    private String warehouseStoreCategorySummary;
    private int warehouseStoreProductNum;
    private List<ProvinceListBean> warehouseDeliveryRangeList;
    private List<CategorySubBean> warehouseStoreCategoryList;

    public DepotResp() {
    }

    protected DepotResp(Parcel in) {
        note = in.readString();
        actionTime = in.readString();
        address = in.readString();
        charge = in.readString();
        actionBy = in.readString();
        groupID = in.readString();
        isOpeningBalance = in.readInt();
        source = in.readInt();
        isActive = in.readInt();
        linkTel = in.readString();
        isOpenWms = in.readInt();
        houseName = in.readString();
        createBy = in.readString();
        isDefault = in.readInt();
        demandID = in.readString();
        thirdHouseID = in.readString();
        createTime = in.readString();
        houseCode = in.readString();
        action = in.readString();
        alias = in.readString();
        id = in.readString();
        isWholeCountry = in.readInt();
        warehouseDeliveryRangeSummary = in.readString();
        warehouseStoreCategorySummary = in.readString();
        warehouseStoreProductNum = in.readInt();
        warehouseDeliveryRangeList = in.createTypedArrayList(ProvinceListBean.CREATOR);
        warehouseStoreCategoryList = in.createTypedArrayList(CategorySubBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(note);
        dest.writeString(actionTime);
        dest.writeString(address);
        dest.writeString(charge);
        dest.writeString(actionBy);
        dest.writeString(groupID);
        dest.writeInt(isOpeningBalance);
        dest.writeInt(source);
        dest.writeInt(isActive);
        dest.writeString(linkTel);
        dest.writeInt(isOpenWms);
        dest.writeString(houseName);
        dest.writeString(createBy);
        dest.writeInt(isDefault);
        dest.writeString(demandID);
        dest.writeString(thirdHouseID);
        dest.writeString(createTime);
        dest.writeString(houseCode);
        dest.writeString(action);
        dest.writeString(alias);
        dest.writeString(id);
        dest.writeInt(isWholeCountry);
        dest.writeString(warehouseDeliveryRangeSummary);
        dest.writeString(warehouseStoreCategorySummary);
        dest.writeInt(warehouseStoreProductNum);
        dest.writeTypedList(warehouseDeliveryRangeList);
        dest.writeTypedList(warehouseStoreCategoryList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DepotResp> CREATOR = new Creator<DepotResp>() {
        @Override
        public DepotResp createFromParcel(Parcel in) {
            return new DepotResp(in);
        }

        @Override
        public DepotResp[] newArray(int size) {
            return new DepotResp[size];
        }
    };

    public List<ProvinceListBean> getWarehouseDeliveryRangeList() {
        return warehouseDeliveryRangeList;
    }

    public void setWarehouseDeliveryRangeList(List<ProvinceListBean> warehouseDeliveryRangeList) {
        this.warehouseDeliveryRangeList = warehouseDeliveryRangeList;
    }

    public List<CategorySubBean> getWarehouseStoreCategoryList() {
        return warehouseStoreCategoryList;
    }

    public void setWarehouseStoreCategoryList(List<CategorySubBean> warehouseStoreCategoryList) {
        this.warehouseStoreCategoryList = warehouseStoreCategoryList;
    }

    public int getIsWholeCountry() {
        return isWholeCountry;
    }

    public void setIsWholeCountry(int isWholeCountry) {
        this.isWholeCountry = isWholeCountry;
    }

    public String getWarehouseDeliveryRangeSummary() {
        return TextUtils.isEmpty(warehouseDeliveryRangeSummary) ? "— —" : warehouseDeliveryRangeSummary;
    }

    public void setWarehouseDeliveryRangeSummary(String warehouseDeliveryRangeSummary) {
        this.warehouseDeliveryRangeSummary = warehouseDeliveryRangeSummary;
    }

    public String getWarehouseStoreCategorySummary() {
        return TextUtils.isEmpty(warehouseStoreCategorySummary) ? "— —" : warehouseStoreCategorySummary;
    }

    public void setWarehouseStoreCategorySummary(String warehouseStoreCategorySummary) {
        this.warehouseStoreCategorySummary = warehouseStoreCategorySummary;
    }

    public int getWarehouseStoreProductNum() {
        return warehouseStoreProductNum;
    }

    public void setWarehouseStoreProductNum(int warehouseStoreProductNum) {
        this.warehouseStoreProductNum = warehouseStoreProductNum;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCharge() {
        return charge;
    }

    public void setCharge(String charge) {
        this.charge = charge;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public int getIsOpeningBalance() {
        return isOpeningBalance;
    }

    public void setIsOpeningBalance(int isOpeningBalance) {
        this.isOpeningBalance = isOpeningBalance;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
    }

    public String getLinkTel() {
        return linkTel;
    }

    public void setLinkTel(String linkTel) {
        this.linkTel = linkTel;
    }

    public int getIsOpenWms() {
        return isOpenWms;
    }

    public void setIsOpenWms(int isOpenWms) {
        this.isOpenWms = isOpenWms;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public int getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(int isDefault) {
        this.isDefault = isDefault;
    }

    public String getDemandID() {
        return demandID;
    }

    public void setDemandID(String demandID) {
        this.demandID = demandID;
    }

    public String getThirdHouseID() {
        return thirdHouseID;
    }

    public void setThirdHouseID(String thirdHouseID) {
        this.thirdHouseID = thirdHouseID;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getHouseCode() {
        return houseCode;
    }

    public void setHouseCode(String houseCode) {
        this.houseCode = houseCode;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
