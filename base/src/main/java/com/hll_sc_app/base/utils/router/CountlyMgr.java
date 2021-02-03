package com.hll_sc_app.base.utils.router;

import android.text.TextUtils;

import com.hll_sc_app.base.bean.PvBean;
import com.hll_sc_app.base.bean.UserBean;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.citymall.util.CommonUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ly.count.android.sdk.Countly;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/6/15
 */
public class CountlyMgr {
    private static final Map<String, String> VIEW_MAP = new HashMap<>();
    private static final Map<String, String> PV_MAP = new HashMap<>();

    static {
        // VIEW_MAP.put("", "首页");
        // VIEW_MAP.put("", "订单管理");
        // VIEW_MAP.put("", "商品管理");
        // VIEW_MAP.put("", "我的");
        VIEW_MAP.put(RouterConfig.PRICE_MANAGE, "售价设置");
        // VIEW_MAP.put("", "定向售卖");
        VIEW_MAP.put(RouterConfig.PRICE, "市场价格");
        VIEW_MAP.put(RouterConfig.COOPERATION_PURCHASER_LIST, "合作客户");
        VIEW_MAP.put(RouterConfig.COOPERATION_PURCHASER_ADD, "添加合作客户");
        // VIEW_MAP.put("". "报表中心");
        VIEW_MAP.put(RouterConfig.REPORT_PRODUCT_SALES_STATISTICS, "商品销量统计汇总");
        VIEW_MAP.put(RouterConfig.REPORT_DAILY_AGGREGATION, "日销售额汇总");
        VIEW_MAP.put(RouterConfig.REPORT_ORDER_GOODS, "客户订货统计");
        VIEW_MAP.put(RouterConfig.CUSTOMER_SALE_AGGREGATION, "客户销售汇总");
        VIEW_MAP.put(RouterConfig.REPORT_SALESMAN_SIGN, "业务员签约绩效");
        VIEW_MAP.put(RouterConfig.REPORT_SALESMAN_SALES, "业务员销售额绩效");
        VIEW_MAP.put(RouterConfig.REPORT_LACK_DIFF, "缺货差异汇总");
        VIEW_MAP.put(RouterConfig.REPORT_RECEIVE_DIFF, "收货差异汇总");
        VIEW_MAP.put(RouterConfig.REPORT_LACK_DETAILS, "缺货商品明细表");
        VIEW_MAP.put(RouterConfig.REPORT_RECEIVE_DIFF_DETAILS, "收货差异商品明细表");
        VIEW_MAP.put(RouterConfig.REPORT_CUSTOMER_LACK, "客户缺货统计表");
        VIEW_MAP.put(RouterConfig.REPORT_DELIVERY_TIME, "配送及时率统计");
        VIEW_MAP.put(RouterConfig.REPORT_WAIT_REFUND, "待退货统计表");
        VIEW_MAP.put(RouterConfig.REPORT_REFUND_STATISTIC, "退货统计表");
        VIEW_MAP.put(RouterConfig.REPORT_REFUND_CUSTOMER_PRODUCT, "退货客户与商品统计表");
        VIEW_MAP.put(RouterConfig.REFUND_REASON_STATICS, "退货原因统计");
        VIEW_MAP.put(RouterConfig.REPORT_PROFIT_CUSTOMER, "客户毛利统计表");
        VIEW_MAP.put(RouterConfig.REPORT_PROFIT_SHOP, "门店毛利统计表");
        VIEW_MAP.put(RouterConfig.REPORT_PROFIT_CATEGORY, "品类毛利统计表");
        VIEW_MAP.put(RouterConfig.REPORT_CREDIT, "客户应收账款");
        VIEW_MAP.put(RouterConfig.REPORT_CREDIT_DETAILS_CUSTOMER, "客户应收账款明细表");
        VIEW_MAP.put(RouterConfig.REPORT_CREDIT_DETAILS_DAILY, "日应收账款汇总表");
        VIEW_MAP.put(RouterConfig.REPORT_CUSTOMER_LOSS, "客户流失率统计");
        VIEW_MAP.put(RouterConfig.REPORT_SHOP_LOSS, "流失门店统计表");
        VIEW_MAP.put(RouterConfig.REPORT_PURCHASE_STATISTIC, "采购汇总统计");
        VIEW_MAP.put(RouterConfig.REPORT_PRODUCE_STATISTIC, "生产汇总统计");
        VIEW_MAP.put(RouterConfig.REPORT_WAREHOUSE_PRODUCT_DETAIL, "代仓商品缺货明细");
        VIEW_MAP.put(RouterConfig.REPORT_WAREHOUSE_DELIVERY, "代仓发货统计");
        VIEW_MAP.put(RouterConfig.REPORT_WAREHOUSE_FEE, "代仓服务费统计");
        VIEW_MAP.put(RouterConfig.REPORT_SALES_DAILY, "日报统计");
        VIEW_MAP.put(RouterConfig.REPORT_CUSTOMER_RECEIVE, "客户收货查询");
        // VIEW_MAP.put("", "二手市场");
        VIEW_MAP.put(RouterConfig.MINE_AGREEMENT_PRICE, "协议价管理");
        VIEW_MAP.put(RouterConfig.WALLET, "我的钱包");
        VIEW_MAP.put(RouterConfig.PAY_MANAGE, "支付设置");
        VIEW_MAP.put(RouterConfig.ORDER_STATISTIC, "下单客户统计");
        VIEW_MAP.put(RouterConfig.INFO_INVITE_CODE, "我的邀请码");
        VIEW_MAP.put(RouterConfig.WAREHOUSE_LIST, "代仓管理");
        VIEW_MAP.put(RouterConfig.AFTER_SALES_AUDIT, "退货审核");
        // VIEW_MAP.put("". "营销设置");
        // VIEW_MAP.put("". "商品促销");
        // VIEW_MAP.put("". "订单促销");
        VIEW_MAP.put(RouterConfig.ACTIVITY_MARKETING_COUPON_LIST, "优惠券");
        VIEW_MAP.put(RouterConfig.ACTIVITY_MARKETING_COUPON_ADD, "新增优惠券");
        VIEW_MAP.put(RouterConfig.REFUND_TIME, "退货时效管理");
        // VIEW_MAP.put("", "品项黑名单");
        // VIEW_MAP.put("". "配送管理");
        VIEW_MAP.put(RouterConfig.DELIVERY_RANGE, "配送范围管理");
        VIEW_MAP.put(RouterConfig.DELIVERY_MINIMUM, "起送金额");
        VIEW_MAP.put(RouterConfig.DELIVERY_AGEING_MANAGE, "配送时效管理");
        // VIEW_MAP.put("". "隔日配送商品管理");
        VIEW_MAP.put(RouterConfig.DELIVERY_TYPE_SET, "配送方式设置");
        VIEW_MAP.put(RouterConfig.SUPPLIER_SHOP, "店铺管理");
        VIEW_MAP.put(RouterConfig.STAFF_LIST, "员工管理");
        VIEW_MAP.put(RouterConfig.INVOICE_ENTRY, "发票管理");
        VIEW_MAP.put(RouterConfig.ACTIVITY_COMPLAIN_MANAGE_LIST, "投诉管理");
        VIEW_MAP.put(RouterConfig.GOODS_DEMAND, "新品反馈");
        VIEW_MAP.put(RouterConfig.GOODS_SPECIAL_DEMAND_ENTRY, "特殊商品需求");
        // VIEW_MAP.put("". "创建特殊商品需求");
        VIEW_MAP.put(RouterConfig.ACTIVITY_CARD_MANAGE_LIST, "卡管理");
        // VIEW_MAP.put("". "库存管理");
        VIEW_MAP.put(RouterConfig.ACTIVITY_DEPOT, "仓库管理");
        VIEW_MAP.put(RouterConfig.ACTIVITY_STOCK_QUERY_LIST, "库存查询");
        // VIEW_MAP.put("". "商品库存校验设置");
        VIEW_MAP.put(RouterConfig.ACTIVITY_STOCK_LOG_QUERY, "库存日志查询");
        VIEW_MAP.put(RouterConfig.ACTIVITY_STOCK_CUSTOMER_SEND, "客户发货仓库管理");
        VIEW_MAP.put(RouterConfig.STOCK_PURCHASER_ORDER, "采购单查询");
        VIEW_MAP.put(RouterConfig.PRIVATE_MALL, "私有商城");
        VIEW_MAP.put(RouterConfig.BILL_LIST, "对账单");
        VIEW_MAP.put(RouterConfig.INSPECTION_LIST, "查看验货单");
        VIEW_MAP.put(RouterConfig.PURCHASE_TEMPLATE, "客户采购模板");
        // VIEW_MAP.put("". "帮助中心");
        // VIEW_MAP.put("". "反馈投诉");
        VIEW_MAP.put(RouterConfig.ACTIVITY_FEED_BACK_LIST, "意见反馈");
        VIEW_MAP.put(RouterConfig.ACTIVITY_PLATFORM_COMPLAIN_LIST, "向平台投诉");
        // VIEW_MAP.put("". "设置");
        VIEW_MAP.put(RouterConfig.MESSAGE, "消息中心");
    }

