package com.hll_sc_app.app.aftersales.common;

import com.hll_sc_app.R;

/**
 * @author <a href="mailto:xuezhixin@hualala.com">Vixb</a>
 * @since 2019/7/8
 */
public class AfterSalesHelper {

    /**
     * 根据售后状态码获取描述文本
     *
     * @param status 售后状态码
     * @return 描述文本
     */
    public static String getRefundStatusDesc(int status) {

        String desc = "";
        switch (status) {
            case 1:
                desc = "待供应商处理";
                break;
            case 2:
                desc = "待司机提货";
                break;
            case 3:
                desc = "待仓库收货";
                break;
            case 4:
                desc = "待财务审核";
                break;
            case 5:
                desc = "完成";
                break;
            case 6:
                desc = "供应商已拒绝";
                break;
            case 7:
                desc = "已取消";
                break;
            case 8:
                desc = "受理中";
                break;
            default:
                break;
        }
        return desc;
    }

    /**
     * 获取售后类型文本
     *
     * @param type 售后类型状态码
     * @return 售后类型
     */
    public static String getRefundTypeDesc(int type) {
        String desc = "";
        switch (type) {
            case 1:
                desc = "仅退款";
                break;
            case 2:
                desc = "收货差异退款";
                break;
            case 3:
                desc = "退货退款";
                break;
            case 4:
                desc = "退押金";
                break;
            case 5:
                desc = "换货";
                break;
            default:
                break;
        }
        return desc;
    }

    /**
     * 获取售后订单标记
     *
     * @param type 类型
     */
    public static int getRefundBillFlag(int type) {
        switch (type) {
            case 1:
            case 2:
                return R.drawable.ic_after_sales_money;
            case 3:
                return R.drawable.ic_after_sales_goods;
            case 4:
                return R.drawable.ic_after_sales_deposit;
            default:
                return 0;
        }
    }

    /**
     * 根据售后类型获取类型对应标签
     *
     * @param type 售后类型状态码
     * @return 售后类型标签
     */
    public static String getRefundTypeLabel(int type) {
        switch (type) {
            case 1:
            case 2:
                return "退款";
            case 3:
                return "退货退款";
            case 4:
                return "退押金";
            case 5:
                return "换货";
        }
        return "退款";
    }

    /**
     * @param type 售后类型码
     * @return 售后信息前缀
     */
    public static String getRefundInfoPrefix(int type){
        switch (type) {
            case 1:
            case 2:
                return "退款";
            case 3:
                return "退货";
            case 4: // 退押金
                return "退押金";
            case 5: // 换货
                return "换货";
            default: // 退货退款
                return "退货";
        }
    }

    /**
     * 获取售后原因前缀文本
     *
     * @param type 售后订单类型码
     * @return 前缀文本
     */
    public static String getReasonPrefix(int type) {
        if (type == 5) {
            return "换货";
        } else if (type == 4 || type == 3) {
            return "退货";
        }
        return "退款";
    }


    /**
     * 获取取消操作人
     *
     * @param cancelRole 取消角色码
     * @return 取消操作人
     */
    public static String getCancelRoleDes(int cancelRole) {
        switch (cancelRole) {
            case 0:
                return "采购商";
            case 1:
                return "销售";
            case 2:
                return "司机";
            case 3:
                return "客服";
            default:
                return "";
        }
    }

    /**
     * 获取售后原因文本
     *
     * @param reason 售后原因码
     * @return 售后原因文本
     */
    public static String getRefundReasonDesc(int reason) {
        switch (reason) {
            case 1:
                return "供应商协商退款";
            case 2:
                return "产品质量退款";
            case 3:
                return "其他原因";
            case 4:
                return "商品质量有问题";
            case 5:
                return "商品与描述不符";
            case 6:
                return "退押金商品";
            case 7:
                return "商品外形品相差";
            case 8:
                return "商品有异物";
            case 9:
                return "商品腐烂变质";
            case 10:
                return "验货差异退款";
            default:
                return "";
        }
    }
}
