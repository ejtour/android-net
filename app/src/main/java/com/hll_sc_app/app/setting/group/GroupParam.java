package com.hll_sc_app.app.setting.group;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2020/3/12
 */

public enum GroupParam {
    P02(2, "自动接单模式", "*开启自动接单，所有订单将自动接受，无需手动接单。",
            "确认开启自动接单模式？", "开启后，新的订单无需手动接单",
            "确认关闭自动接单模式？", "关闭后，新的订单需要手动接单"),
    P03(3, "自动发货模式", "*开启自动发货，所有已接订单将自动变为已发货状态，无需手动发货。",
            "确认开启自动发货开关？", "开启后，新的已接订单无需手动发货",
            "确认关闭自动发货开关？", "关闭后，新的已接订单需要手动发货"),
    P04(4, "只允许合作采购商下单", "*开启该功能后，非合作采购商查找本店铺商品时，不能查看商品价格，同时也不能提交订单。",
            "只允许合作采购商下单？", "开启后，非合作采购商不能下单",
            "允许所有采购商下单？", "关闭后，非合作采购商可以下单"),
    P06(6, "添加我为合作供应商时需要验证", "*不开启需要验证时任意采购商都可以与您建立合作关系；反之则需要通过信息验证才能与您建立合作关系。",
            "添加合作供应商需要验证？",
            "添加合作供应商不需验证？"),
    P07(7, "仅接单", "*该功能仅为哗啦啦供应链用户发送采购订单使用，启用该功能后，订单内的商品为采购方增加，供应商无法做商品统计和库存管理！",
            "确认开启仅接单？",
            "确认关闭仅接单？"),
    P08(8, "由供应商填写验货数量", "*启用该功能后验货数量将由供应商(司机)填写，供应商填写后采购商根据该数量进行收货结算。当订单中包含已发货未验货的订单时，不可切换验货模式。",
            "开启由供应商验货？", "开启后，验货数量由供应商填写",
            "关闭由供应商验货？", "关闭后，验货数量由采购商填写"),
    P09(9, "只允许以协议价进行下单", "*启用该功能后，所有的采购商必须有协议报价时，才能查看商品价格和下单。当协议价到期后，如果没有新的协议价，将不能查看商品价格以及下单购买。",
            "开启只允许协议价下单？", "开启后，采购商必须由生效协议报价时，才能下单",
            "关闭只允许协议价下单？", "关闭后，采购商可在没有协议报价时下单"),
    P10(10, "发货信息修改商品价格", "*开启后，供应商发货时，可修改本订单该商品的发货单价或总价。"),
    P11(11, "非营业时间不接单", "*开启停止接单后当超出您的营业时间范围内时，采购商不可以向您下单。您可以在店铺信息中修改营业时间。",
            "开启非营业时间不接单？", "开启后，除营业时间外不接单",
            "关闭非营业时间不接单？", "关闭后，任何时间下单都可接单"),
    P12(12, "代仓订单自动接单模式", "*开启代仓订单自动接单，所有的代仓类型订单将自动接受，无需手动接单。",
            "开启代仓自动接单模式？", "开启后，代仓订单无需手动接单",
            "关闭代仓自动接单模式？", "关闭后，代仓订单需要手动接单"),
    P14(14, "退换货自动审核", "*开启自动审核后，退换货申请单自动变为同意状态，无需手动审核操作。",
            "开启退换货自动审核？", "开启后，新退货订单无需手动审核",
            "关闭退换货自动审核？", "关闭后，新退货订单需要手动审核"),
    P15(15, "验货模式", "*开启验货模式，所有订单在验货时都需进行二次确认。",
            "确认开启验货模式？",
            "确认关闭验货模式？"),
    P18(18, "新增合作门店时需要验证", "*不开启需要验证时任意采购商门店都可以与您建立合作关系；反之则需要通过信息验证才能与您建立合作关系。",
            "添加合作门店需要验证？",
            "添加合作门店不需要验证？"),
    P21(21, "销售端退货限制退货时效", "*启用该功能后退货不受时效限制，则销售CRM可申请退货不判断时效。",
            "确认开启么？", "开启后，销售CRM申请退货不判断退货时效",
            "确认关闭么？", "关闭后，销售CRM申请退货需要根据退货时效判断"),
    P22(25, "导出配送单带价格", "*若此开关关闭后，则配送单导出时，不导出与价格相关数据。",
            "确认开启么？", "开启后，导出配送单带价格",
            "确认关闭么？", "关闭后，导出配送单隐藏价格"),
    P26(26, "价格根据转换率变价设置", "*开启多规格商品，修改其中一个规格的单价，则其他规格可以根据转化率改变自动变更单价。"),
    P28(28, "仓库发货方式", "开启后可以按照客户所在地区与品类关系拆单。",
            "确认开启仓库发货设置开关？", "开启后可以按照客户所在地区与品类关系拆单",
            "确认关闭仓库发货设置开关？", "");
    private int type;
    private int value = 1;
    private String name;
    private String desc;
    private String onTitle;
    private String onDesc;
    private String offTitle;
    private String offDesc;
    private boolean needConfirm;

    public int getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public String getOnTitle() {
        return onTitle;
    }

    public String getOnDesc() {
        return onDesc;
    }

    public String getOffTitle() {
        return offTitle;
    }

    public String getOffDesc() {
        return offDesc;
    }

    public boolean isNeedConfirm() {
        return needConfirm;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    GroupParam(int type, String name, String desc, String onTitle, String onDesc, String offTitle, String offDesc) {
        this.type = type;
        this.name = name;
        this.desc = desc;
        this.onTitle = onTitle;
        this.onDesc = onDesc;
        this.offTitle = offTitle;
        this.offDesc = offDesc;
        needConfirm = true;
    }

    GroupParam(int type, String name, String desc) {
        this(type, name, desc, null, null, null, null);
        needConfirm = false;
    }

    GroupParam(int type, String name, String desc, String onTitle, String offTitle) {
        this(type, name, desc, onTitle, null, offTitle, null);
    }
}
