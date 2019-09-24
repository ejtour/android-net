package com.hll_sc_app.bean.order.place;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/9/23
 */

public class ExecuteDateBean implements Parcelable {
    public static final Creator<ExecuteDateBean> CREATOR = new Creator<ExecuteDateBean>() {
        @Override
        public ExecuteDateBean createFromParcel(Parcel in) {
            return new ExecuteDateBean(in);
        }

        @Override
        public ExecuteDateBean[] newArray(int size) {
            return new ExecuteDateBean[size];
        }
    };
    private FirstDay firstDay;
    private List<TimeBean> timeList;
    private String times;

    protected ExecuteDateBean(Parcel in) {
        firstDay = in.readParcelable(FirstDay.class.getClassLoader());
        timeList = in.createTypedArrayList(TimeBean.CREATOR);
        times = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(firstDay, flags);
        dest.writeTypedList(timeList);
        dest.writeString(times);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public FirstDay getFirstDay() {
        return firstDay;
    }

    public void setFirstDay(FirstDay firstDay) {
        this.firstDay = firstDay;
    }

    public List<TimeBean> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<TimeBean> timeList) {
        this.timeList = timeList;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public static class FirstDay implements Parcelable {
        public static final Creator<FirstDay> CREATOR = new Creator<FirstDay>() {
            @Override
            public FirstDay createFromParcel(Parcel in) {
                return new FirstDay(in);
            }

            @Override
            public FirstDay[] newArray(int size) {
                return new FirstDay[size];
            }
        };
        private String date;
        private List<TimeBean> firstTimeList;

        protected FirstDay(Parcel in) {
            date = in.readString();
            firstTimeList = in.createTypedArrayList(TimeBean.CREATOR);
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(date);
            dest.writeTypedList(firstTimeList);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<TimeBean> getFirstTimeList() {
            return firstTimeList;
        }

        public void setFirstTimeList(List<TimeBean> firstTimeList) {
            this.firstTimeList = firstTimeList;
        }
    }

    public static class TimeBean implements Parcelable {
        public static final Creator<TimeBean> CREATOR = new Creator<TimeBean>() {
            @Override
            public TimeBean createFromParcel(Parcel in) {
                return new TimeBean(in);
            }

            @Override
            public TimeBean[] newArray(int size) {
                return new TimeBean[size];
            }
        };
        private String arrivalEndTime;
        private String arrivalStartTime;
        private String deliveryTimeID;

        protected TimeBean(Parcel in) {
            arrivalEndTime = in.readString();
            arrivalStartTime = in.readString();
            deliveryTimeID = in.readString();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(arrivalEndTime);
            dest.writeString(arrivalStartTime);
            dest.writeString(deliveryTimeID);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String getArrivalEndTime() {
            return arrivalEndTime;
        }

        public void setArrivalEndTime(String arrivalEndTime) {
            this.arrivalEndTime = arrivalEndTime;
        }

        public String getArrivalStartTime() {
            return arrivalStartTime;
        }

        public void setArrivalStartTime(String arrivalStartTime) {
            this.arrivalStartTime = arrivalStartTime;
        }

        public String getDeliveryTimeID() {
            return deliveryTimeID;
        }

        public void setDeliveryTimeID(String deliveryTimeID) {
            this.deliveryTimeID = deliveryTimeID;
        }
    }
}
