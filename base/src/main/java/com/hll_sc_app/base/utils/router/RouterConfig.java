package com.hll_sc_app.base.utils.router;

/**
 * 路由规则配置
 *
 * @author zhuyingsong
 * @date 2018/12/12
 */
public class RouterConfig {

    public static final String WEB = "/activity/web";

    /**
     * 设置界面
     */
    public static final String SETTING = "/activity/setting";

    /**
     * 通用简单搜索页
     */
    public static final String SEARCH = "/activity/search";
    /**
     * 设置界面-价格比例设置
     */
    public static final String SETTING_PRICE_RATIO = "/activity/setting/priceRatio";
    /**
     * 配送管理
     */
    public static final String DELIVERY_MANAGE = "/activity/deliveryManage";
    /**
     * 代仓公司列表
     */
    public static final String WAREHOUSE_LIST = "/activity/warehouse/list";
    /**
     * 代仓公司-我发出的申请
     */
    public static final String WAREHOUSE_INVITE = "/activity/warehouse/invite";
    /**
     * 代仓公司-我收到的申请
     */
    public static final String WAREHOUSE_APPLICATION = "/activity/warehouse/application";
    /**
     * 代仓公司-我收到的申请-门店
     */
    public static final String WAREHOUSE_APPLICATION_SHOP = "/activity/warehouse/application/shops";
    /**
     * 代仓公司-搜索添加
     */
    public static final String WAREHOUSE_ADD = "/activity/warehouse/add";
    /**
     * 代仓公司-集团详细资料
     */
    public static final String WAREHOUSE_DETAILS = "/activity/warehouse/details";
    /**
     * 代仓公司-详细资料
     */
    public static final String WAREHOUSE_DETAIL = "/activity/warehouse/detail";
    /**
     * 代仓公司-门店详细资料
     */
    public static final String WAREHOUSE_SHOP_DETAIL = "/activity/warehouse/shop/detail";
    /**
     * 代仓管理
     */
    public static final String WAREHOUSE_START = "/activity/warehouse/start";
    /**
     * 支付管理
     */
    public static final String PAY_MANAGE = "/activity/payManage";
    /**
     * 支付管理-账期
     */
    public static final String PAY_MANAGE_ACCOUNT = "/activity/payManage/account";
    /**
     * 支付管理-支付方式选择
     */
    public static final String PAY_MANAGE_METHOD = "/activity/payManage/method";
    /**
     * 代仓管理-我是货主默认介绍页面
     */
    public static final String WAREHOUSE_INTRODUCE = "/activity/warehouse/introduce";
    /**
     * 代仓管理-我是货主
     */
    public static final String WAREHOUSE_SHIPPER = "/activity/warehouse/shipper";
    /**
     * 代仓管理-我是货主-商品管理
     */
    public static final String WAREHOUSE_SHIPPER_GOODS = "/activity/warehouse/shipper/goods";
    /**
     * 代仓管理-我是货主-门店管理
     */
    public static final String WAREHOUSE_SHIPPER_SHOP = "/activity/warehouse/shipper/shop";
    /**
     * 代仓管理-我是货主-门店管理-详情
     */
    public static final String WAREHOUSE_SHIPPER_SHOP_DETAIL = "/activity/warehouse/shipper/shop/detail";
    /**
     * 代仓管理-我是货主-门店管理-详情-选择合作采购商
     */
    public static final String WAREHOUSE_SHIPPER_SHOP_DETAIL_PURCHASER = "/activity/warehouse/shipper/shop/detail" +
            "/purchaser";
    /**
     * 代仓管理-我是货主-门店管理-详情-选择合作采购商-选择门店
     */
    public static final String WAREHOUSE_SHIPPER_SHOP_DETAIL_PURCHASER_SHOP = "/activity/warehouse/shipper/shop" +
            "/detail/purchaser/shop";
    /**
     * 代仓管理-我是货主默认介绍页面-查看推荐
     */
    public static final String WAREHOUSE_RECOMMEND = "/activity/warehouse/recommend";
    /**
     * 配送管理-配送时效管理
     */
    public static final String DELIVERY_AGEING_MANAGE = "/activity/deliveryManage/ageing";
    /**
     * 配送管理-配送时效管理-新增、编辑界面
     */
    public static final String DELIVERY_AGEING_DETAIL = "/activity/deliveryManage/ageing/detail";
    /**
     * 配送管理-配送时效管理-新增、编辑界面-配送时段
     */
    public static final String DELIVERY_AGEING_DETAIL_PERIOD = "/activity/deliveryManage/ageing/detail/period";
    /**
     * 配送管理-配送方式设置
     */
    public static final String DELIVERY_TYPE_SET = "/activity/delivery/type";
    /**
     * 配送管理-起送金额设置
     */
    public static final String DELIVERY_MINIMUM = "/activity/delivery/minimum";
    /**
     * 配送管理-起送金额设置-选择采购商
     */
    public static final String DELIVERY_MINIMUM_PURCHASER = "/activity/delivery/minimum/purchaser";
    /**
     * 配送管理-起送金额设置-选择采购商门店
     */
    public static final String DELIVERY_MINIMUM_PURCHASER_SHOP = "/activity/delivery/minimum/purchaserShop";
    /**
     * 配送管理-起送金额设置-详情
     */
    public static final String DELIVERY_MINIMUM_DETAIL = "/activity/delivery/minimum/detail";
    /**
     * 配送管理-配送范围
     */
    public static final String DELIVERY_RANGE = "/activity/delivery/range";
    /**
     * 配送管理-地区选择
     */
    public static final String DELIVERY_AREA = "/activity/delivery/area";
    /**
     * 配送管理-配送方式设置-选择第三方物流公司
     */
    public static final String DELIVERY_TYPE_COMPANY = "/activity/delivery/company";
    /**
     * 配送管理-配送方式设置-新增第三方物流公司
     */
    public static final String DELIVERY_TYPE_COMPANY_ADD = "/activity/delivery/company/add";
    /**
     * 设置界面-价格比例设置-列表-新增界面
     */
    public static final String SETTING_PRICE_RATIO_ADD = "/activity/setting/priceRatio/add";
    /**
     * 设置界面-价格比例设置-列表
     */
    public static final String SETTING_PRICE_RATIO_LIST = "/activity/setting/priceRatio/list";
    /**
     * 订单设置界面
     */
    public static final String BILL_SETTING = "/activity/setting/bill";
    /**
     * 合作关系设置界面
     */
    public static final String COOPERATION_SETTING = "/activity/setting/cooperation";
    /**
     * 设置界面-帐号管理
     */
    public static final String SETTING_ACCOUNT = "/activity/setting/account";
    /**
     * 设置界面-修改集团手机号
     */
    public static final String ACTIVITY_CHANGE_GROUP_PHONE = "/activity/setting/changeGroupPhone";
    /**
     * 设置界面-子账号解绑
     */
    public static final String ACTIVITY_UNBIND_GROUP = "/activity/setting/unbindGroup";
    /**
     * 售价设置
     */
    public static final String PRICE_MANAGE = "/activity/priceManage";
    /**
     * 售价设置-变更日志
     */
    public static final String PRICE_MANAGE_LOG = "/activity/priceManage/log";
    /**
     * 登录
     */
    public static final String USER_LOGIN = "/activity/user/login";
    /**
     * 找回密码
     */
    public static final String USER_FIND = "/activity/user/findPassword";
    /**
     * 修改密码
     */
    public static final String USER_CHANGE = "/activity/user/changePassword";
    /**
     * 注册
     */
    public static final String USER_REGISTER = "/activity/user/register";
    /**
     * 注册-完善资料
     */
    public static final String USER_REGISTER_COMPLEMENT = "/activity/user/register/complement";
    /**
     * 退货时效
     */
    public static final String REFUND_TIME = "/activity/refundTime";
    /**
     * 供应商店铺
     */
    public static final String SUPPLIER_SHOP = "/activity/supplierShop";
    /**
     * 定向售卖列表
     */
    public static final String ORIENTATION_LIST = "/activity/orientation/list";
    /**
     * 定向售卖详情
     */
    public static final String ORIENTATION_DETAIL = "/activity/orientation/detail";
    /**
     * 定向售卖合作采购商
     */
    public static final String ORIENTATION_COOPERATION_PURCHASER = "/activity/orientation/cooperation/purchaser";
    /**
     * 定向售卖合作门店
     */
    public static final String ORIENTATION_COOPERATION_SHOP = "/activity/orientation/cooperation/shop";
    /**
     * 定向售卖商品
     */
    public static final String ORIENTATION_PRODUCT = "/activity/orientation/product";
    /**
     * 首页
     */
    public static final String ROOT_HOME = "/activity/home/main";
    /**
     * 首页
     */
    public static final String ROOT_HOME_MAIN = "/fragment/home/main";
    /**
     * 订单管理
     */
    public static final String ROOT_HOME_ORDER = "/fragment/home/order";
    /**
     * 商品管理
     */
    public static final String ROOT_HOME_GOODS = "/fragment/home/goods";
    /**
     * 商品管理列表
     */
    public static final String ROOT_HOME_GOODS_LIST = "/fragment/home/goods/list";
    /**
     * 从商品库导入
     */
    public static final String GOODS_TEMPLATE_LIST = "/activity/goods/template/list";
    /**
     * 合作采购商列表
     */
    public static final String COOPERATION_PURCHASER_LIST = "/activity/cooperationPurchaser/list";
    /**
     * 员工列表
     */
    public static final String STAFF_LIST = "/activity/staff/list";
    /**
     * 员工详情
     */
    public static final String STAFF_EDIT = "/activity/staff/detail";
    /**
     * 员工详情-岗位权限简介
     */
    public static final String STAFF_PERMISSION = "/activity/staff/permission";
    /**
     * 员工管理-选择员工岗位
     */
    public static final String STAFF_ROLE_SELECT = "/activity/staff/roleSelect";
    /**
     * 合作采购商-我发出的申请
     */
    public static final String COOPERATION_PURCHASER_MY_APPLICATION = "/activity/cooperationPurchaser/myApplication";
    /**
     * 合作采购商新增
     */
    public static final String COOPERATION_PURCHASER_ADD = "/activity/cooperationPurchaser/add";
    /**
     * 合作采购商详情
     */
    public static final String COOPERATION_PURCHASER_DETAIL = "/activity/cooperationPurchaser/detail";
    /**
     * 门头照
     */
    public static final String STORE_FRONT_IMAGE = "/activity/store/frontImage";
    /**
     * 其他证照
     */
    public static final String STORE_OTHER_LICENSE_IMAGE = "/activity/store/otherLicense";
    /**
     * 营业证照
     */
    public static final String STORE_BUSINESS_LICENSE_IMAGE = "/activity/store/ businessLicense";
    /**
     * 合作采购商-我收到的申请
     */
    public static final String COOPERATION_PURCHASER_APPLICATION = "/activity/cooperationPurchaser" +
            "/cooperationApplication";
    /**
     * 合作采购商-我收到的申请-详情
     */
    public static final String COOPERATION_PURCHASER_APPLICATION_THIRD_PART_DETAIL = "/activity/cooperationPurchaser" +
            "/cooperationApplication/thirdPart/Detail";
    /**
     * 合作采购商详情-详细资料
     */
    public static final String COOPERATION_PURCHASER_DETAIL_DETAILS = "/activity/cooperationPurchaser/detail/details";
    /**
     * 合作采购商详情-新增门店
     */
    public static final String COOPERATION_PURCHASER_DETAIL_ADD_SHOP = "/activity/cooperationPurchaser/detail/addShop";
    /**
     * 合作采购商详情-选择结算方式
     */
    public static final String COOPERATION_PURCHASER_DETAIL_SHOP_SETTLEMENT = "/activity/cooperationPurchaser/detail" +
            "/shop/settlement";
    /**
     * 合作采购商详情-选择配送方式
     */
    public static final String COOPERATION_PURCHASER_DETAIL_SHOP_DELIVERY = "/activity/cooperationPurchaser/detail" +
            "/shop/delivery";
    /**
     * 合作采购商详情-门店详情
     */
    public static final String COOPERATION_PURCHASER_DETAIL_SHOP_DETAIL = "/activity/cooperationPurchaser/detail" +
            "/shopDetail";
    /**
     * 合作采购商详情-门店详情-合作方式
     */
    public static final String COOPERATION_PURCHASER_DETAIL_SHOP_DETAIL_SOURCE = "/activity/cooperationPurchaser" +
            "/detail" +
            "/shopDetail/source";
    /**
     * 合作采购商详情- 批量指派销售
     */
    public static final String COOPERATION_PURCHASER_DETAIL_SHOP_SALES = "/activity/cooperationPurchaser/detail" +
            "/shop/sales";
    /**
     * 合作采购商详情-选择门店
     */
    public static final String COOPERATION_PURCHASER_DETAIL_SELECT_SHOP = "/activity/cooperationPurchaser/detail" +
            "/selectShop";
    /**
     * 合作采购商详情-已选择的门店
     */
    public static final String COOPERATION_PURCHASER_DETAIL_SHOPS = "/activity/cooperationPurchaser/detail" +
            "/Shops";
    /**
     * 商品置顶管理
     */
    public static final String GOODS_STICK_MANAGE = "/activity/goods/stickManage";
    /**
     * 从商品库导入-商品编辑
     */
    public static final String GOODS_TEMPLATE_EDIT = "/activity/goods/template/edit";
    /**
     * 代仓商品库存预警
     */
    public static final String GOODS_INVENTORY_WARNING = "/activity/goods/inventoryWarning";
    /**
     * 第三方商品关联-采购商列表
     */
    public static final String GOODS_RELEVANCE_PURCHASER_LIST = "/activity/goods/relevance/purchaserList";
    /**
     * 第三方商品关联-采购商-关联、未关联商品列表
     */
    public static final String GOODS_RELEVANCE_LIST = "/activity/goods/relevance/goodsList";
    /**
     * 第三方商品关联-采购商-关联、未关联商品列表-选择关联商品
     */
    public static final String GOODS_RELEVANCE_LIST_SELECT = "/activity/goods/relevance/goodsList/select";
    /**
     * 商品详情
     */
    public static final String ROOT_HOME_GOODS_DETAIL = "/activity/home/goods/detail";
    /**
     * 新增商品
     */
    public static final String ROOT_HOME_GOODS_ADD = "/activity/home/goods/add";
    /**
     * 新增商品规格
     */
    public static final String ROOT_HOME_GOODS_SPECS = "/activity/home/goods/specs";
    /**
     * 新增商品规格-选择售卖单位
     */
    public static final String ROOT_HOME_GOODS_SPECS_SALE_UNIT_NAME = "/activity/home/goods/specs/saleUnitName";
    /**
     * 新增商品规格-选择押金商品
     */
    public static final String ROOT_HOME_GOODS_SPECS_DEPOSIT_PRODUCT = "/activity/home/goods/specs/depositProduct";
    /**
     * 新增商品-选择商品属性
     */
    public static final String ROOT_HOME_GOODS_PRODUCT_ATTR = "/activity/home/goods/productAttr";
    /**
     * /**
     * 新增商品-选择商品属性-商品品牌
     */
    public static final String ROOT_HOME_GOODS_PRODUCT_ATTR_BRAND = "/activity/home/goods/productAttr/brand";
    /**
     * /**
     * 新增商品-选择商品属性-商品品牌-新增
     */
    public static final String ROOT_HOME_GOODS_PRODUCT_ATTR_BRAND_ADD = "/activity/home/goods/productAttr/brand/add";
    /**
     * router
     * 新增商品-自定义分类
     */
    public static final String ROOT_HOME_GOODS_CUSTOM_CATEGORY = "/activity/home/goods/customCategory";
    /**
     * 我的
     */
    public static final String ROOT_HOME_MINE = "/fragment/home/mine";