    public static void inflatePvMap(List<PvBean> list) {
        if (!CommonUtils.isEmpty(list)) {
            PV_MAP.clear();
            for (PvBean bean : list) {
                PV_MAP.put(bean.getPv(), bean.getValue());
            }
        }
    }

    public static void recordView(String viewName) {
        recordView(viewName, false);
    }

    public static void recordView(String name, boolean isPath) {
        if (TextUtils.isEmpty(name)) return;
        if (isPath) {
            if (VIEW_MAP.containsKey(name)) {
                Countly.sharedInstance().views().recordView(VIEW_MAP.get(name));
            }
        } else {
            Countly.sharedInstance().views().recordView(name);
        }
    }

    public static void recordEvent(String pv) {
        if (TextUtils.isEmpty(pv)) return;
        String eventName;
        if (PV_MAP.containsKey(pv)) {
            eventName = String.format("%s:%s", pv, PV_MAP.get(pv));
        } else {
            eventName = pv;
        }
        Map<String, Object> segmentation = new HashMap<>();
        segmentation.put("platform", "android");
        UserBean user = GreenDaoUtils.getUser();
        if (user != null) {
            segmentation.put("groupName", user.getGroupName());
            segmentation.put("groupID", user.getGroupID());
            segmentation.put("userID", user.getEmployeeID());
        }
        Countly.sharedInstance().events().recordEvent(eventName, segmentation);
    }
}
