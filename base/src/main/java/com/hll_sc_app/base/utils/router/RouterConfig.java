package com.hll_sc_app.base.utils.router;

/**
 * 路由规则配置
 *
 * @author zhuyingsong
 * @date 2018/12/12
 */
public class RouterConfig {
    /**
     * 首页
     */
    public static final String ROOT_HOME = "/activity/home/main";
    /**
     * 消息中心
     */
    public static final String ROOT_MESSAGE = "/activity/message";
    /**
     * 消息中心-通知详情
     */
    public static final String ROOT_MESSAGE_NOTICE_DETAIL = "/activity/message/noticeDetail";
    /**
     * 消息中心-聊天
     */
    public static final String ROOT_MESSAGE_CHAT = "/activity/message/chat";
    /**
     * 消息中心
     */
    public static final String ROOT_MESSAGE_DETAIL = "/activity/message/detail";
    /**
     * 扫码界面
     */
    public static final String ROOT_HOME_SCAN = "/activity/home/main/scan";
    /**
     * 上次定过
     */
    public static final String ROOT_HOME_LAST_ORDER = "/activity/home/main/lastOrder";
    /**
     * 联盟板块
     */
    public static final String ROOT_HOME_UNION = "/activity/home/main/union";
    /**
     * 自营大厅
     */
    public static final String ROOT_HOME_SELF_HALL = "/activity/home/main/selfHall";
    /**
     * 首页-首页-Fragment
     */
    public static final String ROOT_HOME_MAIN = "/fragment/home/main";
    /**
     * 订单确认
     */
    public static final String ORDER_CONFIRM = "/activity/order/confirm";

    /**
     * 订单确认-添加备注
     */
    public static final String ORDER_CONFIRM_REMARK = "/activity/order/confirm/remark";

    /**
     * 提交订单-商品数量异常
     */
    public static final String ORDER_CONFIRM_UNUSUAL = "/activity/order/confirm/unusual";

    /**
     * 订单确认-商品列表
     */
    public static final String ORDER_CONFIRM_PRODUCT_LIST = "/activity/order/confirm/productList";

    /**
     * 订单提交成功页面
     */
    public static final String ORDER_COMMIT_SUCCESS = "/activity/order/commitSuccess";

    /**
     * 首页-分类-Fragment
     */
    public static final String ROOT_HOME_CATEGORY = "/fragment/home/category";

    /**
     * 首页-购物车-Fragment
     */
    public static final String ROOT_HOME_CART = "/fragment/home/cart";

    /**
     * 首页-购物车-Activity
     */
    public static final String ROOT_HOME_ACTIVITY_CART = "/activity/home/cart";

    /**
     * 首页-用户中心-Fragment
     */
    public static final String ROOT_HOME_MINE = "/fragment/home/mine";