    /**
     * 订单管理-待发货商品总量
     */
    public static final String ORDER_DELIVER = "/activity/order/deliver";

    /**
     * 订单详情
     */
    public static final String ORDER_DETAIL = "/activity/order/detail";

    /**
     * 订单转单详情
     */
    public static final String ORDER_TRANSFER_DETAIL = "/activity/order/transfer/detail";

    /**
     * 订单详情-修改发货信息
     */
    public static final String ORDER_MODIFY_DELIVER = "/activity/order/modify/deliver";

    /**
     * 订单拒收
     */
    public static final String ORDER_REJECT = "/activity/order/reject";

    /**
     * 订单验货
     */
    public static final String ORDER_INSPECTION = "/activity/order/inspection";

    /**
     * 订单结算
     */
    public static final String ORDER_SETTLEMENT = "/activity/order/settlement";

    /**
     * 订单库存检查
     */
    public static final String ORDER_INVENTORY_CHECK = "/activity/order/inventory/check";

    /**
     * 提交成功
     */
    public static final String SUBMIT_SUCCESS = "/activity/submit/success";

    /**
     * 协议价管理
     */
    public static final String MINE_AGREEMENT_PRICE = "/activity/mine/agreementPrice";

    /**
     * 协议价管理搜素
     */
    public static final String MINE_AGREEMENT_PRICE_SEARCH = "/activity/mine/agreementPrice/search";

