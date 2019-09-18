package com.hll_sc_app.bean.goods;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.base.utils.UIUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 商品的 Bean
 *
 * @author zhuyingsong
 * @date 2019-06-11
 */
public class GoodsBean implements Parcelable {
    /**
     * 是组合商品
     */
    public static final String BUNDLING_GOODS_TYPE = "1";
    /**
     * 是押金商品
     */
    public static final String DEPOSIT_GOODS_TYPE = "1";
    /**
     * 是代仓商品
     */
    public static final String WAREHOUSE_TYPE = "1";
    /**
     * 已上架
     */
    public static final String PRODUCT_STATUS_UP = "4";
    /**
     * 未上架
     */
    public static final String PRODUCT_STATUS_DOWN = "5";
    /**
     * 禁用
     */
    public static final String PRODUCT_STATUS_DISABLE = "7";
    /**
     * 从商品库导入-商品编辑
     */
    public static final String EDIT_FROM_TEMPLATE = "10";
    public static final Creator<GoodsBean> CREATOR = new Creator<GoodsBean>() {
        @Override
        public GoodsBean createFromParcel(Parcel source) {
            return new GoodsBean(source);
        }

        @Override
        public GoodsBean[] newArray(int size) {
            return new GoodsBean[size];
        }
    };
    /**
     * 是否置顶 0-非置顶,大于1-置顶
     */
    private int top;
    private String productTemplateID;
    private String productID;
    private String placeProvince;
    private String productName;
    private String productAttr;
    private String appointSellType;
    private String bundlingGoodsType;
    private List<GoodsBean> bundlingGoodsDetails;
    private String productBrief;
    private String isWareHourse;
    private String editFrom;
    private String action;
    /**
     * 是否押金商品(0-不是，1-是)
     */
    private String depositProductType;
    private String purchaserIsVisible;
    private String orgName;
    private String cargoOwnerID;
    private String cargoOwnerName;
    private String standardUnitName;
    private String groupID;
    private boolean isCooperation;
    private boolean isDeliveryRange;
    /**
     * 是否隔日达商品（0-不是，1-是）
     */
    private String nextDayDelivery;
    private String stockCheckType;
    private String brandId;
    private String producer;
    private String productSale;
    private String note;
    private boolean priceIsVisible;
    private String actionTime;
    private String saleSpecNum;
    private boolean isCollection;
    private String categoryThreeID;
    private String shopProductCategoryID;
    private String standardSpecID;
    private String placeCity;
    private String shopProductCategorySubID = "";
    private String categoryID;
    private String categoryName;
    private String categorySubName;
    private String categoryThreeName;
    private String productType;
    private String supplierName;
    private String actionBy;
    private String productStatus;
    private String isSupplierWarehouse;
    private String imgUrl;
    private String imgUrlSub;
    private String productCode;
    private String categorySubID;
    private String placeCityCode;
    private String createTime;
    private String guaranteePeriod;
    private String resourceType;
    private String placeProvinceCode;
    private String shopProductCategoryThreeID = "";
    private String shopProductCategoryThreeName = "";
    private List<SpecsBean> specs;
    private List<ProductAttrBean> productAttrs;
    private List<SupplierShopsBean> supplierShops;
    private List<NicknamesBean> nicknames;
    private String imgUrlDetail;
    private List<LabelBean> labelList;
    /**
     * 添加来源（1-普通添加，2-快速添加）
     */
    private String addResource;
    /**
     * 按钮类型（1-仅保存，2-申请上架）
     */
    private String buttonType;
    private boolean isCheck;
    /**
     * 修改来源（1-普通商品修改，2-其他商品修改）
     */
    private String updateResource;
    private String errorMsg;
    private String shopProductCategorySubName = "";
    private String usableStock;
    private double stockWarnNum;
    private boolean select;
    private String occupiedStock;
    private String totalStock;
    private boolean isSelected;

    public GoodsBean() {
    }

