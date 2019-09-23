package com.hll_sc_app.bean.order.place;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/19
 */
public class DiscountPlanBean implements Parcelable {
    public static final int DISCOUNT_PRODUCT = 2;
    public static final int DISCOUNT_ORDER = 1;
    public static final int DISCOUNT_NONE = 0;
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
    private List<DiscountBean> productDiscounts;
    private List<DiscountBean> orderDiscounts;
    private transient List<DiscountBean> shopDiscounts;

    protected DiscountPlanBean(Parcel in) {
        groupID = in.readString();
        productDiscountMoney = in.readDouble();
        useDiscountType = in.readInt();
        productDiscounts = in.createTypedArrayList(DiscountBean.CREATOR);
        orderDiscounts = in.createTypedArrayList(DiscountBean.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(groupID);
        dest.writeDouble(productDiscountMoney);
        dest.writeInt(useDiscountType);
        dest.writeTypedList(productDiscounts);
        dest.writeTypedList(orderDiscounts);
    }

    @Override
    public int describeContents() {
        return 0;
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

    public List<DiscountBean> getProductDiscounts() {
        return productDiscounts;
    }

    public void setProductDiscounts(List<DiscountBean> productDiscounts) {
        this.productDiscounts = productDiscounts;
    }

    public List<DiscountBean> getOrderDiscounts() {
        return orderDiscounts;
    }

    public void setOrderDiscounts(List<DiscountBean> orderDiscounts) {
        this.orderDiscounts = orderDiscounts;
    }

    public List<DiscountBean> getShopDiscounts() {
        if (shopDiscounts == null) {
            if (CommonUtils.isEmpty(productDiscounts) && CommonUtils.isEmpty(orderDiscounts)) {
                return null;
            }
            shopDiscounts = new ArrayList<>();
            DiscountBean noneBean = new DiscountBean();
            noneBean.setRuleName("不使用店铺优惠");
            shopDiscounts.add(noneBean);
            if (!CommonUtils.isEmpty(orderDiscounts)) {
                shopDiscounts.addAll(orderDiscounts);
            }
            if (!CommonUtils.isEmpty(productDiscounts)) {
                DiscountBean discountBean = new DiscountBean();
                shopDiscounts.add(discountBean);
                discountBean.setRuleType(-1);
                discountBean.setRuleName("商品优惠活动");
                discountBean.setDiscountValue(productDiscountMoney);
                List<OrderCommitReq.DiscountBean.DiscountSpecBean> specBeans = new ArrayList<>();
                discountBean.setSpecList(specBeans);
                for (DiscountBean bean : productDiscounts) {
                    OrderCommitReq.DiscountBean.DiscountSpecBean specBean = new OrderCommitReq.DiscountBean.DiscountSpecBean();
                    specBean.setSpecID(bean.getSpecID());
                    specBean.setDiscountID(bean.getDiscountID());
                    specBean.setProductID(bean.getProductID());
                    specBean.setRuleID(bean.getRuleID());
                    specBean.setDiscountAmount(bean.getDiscountValue());
                    specBeans.add(specBean);
                }
            }
        }
        return shopDiscounts;
    }

    public static class DiscountBean implements Parcelable {
        public static final Creator<DiscountBean> CREATOR = new Creator<DiscountBean>() {
            @Override
            public DiscountBean createFromParcel(Parcel in) {
                return new DiscountBean(in);
            }

            @Override
            public DiscountBean[] newArray(int size) {
                return new DiscountBean[size];
            }
        };
        /**
         * 优惠 id
         */
        private String discountID;
        /**
         * 优惠额
         */
        private double discountValue;
        /**
         * 商品优惠时商品id
         */
        private String productID;
        /**
         * 活动规则id
         */
        private String ruleID;
        /**
         * 活动规则名称
         */
        private String ruleName;
        /**
         * 活动规则类型 1 赠送 2 满减 3 满折 4 直降 5 赠券
         */
        private int ruleType;
        private String specID;
        private transient List<OrderCommitReq.DiscountBean.DiscountSpecBean> specList;

        public DiscountBean() {
        }

        protected DiscountBean(Parcel in) {
            discountID = in.readString();
            discountValue = in.readDouble();
            productID = in.readString();
            ruleID = in.readString();
            ruleName = in.readString();
            ruleType = in.readInt();
            specID = in.readString();
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

        public String getSpecID() {
            return specID;
        }

        public void setSpecID(String specID) {
            this.specID = specID;
        }

        public List<OrderCommitReq.DiscountBean.DiscountSpecBean> getSpecList() {
            return specList;
        }

        public void setSpecList(List<OrderCommitReq.DiscountBean.DiscountSpecBean> specList) {
            this.specList = specList;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(discountID);
            dest.writeDouble(discountValue);
            dest.writeString(productID);
            dest.writeString(ruleID);
            dest.writeString(ruleName);
            dest.writeInt(ruleType);
            dest.writeString(specID);
        }

        @Override
        public int describeContents() {
            return 0;
        }
    }
}
