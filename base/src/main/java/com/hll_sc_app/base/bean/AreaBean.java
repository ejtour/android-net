package com.hll_sc_app.base.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.hll_sc_app.base.utils.UIUtils;

import java.util.List;

/**
 * 地区数据Bean
 *
 * @author zys
 * @date 2018/87
 */
public class AreaBean implements Parcelable {
    public static final Creator<AreaBean> CREATOR = new Creator<AreaBean>() {
        @Override
        public AreaBean createFromParcel(Parcel source) {
            return new AreaBean(source);
        }

        @Override
        public AreaBean[] newArray(int size) {
            return new AreaBean[size];
        }
    };
    private int areaNumQueryByProince;
    private int cityNumQueryByProince;
    private String code;
    private String name;
    private List<ChildBeanX> child;
    private boolean select;

    public AreaBean() {
    }

    protected AreaBean(Parcel in) {
        this.areaNumQueryByProince = in.readInt();
        this.cityNumQueryByProince = in.readInt();
        this.code = in.readString();
        this.name = in.readString();
        this.child = in.createTypedArrayList(ChildBeanX.CREATOR);
        this.select = in.readByte() != 0;
    }

    public boolean isSelect() {
        return select;
    }

    public void setSelect(boolean select) {
        this.select = select;
    }

    public int getAreaNumQueryByProince() {
        return areaNumQueryByProince;
    }

    public void setAreaNumQueryByProince(int areaNumQueryByProince) {
        this.areaNumQueryByProince = areaNumQueryByProince;
    }

    public int getCityNumQueryByProince() {
        return cityNumQueryByProince;
    }

    public void setCityNumQueryByProince(int cityNumQueryByProince) {
        this.cityNumQueryByProince = cityNumQueryByProince;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ChildBeanX> getChild() {
        return child;
    }

    public void setChild(List<ChildBeanX> child) {
        this.child = child;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.areaNumQueryByProince);
        dest.writeInt(this.cityNumQueryByProince);
        dest.writeString(this.code);
        dest.writeString(this.name);
        dest.writeTypedList(this.child);
        dest.writeByte(this.select ? (byte) 1 : (byte) 0);
    }

    public static class ChildBeanX implements Parcelable {
        private int areaNumQueryByCity;
        private String pName;
        private String pCode;
        private String code;
        private String name;
        private List<ChildBean> child;
        private String Initial;
        private boolean select;
        private String flag = "2";

        public ChildBeanX() {
        }

        public String getpName() {
            return pName;
        }

        public void setpName(String pName) {
            this.pName = pName;
        }

        public String getpCode() {
            return pCode;
        }

        public void setpCode(String pCode) {
            this.pCode = pCode;
        }

        public String getFlag() {

            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public boolean isSelect() {
            return select;
        }

        public void setSelect(boolean select) {
            this.select = select;
        }

        public String getInitial() {
            return Initial;
        }

        public void setInitial(String initial) {
            Initial = initial;
        }

        public int getAreaNumQueryByCity() {
            return areaNumQueryByCity;
        }

        public void setAreaNumQueryByCity(int areaNumQueryByCity) {
            this.areaNumQueryByCity = areaNumQueryByCity;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ChildBean> getChild() {
            return child;
        }

        public void setChild(List<ChildBean> child) {
            this.child = child;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            final ChildBeanX other = (ChildBeanX) obj;
            return UIUtils.equals(this.code, other.code);
        }

        public static class ChildBean implements Parcelable {
            public static final Creator<ChildBean> CREATOR = new Creator<ChildBean>() {
                @Override
                public ChildBean createFromParcel(Parcel source) {
                    return new ChildBean(source);
                }

                @Override
                public ChildBean[] newArray(int size) {
                    return new ChildBean[size];
                }
            };
            private String code;
            private String name;
            private String flag = "2";
            private String divideName;

            public ChildBean() {
            }

            protected ChildBean(Parcel in) {
                this.code = in.readString();
                this.name = in.readString();
                this.flag = in.readString();
                this.divideName = in.readString();
            }

            public String getDivideName() {
                return divideName;
            }

            public void setDivideName(String divideName) {
                this.divideName = divideName;
            }

            public String getFlag() {
                return flag;
            }

            public void setFlag(String flag) {
                this.flag = flag;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeString(this.code);
                dest.writeString(this.name);
                dest.writeString(this.flag);
                dest.writeString(this.divideName);
            }
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.areaNumQueryByCity);
            dest.writeString(this.pName);
            dest.writeString(this.pCode);
            dest.writeString(this.code);
            dest.writeString(this.name);
            dest.writeTypedList(this.child);
            dest.writeString(this.Initial);
            dest.writeByte(this.select ? (byte) 1 : (byte) 0);
            dest.writeString(this.flag);
        }

        protected ChildBeanX(Parcel in) {
            this.areaNumQueryByCity = in.readInt();
            this.pName = in.readString();
            this.pCode = in.readString();
            this.code = in.readString();
            this.name = in.readString();
            this.child = in.createTypedArrayList(ChildBean.CREATOR);
            this.Initial = in.readString();
            this.select = in.readByte() != 0;
            this.flag = in.readString();
        }

        public static final Creator<ChildBeanX> CREATOR = new Creator<ChildBeanX>() {
            @Override
            public ChildBeanX createFromParcel(Parcel source) {
                return new ChildBeanX(source);
            }

            @Override
            public ChildBeanX[] newArray(int size) {
                return new ChildBeanX[size];
            }
        };
    }
}