    protected GoodsBean(Parcel in) {
        this.top = in.readInt();
        this.productTemplateID = in.readString();
        this.productID = in.readString();
        this.placeProvince = in.readString();
        this.productName = in.readString();
        this.productAttr = in.readString();
        this.appointSellType = in.readString();
        this.bundlingGoodsType = in.readString();
        this.bundlingGoodsDetails = in.createTypedArrayList(GoodsBean.CREATOR);
        this.productBrief = in.readString();
        this.isWareHourse = in.readString();
        this.editFrom = in.readString();
        this.action = in.readString();
        this.depositProductType = in.readString();
        this.purchaserIsVisible = in.readString();
        this.orgName = in.readString();
        this.cargoOwnerID = in.readString();
        this.cargoOwnerName = in.readString();
        this.standardUnitName = in.readString();
        this.groupID = in.readString();
        this.isCooperation = in.readByte() != 0;
        this.isDeliveryRange = in.readByte() != 0;
        this.nextDayDelivery = in.readString();
        this.stockCheckType = in.readString();
        this.brandId = in.readString();
        this.producer = in.readString();
        this.productSale = in.readString();
        this.note = in.readString();
        this.priceIsVisible = in.readByte() != 0;
        this.actionTime = in.readString();
        this.saleSpecNum = in.readString();
        this.isCollection = in.readByte() != 0;
        this.categoryThreeID = in.readString();
        this.shopProductCategoryID = in.readString();
        this.standardSpecID = in.readString();
        this.placeCity = in.readString();
        this.shopProductCategorySubID = in.readString();
        this.categoryID = in.readString();
        this.categoryName = in.readString();
        this.categorySubName = in.readString();
        this.categoryThreeName = in.readString();
        this.productType = in.readString();
        this.supplierName = in.readString();
        this.actionBy = in.readString();
        this.productStatus = in.readString();
        this.isSupplierWarehouse = in.readString();
        this.imgUrl = in.readString();
        this.imgUrlSub = in.readString();
        this.productCode = in.readString();
        this.categorySubID = in.readString();
        this.placeCityCode = in.readString();
        this.createTime = in.readString();
        this.guaranteePeriod = in.readString();
        this.resourceType = in.readString();
        this.placeProvinceCode = in.readString();
        this.shopProductCategoryThreeID = in.readString();
        this.shopProductCategoryThreeName = in.readString();
        this.specs = in.createTypedArrayList(SpecsBean.CREATOR);
        this.productAttrs = in.createTypedArrayList(ProductAttrBean.CREATOR);
        this.supplierShops = in.createTypedArrayList(SupplierShopsBean.CREATOR);
        this.nicknames = in.createTypedArrayList(NicknamesBean.CREATOR);
        this.imgUrlDetail = in.readString();
        this.labelList = in.createTypedArrayList(LabelBean.CREATOR);
        this.addResource = in.readString();
        this.buttonType = in.readString();
        this.isCheck = in.readByte() != 0;
        this.updateResource = in.readString();
        this.errorMsg = in.readString();
        this.shopProductCategorySubName = in.readString();
        this.usableStock = in.readString();
        this.stockWarnNum = in.readDouble();
        this.select = in.readByte() != 0;
        this.occupiedStock = in.readString();
        this.totalStock = in.readString();
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(String totalStock) {
        this.totalStock = totalStock;
    }

    public String getOccupiedStock() {
        return occupiedStock;
    }

    public void setOccupiedStock(String occupiedStock) {
        this.occupiedStock = occupiedStock;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public double getStockWarnNum() {
        return stockWarnNum;
    }

    public void setStockWarnNum(double stockWarnNum) {
        this.stockWarnNum = stockWarnNum;
    }

    public String getProductTemplateID() {
        return productTemplateID;
    }

    public void setProductTemplateID(String productTemplateID) {
        this.productTemplateID = productTemplateID;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getButtonType() {
        return buttonType;
    }

    public void setButtonType(String buttonType) {
        this.buttonType = buttonType;
    }

    public String getImgUrlDetail() {
        return imgUrlDetail;
    }

    public void setImgUrlDetail(String imgUrlDetail) {
        this.imgUrlDetail = imgUrlDetail;
    }

    public String getShopProductCategoryThreeName() {
        return shopProductCategoryThreeName;
    }

    public void setShopProductCategoryThreeName(String shopProductCategoryThreeName) {
        this.shopProductCategoryThreeName = shopProductCategoryThreeName;
    }

    public String getShopProductCategorySubName() {
        return shopProductCategorySubName;
    }

    public void setShopProductCategorySubName(String shopProductCategorySubName) {
        this.shopProductCategorySubName = shopProductCategorySubName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategorySubName() {
        return categorySubName;
    }

    public void setCategorySubName(String categorySubName) {
        this.categorySubName = categorySubName;
    }

    public String getCategoryThreeName() {
        return categoryThreeName;
    }

    public void setCategoryThreeName(String categoryThreeName) {
        this.categoryThreeName = categoryThreeName;
    }

    public String getImgUrlSub() {
        return imgUrlSub;
    }

    public void setImgUrlSub(String imgUrlSub) {
        this.imgUrlSub = imgUrlSub;
    }

    public List<GoodsBean> getBundlingGoodsDetails() {
        return bundlingGoodsDetails;
    }

    public void setBundlingGoodsDetails(List<GoodsBean> bundlingGoodsDetails) {
        this.bundlingGoodsDetails = bundlingGoodsDetails;
    }

    public String getCargoOwnerName() {
        return cargoOwnerName;
    }

    public void setCargoOwnerName(String cargoOwnerName) {
        this.cargoOwnerName = cargoOwnerName;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getPlaceProvince() {
        return placeProvince;
    }

    public void setPlaceProvince(String placeProvince) {
        this.placeProvince = placeProvince;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(String productAttr) {
        this.productAttr = productAttr;
    }

    public String getAppointSellType() {
        return appointSellType;
    }

    public void setAppointSellType(String appointSellType) {
        this.appointSellType = appointSellType;
    }

    public String getBundlingGoodsType() {
        return bundlingGoodsType;
    }

    public void setBundlingGoodsType(String bundlingGoodsType) {
        this.bundlingGoodsType = bundlingGoodsType;
    }

    public String getProductBrief() {
        return productBrief;
    }

    public void setProductBrief(String productBrief) {
        this.productBrief = productBrief;
    }

    public String getIsWareHourse() {
        return isWareHourse;
    }

    public void setIsWareHourse(String isWareHourse) {
        this.isWareHourse = isWareHourse;
    }

    public String getShopProductCategorySubID() {
        return shopProductCategorySubID;
    }

    public void setShopProductCategorySubID(String shopProductCategorySubID) {
        this.shopProductCategorySubID = shopProductCategorySubID;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDepositProductType() {
        return depositProductType;
    }

    public void setDepositProductType(String depositProductType) {
        this.depositProductType = depositProductType;
    }

    public String getPurchaserIsVisible() {
        return purchaserIsVisible;
    }

    public void setPurchaserIsVisible(String purchaserIsVisible) {
        this.purchaserIsVisible = purchaserIsVisible;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getCargoOwnerID() {
        return cargoOwnerID;
    }

    public void setCargoOwnerID(String cargoOwnerID) {
        this.cargoOwnerID = cargoOwnerID;
    }

    public String getStandardUnitName() {
        return standardUnitName;
    }

    public void setStandardUnitName(String standardUnitName) {
        this.standardUnitName = standardUnitName;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public boolean isIsCooperation() {
        return isCooperation;
    }

    public void setIsCooperation(boolean isCooperation) {
        this.isCooperation = isCooperation;
    }

    public boolean isIsDeliveryRange() {
        return isDeliveryRange;
    }

    public void setIsDeliveryRange(boolean isDeliveryRange) {
        this.isDeliveryRange = isDeliveryRange;
    }

    public String getNextDayDelivery() {
        return nextDayDelivery;
    }

    public void setNextDayDelivery(String nextDayDelivery) {
        this.nextDayDelivery = nextDayDelivery;
    }

    public String getStockCheckType() {
        return stockCheckType;
    }

    public void setStockCheckType(String stockCheckType) {
        this.stockCheckType = stockCheckType;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getProductSale() {
        return productSale;
    }

    public void setProductSale(String productSale) {
        this.productSale = productSale;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isPriceIsVisible() {
        return priceIsVisible;
    }

    public void setPriceIsVisible(boolean priceIsVisible) {
        this.priceIsVisible = priceIsVisible;
    }

    public String getActionTime() {
        return actionTime;
    }

    public void setActionTime(String actionTime) {
        this.actionTime = actionTime;
    }

    public String getSaleSpecNum() {
        return saleSpecNum;
    }

    public void setSaleSpecNum(String saleSpecNum) {
        this.saleSpecNum = saleSpecNum;
    }

    public boolean isIsCollection() {
        return isCollection;
    }

    public void setIsCollection(boolean isCollection) {
        this.isCollection = isCollection;
    }

    public String getCategoryThreeID() {
        return categoryThreeID;
    }

    public void setCategoryThreeID(String categoryThreeID) {
        this.categoryThreeID = categoryThreeID;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public String getShopProductCategoryID() {
        return shopProductCategoryID;
    }

    public void setShopProductCategoryID(String shopProductCategoryID) {
        this.shopProductCategoryID = shopProductCategoryID;
    }

    public String getStandardSpecID() {
        return standardSpecID;
    }

    public void setStandardSpecID(String standardSpecID) {
        this.standardSpecID = standardSpecID;
    }

    public String getPlaceCity() {
        return placeCity;
    }

    public void setPlaceCity(String placeCity) {
        this.placeCity = placeCity;
    }

    public String getShopProductCategoryThreeID() {
        return shopProductCategoryThreeID;
    }

    public void setShopProductCategoryThreeID(String shopProductCategoryThreeID) {
        this.shopProductCategoryThreeID = shopProductCategoryThreeID;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getActionBy() {
        return actionBy;
    }

    public void setActionBy(String actionBy) {
        this.actionBy = actionBy;
    }

    public String getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(String productStatus) {
        this.productStatus = productStatus;
    }

    public String getIsSupplierWarehouse() {
        return isSupplierWarehouse;
    }

    public void setIsSupplierWarehouse(String isSupplierWarehouse) {
        this.isSupplierWarehouse = isSupplierWarehouse;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getCategorySubID() {
        return categorySubID;
    }

    public void setCategorySubID(String categorySubID) {
        this.categorySubID = categorySubID;
    }

    public String getPlaceCityCode() {
        return placeCityCode;
    }

    public void setPlaceCityCode(String placeCityCode) {
        this.placeCityCode = placeCityCode;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGuaranteePeriod() {
        return guaranteePeriod;
    }

    public void setGuaranteePeriod(String guaranteePeriod) {
        this.guaranteePeriod = guaranteePeriod;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getPlaceProvinceCode() {
        return placeProvinceCode;
    }

    public void setPlaceProvinceCode(String placeProvinceCode) {
        this.placeProvinceCode = placeProvinceCode;
    }

    public List<SpecsBean> getSpecs() {
        return specs;
    }

    public void setSpecs(List<SpecsBean> specs) {
        this.specs = specs;
    }

    public List<ProductAttrBean> getProductAttrs() {
        return productAttrs;
    }

    public void setProductAttrs(List<ProductAttrBean> productAttrs) {
        this.productAttrs = productAttrs;
    }

    public List<SupplierShopsBean> getSupplierShops() {
        return supplierShops;
    }

    public void setSupplierShops(List<SupplierShopsBean> supplierShops) {
        this.supplierShops = supplierShops;
    }

    public List<NicknamesBean> getNicknames() {
        return nicknames;
    }

    public void setNicknames(List<NicknamesBean> nicknames) {
        this.nicknames = nicknames;
    }

    public String getAddResource() {
        return addResource;
    }

    public void setAddResource(String addResource) {
        this.addResource = addResource;
    }

    public List<LabelBean> getLabelList() {
        return labelList;
    }

    public void setLabelList(List<LabelBean> labelList) {
        this.labelList = labelList;
    }

    public String getUpdateResource() {
        return updateResource;
    }

    public void setUpdateResource(String updateResource) {
        this.updateResource = updateResource;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{productID, placeProvince, productName, productAttr, appointSellType,
                bundlingGoodsType,
                bundlingGoodsDetails, productBrief, isWareHourse, shopProductCategorySubID, action, depositProductType,
                purchaserIsVisible, orgName, cargoOwnerID, cargoOwnerName, standardUnitName, groupID, isCooperation,
                isDeliveryRange, nextDayDelivery, stockCheckType, brandId, producer, productSale, note, priceIsVisible,
                actionTime, saleSpecNum, isCollection, categoryThreeID, top, shopProductCategoryID, standardSpecID,
                placeCity, shopProductCategoryThreeID, categoryID, categoryName, categorySubName, categoryThreeName,
                productType, supplierName, actionBy, productStatus, isSupplierWarehouse, select});
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        final GoodsBean other = (GoodsBean) obj;
        return UIUtils.equals(this.productID, other.productID);
    }

    public String getEditFrom() {
        return editFrom;
    }

    public void setEditFrom(String editFrom) {
        this.editFrom = editFrom;
    }

    public String getUsableStock() {
        return usableStock;
    }

    public void setUsableStock(String usableStock) {
        this.usableStock = usableStock;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.top);
        dest.writeString(this.productTemplateID);
        dest.writeString(this.productID);
        dest.writeString(this.placeProvince);
        dest.writeString(this.productName);
        dest.writeString(this.productAttr);
        dest.writeString(this.appointSellType);
        dest.writeString(this.bundlingGoodsType);
        dest.writeTypedList(this.bundlingGoodsDetails);
        dest.writeString(this.productBrief);
        dest.writeString(this.isWareHourse);
        dest.writeString(this.editFrom);
        dest.writeString(this.action);
        dest.writeString(this.depositProductType);
        dest.writeString(this.purchaserIsVisible);
        dest.writeString(this.orgName);
        dest.writeString(this.cargoOwnerID);
        dest.writeString(this.cargoOwnerName);
        dest.writeString(this.standardUnitName);
        dest.writeString(this.groupID);
        dest.writeByte(this.isCooperation ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isDeliveryRange ? (byte) 1 : (byte) 0);
        dest.writeString(this.nextDayDelivery);
        dest.writeString(this.stockCheckType);
        dest.writeString(this.brandId);
        dest.writeString(this.producer);
        dest.writeString(this.productSale);
        dest.writeString(this.note);
        dest.writeByte(this.priceIsVisible ? (byte) 1 : (byte) 0);
        dest.writeString(this.actionTime);
        dest.writeString(this.saleSpecNum);
        dest.writeByte(this.isCollection ? (byte) 1 : (byte) 0);
        dest.writeString(this.categoryThreeID);
        dest.writeString(this.shopProductCategoryID);
        dest.writeString(this.standardSpecID);
        dest.writeString(this.placeCity);
        dest.writeString(this.shopProductCategorySubID);
        dest.writeString(this.categoryID);
        dest.writeString(this.categoryName);
        dest.writeString(this.categorySubName);
        dest.writeString(this.categoryThreeName);
        dest.writeString(this.productType);
        dest.writeString(this.supplierName);
        dest.writeString(this.actionBy);
        dest.writeString(this.productStatus);
        dest.writeString(this.isSupplierWarehouse);
        dest.writeString(this.imgUrl);
        dest.writeString(this.imgUrlSub);
        dest.writeString(this.productCode);
        dest.writeString(this.categorySubID);
        dest.writeString(this.placeCityCode);
        dest.writeString(this.createTime);
        dest.writeString(this.guaranteePeriod);
        dest.writeString(this.resourceType);
        dest.writeString(this.placeProvinceCode);
        dest.writeString(this.shopProductCategoryThreeID);
        dest.writeString(this.shopProductCategoryThreeName);
        dest.writeTypedList(this.specs);
        dest.writeTypedList(this.productAttrs);
        dest.writeTypedList(this.supplierShops);
        dest.writeTypedList(this.nicknames);
        dest.writeString(this.imgUrlDetail);
        dest.writeTypedList(this.labelList);
        dest.writeString(this.addResource);
        dest.writeString(this.buttonType);
        dest.writeByte(this.isCheck ? (byte) 1 : (byte) 0);
        dest.writeString(this.updateResource);
        dest.writeString(this.errorMsg);
        dest.writeString(this.shopProductCategorySubName);
        dest.writeString(this.usableStock);
        dest.writeDouble(this.stockWarnNum);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
        dest.writeString(this.occupiedStock);
        dest.writeString(this.totalStock);
    }
}
