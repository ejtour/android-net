package com.hll_sc_app.bean.event;

import java.util.List;

public class StaffDepartListEvent {
    private List<String> departIds;
    public StaffDepartListEvent(List<String> departIds) {
        this.departIds = departIds;
    }

    public List<String> getDepartIds() {
        return departIds;
    }

    public void setDepartIds(List<String> departIds) {
        this.departIds = departIds;
    }
}