    /**
     * 协议价管理-报价单详情
     */
    public static final String MINE_AGREEMENT_PRICE_DETAIL = "/activity/mine/agreementPrice/quotation/detail";

    /**
     * 协议价管理-报价单
     */
    public static final String MINE_AGREEMENT_PRICE_QUOTATION = "/fragment/mine/agreementPrice/quotation";

    /**
     * 协议价管理-商品
     */
    public static final String MINE_AGREEMENT_PRICE_GOODS = "/fragment/mine/agreementPrice/goods";

    /**
     * 协议价管理-商品详情
     */
    public static final String MINE_AGREEMENT_PRICE_GOODS_DETAIL = "/activity/mine/agreementPrice/goodsDetail";

    /**
     * 协议价管理-添加报价单
     */
    public static final String MINE_AGREEMENT_PRICE_QUOTATION_ADD = "/activity/mine/agreementPrice/quotation/add";

    /**
     * 协议价管理-添加报价单-选择报价对象
     */
    public static final String MINE_AGREEMENT_PRICE_QUOTATION_ADD_PURCHASER = "/activity/mine/agreementPrice" +
            "/quotation/add/purchaser";

    /**
     * 协议价管理-添加报价单-选择报价对象-选择报价门店
     */
    public static final String MINE_AGREEMENT_PRICE_QUOTATION_ADD_PURCHASER_SHOP = "/activity/mine/agreementPrice" +
            "/quotation/add/purchaserShop";

