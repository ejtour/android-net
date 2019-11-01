package com.hll_sc_app.bean.user;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/10/31
 */

public class SpecialTaxSaveReq {
    private List<SpecialTaxSaveBean> addList;
    private List<SpecialTaxSaveBean> deleteList;
    private List<SpecialTaxSaveBean> updateList;

    public SpecialTaxSaveReq() {
        addList = new ArrayList<>();
        deleteList = new ArrayList<>();
        updateList = new ArrayList<>();
    }

    public List<SpecialTaxSaveBean> getAddList() {
        return addList;
    }

    public void setAddList(List<SpecialTaxSaveBean> addList) {
        this.addList = addList;
    }

    public List<SpecialTaxSaveBean> getDeleteList() {
        return deleteList;
    }

    public void setDeleteList(List<SpecialTaxSaveBean> deleteList) {
        this.deleteList = deleteList;
    }

    public List<SpecialTaxSaveBean> getUpdateList() {
        return updateList;
    }

    public void setUpdateList(List<SpecialTaxSaveBean> updateList) {
        this.updateList = updateList;
    }
}
