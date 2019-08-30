package com.hll_sc_app.bean.export;

import com.google.gson.annotations.SerializedName;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/14
 */

public class ExportResp {
    @SerializedName(value = "Email", alternate = {"groupMail", "email"})
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
