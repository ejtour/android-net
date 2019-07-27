package com.hll_sc_app.base.utils.router;

/**
 * 路由规则配置
 *
 * @author zhuyingsong
 * @date 2018/12/12
 */
public class RouterConfig {
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
     * 协议
     */
    public static final String WEB_VIEW_PROTOCOL = "/activity/webView/Protocol";
    /**
     * 注册
     */
    public static final String USER_REGISTER = "/activity/user/register";
    /**
     * 注册-完善资料
     */
    public static final String USER_REGISTER_COMPLEMENT = "/activity/user/register/complement";
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
     * 订单管理-搜索
     */
    public static final String ORDER_SEARCH = "/activity/order/search";

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
}
