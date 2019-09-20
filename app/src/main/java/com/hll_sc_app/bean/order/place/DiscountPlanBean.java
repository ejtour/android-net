package com.hll_sc_app.bean.order.place;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/19
 */
public class DiscountPlanBean implements Parcelable {
    public static final Creator<DiscountPlanBean> CREATOR = new Creator<DiscountPlanBean>() {
        @Override
        public DiscountPlanBean createFromParcel(Parcel in) {
            return new DiscountPlanBean(in);
        }

        @Override
        public DiscountPlanBean[] newArray(int size) {
            return new DiscountPlanBean[size];
        }
    };
    private String groupID;
    private double productDiscountMoney;
    private int useDiscountType;
    private List<ProductDiscountBean> productDiscounts;

    protected DiscountPlanBean(Parcel in) {
        groupID = in.readString();
        productDiscountMoney = in.readDouble();
        useDiscountType = in.readInt();
        productDiscounts = in.createTypedArrayList(ProductDiscountBean.CREATOR);
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String groupID) {
        this.groupID = groupID;
    }

    public double getProductDiscountMoney() {
        return productDiscountMoney;
    }

    public void setProductDiscountMoney(double productDiscountMoney) {
        this.productDiscountMoney = productDiscountMoney;
    }

    public int getUseDiscountType() {
        return useDiscountType;
    }

    public void setUseDiscountType(int useDiscountType) {
        this.useDiscountType = useDiscountType;
    }

    public List<ProductDiscountBean> getProductDiscounts() {
        return productDiscounts;
    }

    public void setProductDiscounts(List<ProductDiscountBean> productDiscounts) {
        this.productDiscounts = productDiscounts;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupID);
        dest.writeDouble(productDiscountMoney);
        dest.writeInt(useDiscountType);
        dest.writeTypedList(productDiscounts);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static class ProductDiscountBean implements Parcelable {
        public static final Creator<ProductDiscountBean> CREATOR = new Creator<ProductDiscountBean>() {
            @Override
            public ProductDiscountBean createFromParcel(Parcel in) {
                return new ProductDiscountBean(in);
            }

            @Override
            public ProductDiscountBean[] newArray(int size) {
                return new ProductDiscountBean[size];
            }
        };
        private int discountCondition;
        private String discountID;
        private double discountValue;
        private String productID;
        private int ruleCondition;
        private double ruleDiscountValue;
        private String ruleID;
        private String ruleName;
        private int ruleType;
        private String ruleTypeName;
        private String specID;

        protected ProductDiscountBean(Parcel in) {
            discountCondition = in.readInt();
            discountID = in.readString();
            discountValue = in.readDouble();
            productID = in.readString();
            ruleCondition = in.readInt();
            ruleDiscountValue = in.readDouble();
            ruleID = in.readString();
            ruleName = in.readString();
            ruleType = in.readInt();
            ruleTypeName = in.readString();
            specID = in.readString();
        }

        public int getDiscountCondition() {
            return discountCondition;
        }

        public void setDiscountCondition(int discountCondition) {
            this.discountCondition = discountCondition;
        }

        public String getDiscountID() {
            return discountID;
        }

        public void setDiscountID(String discountID) {
            this.discountID = discountID;
        }

        public double getDiscountValue() {
            return discountValue;
        }

        public void setDiscountValue(double discountValue) {
            this.discountValue = discountValue;
        }

        public String getProductID() {
            return productID;
        }

        public void setProductID(String productID) {
            this.productID = productID;
        }

        public int getRuleCondition() {
            return ruleCondition;
        }

        public void setRuleCondition(int ruleCondition) {
            this.ruleCondition = ruleCondition;
        }

        public double getRuleDiscountValue() {
            return ruleDiscountValue;
        }

        public void setRuleDiscountValue(double ruleDiscountValue) {
            this.ruleDiscountValue = ruleDiscountValue;
        }

        public String getRuleID() {
            return ruleID;
        }

        public void setRuleID(String ruleID) {
            this.ruleID = ruleID;
        }

        public String getRuleName() {
            return ruleName;
        }

        public void setRuleName(String ruleName) {
            this.ruleName = ruleName;
        }

        public int getRuleType() {
            return ruleType;
        }

        public void setRuleType(int ruleType) {
            this.ruleType = ruleType;
        }

        public String getRuleTypeName() {
            return ruleTypeName;
        }

        public void setRuleTypeName(String ruleTypeName) {
            this.ruleTypeName = ruleTypeName;
        }

        public String getSpecID() {
            return specID;
        }

        public void setSpecID(String specID) {
            this.specID = specID;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(discountCondition);
            dest.writeString(discountID);
            dest.writeDouble(discountValue);
            dest.writeString(productID);
            dest.writeInt(ruleCondition);
            dest.writeDouble(ruleDiscountValue);
            dest.writeString(ruleID);
            dest.writeString(ruleName);
            dest.writeInt(ruleType);
            dest.writeString(ruleTypeName);
            dest.writeString(specID);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
