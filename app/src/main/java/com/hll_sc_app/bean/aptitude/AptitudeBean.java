package com.hll_sc_app.bean.aptitude;

import com.hll_sc_app.base.utils.UIUtils;

import java.util.Arrays;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public class AptitudeBean implements Cloneable {
    private String aptitudeName;
    private String aptitudeType;
    private String aptitudeUrl;
    private String endTime;

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException e) {
            return this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AptitudeBean that = (AptitudeBean) o;
        return UIUtils.equals(aptitudeName, that.aptitudeName) &&
                UIUtils.equals(aptitudeType, that.aptitudeType) &&
                UIUtils.equals(aptitudeUrl, that.aptitudeUrl) &&
                UIUtils.equals(endTime, that.endTime);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(new Object[]{aptitudeName, aptitudeType, aptitudeUrl, endTime});
    }

    public String getAptitudeName() {
        return aptitudeName;
    }

    public void setAptitudeName(String aptitudeName) {
        this.aptitudeName = aptitudeName;
    }

    public String getAptitudeType() {
        return aptitudeType;
    }

    public void setAptitudeType(String aptitudeType) {
        this.aptitudeType = aptitudeType;
    }

    public String getAptitudeUrl() {
        return aptitudeUrl;
    }

    public void setAptitudeUrl(String aptitudeUrl) {
        this.aptitudeUrl = aptitudeUrl;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
