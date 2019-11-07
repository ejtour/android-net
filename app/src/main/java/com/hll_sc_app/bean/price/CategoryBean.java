package com.hll_sc_app.bean.price;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/28
 */

public class CategoryBean {
    @SerializedName(value = "fatherName", alternate = "breedTypeName")
    private String fatherName;
    @SerializedName(value = "fatherCode", alternate = "breedTypeCode")
    private String fatherCode;

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getFatherCode() {
        return fatherCode;
    }

    public void setFatherCode(String fatherCode) {
        this.fatherCode = fatherCode;
    }
}
