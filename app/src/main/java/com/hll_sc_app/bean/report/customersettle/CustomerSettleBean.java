package com.hll_sc_app.bean.report.customersettle;

import com.hll_sc_app.bean.window.NameValue;

import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/4/24
 */

public class CustomerSettleBean {
    private String label;
    private List<NameValue> list;

    public CustomerSettleBean(String label, List<NameValue> list) {
        this.label = label;
        this.list = list;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<NameValue> getList() {
        return list;
    }

    public void setList(List<NameValue> list) {
        this.list = list;
    }
}