    /**
     * login
     */
    public static final String USER_LOGIN = "/activity/user/login";
    /**
     * 找回密码
     */
    public static final String USER_FIND_PWD = "/activity/user/findPWD";
    /**
     * 用户中心-采购模板
     */
    public static final String USER_PURCHASE = "/activity/user/purchase";
    /**
     * 用户中心-协议价管理
     */
    public static final String USER_PRICE_MANAGER = "/activity/user/priceManager";
    /**
     * 用户中心-合作供应商
     */
    public static final String USER_SUPPLIER = "/activity/user/supplier";
    /**
     * 用户中心-合作供应商详情
     */
    public static final String USER_SUPPLIER_DETAIL = "/activity/user/supplier/detail";
    /**
     * 用户中心-添加合作供应商输入验证信息界面
     */
    public static final String USER_SUPPLIER_ADD_VERIFICATION = "/activity/user/supplier/add/verification";
    /**
     * 用户中心-合作供应商详情-详细信息
     */
    public static final String USER_SUPPLIER_DETAIL_INFO = "/activity/user/supplier/detail/info";
    /**
     * 用户中心-合作供应商详情-新增门店
     */
    public static final String USER_SUPPLIER_DETAIL_ADD_SHOP = "/activity/user/supplier/detail/addShop";
    /**
     * 用户中心-合作供应商-我收到的申请
     */
    public static final String USER_SUPPLIER_INVITE = "/activity/user/supplier/invite";
    /**
     * 用户中心-合作供应商-我发出的申请
     */
    public static final String USER_SUPPLIER_APPLY = "/activity/user/supplier/apply";
    /**
     * 用户中心-合作供应商-新添加搜索页面
     */
    public static final String USER_SUPPLIER_NEW_SIGN = "/activity/user/supplier/newSign";
    /**
     * 用户中心-代仓管理
     */
    public static final String USER_WAREHOUSE_MANAGER = "/activity/user/warehouseManager";
    /**
     * 用户中心-代仓管理启动页面
     */
    public static final String USER_WAREHOUSE_MANAGER_START = "/activity/user/warehouseManager/start";
    /**
     * 用户中心-代仓管理介绍页
     */
    public static final String USER_WAREHOUSE_INTRODUCE = "/activity/user/warehouseIntroduce";
    /**
     * 用户中心-代仓管理-推荐代仓公司
     */
    public static final String USER_WAREHOUSE_MANAGER_RECOMMEND = "/activity/user/warehouseManager/recommend";
    /**
     * 用户中心-代仓管理-代仓商品管理
     */
    public static final String USER_WAREHOUSE_MANAGER_PRODUCT = "/activity/user/warehouseManager/product";
    /**
     * 用户中心-代仓管理-我的申请
     */
    public static final String USER_WAREHOUSE_MANAGER_MY_APPLY = "/activity/user/warehouseManager/myApply";
    /**
     * 用户中心-代仓管理-库存查询
     */
    public static final String USER_WAREHOUSE_MANAGER_STOCK_QUERY = "/activity/user/warehouseManager/stockQuery";
    /**
     * 用户中心-代仓管理-库存日期查询
     */
    public static final String USER_WAREHOUSE_MANAGER_STOCK_LOGCAT_QUERY = "/activity/user/warehouseManager/stockLogcatQuery";
    /**
     * 用户中心-代仓管理-新公司申请
     */
    public static final String USER_WAREHOUSE_MANAGER_INVITE = "/activity/user/warehouseManager/invite";
    /**
     * 用户中心-代仓管理-我的代仓公司
     */
    public static final String USER_WAREHOUSE_MANAGER_MY_WAREHOUSE = "/activity/user/warehouseManager/myWarehouse";
    /**
     * 用户中心-代仓管理-新签代仓
     */
    public static final String USER_WAREHOUSE_MANAGER_NEW_SIGN = "/activity/user/warehouseManager/newSign";
    /**
     * 用户中心-代仓管理-代仓公司详情
     */
    public static final String USER_WAREHOUSE_MANAGER_DETAIL = "/activity/user/warehouseManager/detail";
    /**
     * 用户中心-代仓管理-我的代仓公司-代仓公司详情
     */
    public static final String USER_WAREHOUSE_MANAGER_DETAIL_MY = "/activity/user/warehouseManager/myDetail";
    /**
     * 用户中心-代仓管理-我的代仓公司-代仓公司详情-门店详情
     */
    public static final String USER_WAREHOUSE_MANAGER_DETAIL_MY_SHOP = "/activity/user/warehouseManager/myDetail/shopDetail";
    /**
     * 用户中心-代仓管理-我的代仓公司-代仓公司详情-管理代仓门店
     */
    public static final String USER_WAREHOUSE_MANAGER_DETAIL_MANAGER_SHOP = "/activity/user/warehouseManager/detail/managerShop";
    /**
     * 用户中心-代仓管理-代仓公司详情-代仓门店列表
     */
    public static final String USER_WAREHOUSE_MANAGER_DETAIL_SHOP = "/activity/user/warehouseManager/detail/shop";
    /**
     * 用户中心-代仓管理-选择代仓门店
     */
    public static final String USER_WAREHOUSE_MANAGER_SELECT_SHOP = "/activity/user/warehouseManager/selectShop";
    /**
     * 用户中心-代仓管理-新公司申请-选择代仓门店
     */
    public static final String USER_WAREHOUSE_MANAGER_INVITE_SHOP = "/activity/user/warehouseManager/inviteShop";
    /**
     * 用户中心-协议价管理-报价单
     */
    public static final String USER_PRICE_MANAGER_QUOTATION = "/fragment/user/priceManager/quotation";
    /**
     * 用户中心-协议价管理-报价单详情
     */
    public static final String USER_PRICE_MANAGER_QUOTATION_DETAIL = "/activity/user/priceManager/quotation/detail";
    /**
     * 用户中心-协议价管理-商品详情
     */
    public static final String USER_PRICE_MANAGER_GOODS_DETAIL = "/activity/user/priceManager/goods/detail";
    /**
     * 用户中心-协议价管理-商品
     */
    public static final String USER_PRICE_MANAGER_GOODS = "/fragment/user/priceManager/goods";
    /**
     * 用户中心-协议价管理-搜索
     */
    public static final String USER_PRICE_MANAGER_SEARCH = "/activity/user/priceManager/search";
    /**
     * 用户中心-我的收藏
     */
    public static final String USER_COLLECT = "/activity/user/collect";
    /**
     * 用户中心-我的收藏-搜索
     */
    public static final String USER_COLLECT_SEARCH = "/activity/user/collect/search";
    /**
     * 用户中心-我的收藏-供应商
     */
    public static final String USER_COLLECT_SUPPLIER = "/fragment/user/collect/supplier";
    /**
     * 用户中心-我的合作供应商-详情-基本信息
     */
    public static final String USER_SUPPLIER_DETAIL_BASIC = "/fragment/user/supplier/detail/basic";
    /**
     * 用户中心-我的合作供应商-详情-认证信息
     */
    public static final String USER_SUPPLIER_DETAIL_CERTIFIED = "/fragment/user/supplier/detail/certified";
    /**
     * 用户中心-我的收藏-商品
     */
    public static final String USER_COLLECT_PRODUCT = "/fragment/user/collect/product";
    /**
     * 用户中心-新品需求
     */
    public static final String USER_DEMAND = "/activity/user/demand";
    /**
     * 用户中心-新品需求-详情
     */
    public static final String USER_DEMAND_DETAIL = "/activity/user/demand/detail";
    /**
     * 用户中心-新品需求-新增
     */
    public static final String USER_DEMAND_ADD = "/activity/user/demand/add";
    /**
     * 用户中心-新品需求-新增-下一步
     */
    public static final String USER_DEMAND_ADD_NEXT = "/activity/user/demand/add/next";
    /**
     * 用户中心-新品需求-新增-选择供应商
     */
    public static final String USER_DEMAND_ADD_SELECT_SUPPLIER = "/activity/user/demand/selectSupplier";
    /**
     * 用户中心-新品需求-列表
     */
    public static final String USER_DEMAND_LIST = "/activity/user/demand/list";
    /**
     * 用户中心-门店管理
     */
    public static final String USER_SHOP = "/activity/user/shop";
    /**
     * 用户中心-员工管理
     */
    public static final String USER_STAFF = "/activity/user/staff";
    /**
     * 用户中心-员工管理
     */
    public static final String USER_STAFF_EDIT = "/activity/user/staff/edit";
    /**
     * 用户中心-员工管理-选择门店
     */
    public static final String USER_STAFF_EDIT_SHOP = "/activity/user/staff/edit/shop";
    /**
     * 用户中心-员工管理-选择岗位
     */
    public static final String USER_STAFF_EDIT_ROLE = "/activity/user/staff/edit/role";
    /**
     * 用户中心-员工管理-查看岗位权限
     */
    public static final String USER_STAFF_ROLE_PERMISSION = "/activity/user/staff/role/permission";
    /**
     * 用户中心-员工管理-搜索
     */
    public static final String USER_STAFF_SEARCH = "/activity/user/staff/search";
    /**
     * 用户中心-门店管理-编辑门店
     */
    public static final String USER_SHOP_EDIT = "/activity/user/shop/edit";
    /**
     * 用户中心-门店管理-地图
     */
    public static final String USER_SHOP_MAP = "/activity/user/shop/edit/map";
    /**
     * 用户中心-门店管理-编辑门店-选择员工
     */
    public static final String USER_SHOP_EDIT_EMPLOYEE = "/activity/user/shop/edit/employee";
    /**
     * 用户中心-门店管理-搜索
     */
    public static final String USER_SHOP_SEARCH = "/activity/user/shop/search";
    /**
     * 用户中心-采购模板-搜索
     */
    public static final String USER_PURCHASE_SEARCH = "/activity/user/purchase/search";
    /**
     * 用户中心-采购模板-编辑
     */
    public static final String USER_PURCHASE_EDIT = "/activity/user/purchase/edit";

