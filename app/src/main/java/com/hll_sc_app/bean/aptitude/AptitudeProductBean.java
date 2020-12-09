package com.hll_sc_app.bean.aptitude;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 11/30/20.
 */
public class AptitudeProductBean implements Parcelable {
    private String id;
    private String productBatch;
    private String productID;
    private String extGroupID;
    private ProductInfoBean aptitudeProduct;

    public AptitudeProductBean() {
    }

    protected AptitudeProductBean(Parcel in) {
        id = in.readString();
        productBatch = in.readString();
        productID = in.readString();
        extGroupID = in.readString();
        aptitudeProduct = in.readParcelable(ProductInfoBean.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(productBatch);
        dest.writeString(productID);
        dest.writeString(extGroupID);
        dest.writeParcelable(aptitudeProduct, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<AptitudeProductBean> CREATOR = new Creator<AptitudeProductBean>() {
        @Override
        public AptitudeProductBean createFromParcel(Parcel in) {
            return new AptitudeProductBean(in);
        }

        @Override
        public AptitudeProductBean[] newArray(int size) {
            return new AptitudeProductBean[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductBatch() {
        return productBatch;
    }

    public void setProductBatch(String productBatch) {
        this.productBatch = productBatch;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getExtGroupID() {
        return extGroupID;
    }

    public void setExtGroupID(String extGroupID) {
        this.extGroupID = extGroupID;
    }

    public ProductInfoBean getAptitudeProduct() {
        return aptitudeProduct;
    }

    public void setAptitudeProduct(ProductInfoBean aptitudeProduct) {
        this.aptitudeProduct = aptitudeProduct;
    }

    public static class ProductInfoBean implements Parcelable {
        private String groupID;
        private String imgUrl;
        private String productCode;
        private String productID;
        private String productName;
        private String saleSpecNum;

        public ProductInfoBean() {
        }

        protected ProductInfoBean(Parcel in) {
            groupID = in.readString();
            imgUrl = in.readString();
            productCode = in.readString();
            productID = in.readString();
            productName = in.readString();
            saleSpecNum = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(groupID);
            dest.writeString(imgUrl);
            dest.writeString(productCode);
            dest.writeString(productID);
            dest.writeString(productName);
            dest.writeString(saleSpecNum);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ProductInfoBean> CREATOR = new Creator<ProductInfoBean>() {
            @Override
            public ProductInfoBean createFromParcel(Parcel in) {
                return new ProductInfoBean(in);
            }

            @Override
            public ProductInfoBean[] newArray(int size) {
                return new ProductInfoBean[size];
            }
        };

        public String getGroupID() {
            return groupID;
        }

        public void setGroupID(String groupID) {
            this.groupID = groupID;
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

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getSaleSpecNum() {
            return saleSpecNum;
        }

        public void setSaleSpecNum(String saleSpecNum) {
            this.saleSpecNum = saleSpecNum;
        }
    }
}