    /**
     * 协议价管理-添加报价单-选择比例模板
     */
    public static final String MINE_AGREEMENT_PRICE_QUOTATION_ADD_RATIO = "/activity/mine/agreementPrice" +
            "/quotation/add/ratio";

    /**
     * 协议价管理-添加报价单-新增商品
     */
    public static final String MINE_AGREEMENT_PRICE_QUOTATION_ADD_GOODS = "/activity/mine/agreementPrice" +
            "/quotation/add/goods";

    /**
     * 我的-退货审核
     */
    public static final String AFTER_SALES_AUDIT = "/activity/afterSales/audit";

    /**
     * 我的-退货审核-售后详情
     */
    public static final String AFTER_SALES_DETAIL = "/activity/afterSales/detail";

    /**
     * 售后详情-协商历史
     */
    public static final String AFTER_SALES_NEGOTIATION_HISTORY = "/activity/afterSales/negotiationHistory";

    /**
     * 售后审核-仓库/司机收货
     */
    public static final String AFTER_SALES_GOODS_OPERATION = "/activity/afterSales/goodsOperation";

    /**
     * 报表中心
     */
    public static final String REPORT_ENTRY = "/activity/report/entry";

    /**
     * 商品销量统计汇总
     */
    public static final String REPORT_PRODUCT_SALES_STATISTICS = "/activity/report/salesStatistics";

