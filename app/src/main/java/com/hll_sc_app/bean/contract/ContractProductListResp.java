package com.hll_sc_app.bean.contract;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.impl.IStringArrayGenerator;

import java.util.ArrayList;
import java.util.List;

//合同里的商品
public class ContractProductListResp implements Parcelable {
    private List<ProduceBean> list;

    protected ContractProductListResp(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ContractProductListResp> CREATOR = new Creator<ContractProductListResp>() {
        @Override
        public ContractProductListResp createFromParcel(Parcel in) {
            return new ContractProductListResp(in);
        }

        @Override
        public ContractProductListResp[] newArray(int size) {
            return new ContractProductListResp[size];
        }
    };

    public List<ProduceBean> getList() {
        return list;
    }

    public void setList(List<ProduceBean> list) {
        this.list = list;
    }


    public static class ProduceBean implements IStringArrayGenerator,Parcelable {
        private String index;
        private String productCode;
        private String productID;
        private String productName;
        private String productNum;
        private String productSpecID;
        private String remarks;
        private String saleUnitName;
        private String specContent;

        public ProduceBean(){

        }
        protected ProduceBean(Parcel in) {
            index = in.readString();
            productCode = in.readString();
            productID = in.readString();
            productName = in.readString();
            productNum = in.readString();
            productSpecID = in.readString();
            remarks = in.readString();
            saleUnitName = in.readString();
            specContent = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(index);
            dest.writeString(productCode);
            dest.writeString(productID);
            dest.writeString(productName);
            dest.writeString(productNum);
            dest.writeString(productSpecID);
            dest.writeString(remarks);
            dest.writeString(saleUnitName);
            dest.writeString(specContent);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<ProduceBean> CREATOR = new Creator<ProduceBean>() {
            @Override
            public ProduceBean createFromParcel(Parcel in) {
                return new ProduceBean(in);
            }

            @Override
            public ProduceBean[] newArray(int size) {
                return new ProduceBean[size];
            }
        };

        @Override
        public List<CharSequence> convertToRowData() {
            List<CharSequence> list = new ArrayList<>();
            list.add(index);
            list.add(productCode);
            list.add(productName);
            list.add(specContent+"/"+saleUnitName);
            list.add(productNum);
            list.add(remarks);
            return list;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
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

        public String getProductNum() {
            return productNum;
        }

        public void setProductNum(String productNum) {
            this.productNum = productNum;
        }

        public String getProductSpecID() {
            return productSpecID;
        }

        public void setProductSpecID(String productSpecID) {
            this.productSpecID = productSpecID;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getSaleUnitName() {
            return saleUnitName;
        }

        public void setSaleUnitName(String saleUnitName) {
            this.saleUnitName = saleUnitName;
        }

        public String getSpecContent() {
            return specContent;
        }

        public void setSpecContent(String specContent) {
            this.specContent = specContent;
        }
    }
}
