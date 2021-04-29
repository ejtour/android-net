package com.hll_sc_app.bean.print;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 2021/4/23
 */
public class PrintTemplateResp {
    @SerializedName("商城配送单")
    private List<PrintTemplateBean> list;

    public List<PrintTemplateBean> getList() {
        return list;
    }

    public void setList(List<PrintTemplateBean> list) {
        this.list = list;
    }
}
