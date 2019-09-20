package com.hll_sc_app.bean.order.place;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.bean.goods.GoodsBean;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/19
 */

public class SupplierGroupBean implements Parcelable {
    private int deliverType;
    private double depositAmount;
    private DiscountPlanBean discountPlan;
    private String houseAddress;
    private PaymentBean payment;
    private int payType;
    private List<GoodsBean> productList;
    private String supplierID;
    private String supplierShopID;
    private String supplierShopName;
    private double totalAmount;
    private int wareHourseStatus;

    protected SupplierGroupBean(Parcel in) {
        deliverType = in.readInt();
        depositAmount = in.readDouble();
        discountPlan = in.readParcelable(DiscountPlanBean.class.getClassLoader());
        houseAddress = in.readString();
        payment = in.readParcelable(PaymentBean.class.getClassLoader());
        payType = in.readInt();
        productList = in.createTypedArrayList(GoodsBean.CREATOR);
        supplierID = in.readString();
        supplierShopID = in.readString();
        supplierShopName = in.readString();
        totalAmount = in.readDouble();
        wareHourseStatus = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(deliverType);
        dest.writeDouble(depositAmount);
        dest.writeParcelable(discountPlan, flags);
        dest.writeString(houseAddress);
        dest.writeParcelable(payment, flags);
        dest.writeInt(payType);
        dest.writeTypedList(productList);
        dest.writeString(supplierID);
        dest.writeString(supplierShopID);
        dest.writeString(supplierShopName);
        dest.writeDouble(totalAmount);
        dest.writeInt(wareHourseStatus);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SupplierGroupBean> CREATOR = new Creator<SupplierGroupBean>() {
        @Override
        public SupplierGroupBean createFromParcel(Parcel in) {
            return new SupplierGroupBean(in);
        }

        @Override
        public SupplierGroupBean[] newArray(int size) {
            return new SupplierGroupBean[size];
        }
    };

    public int getDeliverType() {
        return deliverType;
    }

    public void setDeliverType(int deliverType) {
        this.deliverType = deliverType;
    }

    public double getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(double depositAmount) {
        this.depositAmount = depositAmount;
    }

    public DiscountPlanBean getDiscountPlan() {
        return discountPlan;
    }

    public void setDiscountPlan(DiscountPlanBean discountPlan) {
        this.discountPlan = discountPlan;
    }

    public String getHouseAddress() {
        return houseAddress;
    }

    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }

    public PaymentBean getPayment() {
        return payment;
    }

    public void setPayment(PaymentBean payment) {
        this.payment = payment;
    }

    public int getPayType() {
        return payType;
    }

    public void setPayType(int payType) {
        this.payType = payType;
    }

    public List<GoodsBean> getProductList() {
        return productList;
    }

    public void setProductList(List<GoodsBean> productList) {
        this.productList = productList;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierShopID() {
        return supplierShopID;
    }

    public void setSupplierShopID(String supplierShopID) {
        this.supplierShopID = supplierShopID;
    }

    public String getSupplierShopName() {
        return supplierShopName;
    }

    public void setSupplierShopName(String supplierShopName) {
        this.supplierShopName = supplierShopName;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getWareHourseStatus() {
        return wareHourseStatus;
    }

    public void setWareHourseStatus(int wareHourseStatus) {
        this.wareHourseStatus = wareHourseStatus;
    }

    public static class PaymentBean implements Parcelable {
        public static final Creator<PaymentBean> CREATOR = new Creator<PaymentBean>() {
            @Override
            public PaymentBean createFromParcel(Parcel in) {
                return new PaymentBean(in);
            }

            @Override
            public PaymentBean[] newArray(int size) {
                return new PaymentBean[size];
            }
        };
        private int accountPayment;
        private int cashPayment;
        private int onlinePayment;

        protected PaymentBean(Parcel in) {
            accountPayment = in.readInt();
            cashPayment = in.readInt();
            onlinePayment = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(accountPayment);
            dest.writeInt(cashPayment);
            dest.writeInt(onlinePayment);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public int getAccountPayment() {
            return accountPayment;
        }

        public void setAccountPayment(int accountPayment) {
            this.accountPayment = accountPayment;
        }

        public int getCashPayment() {
            return cashPayment;
        }

        public void setCashPayment(int cashPayment) {
            this.cashPayment = cashPayment;
        }

        public int getOnlinePayment() {
            return onlinePayment;
        }

        public void setOnlinePayment(int onlinePayment) {
            this.onlinePayment = onlinePayment;
        }
    }
}
