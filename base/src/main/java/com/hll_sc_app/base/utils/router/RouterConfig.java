package com.hll_sc_app.base.utils.router;

/**
 * 路由规则配置
 *
 * @author zhuyingsong
 * @date 2018/12/12
 */
public class RouterConfig {
    /**
     * 登录
     */
    public static final String USER_LOGIN = "/activity/user/login";
    /**
     * 找回密码
     */
    public static final String USER_FIND = "/activity/user/findPassword";
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
     * 提交成功
     */
    public static final String SUBMIT_SUCCESS = "/activity/submit/success";
}
