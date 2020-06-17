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
     * 税率设置
     */
    public static final String SETTING_TAX = "/activity/setting/tax";

    /**
     * 商品特殊税率设置
     */
    public static final String SETTING_TAX_SPECIAL = "/activity/setting/tax/special";

    /**
     * 税率设置-选择商品
     */
    public static final String SETTING_TAX_SELECT_GOODS = "/activity/select/goods";

    /**
     * 提醒设置
     */
    public static final String SETTING_REMIND = "/activity/setting/remind";

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
     * 集团设置
     */
    public static final String GROUP_SETTING = "/activity/setting/group";
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
     * 订单搜索
     */
    public static final String ORDER_SEARCH = "/activity/order/search";

    /**
     * 下单门店统计
     */
    public static final String ORDER_STATISTIC = "/activity/order/statistic";

    /**
     * 订单汇总搜索
     */
    public static final String ORDER_SUMMARY_SEARCH = "/activity/order/summary/search";

    /**
     * 订单汇总
     */
    public static final String ORDER_SUMMARY = "/activity/order/summary";

    /**
     * 订单汇总详情
     */
    public static final String ORDER_SUMMARY_DETAIL = "/activity/order/summary/detail";

    /**
     * 订单详情
     */
    public static final String ORDER_DETAIL = "/activity/order/detail";

    /**
     * 订单追踪
     */
    public static final String ORDER_TRACE = "/activity/order/trace";

    /**
     * 订单转单详情
     */
    public static final String ORDER_TRANSFER_DETAIL = "/activity/order/transfer/detail";

    /**
     * 订单详情-修改发货信息
     */
    public static final String ORDER_MODIFY_DELIVER = "/activity/order/modify/deliver";

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
    public static final String ORDER_COMMODITY_CHECK = "/activity/order/commodity/check";

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
     * 申请退换货-入口
     */
    public static final String AFTER_SALES_ENTRY = "/activity/afterSales/entry";

    /**
     * 申请退换货-售后申请页面
     */
    public static final String AFTER_SALES_APPLY = "/activity/afterSales/apply";

    /**
     * 申请退换货-明细选择
     */
    public static final String AFTER_SALES_SELECT = "/activity/afterSales/select";

    /**
     * 订单管理-订单详情-退换货列表
     */
    public static final String AFTER_SALES_LIST = "/activity/afterSales/list";

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
     * 客户销售汇总详情
     */
    public static final String CUSTOMER_SALE_AGGREGATION_DETAIL = "/activity/customer/aggregation/detail";

    /**
     * 退货原因统计
     */
    public static final String REFUND_REASON_STATICS = "/activity/report/refundreason";

    /**
     * 采购汇总统计
     */
    public static final String REPORT_PURCHASE_STATISTIC = "/activity/report/purchaseSummary";

    /**
     * 录入采购信息
     */
    public static final String REPORT_PURCHASE_INPUT = "/activity/report/purchase/input";

    /**
     * 生产汇总统计
     */
    public static final String REPORT_PRODUCE_STATISTIC = "/activity/report/produceSummary";

    /**
     * 生产汇总统计-查看明细
     */
    public static final String REPORT_PRODUCE_DETAILS = "/activity/report/produce/details";

    /**
     * 生产汇总统计-设置工时费
     */
    public static final String REPORT_PRODUCE_MAN_HOUR = "/activity/report/produce/manHour";

    /**
     * 生产汇总统计-录入生产数据
     */
    public static final String REPORT_PRODUCE_INPUT = "/activity/produce/input";

    /**
     * 生产汇总统计-录入具体数据
     */
    public static final String REPORT_PRODUCE_INPUT_DETAIL = "/activity/produce/input/detail";

    /**
     * 生产汇总统计-录入人效
     */
    public static final String REPORT_PRODUCE_INPUT_PEOPLE = "/activity/produce/input/people";

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
    public static final String REPORT_SALESMAN_SIGN = "/activity/report/salesman/sign";

    /**
     * 业务员销售额绩效
     */
    public static final String REPORT_SALESMAN_SALES = "/activity/report/salesman/sales";

    /**
     * 缺货差异汇总
     */
    public static final String REPORT_LACK_DIFF = "/activity/report/lack/diff";

    /**
     * 缺货商品明细表
     */
    public static final String REPORT_LACK_DETAILS = "/activity/report/lack/details";

    /*
     * 我的钱包-银行列表
     */
    public static final String WALLET_BANK_LIST = "/activity/wallet/bankList";

    /**
     * 营销中心菜单主页
     */
    public static final String ACTIVITY_MARKETING_SETTING_MENU = "/activity/marketing/setting/menu";

    /**
     * 营销中心商品营销列表
     */
    public static final String ACTIVITY_MARKETING_PRODUCT_LIST = "/activity/marketing/product/list";

    /**
     * 营销中心商品营销列表-新增
     */
    public static final String ACTIVITY_MARKETING_PRODUCT_LIST_ADD = "/activity/marketing/product/list/add";

    /**
     * 营销中心-选择商品
     */
    public static final String ACTIVITY_MARKETING_SELECT_PRODUCT = "/activity/marketing/select/product";

    /**
     * 选择区域
     */
    public static final String ACTIVITY_SELECT_AREA_PROVINCE_CITY = "/activity/select/area/province/city";

    /**
     * 选择优惠券
     */
    public static final String ACTIVITY_SELECT_COUPON_LIST = "/activity/select/coupon/list";

    /**
     * 商品营销详情
     */
    public static final String ACTIVITY_MARKETING_PRODUCT_DETAIL = "/activity/marketing/product/detail";

    /**
     * 商品营销显示所有选择的活动商品列表页
     */
    public static final String ACTIVITY_MARKETING_SELECT_PRODUCT_LIST = "/activity/marketing/select/product/list";

    /**
     * 营销优惠券列表
     */
    public static final String ACTIVITY_MARKETING_COUPON_LIST = "/activity/marketing/coupon/list";

    /**
     * 营销优惠券-新增
     */
    public static final String ACTIVITY_MARKETING_COUPON_ADD = "/activity/marketing/coupon/add";

    /**
     * 营销优惠券-查看
     */
    public static final String ACTIVITY_MARKETING_COUPON_CHECK = "/activity/marketing/coupon/check";

    /**
     * 营销优惠券-优惠券使用详情
     */
    public static final String ACTIVITY_MARKETING_COUPON_USE_DETAIL_LIST = "/activity/marketing/coupon/use/detail/list";

    /**
     * 营销优惠券-优惠券分发页面
     */
    public static final String ACTIVITY_MARKETING_COUPON_SEND = "/activity/marketing/coupon/send";

    /*营销优惠券-选择客户-集团*/
    public static final String ACTIVITY_MARKETING_COUPON_SELECT_GROUPS = "/activity/marketing/coupon/select/groups";
    /*营销优惠券-选择客户-门店*/
    public static final String ACTIVITY_MARKETING_COUPON_SELECT_SHOPS = "/activity/marketing/coupon/select/shops";

    public static final String ACTIVITY_MARKETING_CHECK_GROUPS = "/activity/marketing/check/groups";

    public static final String ACTIVITY_MARKETING_CHECK_SHOPS = "/activity/marketing/check/shops";

    public static final String BILL_LIST = "/activity/bill/list";

    /**
     * 对账单详情
     */
    public static final String BILL_DETAIL = "/activity/bill/detail";

    public static final String BILL_LOG = "/activity/bill/log";

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
     * 发票管理-发票详情-关联门店
     */
    public static final String INVOICE_DETAIL_SHOP = "/activity/invoice/detail/shop";

    /**
     * 发票管理-门店搜索
     */
    public static final String INVOICE_SEARCH = "/activity/invoice/search";

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
     * 发票管理-填写发票信息-回款记录
     */
    public static final String INVOICE_RETURN_RECORD = "/activity/invoice/returnRecord";

    /**
     * 客户缺货汇总
     */
    public static final String REPORT_CUSTOMER_LACK = "/activity/customer/lack/summary";

    /**
     * 客户缺货明细
     */
    public static final String REPORT_CUSTOMER_LACK_DETAIL = "/activity/customer/lack/detail";

    /**
     * 收货差异汇总
     */
    public static final String REPORT_RECEIVE_DIFF = "/activity/report/receive/diff";

    /**
     * 收货差异明细
     */
    public static final String REPORT_RECEIVE_DIFF_DETAILS = "/activity/report/receive/diff/details";

    /**
     * 代仓商品明细
     */
    public static final String REPORT_WAREHOUSE_PRODUCT_DETAIL = "/activity/warehouse/product/detail";

    /**
     * 配送及时率汇总数据
     */
    public static final String REPORT_DELIVERY_TIME = "/activity/report/delivery/time";

    /**
     * 配送及时率详情
     */
    public static final String REPORT_DELIVERY_TIME_DETAIL = "/activity/report/delivery/time/detail";

    /**
     * CRM-首页
     */
    public static final String CRM_HOME = "/fragment/crm/home";

    /**
     * CRM-订单
     */
    public static final String CRM_ORDER = "/fragment/crm/order";

    /**
     * CRM-客户
     */
    public static final String CRM_CUSTOMER = "/fragment/crm/customer";

    /**
     * CRM-日报
     */
    public static final String CRM_DAILY = "/fragment/crm/daily";

    /**
     * CRM-我的
     */
    public static final String CRM_MINE = "/fragment/crm/mine";

    /**
     * CRM-设置
     */
    public static final String CRM_SETTING = "/activity/crm/settings";

    /**
     * CRM-订单列表
     */
    public static final String CRM_ORDER_LIST = "/activity/crm/order/list";

    /**
     * 待退合计
     */
    public static final String REPORT_WAIT_REFUND = "/activity/report/refund/wait";

    /**
     * 待退客户明细
     */
    public static final String REPORT_WAIT_REFUND_CUSTOMER = "/activity/report/refund/wait/customer";

    /**
     * 待退商品明细
     */
    public static final String REPORT_WAIT_REFUND_PRODUCT_DETAIL = "/activity/report/refund/wait/product/detail";

    /**
     * 退货统计
     */
    public static final String REPORT_REFUND_STATISTIC = "/activity/report/refund/statistic";

    /**
     * 退货统计明细
     */
    public static final String REPORT_REFUND_STATISTIC_DETAILS = "/activity/report/refund/statistic/details";

    /**
     * 退货客户，商品汇总
     */
    public static final String REPORT_REFUND_CUSTOMER_PRODUCT = "/activity/report/refund/customerProduct";

    /**
     * 退货客户明细
     */
    public static final String REPORT_REFUNDED_CUSTOMER_DETAIL = "/activity/report/refund/customerproduct/customer";

    /**
     * 退货商品明细
     */
    public static final String REPORT_REFUNDED_PRODUCT_DETAIL = "/activity/report/refund/customerproduct/product";
    /**
     * 查看验货单-列表
     */
    public static final String INSPECTION_LIST = "/activity/inspection/list";

    /**
     * 查看验货单-详情
     */
    public static final String INSPECTION_DETAIL = "/activity/inspection/detail";

    /**
     * 客户流失率明细
     */
    public static final String REPORT_CUSTOMER_LOSS = "/activity/report/loss/customer/loss";

    /**
     * 门店流失率明细
     */
    public static final String REPORT_SHOP_LOSS = "/activity/report/loss/shop/loss";

    /**
     * 代仓发货统计
     */
    public static final String REPORT_WAREHOUSE_DELIVERY = "/activity/report/warehouse/delivery";

    /**
     * 代仓服务费
     */
    public static final String REPORT_WAREHOUSE_FEE = "/activity/report/warehouse/serviceFee";

    /**
     * 报表退货搜索
     */
    public static final String REPORT_REFUNDED_SEARCH = "/activity/report/refund/search";

    public static final String REPORT_REFUND_SEARCH_FRAGMENT = "/activity/report/refund/search/fragment";

    /**
     * 销售日报
     */
    public static final String REPORT_SALES_DAILY = "/activity/report/sales/daily";

    /**
     * 采购单查询
     */
    public static final String STOCK_PURCHASER_ORDER = "/activity/stock/purchaser/order";

    /**
     * 采购单明细查询
     */
    public static final String STOCK_PURCHASER_ORDER_DETAIL = "/activity/stock/purchaser/order/detail";

    /**
     * 采购单搜索
     */
    public static final String STOCK_PURCHASER_ORDER_SEARCH = "/activity/stock/purchaser/order/search";

    /*库存管理首页*/
    public static final String ACTIVITY_STOCK_MANAGE_MENU = "/activity/stock/mange/menu";

    /*仓库查询 管理*/
    public static final String ACTIVITY_DEPOT = "/activity/depot";

    /*仓库新增编辑*/
    public static final String ACTIVITY_DEPOT_EDIT = "/activity/depot/add";

    /*仓库详情*/
    public static final String ACTIVITY_DEPOT_DETAIL = "/activity/depot/detail";

    /*仓库库存分类*/
    public static final String ACTIVITY_DEPOT_CATEGORY = "/activity/depot/category";

    /*仓库-库存查询*/
    public static final String ACTIVITY_STOCK_QUERY_LIST = "/activity/stock/query/list";

    /*仓库-库存日志查询*/
    public static final String ACTIVITY_STOCK_LOG_QUERY = "/activity/stock/log/query";

    /*仓库-客户发货仓库管理*/
    public static final String ACTIVITY_STOCK_CUSTOMER_SEND = "/activity/stock/customer/send";

    /*仓库-仓库库存校验设置*/
    public static final String ACTIVITY_STOCK_CHECK_SETTING = "/activity/stock/check/setting";
    /*仓库-库存校验选择商品*/
    public static final String ACTIVITY_STOCK_CHECK_SELECT_PRODUCT = "/activity/stock/check/select/product";
    /*投诉管理页面*/
    public static final String ACTIVITY_COMPLAIN_MANAGE_LIST = "/activity/complain/manage/list";

    /*选择员工部门*/
    public static final String ACTIVITY_SELECT_DEPARTMENT_LIST = "/activity/staff/select/department/list";

    /**
     * 代客下单-选择商品
     */
    public static final String ORDER_PLACE_SELECT_GOODS = "/activity/order/place/select/goods";

    /**
     * 代客下单-代下单确认
     */
    public static final String ORDER_PLACE_CONFIRM = "/activity/order/place/confirm";

    /**
     * 代客下单-代下单确认-添加备注
     */
    public static final String ORDER_PLACE_CONFIRM_REMARK = "/activity/order/place/confirm/remark";

    /**
     * 代客下单-代下单确认-商品列表
     */
    public static final String ORDER_PLACE_DETAILS = "/activity/order/place/details";

    /**
     * 代客下单-代下单确认-提交成功
     */
    public static final String ORDER_PLACE_COMMIT = "/activity/order/place/commit";

    /*投诉详情*/
    public static final String ACTIVITY_COMPLAIN_DETAIL="/activity/complain/detail";

    /**
     * 投诉协商历史
     */
    public static final String ACTIVITY_COMPLAIN_HISTORY="/activity/complain/history";

    /*投诉的内部记录*/
    public static final String ACTIVITY_COMPLAIN_INNER_LOG="/activity/complain/inner/log";

    /*投诉管理-发起投诉回复*/
    public static final String ACTIVITY_COMPLAIN_SEND_REPLY = "/activity/complain/send/reply";

    /*投诉管理-发起投诉回复结果页*/
    public static final String ACTIVITY_COMPLAIN_SEND_RESULT = "/activity/complain/send/result";

    /*投诉管理-新增*/
    public static final String ACTIVITY_COMPLAIN_ADD = "/activity/complain/add";

    /*选择订单号列表*/
    public static final String ACTIVITY_ORDER_LIST_NUMBER = "/activity/order/list/number";

    /*选择采购商集团*/
    public static final String ACTIVITY_SELECT_PURCHASER_LIST = "/activity/select/purchaser/list";

    /**
     * CRM-我的-配送路线
     */
    public static final String DELIVERY_ROUTE = "/activity/delivery/route";

    /**
     * CRM-我的-配送路线-路线详情
     */
    public static final String DELIVERY_ROUTE_DETAIL = "/activity/delivery/route/detail";

    /**
     * CRM-我的-销售排名
     */
    public static final String CRM_RANK = "/activity/rank";

    /*选择投诉管理选择商品*/
    public static final String ACTIVITY_COMPLAIN_SELECT_PRODUCT_LIST = "/activity/complain/select/product/list";

    /*意见反馈的选项页面*/
    public static final String ACTIVITY_FEED_BACK_COMPLAIN="/activity/feedback/complain";

    /*向平台投诉列表页面*/
    public static final String ACTIVITY_PLATFORM_COMPLAIN_LIST="/activity/platform/complain/list";

    /**
     * 运营分析
     */
    public static final String OPERATION_ANALYSIS = "/activity/operationanalysis";

    /*客户收货查询*/
    public static final String ACTIVITY_QUERY_CUSTOM_RECEIVE = "/activity/report/query/custom/receive";

    /*客户收货查询详情*/
    public static final String ACTIVITY_QUERY_CUSTOM_RECEIVE_DETAIL = "/activity/report/query/custom/receive/detail";

    /**
     * 商品需求-入口
     */
    public static final String GOODS_DEMAND_ENTRY = "/activity/goods/demand/entry";

    /**
     * 商品需求
     */
    public static final String GOODS_DEMAND = "/activity/goods/demand";

    /**
     * 商品需求-新增
     */
    public static final String GOODS_DEMAND_ADD = "/activity/goods/demand/add";

    /**
     * 商品需求-提交
     */
    public static final String GOODS_DEMAND_COMMIT = "/activity/goods/demand/commit";

    /**
     * 商品需求-详情
     */
    public static final String GOODS_DEMAND_DETAIL = "/activity/goods/demand/detail";

    /**
     * 商品需求-商品选择
     */
    public static final String GOODS_DEMAND_SELECT = "/activity/goods/demand/select";

    /**
     * 商品需求-新增-采购商搜索
     */
    public static final String GOODS_DEMAND_PURCHASER_SEARCH = "/activity/goods/demand/purchaser/search";

    /**
     * 市场价格
     */
    public static final String PRICE = "/activity/price";

    /**
     * 意见反馈列表
     */
    public static final String ACTIVITY_FEED_BACK_LIST="/activity/feedback/list";
    
    /**
     * 采购模板
     */
    public static final String PURCHASE_TEMPLATE = "/activity/purchase/template";

    /**
     * 卡管理列表页面
     */
    public static  final String ACTIVITY_CARD_MANAGE_LIST ="/activity/card/manage/list";

    /**
     * 卡管理详情
     */
    public static  final String ACTIVITY_CARD_MANAGE_DETAIL ="/activity/card/manage/detail";

    /**
     * 卡管理添加选择合作采购商
     */
    public static  final String ACTIVITY_CARD_MANAGE_ADD_SELECT_PURCHASER ="/activity/card/manage/add/select/purchaser";

    /**
     * 卡管理添加新建卡
     */
    public static final String ACTIVITY_CARD_MANAGE_ADD_CARD = "/activity/card/manage/add/card/";

    /**
     * 卡管理添加充值
     */
    public static final String ACTIVITY_CARD_MANAGE_RECHARGE = "/activity/card/manage/recharge/";

    /**
     * 卡管理-卡日志
     */
    public static final String ACTIVITY_CARD_MANAGE_LOG = "/activity/card/manage/log/";

    /**
     * 用户信息
     */
    public static final String INFO = "/activity/info";

    /**
     * 修改用户信息
     */
    public static final String INFO_MODIFY = "/activity/info/modify";

    /**
     * 用户信息-门头照
     */
    public static final String INFO_DOORWAY = "/activity/info/doorway";

    /**
     * 用户信息-营业执照
     */
    public static final String INFO_LICENSE = "/activity/info/license";

    /**
     * 用户信息-其他证照
     */
    public static final String INFO_OTHER = "/activity/info/other";

    /**
     * 卡管理-卡交易日志
     */
    public static final String ACTIVITY_CARD_MANAGE_TRANSACTION_LIST = "/activity/card/manage/transaction/list";

    /**
     * 卡管理-卡交易日志详情
     */
    public static final String ACTIVITY_CARD_MANAGE_TRANSACTION_LIST_DETAIL = "/activity/card/manage/transaction/list/detail";


    /**
     * 用户信息-邀请码
     */
    public static final String INFO_INVITE_CODE = "/activity/invite/code";

    /**
     * 商品特殊需求
     */
    public static final String GOODS_SPECIAL_DEMAND_ENTRY = "/activity/goods/demand/special/entry";

    /**
     * 商品特殊需求-特定客户
     */
    public static final String GOODS_SPECIAL_DEMAND_LIST = "/activity/goods/demand/special/list";

    /**
     * 商品特殊需求-需求详情
     */
    public static final String GOODS_SPECIAL_DEMAND_DETAIL = "/activity/goods/demand/special/detail";

    /**
     * 私有商城
     */
    public static final String PRIVATE_MALL = "/activity/mall/private";

    /**
     * 日报-列表
     */
    public static final String CRM_DAILY_LIST = "/activity/daily/list";

    /**
     * 日报-详情
     */
    public static final String CRM_DAILY_DETAIL = "/activity/daily/detail";

    /**
     * 日报-编辑
     */
    public static final String CRM_DAILY_EDIT = "/activity/daily/edit";

    /**
     * 客户-新增
     */
    public static final String CRM_CUSTOMER_ADD = "/activity/customer/add";

    /**
     * 消息中心 - 首页
     */
    public static final String MESSAGE = "/activity/message";

    /**
     * 消息中心-详情
     */
    public static final String MESSAGE_DETAIL = "/activity/message/detail";

    /**
     * 消息中心-通知
     */
    public static final String MESSAGE_NOTICE = "/activity/message/notice";

    /**
     * 消息中心-im
     */
    public static final String MESSAGE_CHAT = "/activity/message/chat";

    /**
     * 客户-意向客户-新增
     */
    public static final String CRM_CUSTOMER_INTENT_ADD = "/activity/customer/intent/add";

    /**
     * 客户-意向客户
     */
    public static final String CRM_CUSTOMER_INTENT = "/activity/customer/intent";

    /**
     * 客户-意向客户-详情
     */
    public static final String CRM_CUSTOMER_INTENT_DETAIL = "/activity/customer/intent/detail";

    /**
     * 客户-拜访记录
     */
    public static final String CRM_CUSTOMER_RECORD = "/activity/customer/record";

    /**
     * 客户-拜访记录-新增
     */
    public static final String CRM_CUSTOMER_RECORD_ADD = "/activity/customer/record/add";

    /**
     * 客户-拜访记录-详情
     */
    public static final String CRM_CUSTOMER_RECORD_DETAIL = "/activity/customer/record/detail";

    /**
     * 客户-拜访计划
     */
    public static final String CRM_CUSTOMER_PLAN = "/activity/customer/plan";

    /**
     * 客户-拜访计划-新增
     */
    public static final String CRM_CUSTOMER_PLAN_ADD = "/activity/customer/plan/add";

    /**
     * 客户-拜访计划-详情
     */
    public static final String CRM_CUSTOMER_PLAN_DETAIL = "/activity/customer/plan/detail";

    /**
     * 客户-客户公海
     */
    public static final String CRM_CUSTOMER_SEAS = "/activity/customer/seas";

    /**
     * 客户-客户公海-客户详情
     */
    public static final String CRM_CUSTOMER_SEAS_DETAIL = "/activity/customer/seas/detail";

    /**
     * 客户-客户公海-分配销售
     */
    public static final String CRM_CUSTOMER_SEAS_ALLOT = "/activity/customer/seas/allot";

    /**
     * 客户-合作客户
     */
    public static final String CRM_CUSTOMER_PARTNER = "/activity/customer/partner";

    /**
     * 客户-合作客户-详情
     */
    public static final String CRM_CUSTOMER_PARTNER_DETAIL = "/activity/customer/partner/detail";

    /**
     * 客户-拜访记录-搜索
     */
    public static final String CRM_CUSTOMER_SEARCH = "/activity/customer/search";

    /**
     * 客户-拜访记录-搜索计划
     */
    public static final String CRM_CUSTOMER_SEARCH_PLAN = "/activity/customer/search/plan";


    /**
     * 定向售卖列表
     */
    public static final String BLACK_LIST = "/activity/black/list";
    /**
     * 定向售卖详情
     */
    public static final String BLACK_DETAIL = "/activity/black/detail";
    /**
     * 定向售卖合作采购商
     */
    public static final String BLACK_COOPERATION_PURCHASER = "/activity/black/cooperation/purchaser";
    /**
     * 定向售卖合作门店
     */
    public static final String BLACK_COOPERATION_SHOP = "/activity/black/cooperation/shop";
    /**
     * 定向售卖商品
     */
    public static final String BLACK_PRODUCT = "/activity/black/product";

    /**
     * 新建意见反馈
     */
    public static final String ACTIVITY_FEED_BACK_ADD = "/activity/feed/back/add";

    /**
     * 意见反馈详情
     */
    public static final String ACTIVITY_FEED_BACK_DETAIL = "/activity/feed/back/detail";

    /**
     * 客户毛利统计表
     */
    public static final String REPORT_PROFIT_CUSTOMER = "/activity/report/profit/customer";

    /**
     * 门店毛利统计表
     */
    public static final String REPORT_PROFIT_SHOP = "/activity/report/profit/shop";

    /**
     * 品类毛利统计表
     */
    public static final String REPORT_PROFIT_CATEGORY = "/activity/report/profit/category";

    /**
     * 客户应收账款
     */
    public static final String REPORT_CREDIT = "/activity/report/credit";

    /**
     * 客户应收账款明细表
     */
    public static final String REPORT_CREDIT_DETAILS_CUSTOMER = "/activity/report/credit/details/customer";

    /**
     * 日应收账款汇总表
     */
    public static final String REPORT_CREDIT_DETAILS_DAILY = "/activity/report/credit/details/daily";

    /**
     * 员工列表-关联门店
     */
    public static final String STAFF_LIST_LINK_SHOP = "/activity/staff/list/link/shop";
    /**
     * 员工列表-关联门店_选择接收人
     */
    public static final String STAFF_LIST_LINK_SHOP_SALE_LIST = "/activity/staff/list/link/shop/sale/list";

    /**
     * 合同管理列表
     */
    public static final String ACTIVITY_CONTRACT_MANAGE_LIST = "/activity/contract/manage/list";

    /**
     * 合同管理新增
     */
    public static final String ACTIVITY_CONTRACT_MANAGE_ADD = "/activity/contract/manage/add";


    public static final String ACTIVITY_CONTRACT_MANAGE_ADD_SELECT_PURCHASER = "/activity/contract/manage/add/select/purchase";

    public static final String ACTIVITY_CONTRACT_MANAGE_DETAIL =  "/activity/contract/manage/detail";


    public static final String ACTIVITY_CONTRACT_SEARCH = "/activity/contract/search";

    /**
     * 客户收货查询
     */
    public static final String REPORT_CUSTOMER_RECEIVE = "/activity/report/customer/receive";

    /**
     * 客户结算查询
     */
    public static final String REPORT_CUSTOMER_SETTLE = "/activity/report/customer/settle";

    /**
     * 客户结算详情
     */
    public static final String REPORT_CUSTOMER_SETTLE_DETAIL = "/activity/report/customer/settle/detail";

    public static final String REPORT_CUSTOMER_SETTLE_SEARCH = "/activity/report/customer/settle/search";

    public static final String ACTIVITY_SELECT_EMPLOY_LIST = "/activity/select/employ/list";


    public static final String ACTIVITY_WAREHOUSE_PAY_LIST = "/activity/warehouse/pay/list";

    public static final String ACTIVITY_LINK_CONTRACT_LIST = "/activity/contract/link/list";

    public static final String ACTIVITY_CONTRACT_SELECT_MAIN_CONTRACT = "/activity/contract/select/main/contract";


    public static final String ACTIVITY_CONTRACT_MOUNT = "/activity/contract/mount";

    public static final String ACTIVITY_SELECT_PRODUCT_OWNER = "/activity/select/product/owner";



    /**
     * 我的钱包 实名认证主页面
     */
    public static final String ACTIVITY_WALLET_AUTHEN_ACCOUNT = "/activity/wallet/authen/account";


    public static final String ACTIVTY_WALLET_CREATE_ACCOUNT = "/activity/wallet/create/account";

    /**
     * 快捷导出
     */
    public static final String ACTIVITY_EXPORT = "/activity/export";

    /**
     * 简易文本列表
     */
    public static final String SIMPLE_LIST = "/activity/simple/list";
}

