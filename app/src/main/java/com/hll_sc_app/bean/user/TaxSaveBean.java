package com.hll_sc_app.bean.user;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/30
 */
public class TaxSaveBean {
    private String id;
    private String taxRate;

    public TaxSaveBean(String id, String taxRate) {
        this.id = id;
        this.taxRate = taxRate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate;
    }
}
