package com.hll_sc_app.bean.export;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/14
 */

public class ExportResp {
    @SerializedName("Email")
    private String mockEmail;
    private String email;

    public String getEmail() {
        return TextUtils.isEmpty(mockEmail) ? email : mockEmail;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setMockEmail(String mockEmail) {
        this.mockEmail = mockEmail;
    }

    public String getMockEmail() {
        return mockEmail;
    }
}
