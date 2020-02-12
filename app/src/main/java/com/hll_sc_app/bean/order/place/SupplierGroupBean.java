package com.hll_sc_app.bean.order.place;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.hll_sc_app.citymall.util.CommonUtils;
import com.hll_sc_app.utils.DateUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/19
 */

public class SupplierGroupBean implements Parcelable {
    private static String LOCALE_DATE = "MM月dd日";
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
    private int deliverType;
    private double depositAmount;
    private DiscountPlanBean discountPlan;
    private ExecuteDateBean executeDateList;
    private String houseAddress;
    private PaymentBean payment;
    private int payType;
    private List<ProductBean> productList;
    private String supplierID;
    private String supplierShopID;
    private String supplierShopName;
    private double totalAmount;
    private int wareHourseStatus;

    private String remark;
    private String startDate;
    private String endDate;
    private DiscountPlanBean.DiscountBean discountBean;
    private List<String> dayList;
    private Map<String, List<String>> map;
    private double subTotalAmount;

    public void initExecuteDate() {
        dayList = new ArrayList<>();
        map = new LinkedHashMap<>();
        if (executeDateList != null) {
            ExecuteDateBean.FirstDay firstDayBean = executeDateList.getFirstDay();
            if (firstDayBean != null) {
                List<String> listFirstTime = new ArrayList<>();
                if (!CommonUtils.isEmpty(firstDayBean.getFirstTimeList())) {
                    for (ExecuteDateBean.TimeBean firstTimeListBean : firstDayBean.getFirstTimeList()) {
                        listFirstTime.add(firstTimeListBean.getArrivalStartTime() + " - " + firstTimeListBean.getArrivalEndTime());
                    }
                }
                if (!TextUtils.isEmpty(firstDayBean.getDate()) && !CommonUtils.isEmpty(listFirstTime)) {
                    dayList.add(firstDayBean.getDate());
                    map.put(DateUtil.getReadableTime(firstDayBean.getDate(), LOCALE_DATE), listFirstTime);
                }
            }

            List<String> listTime = new ArrayList<>();
            if (!CommonUtils.isEmpty(executeDateList.getTimeList())) {
                for (ExecuteDateBean.TimeBean timeListBean : executeDateList.getTimeList()) {
                    listTime.add(timeListBean.getArrivalStartTime() + " - " + timeListBean.getArrivalEndTime());
                }
            }
            if (CommonUtils.isEmpty(listTime)) return;
            if (!TextUtils.isEmpty(executeDateList.getTimes())) {
                String[] timeStr = executeDateList.getTimes().split(",");
                for (String s : timeStr) {
                    if (!TextUtils.isEmpty(s)) {
                        dayList.add(s);
                        map.put(DateUtil.getReadableTime(s, LOCALE_DATE), listTime);
                    }
                }
            }
            if (dayList.size() > 0) {
                String dayStr = dayList.get(0);
                ExecuteDateBean.TimeBean timeBean;
                if (firstDayBean != null && dayStr.equals(firstDayBean.getDate())) {
                    timeBean = firstDayBean.getFirstTimeList().get(0);
                } else {
                    timeBean = executeDateList.getTimeList().get(0);
                }
                startDate = dayStr + timeBean.getArrivalStartTime().replace(":", "");
                endDate = dayStr + timeBean.getArrivalEndTime().replace(":", "");
            }
        } else {
            startDate = "0";
            endDate = "0";
        }
    }

    public void initDiscount() {
        if (discountPlan == null) {
            setDiscountBean(null);
            return;
        }
        DiscountPlanBean.DiscountBean discountBean = null;
        List<DiscountPlanBean.DiscountBean> discounts = discountPlan.getShopDiscounts();
        if (discountPlan.getUseDiscountType() == DiscountPlanBean.DISCOUNT_PRODUCT) {
            for (DiscountPlanBean.DiscountBean bean : discounts) {
                if (bean.getRuleType() == -1) {
                    discountBean = bean;
                    break;
                }
            }
        } else if (discountPlan.getUseDiscountType() == DiscountPlanBean.DISCOUNT_NONE) {
            for (DiscountPlanBean.DiscountBean bean : discounts) {
                if (bean.getRuleType() == 0) {
                    discountBean = bean;
                }
            }
        } else discountBean = discounts.get(1);
        setDiscountBean(discountBean);
    }

    public void initPayType() {
        if (payType == 1 && payment.cashPayment != 1) {
            payType = 0;
        }
        if (payType == 2 && payment.accountPayment != 1) {
            payType = 0;
        }
        if (payType == 3 && payment.onlinePayment != 1) {
            payType = 0;
        }
    }

    protected SupplierGroupBean(Parcel in) {
        deliverType = in.readInt();
        depositAmount = in.readDouble();
        discountPlan = in.readParcelable(DiscountPlanBean.class.getClassLoader());
        executeDateList = in.readParcelable(ExecuteDateBean.class.getClassLoader());
        houseAddress = in.readString();
        payment = in.readParcelable(PaymentBean.class.getClassLoader());
        payType = in.readInt();
        productList = in.createTypedArrayList(ProductBean.CREATOR);
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
        dest.writeParcelable(executeDateList, flags);
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public DiscountPlanBean.DiscountBean getDiscountBean() {
        return discountBean;
    }

    public void setDiscountBean(DiscountPlanBean.DiscountBean discountBean) {
        if (discountBean == null) {
            subTotalAmount = totalAmount;
        } else {
            subTotalAmount = CommonUtils.subDouble(totalAmount, discountBean.getDiscountValue()).doubleValue();
        }
        this.discountBean = discountBean;
    }

    public List<String> getDayList() {
        return dayList;
    }

    public Map<String, List<String>> getMap() {
        return map;
    }

    public double getSubTotalAmount() {
        return subTotalAmount;
    }

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

    public ExecuteDateBean getExecuteDateList() {
        return executeDateList;
    }

    public void setExecuteDateList(ExecuteDateBean executeDateList) {
        this.executeDateList = executeDateList;
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

    public List<ProductBean> getProductList() {
        return productList;
    }

    public void setProductList(List<ProductBean> productList) {
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
