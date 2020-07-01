package com.hll_sc_app.bean.aptitude;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/28
 */

public class AptitudeTypeBean {
    private String aptitudeName;
    private String aptitudeType;
    private transient boolean enable = true;

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

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