    /**
     * 分类跳转到商品列表
     */
    public static final String CATEGORY_PRODUCT_LIST = "/activity/category/productList";
    /**
     * 订单首页
     */
    public static final String ORDER_MAIN_SCENE = "/activity/order/main";

    /**
     * 订单详情
     */
    public static final String ORDER_DETAIL_MAIN_SCENE = "/activity/order/main/detail";

    /**
     * 订单拒收
     */
    public static final String ORDER_ACTION_REJECT = "/activity/order/info/reject";

    /**
     * 上传验货单
     */
    public static final String ORDER_UPLOAD_INSPECTION = "/activity/order/upload/inspection";

    /**
     * 支付页面
     */
    public static final String ORDER_PAY = "/activity/order/pay";

    /**
     * 验货单
     */
    public static final String ORDER_ACTION_CONFIRM = "/activity/order/info/confirm";

    /**
     * 商品详情页
     */
    public static final String ACTIVITY_PRODUCT_DETAIL = "/activity/product/productDetail";

    /**
     * 补单提交成功页面
     */
    public static final String ORDER_ACTION_ADD_COMMIT_SUCCESS = "/activity/order/info/add/commitSuccess";

    /**
     * 补单商品确认页
     */
    public static final String ORDER_ACTION_ADD_COMMODITY_CONFIRM = "/activity/order/info/add/confirm";

