package com.hll_sc_app.base.utils.router;

import android.content.Context;
import android.text.TextUtils;

import com.hll_sc_app.base.R;
import com.hll_sc_app.base.greendao.GreenDaoUtils;
import com.hll_sc_app.base.utils.UserConfig;
import com.hll_sc_app.citymall.util.LogUtil;

import java.util.HashMap;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/12/2
 */

public class RightConfig {
    private static final HashMap<String, Integer> RIGHT_MAP = new HashMap<>();

    static {
        // 商品详情
        RIGHT_MAP.put(RouterConfig.ROOT_HOME_GOODS_DETAIL, R.string.right_productManagement_query);
        // 商品置顶
        RIGHT_MAP.put(RouterConfig.GOODS_STICK_MANAGE, R.string.right_productManagement_productStick);
        // 代仓商品库存预警
        RIGHT_MAP.put(RouterConfig.GOODS_INVENTORY_WARNING, R.string.right_stock_warn_query);
        // 订单详情
        RIGHT_MAP.put(RouterConfig.ORDER_DETAIL, R.string.right_orderManagement_query);
        RIGHT_MAP.put(RouterConfig.ORDER_TRANSFER_DETAIL, R.string.right_orderManagement_query);
        // 订单验货
        RIGHT_MAP.put(RouterConfig.ORDER_INSPECTION, R.string.right_orderManagement_examine);
        // 订单拒收 （订单拒收和销售代退为一个页面，目前只核验订单拒收，在权限判断方法中处理）
        RIGHT_MAP.put(RouterConfig.AFTER_SALES_APPLY, R.string.right_orderManagement_rejection);
        // 立即收款
        RIGHT_MAP.put(RouterConfig.ORDER_SETTLEMENT, R.string.right_orderManagement_gathering);
        // 修改发货信息
        RIGHT_MAP.put(RouterConfig.ORDER_MODIFY_DELIVER, R.string.right_orderManagement_editQuantity);
        // 协议价新增
        RIGHT_MAP.put(RouterConfig.MINE_AGREEMENT_PRICE_QUOTATION_ADD, R.string.right_agreementPriceManagement_create);
        // 协议价查看
        RIGHT_MAP.put(RouterConfig.MINE_AGREEMENT_PRICE_DETAIL, R.string.right_agreementPriceManagement_query);
        // 合作采购商列表
        RIGHT_MAP.put(RouterConfig.COOPERATION_PURCHASER_DETAIL, R.string.right_workingMeal_query);
        // 合作采购商详情
        RIGHT_MAP.put(RouterConfig.COOPERATION_PURCHASER_DETAIL_DETAILS, R.string.right_workingMeal_queryDetailInfo);
        // 合作采购商门店详情
        RIGHT_MAP.put(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_DETAIL, R.string.right_workingMeal_queryShopInfo);
        // 我收到的合作申请
        RIGHT_MAP.put(RouterConfig.COOPERATION_PURCHASER_APPLICATION, R.string.right_workingMeal_update);
        // 我发出的合作申请
        RIGHT_MAP.put(RouterConfig.COOPERATION_PURCHASER_MY_APPLICATION, R.string.right_workingMeal_update);
        // 新增合作采购商
        RIGHT_MAP.put(RouterConfig.COOPERATION_PURCHASER_ADD, R.string.right_workingMeal_create);
        // 结算方式
        RIGHT_MAP.put(RouterConfig.COOPERATION_PURCHASER_DETAIL_SHOP_SETTLEMENT, R.string.right_settlementMethod_creat);
        // 店铺信息编辑
        RIGHT_MAP.put(RouterConfig.SUPPLIER_SHOP, R.string.right_storeManagement_update);
        // 账号管理
        RIGHT_MAP.put(RouterConfig.SETTING_ACCOUNT, R.string.right_accountManagement);
        // 更改集团绑定手机号
        RIGHT_MAP.put(RouterConfig.ACTIVITY_CHANGE_GROUP_PHONE, R.string.right_accountManagement_updateBindAccount);
        // 修改登录密码
        RIGHT_MAP.put(RouterConfig.USER_CHANGE, R.string.right_accountManagement_updateLoginPass);
        // 解绑当前集团
        RIGHT_MAP.put(RouterConfig.ACTIVITY_UNBIND_GROUP, R.string.right_accountManagement_unbindChildAccount);
        // 供应商信息编辑
        RIGHT_MAP.put(RouterConfig.INFO_MODIFY, R.string.right_supplierInfoManagement_update);
        RIGHT_MAP.put(RouterConfig.INFO_DOORWAY, R.string.right_supplierInfoManagement_update);
        RIGHT_MAP.put(RouterConfig.INFO_LICENSE, R.string.right_supplierInfoManagement_update);
        RIGHT_MAP.put(RouterConfig.INFO_OTHER, R.string.right_supplierInfoManagement_update);
        // 充值
        RIGHT_MAP.put(RouterConfig.WALLET_RECHARGE, R.string.right_accountStatement_recharge);
        // 提现
        RIGHT_MAP.put(RouterConfig.WALLET_WITHDRAW, R.string.right_accountStatement_cash);
        // 明细
        RIGHT_MAP.put(RouterConfig.WALLET_DETAILS_LIST, R.string.right_accountStatement_detail);
        // 钱包账号
        RIGHT_MAP.put(RouterConfig.WALLET_ACCOUNT_MY, R.string.right_accountStatement_accountInfo);
        // 开通钱包账号
        RIGHT_MAP.put(RouterConfig.WALLET_ACCOUNT_CREATE, R.string.right_accountStatement_apply);
        // 商品特殊税率设置
        RIGHT_MAP.put(RouterConfig.SETTING_TAX_SPECIAL, R.string.right_taxSetting_product);
        // 新增特殊设置商品
        RIGHT_MAP.put(RouterConfig.SETTING_TAX_SELECT_GOODS, R.string.right_taxSetting_product_add);
        // 协议价比例设置新增
        RIGHT_MAP.put(RouterConfig.SETTING_PRICE_RATIO_ADD, R.string.right_negotiatepriceSetting_add);
        // 定向售卖编辑
        RIGHT_MAP.put(RouterConfig.ORIENTATION_DETAIL, R.string.right_targetedSale_modify);
        // 提醒设置
        RIGHT_MAP.put(RouterConfig.SETTING_REMIND, R.string.right_bussinesSetting_billMaxNumberAlert);
        // 退货审核详情
        RIGHT_MAP.put(RouterConfig.AFTER_SALES_DETAIL, R.string.right_returnedPurchaseCheck_queryDetailInfo);
        // 仓库管理
        RIGHT_MAP.put(RouterConfig.ACTIVITY_DEPOT, R.string.right_warehouseManagement_query);
        // 库存查询
        RIGHT_MAP.put(RouterConfig.ACTIVITY_STOCK_QUERY_LIST, R.string.right_inventoryInquiry_query);
        // 新增库存校验商品
        RIGHT_MAP.put(RouterConfig.ACTIVITY_STOCK_CHECK_SELECT_PRODUCT, R.string.right_verify_creat);
        // 库存日志查询
        RIGHT_MAP.put(RouterConfig.ACTIVITY_STOCK_LOG_QUERY, R.string.right_inventoryIog_query);
        // 客户发货仓库管理
        RIGHT_MAP.put(RouterConfig.ACTIVITY_STOCK_CUSTOMER_SEND, R.string.right_clientWarehouseManagement_query);
        // 代仓客户新增
        RIGHT_MAP.put(RouterConfig.WAREHOUSE_ADD, R.string.right_corporateWarehouse_create);
        // 我收到的代仓申请
        RIGHT_MAP.put(RouterConfig.WAREHOUSE_APPLICATION, R.string.right_corporateWarehouse_queryNewApply);
        // 我发出的代仓申请
        RIGHT_MAP.put(RouterConfig.WAREHOUSE_INVITE, R.string.right_corporateWarehouse_myApply);
        // 查看代仓客户信息
        RIGHT_MAP.put(RouterConfig.WAREHOUSE_DETAILS, R.string.right_corporateWarehouse_queryInfo);
        // 新建意见反馈
        RIGHT_MAP.put(RouterConfig.ACTIVITY_FEED_BACK_ADD,R.string.right_feedback_add);
        // 意见反馈详情
        RIGHT_MAP.put(RouterConfig.ACTIVITY_FEED_BACK_DETAIL,R.string.right_feedback_detail);
    }

    /**
     * 获取对应页面的权限代码
     *
     * @param path 页面路径
     * @return 权限代码
     */
    static String getRightCode(Context context, String path) {
        if (!UserConfig.crm() && RIGHT_MAP.get(path) != null)
            return context.getString(RIGHT_MAP.get(path));
        return null;
    }

    /**
     * 检验是否有权限
     *
     * @param rightCode 权限代码
     * @return true-有权限
     */
    public static boolean checkRight(String rightCode) {
        if (!TextUtils.isEmpty(rightCode)) {
            LogUtil.d("right", "当前权限是：" + rightCode);
        }
        if (!TextUtils.isEmpty(rightCode) && UserConfig.isLogin()) {
            return GreenDaoUtils.containsAuth(rightCode);
        }
        return true;
    }
}