    /**
     * 客户订货明细汇总
     */
    public static final String REPORT_ORDER_GOODS = "/activity/report/orderGoods";

    /**
     * 客户订货明细汇总-门店详情
     */
    public static final String REPORT_ORDER_GOODS_DETAIL = "/activity/report/orderGoods/detail";

    /**
     * 日销售汇总
     */
    public static final String REPORT_DAILY_AGGREGATION = "/activity/daily/aggregation";

    /**
     * 客户销售汇总
     */
    public static final String CUSTOMER_SALE_AGGREGATION = "/activity/customer/aggregation";

    /**
     * 客户销售汇总明细
     */
    public static final String CUSTOMER_SALE_DETAILS = "/activity/customer/sale/detail";

    /**
     * 客户销售汇总门店明细
     */
    public static final String CUSTOMER_SALE_SHOP_DETAILS = "/activity/customer/sale/shop/detail";

    /**
     * 退货原因统计
     */
    public static final String REFUND_REASON_STATICS = "/activity/report/refundreason";

    /**
     * 客户销售汇总搜索
     */
    public static final String CUSTOMER_SALE_SEARCH = "/activity/customer/sale/search";

    /**
     * 我的钱包
     */
    public static final String WALLET = "/activity/wallet";

    /**
     * 我的钱包-明细
     */
    public static final String WALLET_DETAILS_LIST = "/activity/wallet/details/list";