    /**
     * 售后订单列表页
     */
    public static final String AFTER_SALES_ORDER_LIST = "/activity/afterSales/order/list";

    /**
     * 售后详情展示页
     */
    public static final String AFTER_SALES_DETAIL_SHOW = "/activity/afterSales/details/show";

    /**
     * 售后入口页
     */
    public static final String AFTER_SALES_ENTRY = "/activity/afterSales/entry";

    /**
     * 售后操作页
     */
    public static final String AFTER_SALES_OPERATION = "/activity/afterSales/operation";

    /**
     * 售后明细选择页
     */
    public static final String AFTER_SALES_DETAILS_LIST = "/activity/afterSales/details/select";

    /**
     * 售后协商历史页
     */
    public static final String AFTER_SALES_NEGOTIATION_HISTORY = "/activity/afterSales/negotiationHistory";

    /**
     * 用户中心-代仓管理-退货审核
     */
    public static final String AFTER_SALES_CHECK = "/activity/afterSales/check";

    /**
     * 订单搜索页
     */
    public static final String ORDER_SEARCH_ACTIVITY = "/activity/order/search";

    /**
     * 押金商品详情页
     */
    public static final String ACTIVITY_PRODUCT_DEPOSIT_DETAIL = "/activity/product/depositProductDetail";

    /*
     * 店铺首页*/
    public static final String ACTIVITY_SHOP_CENTER = "/activity/shopCenter";

    /*
     * 搜索页面*/
    public static final String ACTIVITY_SEARCH_PAGER = "/activity/searchPage";

    /*
     * 搜索结果页面*/
    public static final String ACTIVITY_SEARCH_RESULT_PAGER = "/activity/search/resultPage";


