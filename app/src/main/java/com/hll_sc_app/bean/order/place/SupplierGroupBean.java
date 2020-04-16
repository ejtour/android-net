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
    private int wareHourseIsOpenPay;
    private int wareHourseStatus;
    private PreOrderDateBean preOrderDate;

    private String remark;
    private String startDate;
    private String endDate;
    private DiscountPlanBean.DiscountBean discountBean;
    private List<String> dayList;
    private Map<String, List<String>> map;
    private double subTotalAmount;

    /**
     * 当executeDateList不存在：认为供应商关闭了配送时间选择  显示 “按照供应商时间配送”。且不能选择时间，传后端的起始时间，结束时间的值均为0。
     * 当executeDateList存在，preOrderDate不存在：则认为是对该供应商的第一次下单，则显示“请选择要求到货日期”。
     * 当executeDateList存在，preOrderDate存在：
     * preOrderDate的时间段严格匹配firstDay中的firstTimeList的其中一个的时候，则日期选择firstDay中的date，否则日期选择times中第一个，
     * 时间则先判断preOrderDate中的时间是否在firstTimeList和timeList中，在其中一个即认为存在，如果在则使用，如果不在则使用timeList中的第一个时间段。
     */
    public void initExecuteDate() {
        dayList = new ArrayList<>();
        map = new LinkedHashMap<>();
        if (executeDateList != null) {
            String defaultDay = null; // 默认选中的天
            ExecuteDateBean.TimeBean defaultTime = null; // 默认选中的时间段
            ExecuteDateBean.FirstDay firstDayBean = executeDateList.getFirstDay();
            if (firstDayBean != null && !CommonUtils.isEmpty(firstDayBean.getFirstTimeList()) && !TextUtils.isEmpty(firstDayBean.getDate())) {
                List<String> listFirstTime = new ArrayList<>();
                dayList.add(firstDayBean.getDate());
                map.put(DateUtil.getReadableTime(firstDayBean.getDate(), LOCALE_DATE), listFirstTime);
                for (ExecuteDateBean.TimeBean timeBean : firstDayBean.getFirstTimeList()) {
                    listFirstTime.add(timeBean.getArrivalStartTime() + " - " + timeBean.getArrivalEndTime());
                    if (preOrderDate != null && TextUtils.equals(preOrderDate.getPreOrderArrivalStartTime(), timeBean.getArrivalStartTime())
                            && TextUtils.equals(preOrderDate.getPreOrderArrivalEndTime(), timeBean.getArrivalEndTime())) {
                        defaultDay = firstDayBean.getDate();
                        defaultTime = timeBean;
                    }
                }
            }
            if (!TextUtils.isEmpty(executeDateList.getTimes())) {
                String[] timeStr = executeDateList.getTimes().split(",");
                if (defaultDay == null && preOrderDate != null) {
                    defaultDay = timeStr[0];
                }
                List<String> listTime = new ArrayList<>();
                if (!CommonUtils.isEmpty(executeDateList.getTimeList())) {
                    for (ExecuteDateBean.TimeBean timeBean : executeDateList.getTimeList()) {
                        listTime.add(timeBean.getArrivalStartTime() + " - " + timeBean.getArrivalEndTime());
                        if (defaultTime == null && preOrderDate != null
                                && TextUtils.equals(preOrderDate.getPreOrderArrivalStartTime(), timeBean.getArrivalStartTime())
                                && TextUtils.equals(preOrderDate.getPreOrderArrivalEndTime(), timeBean.getArrivalEndTime())) {
                            defaultTime = timeBean;
                        }
                    }
                    if (defaultTime == null && preOrderDate != null) {
                        defaultTime = executeDateList.getTimeList().get(0);
                    }
                }
                if (CommonUtils.isEmpty(listTime)) return;
                for (String s : timeStr) {
                    if (!TextUtils.isEmpty(s)) {
                        dayList.add(s);
                        map.put(DateUtil.getReadableTime(s, LOCALE_DATE), listTime);
                    }
                }
            }
            if (defaultDay != null && defaultTime != null) {
                startDate = defaultDay + defaultTime.getArrivalStartTime().replace(":", "");
                endDate = defaultDay + defaultTime.getArrivalEndTime().replace(":", "");
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
        for (DiscountPlanBean.DiscountBean bean : discounts) {
            if (discountPlan.getUseDiscountType() == DiscountPlanBean.DISCOUNT_PRODUCT && bean.getRuleType() == -1
                    || discountPlan.getUseDiscountType() == DiscountPlanBean.DISCOUNT_NONE && bean.getRuleType() == 0
                    || discountPlan.getUseDiscountType() == DiscountPlanBean.DISCOUNT_ORDER && bean.getRuleType() > 0) {
                discountBean = bean;
                break;
            }
        }
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

    /**
     * @return 非代仓品或者开启代仓支付时，启用支付选择
     */
    public boolean enablePay() {
        return wareHourseStatus != 1 || wareHourseIsOpenPay == 1;
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
        wareHourseIsOpenPay = in.readInt();
        wareHourseStatus = in.readInt();
        preOrderDate = in.readParcelable(PreOrderDateBean.class.getClassLoader());
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
        dest.writeInt(wareHourseIsOpenPay);
        dest.writeInt(wareHourseStatus);
        dest.writeParcelable(preOrderDate, flags);
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
        if (discountBean != null && discountPlan != null) {
            if (discountBean.getRuleType() == 0) {
                discountPlan.setUseDiscountType(DiscountPlanBean.DISCOUNT_NONE);
            } else if (discountBean.getRuleType() == -1) {
                discountPlan.setUseDiscountType(DiscountPlanBean.DISCOUNT_PRODUCT);
            } else {
                discountPlan.setUseDiscountType(DiscountPlanBean.DISCOUNT_ORDER);
            }
        }
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

    public int getWareHourseIsOpenPay() {
        return wareHourseIsOpenPay;
    }

    public void setWareHourseIsOpenPay(int wareHourseIsOpenPay) {
        this.wareHourseIsOpenPay = wareHourseIsOpenPay;
    }

    public int getWareHourseStatus() {
        return wareHourseStatus;
    }

    public void setWareHourseStatus(int wareHourseStatus) {
        this.wareHourseStatus = wareHourseStatus;
    }

    public PreOrderDateBean getPreOrderDate() {
        return preOrderDate;
    }

    public void setPreOrderDate(PreOrderDateBean preOrderDate) {
        this.preOrderDate = preOrderDate;
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

    public static class PreOrderDateBean implements Parcelable {
        private String preOrderArrivalEndTime;
        private String preOrderArrivalStartTime;

        protected PreOrderDateBean(Parcel in) {
            preOrderArrivalEndTime = in.readString();
            preOrderArrivalStartTime = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(preOrderArrivalEndTime);
            dest.writeString(preOrderArrivalStartTime);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<PreOrderDateBean> CREATOR = new Creator<PreOrderDateBean>() {
            @Override
            public PreOrderDateBean createFromParcel(Parcel in) {
                return new PreOrderDateBean(in);
            }

            @Override
            public PreOrderDateBean[] newArray(int size) {
                return new PreOrderDateBean[size];
            }
        };

        public String getPreOrderArrivalEndTime() {
            return preOrderArrivalEndTime;
        }

        public void setPreOrderArrivalEndTime(String preOrderArrivalEndTime) {
            this.preOrderArrivalEndTime = preOrderArrivalEndTime;
        }

        public String getPreOrderArrivalStartTime() {
            return preOrderArrivalStartTime;
        }

        public void setPreOrderArrivalStartTime(String preOrderArrivalStartTime) {
            this.preOrderArrivalStartTime = preOrderArrivalStartTime;
        }
    }
}