    /**
     * 我的钱包-明细展示
     */
    public static final String WALLET_DETAILS_SHOW = "/activity/wallet/details/show";

    /**
     * 我的钱包-开通账户
     */
    public static final String WALLET_ACCOUNT_CREATE = "/activity/wallet/account/create";

    /**
     * 我的钱包-状态-未开通账户
     */
    public static final String WALLET_STATUS_NONE = "/fragment/wallet/status/none";

    /**
     * 我的钱包-状态-正常开通账户
     */
    public static final String WALLET_STATUS_NORMAL = "/fragment/wallet/status/normal";

    /**
     * 我的钱包-状态-账户核验状态
     */
    public static final String WALLET_STATUS_VERIFY = "/fragment/wallet/status/verify";

    /**
     * 我的钱包-企业认证
     */
    public static final String WALLET_ACCOUNT_AUTH = "/activity/wallet/account/auth";

    /**
     * 我的钱包-我的账号
     */
    public static final String WALLET_ACCOUNT_MY = "/activity/wallet/account_my";

    /**
     * 我的钱包-充值
     */
    public static final String WALLET_RECHARGE = "/activity/wallet/recharge";

    /**
     * 我的钱包-提现
     */
    public static final String WALLET_WITHDRAW = "/activity/wallet/withdraw";

    /**
     * 业务员签约绩效
     */
    public static final String REPORT_SALESMAN_SIGN_ACHIEVEMENT = "/activity/report/salesman/sign/achievement";

    /**
     * 业务员销售额绩效
     */
    public static final String REPORT_SALESMAN_SALES_ACHIEVEMENT = "/activity/report/salesman/sales/achievement";

    /**
     * 缺货汇总
     */
    public static final String REPORT_DELIVERY_LACK_GATHER = "/activity/report/delivery/lack/gather";
    /*
     * 我的钱包-银行列表
     */
    public static final String WALLET_BANK_LIST = "/activity/wallet/bankList";

    /**
     * 对账单列表
     */
    public static final String BILL_LIST = "/activity/bill/list";

    /**
     * 对账单详情
     */
    public static final String BILL_DETAIL = "/activity/bill/detail";

    /**
     * 发票管理-入口
     */
    public static final String INVOICE_ENTRY = "/activity/invoice/entry";

    /**
     * 发票管理-发票详情
     */
    public static final String INVOICE_DETAIL = "/activity/invoice/detail";

    /**
     * 发票管理-发票详情-关联订单
     */
    public static final String INVOICE_DETAIL_ORDER = "/activity/invoice/detail/order";

    /**
     * 发票管理-选择开票门店
     */
    public static final String INVOICE_SELECT_SHOP = "/activity/invoice/select/shop";

    /**
     * 发票管理-选择关联订单
     */
    public static final String INVOICE_SELECT_ORDER = "/activity/invoice/select/order";

    /**
     * 发票管理-填写发票信息
     */
    public static final String INVOICE_INPUT = "/activity/invoice/input";

    /**
     * 客户缺货汇总
     */
    public static final String REPORT_CUSTOMER_LACK_SUMMARY = "/activity/customer/lack/summary";

    /**
     * 客户缺货明细
     */
    public static final String REPORT_CUSTOMER_LACK_DETAIL = "/activity/customer/lack/detail";
}

