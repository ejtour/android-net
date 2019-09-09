package com.hll_sc_app.bean.event;

public class StockManageEvent {
    public static final int TYPE_LOG = 1;
    public static final int TYPE_COSTOMER_SEND_STOCK = 2;
    private int type;
    private String content;

    public StockManageEvent(int type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
