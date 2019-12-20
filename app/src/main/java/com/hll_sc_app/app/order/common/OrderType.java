package com.hll_sc_app.app.order.common;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/6
 */

public enum OrderType {
    PENDING_TRANSFER("待转单", 0){
        @Override
        public String getButtonText() {
            return "商城下单";
        }
    },
    PENDING_RECEIVE("待接单", 1){
        @Override
        public String getButtonText() {
            return "立即接单";
        }
    },
    PENDING_DELIVER("待发货", 2){
        @Override
        public String getButtonText() {
            return "确认发货";
        }
    },
    DELIVERED("已发货", 3),
    PENDING_SETTLE("待结算", 4),
    RECEIVED("已签收", 6) {
        @Override
        public boolean contain(int billStatus) {
            return billStatus == getStatus() || billStatus == 8; // 8 为已拒收
        }
    },
    CANCELED("已取消", 7);
    private String label;
    private int status;

    OrderType(String label, int status) {
        this.label = label;
        this.status = status;
    }

    public String getLabel() {
        return label;
    }

    public static int getPosition(int status) {
        int position = 0;
        OrderType[] values = OrderType.values();
        for (int i = 0; i < values.length; i++) {
            if (values[i].contain(status)) {
                position = i;
                break;
            }
        }
        return position;
    }

    public String getButtonText(){
        return null;
    }

    public static String[] getTitles() {
        OrderType[] values = OrderType.values();
        String[] titles = new String[values.length];
        for (int i = 0; i < values.length; i++) {
            titles[i] = values[i].label;
        }
        return titles;
    }

    public int getStatus() {
        return status;
    }

    public boolean contain(int billStatus) {
        return status == billStatus;
    }
}
