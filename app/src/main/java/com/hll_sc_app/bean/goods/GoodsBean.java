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
        public GoodsBean createFromParcel(Parcel in) {
            return new GoodsBean(in);
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
    private String occupiedStock;
    private String totalStock;
    private String discountRuleTypeName;
    private String specID;
    private String bgdSpecID;
    private String specNum;
    private String specStatus;
    private String minOrder;
    private String assistUnitStatus;
    private String offShelfTime;
    private String productNameSuggest;
    private String isDecimalBuy;
    private String onShelfTime;
    private String buyMinNum;
    private String odmId;
    private String lastChangePriceTime;
    private String bgdProductID;
    private String productPrice;
    private String skuCode;
    private String saleUnitName;
    private String standardUnitStatus;
    private String saleUnitID;
    private String substitutionType;
    private String ration;
    private String specContent;
    private String costPrice;
    private String blacklist;
    private String specPrice;
    private String convertRatio;
    private String costPriceModifyFlag;
    private String productNameKeyword;

    public GoodsBean() {
    }

    protected GoodsBean(Parcel in) {
        top = in.readInt();
        productTemplateID = in.readString();
        productID = in.readString();
        placeProvince = in.readString();
        productName = in.readString();
        productAttr = in.readString();
        appointSellType = in.readString();
        bundlingGoodsType = in.readString();
        bundlingGoodsDetails = in.createTypedArrayList(GoodsBean.CREATOR);
        productBrief = in.readString();
        isWareHourse = in.readString();
        editFrom = in.readString();
        action = in.readString();
        depositProductType = in.readString();
        purchaserIsVisible = in.readString();
        orgName = in.readString();
        cargoOwnerID = in.readString();
        cargoOwnerName = in.readString();
        standardUnitName = in.readString();
        groupID = in.readString();
        isCooperation = in.readByte() != 0;
        isDeliveryRange = in.readByte() != 0;
        nextDayDelivery = in.readString();
        stockCheckType = in.readString();
        brandId = in.readString();
        producer = in.readString();
        productSale = in.readString();
        note = in.readString();
        priceIsVisible = in.readByte() != 0;
        actionTime = in.readString();
        saleSpecNum = in.readString();
        isCollection = in.readByte() != 0;
        categoryThreeID = in.readString();
        shopProductCategoryID = in.readString();
        standardSpecID = in.readString();
        placeCity = in.readString();
        shopProductCategorySubID = in.readString();
        categoryID = in.readString();
        categoryName = in.readString();
        categorySubName = in.readString();
        categoryThreeName = in.readString();
        productType = in.readString();
        supplierName = in.readString();
        actionBy = in.readString();
        productStatus = in.readString();
        isSupplierWarehouse = in.readString();
        imgUrl = in.readString();
        imgUrlSub = in.readString();
        productCode = in.readString();
        categorySubID = in.readString();
        placeCityCode = in.readString();
        createTime = in.readString();
        guaranteePeriod = in.readString();
        resourceType = in.readString();
        placeProvinceCode = in.readString();
        shopProductCategoryThreeID = in.readString();
        shopProductCategoryThreeName = in.readString();
        specs = in.createTypedArrayList(SpecsBean.CREATOR);
        productAttrs = in.createTypedArrayList(ProductAttrBean.CREATOR);
        supplierShops = in.createTypedArrayList(SupplierShopsBean.CREATOR);
        nicknames = in.createTypedArrayList(NicknamesBean.CREATOR);
        imgUrlDetail = in.readString();
        labelList = in.createTypedArrayList(LabelBean.CREATOR);
        addResource = in.readString();
        buttonType = in.readString();
        isCheck = in.readByte() != 0;
        updateResource = in.readString();
        errorMsg = in.readString();
        shopProductCategorySubName = in.readString();
        usableStock = in.readString();
        stockWarnNum = in.readDouble();
        occupiedStock = in.readString();
        totalStock = in.readString();
        discountRuleTypeName = in.readString();
        specID = in.readString();
        bgdSpecID = in.readString();
        specNum = in.readString();
        specStatus = in.readString();
        minOrder = in.readString();
        assistUnitStatus = in.readString();
        offShelfTime = in.readString();
        productNameSuggest = in.readString();
        isDecimalBuy = in.readString();
        onShelfTime = in.readString();
        buyMinNum = in.readString();
        odmId = in.readString();
        lastChangePriceTime = in.readString();
        bgdProductID = in.readString();
        productPrice = in.readString();
        skuCode = in.readString();
        saleUnitName = in.readString();
        standardUnitStatus = in.readString();
        saleUnitID = in.readString();
        substitutionType = in.readString();
        ration = in.readString();
        specContent = in.readString();
        costPrice = in.readString();
        blacklist = in.readString();
        specPrice = in.readString();
        convertRatio = in.readString();
        costPriceModifyFlag = in.readString();
        productNameKeyword = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(top);
        dest.writeString(productTemplateID);
        dest.writeString(productID);
        dest.writeString(placeProvince);
        dest.writeString(productName);
        dest.writeString(productAttr);
        dest.writeString(appointSellType);
        dest.writeString(bundlingGoodsType);
        dest.writeTypedList(bundlingGoodsDetails);
        dest.writeString(productBrief);
        dest.writeString(isWareHourse);
        dest.writeString(editFrom);
        dest.writeString(action);
        dest.writeString(depositProductType);
        dest.writeString(purchaserIsVisible);
        dest.writeString(orgName);
        dest.writeString(cargoOwnerID);
        dest.writeString(cargoOwnerName);
        dest.writeString(standardUnitName);
        dest.writeString(groupID);
        dest.writeByte((byte) (isCooperation ? 1 : 0));
        dest.writeByte((byte) (isDeliveryRange ? 1 : 0));
        dest.writeString(nextDayDelivery);
        dest.writeString(stockCheckType);
        dest.writeString(brandId);
        dest.writeString(producer);
        dest.writeString(productSale);
        dest.writeString(note);
        dest.writeByte((byte) (priceIsVisible ? 1 : 0));
        dest.writeString(actionTime);
        dest.writeString(saleSpecNum);
        dest.writeByte((byte) (isCollection ? 1 : 0));
        dest.writeString(categoryThreeID);
        dest.writeString(shopProductCategoryID);
        dest.writeString(standardSpecID);
        dest.writeString(placeCity);
        dest.writeString(shopProductCategorySubID);
        dest.writeString(categoryID);
        dest.writeString(categoryName);
        dest.writeString(categorySubName);
        dest.writeString(categoryThreeName);
        dest.writeString(productType);
        dest.writeString(supplierName);
        dest.writeString(actionBy);
        dest.writeString(productStatus);
        dest.writeString(isSupplierWarehouse);
        dest.writeString(imgUrl);
        dest.writeString(imgUrlSub);
        dest.writeString(productCode);
        dest.writeString(categorySubID);
        dest.writeString(placeCityCode);
        dest.writeString(createTime);
        dest.writeString(guaranteePeriod);
        dest.writeString(resourceType);
        dest.writeString(placeProvinceCode);
        dest.writeString(shopProductCategoryThreeID);
        dest.writeString(shopProductCategoryThreeName);
        dest.writeTypedList(specs);
        dest.writeTypedList(productAttrs);
        dest.writeTypedList(supplierShops);
        dest.writeTypedList(nicknames);
        dest.writeString(imgUrlDetail);
        dest.writeTypedList(labelList);
        dest.writeString(addResource);
        dest.writeString(buttonType);
        dest.writeByte((byte) (isCheck ? 1 : 0));
        dest.writeString(updateResource);
        dest.writeString(errorMsg);
        dest.writeString(shopProductCategorySubName);
        dest.writeString(usableStock);
        dest.writeDouble(stockWarnNum);
        dest.writeString(occupiedStock);
        dest.writeString(totalStock);
        dest.writeString(discountRuleTypeName);
        dest.writeString(specID);
        dest.writeString(bgdSpecID);
        dest.writeString(specNum);
        dest.writeString(specStatus);
        dest.writeString(minOrder);
        dest.writeString(assistUnitStatus);
        dest.writeString(offShelfTime);
        dest.writeString(productNameSuggest);
        dest.writeString(isDecimalBuy);
        dest.writeString(onShelfTime);
        dest.writeString(buyMinNum);
        dest.writeString(odmId);
        dest.writeString(lastChangePriceTime);
        dest.writeString(bgdProductID);
        dest.writeString(productPrice);
        dest.writeString(skuCode);
        dest.writeString(saleUnitName);
        dest.writeString(standardUnitStatus);
        dest.writeString(saleUnitID);
        dest.writeString(substitutionType);
        dest.writeString(ration);
        dest.writeString(specContent);
        dest.writeString(costPrice);
        dest.writeString(blacklist);
        dest.writeString(specPrice);
        dest.writeString(convertRatio);
        dest.writeString(costPriceModifyFlag);
        dest.writeString(productNameKeyword);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getDiscountRuleTypeName() {
        return discountRuleTypeName;
    }

    public void setDiscountRuleTypeName(String discountRuleTypeName) {
        this.discountRuleTypeName = discountRuleTypeName;
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

    public boolean isCooperation() {
        return isCooperation;
    }

    public void setCooperation(boolean cooperation) {
        isCooperation = cooperation;
    }

    public boolean isDeliveryRange() {
        return isDeliveryRange;
    }

    public void setDeliveryRange(boolean deliveryRange) {
        isDeliveryRange = deliveryRange;
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

    public boolean isCollection() {
        return isCollection;
    }

    public void setCollection(boolean collection) {
        isCollection = collection;
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

    public String getSpecID() {
        return specID;
    }

    public void setSpecID(String specID) {
        this.specID = specID;
    }

    public String getBgdSpecID() {
        return bgdSpecID;
    }

    public void setBgdSpecID(String bgdSpecID) {
        this.bgdSpecID = bgdSpecID;
    }

    public String getSpecNum() {
        return specNum;
    }

    public void setSpecNum(String specNum) {
        this.specNum = specNum;
    }

    public String getSpecStatus() {
        return specStatus;
    }

    public void setSpecStatus(String specStatus) {
        this.specStatus = specStatus;
    }

    public String getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(String minOrder) {
        this.minOrder = minOrder;
    }

    public String getAssistUnitStatus() {
        return assistUnitStatus;
    }

    public void setAssistUnitStatus(String assistUnitStatus) {
        this.assistUnitStatus = assistUnitStatus;
    }

    public String getOffShelfTime() {
        return offShelfTime;
    }

    public void setOffShelfTime(String offShelfTime) {
        this.offShelfTime = offShelfTime;
    }

    public String getProductNameSuggest() {
        return productNameSuggest;
    }

    public void setProductNameSuggest(String productNameSuggest) {
        this.productNameSuggest = productNameSuggest;
    }

    public String getIsDecimalBuy() {
        return isDecimalBuy;
    }

    public void setIsDecimalBuy(String isDecimalBuy) {
        this.isDecimalBuy = isDecimalBuy;
    }

    public String getOnShelfTime() {
        return onShelfTime;
    }

    public void setOnShelfTime(String onShelfTime) {
        this.onShelfTime = onShelfTime;
    }

    public String getBuyMinNum() {
        return buyMinNum;
    }

    public void setBuyMinNum(String buyMinNum) {
        this.buyMinNum = buyMinNum;
    }

    public String getOdmId() {
        return odmId;
    }

    public void setOdmId(String odmId) {
        this.odmId = odmId;
    }

    public String getLastChangePriceTime() {
        return lastChangePriceTime;
    }

    public void setLastChangePriceTime(String lastChangePriceTime) {
        this.lastChangePriceTime = lastChangePriceTime;
    }

    public String getBgdProductID() {
        return bgdProductID;
    }

    public void setBgdProductID(String bgdProductID) {
        this.bgdProductID = bgdProductID;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getSaleUnitName() {
        return saleUnitName;
    }

    public void setSaleUnitName(String saleUnitName) {
        this.saleUnitName = saleUnitName;
    }

    public String getStandardUnitStatus() {
        return standardUnitStatus;
    }

    public void setStandardUnitStatus(String standardUnitStatus) {
        this.standardUnitStatus = standardUnitStatus;
    }

    public String getSaleUnitID() {
        return saleUnitID;
    }

    public void setSaleUnitID(String saleUnitID) {
        this.saleUnitID = saleUnitID;
    }

    public String getSubstitutionType() {
        return substitutionType;
    }

    public void setSubstitutionType(String substitutionType) {
        this.substitutionType = substitutionType;
    }

    public String getRation() {
        return ration;
    }

    public void setRation(String ration) {
        this.ration = ration;
    }

    public String getSpecContent() {
        return specContent;
    }

    public void setSpecContent(String specContent) {
        this.specContent = specContent;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public void setBlacklist(String blacklist) {
        this.blacklist = blacklist;
    }

    public String getSpecPrice() {
        return specPrice;
    }

    public void setSpecPrice(String specPrice) {
        this.specPrice = specPrice;
    }

    public String getConvertRatio() {
        return convertRatio;
    }

    public void setConvertRatio(String convertRatio) {
        this.convertRatio = convertRatio;
    }

    public String getCostPriceModifyFlag() {
        return costPriceModifyFlag;
    }

    public void setCostPriceModifyFlag(String costPriceModifyFlag) {
        this.costPriceModifyFlag = costPriceModifyFlag;
    }

    public String getProductNameKeyword() {
        return productNameKeyword;
    }

    public void setProductNameKeyword(String productNameKeyword) {
        this.productNameKeyword = productNameKeyword;
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
                productType, supplierName, actionBy, productStatus, isSupplierWarehouse});
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
}