    /*
     * 设置页面*/
    public static final String ACTIVITY_SETTING = "/activity/setting";

    /**
     * 采购模板设置
     */
    public static final String ACTIVITY_PURCHASING_TEMPLATE_SETTING = "/activity/purchasingTemplate/setting";

    /**
     * 合作供应商设置
     */
    public static final String ACTIVITY_COOPER_SUPPLYER_SETTING = "/activity/cooper/supplyer/setting";

    /**
     * 意见反馈
     */
    public static final String ACTIVITY_FEEDBACK = "/activity/feedback/setting";

    /**
     * 新建意见反馈
     */
    public static final String ACTIVITY_FEEDBACK_ADD = "/activity/feedback/setting/add";

    /**
     * 查看意见反馈
     */
    public static final String ACTIVITY_FEEDBACK_CHECK = "/activity/feedback/setting/check";

    /**
     * 帮助中心
     */
    public static final String ACTIVITY_HELP_CENTER = "/activity/help/center";
    /**
     * H5支付
     */
    public static final String ACTIVITY_H5_PAY = "/activity/h5/pay";

    /**
     * 账号管理
     */
    public static final String ACTIVITY_ACCOUNT_MANAGER = "/activity/account/manager";

    /**
     * 更改集团绑定账号
     */
    public static final String ACTIVITY_CHANGE_GROUP_PHONE = "/activity/change/group/phone";

    /**
     * 修改密码
     */
    public static final String ACTIVITY_CHANGE_PWD = "/activity/change/group/pwd";

    /**
     * 解绑集团
     */
    public static final String ACTIVITY_UNBIND_GROUP = "/activity/unbind/group";

    /**
     * 押金管理列表
     */
    public static final String ACTIVITY_DEPOSIT_MANAGER = "/activity/deposit/manager";

    /**
     * 退押金
     */
    public static final String ACTIVITY_RETURN_DEPOSIT = "/activity/deposit/manager/return";

    /**
     * 提交成功页面
     */
    public static final String ACTIVITY_SUBMIT_SUCCESS = "/activity/submit/success";

    /**
     * 押金管理的历史记录
     */
    public static final String ACTIVITY_DEPOSIT_MANAGE_HISTORY = "/activity/deposit/manager//history";

    /**
     * 押金管理的历史记录详情
     */
    public static final String ACTIVITY_DEPOSIT_MANAGE_HISTOR_DETAIL = "/activity/deposit/manager//history/detail";

    /**
     * 对账单
     */
    public static final String ACTIVITY_BILL = "/activity/mine/bill/";

    /**
     * 对账单详情
     */
    public static final String ACTIVITY_BILL_DETAIL = "/activity/mine/bill/detail";

    /**
     * 对账单详情pdf
     */
    public static final String ACTIVITY_BILL_DETAIL_PDF = "/activity/mine/bill/detail/pdf";

    /**
     * 市场价格
     */
    public static final String ACTIVITY_MARKET_PRICE = "/activity/market/price";

    /**
     * 订单补单-商品列表
     */
    public static final String ACTIVITY_PRODUCT_LIST_FOR_ORDER = "/activity/product/list/order";

    /**
     * 注册
     */
    public static final String ACTIVITY_USER_REGISTER = "/activity/user/register";

    /**
     * 公共的  显示协议文本的webview
     */
    public static final String ACTIVITY_WEBVIEW_PROTOCOL = "/activity/webview/protocol";

    /**
     * 集团信息页面
     */
    public static final String ACTIVITY_GROUP_INFO = "/activity/group/info";
    /**
     * 修改集团基本信息页面
     */
    public static final String ACTIVITY_CHANGE_GROUP_INFO = "/activity/change/group/info";

    /**
     * 集团信息-门头照
     */
    public static final String ACTIVITY_GROUP_INFO_FRONT_IMG = "/activity/change/group/info/frontimg";

    /**
     * 集团信息-营业执照
     */
    public static final String ACTIVITY_GROUP_INFO_BUSINESS_LICENSE = "/activity/change/group/info/business/license";

