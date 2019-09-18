package com.hll_sc_app.bean.event;

import java.util.List;

public class StaffDepartListEvent {
    private String content;
    private List<String> departIds;

    public StaffDepartListEvent(List<String> departIds) {
        this.departIds = departIds;
    }

    public StaffDepartListEvent(String content) {
        this.content = content;
    }

    public List<String> getDepartIds() {
        return departIds;
    }

    public void setDepartIds(List<String> departIds) {
        this.departIds = departIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
