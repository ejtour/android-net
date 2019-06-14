package com.hll_sc_app.app.order.common;

import android.support.annotation.DrawableRes;

import com.hll_sc_app.R;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/6/11
 */

public enum OrderStatus {
    PENDING_TRANSFER(R.drawable.ic_filter_option, 0, "待转单", "您有一个待转换商城的订单，请及时处理"),
    PENDING_RECEIVE(R.drawable.ic_filter_option, 1, "待接单", "您有新的订单，请注意查收"),
    PENDING_DELIVER(R.drawable.ic_order_deliver, 2, "待发货", "请注意核对发货数量，及时发货"),
    DELIVERED(R.drawable.ic_order_transport, 3, "已发货", "您已发货，请等待采购商验收"),
    PENDING_SETTLE(R.drawable.ic_order_settle, 4, "待结算", "订单已发货，等待结算"),
    RECEIVED(R.drawable.ic_order_sign, 6, "已签收", "采购商已签收，订单完成"),
    CANCELED(R.drawable.ic_order_ban, 7, "已取消", "采购商取消") {
        @Override
        public String getDesc(int canceler, String actionBy, String cancelReason) {
            StringBuilder builder = new StringBuilder();
            if (canceler == 0) {
                builder.append("系统自动取消");
            } else {
                if (canceler == 1) {
                    builder.append("采购商");
                } else if (canceler == 2) {
                    builder.append("供应商");
                } else if (canceler == 3) {
                    builder.append("客服");
                }
                builder.append(actionBy.equals("admin") ? "管理员" : actionBy).append("取消：").append(cancelReason);
            }
            return builder.toString();
        }
    },
    REJECT(R.drawable.ic_order_ban, 8, "已拒收", "采购商已拒收，订单完成");
    private int icon;
    private String label;
    private String desc;
    private int status;

    public int getStatus() {
        return status;
    }

    public int getIcon() {
        return icon;
    }

    public String getLabel() {
        return label;
    }

    public String getDesc(int canceler, String actionBy, String cancelReason) {
        return desc;
    }

    OrderStatus(@DrawableRes int icon, int status, String label, String desc) {
        this.icon = icon;
        this.status = status;
        this.label = label;
        this.desc = desc;
    }
}