package com.hll_sc_app.bean.stockmanage.purchaserorder;

import android.text.TextUtils;

public class PurchaserOrderSearchBean {

    private String supplierID;
    private String supplierName;

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return TextUtils.equals(supplierID, ((PurchaserOrderSearchBean) obj).supplierID);
    }
}
