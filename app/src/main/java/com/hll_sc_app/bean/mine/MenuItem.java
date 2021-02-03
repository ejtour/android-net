package com.hll_sc_app.bean.mine;

import com.hll_sc_app.R;
import com.hll_sc_app.base.utils.UserConfig;

import java.util.Arrays;
import java.util.List;

/**
 * Created by <a href="mailto:xuezhixin@hualala.com">Vixb</a> on 1/27/21.
 */
public enum MenuItem {
    RETURN_AUDIT(R.drawable.ic_mine_return_audit, "退货审核") {
        @Override
        public String getLabel() {
            return UserConfig.crm() ? "退货退款" : super.getLabel();
        }
    },
    AGREEMENT_PRICE(R.drawable.ic_mine_agreement_price, "协议价管理"),
    WAREHOUSE(R.drawable.ic_mine_warehouse_manage, "代仓管理"),
    CO_PURCHASER(R.drawable.ic_mine_cooperation_purchaser, "合作客户"),

    SHOP(R.drawable.ic_mine_store_manage, "店铺管理"),
    STAFF(R.drawable.ic_mine_staff_manage, "员工管理"),
    SELL_PRICE(R.drawable.ic_mine_price_setting, "售价设置"),
    DELIVERY(R.drawable.ic_mine_distribution_manage, "配送管理"),
    PAYMENT(R.drawable.ic_mine_payment_settings, "支付设置"),
    RETURN_AGING(R.drawable.ic_mine_return_time, "退货时效"),
    INVENTORY(R.drawable.ic_mine_inventory_manage, "库存管理"),
    PRIVATE_MALL(R.drawable.ic_mine_wechat_mall, "私有商城"),

    WALLET(R.drawable.ic_mine_wallet, "我的钱包"),
    BILL_LIST(R.drawable.ic_mine_account_statement, "对账单"),
    CARD(R.drawable.ic_mine_card_manage, "卡管理"),

    INVOICE(R.drawable.ic_mine_invoice_manage, "发票管理") {
        @Override
        public String getLabel() {
            return UserConfig.crm() ? "发票中心" : super.getLabel();
        }
    },
    COMPLIANT(R.drawable.ic_mine_complaint_manage, "投诉管理"),
    TARGET_SALE(R.drawable.ic_mine_directional_selling, "定向售卖"),
    ITEM_BLOCK_LIST(R.drawable.ic_mine_black_list, "品项黑名单"),
    GOODS_DEMAND(R.drawable.ic_mine_new_product_demand, "新品需求"),
    GOODS_SPECIAL_DEMAND(R.drawable.ic_mine_product_special_demand, "商品特殊需求"),
    INSPECTION(R.drawable.ic_mine_check_inspection, "查看验货单"),
    PURCHASE_TEMPLATE(R.drawable.ic_mine_customer_purchase_template, "客户采购模板"),
    INQUIRY(R.drawable.ic_mine_inquiry_manage, "询价管理"),
    APTITUDE(R.drawable.ic_mine_aptitude_manage, "资质管理"),

    MARKETING(R.drawable.ic_mine_marketing_settings, "营销设置"),
    REPORT_CENTER(R.drawable.ic_mine_report_center, "报表中心"),
    MARKET_PRICE(R.drawable.ic_mine_market_price, "市场价格"),

    GOODS_REPO(R.drawable.ic_mine_product_lib, "商品库"),
    GOODS_FEEDBACK(R.drawable.ic_mine_new_product_feedback, "新品反馈"),
    DELIVERY_ROUTE(R.drawable.ic_mine_distribution_manage, "配送路线"),
    SALESMAN_RANK(R.drawable.ic_mine_directional_selling, "销售排名");

    private final int icon;
    private final String label;

    MenuItem(int icon, String label) {
        this.icon = icon;
        this.label = label;
    }

    public int getIcon() {
        return icon;
    }

    public String getLabel() {
        return label;
    }

    public static final List<MenuItem> ONLY_RECEIVE_BAN_LIST = Arrays.asList(
            WAREHOUSE, SELL_PRICE, MARKETING, TARGET_SALE, RETURN_AGING,
            ITEM_BLOCK_LIST, DELIVERY, GOODS_DEMAND, GOODS_SPECIAL_DEMAND,
            CARD, INVENTORY, BILL_LIST, PURCHASE_TEMPLATE);
}