    /**
     * 集团信息-其他证照
     */
    public static final String ACTIVITY_GROUP_INFO_OTHER_LICENSE = "/activity/change/group/info/other/license";

    /**
     * 设置-自动收货设置
     */
    public static final String ACTIVITY_SETTING_AUTO_RECEIVE = "/activity/setting/auto/receive";

    /**
     * 设置-关联供应链集团
     */
    public static final String ACTIVITY_SETTING_ASSOCIATE_SUPPLYER_GROUP = "/activity/setting/associate/supplyer/group";

    /**
     * 设置-反馈投诉
     */
    public static final String ACTIVITY_FEED_BACK_COMPLAIN = "/activity/setting/feedback/complain";
    /**
     * 设置-平台投诉
     */
    public static final String ACTIVITY_PLATFORM_COMPLAIN = "/activity/setting/platform/complain";
    /**
     * 设置-平台投诉查看详情
     */
    public static final String ACTIVITY_PLATFORM_COMPLAIN_CHECK = "/activity/setting/platform/complain/check";

    /**
     * 平台协商历史
     */
    public static final String ACTIVITY_PLATFORM_COMPLAIN_HISTORY = "/activity/setting/platform/complain/history";

    /**
     * 平台新增投诉
     */
    public static final String ACTIVITY_PLATFORM_COMPLAIN_ADD = "/activity/setting/platform/complain/add";
    /**
     * 平台新增投诉成功
     */
    public static final String ACTIVITY_PLATFORM_COMPLAIN_ADD_SUCCESS = "/activity/setting/platform/complain/add/success";

    /**
     * 向供应商投诉-选择商品
     */
    public static final String ACTIVITY_PLATFORM_COMPLAIN_ADD_SELECT_PRODUCT = "/activity/setting/platform/complain/add/selectproduct";

    /**
     * 我的优惠券
     */
    public static final String ACTIVITY_MY_DISCOUNT_LIST = "/activity/discount/unused";
    /**
     * 我的优惠券历史记录
     */
    public static final String ACTIVITY_DISCOUNT_HISTORY_LIST = "/activity/discount/history";

    /**
     * 我的钱包入口页面
     */
    public static final String ACTIVITY_WALLET = "/activity/wallet/enter";

    /**
     * 我的钱包 开通账号
     */
    public static final String ACTIVITY_WALLET_OPEN_ACCOUNT = "/activity/wallet/open/account";

    /**
     * 我的钱包 开通账号 协议页面
     */
    public static final String ACTIVITY_WALLET_OPEN_ACCOUNT_PROTOCOL = "/activity/wallet/open/account/protocol";

    /**
     * 我的钱包 我的账号
     */
    public static final String ACTIVITY_WALLET_MY_ACCOUNT = "/activity/wallet/my/account";

    /**
     * 我的钱包 充值
     */
    public static final String ACTIVITY_WALLET_RECHARGE = "/activity/wallet/recharge";

    /**
     * 我的钱包 提现
     */
    public static final String ACTIVITY_WALLET_WITHDRAW = "/activity/wallet/withdraw";

    /**
     * 我的钱包 绑定账号
     */
    public static final String ACTIVITY_WALLET_BIND_ACCOUNT = "/activity/wallet/bind/account";

    /**
     * 我的钱包 实名认证主页面
     */
    public static final String ACTIVITY_WALLET_AUTHEN_ACCOUNT = "/activity/wallet/authen/account";

    /**
     * 我的钱包 银行列表
     */
    public static final String ACTIVITY_WALLET_BANK_LIST = "/activity/wallet/bank/list";


    /**
     * 我的钱包 明细
     */
    public static final String ACTIVITY_WALLET_DETAILS_LIST = "/activity/wallet/details/list";

    /**
     * 我的钱包 明细详情
     */
    public static final String ACTIVITY_WALLET_DETAILS_SHOW = "/activity/wallet/details/show";


    /**
     * 简单搜索
     */
    public static final String ACTIVITY_SIMPLE_SEARCH = "/activity/simple/search";
}
